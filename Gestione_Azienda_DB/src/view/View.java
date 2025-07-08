package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

import Utils.Ruolo;
import exceptions.EtaNonValidaException;
import exceptions.RuoloNonValidoException;
import exceptions.StipendioNonValidoException;
import model.Persona;
import model.Dipendente;
import model.Manager;
import Utils.UtilsView;

public class View {
    private final Scanner input;

    public View() {
        this.input = new Scanner(System.in);
    }

    public int leggiValore(String s) {
        int val = 0;
        boolean flag;
        do {
            System.out.print(s + " ");
            flag = false;
            try {
                val = Integer.parseInt(this.input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Non hai fornito un numero!");
                flag = true;
            }
        } while (flag);
        return val;
    }

    public String leggiString(String s) {
        System.out.print(s + " ");
        return this.input.nextLine();
    }

    public Ruolo leggiRuolo(String messaggio, boolean opzionale) {
        boolean flag = false;
        Ruolo ruolo = null;

        do {
            System.out.print(messaggio + " (");
            Ruolo[] ruoli = Ruolo.values();
            for (int i = 0; i < ruoli.length; i++) {
                System.out.print(ruoli[i]);
                if (i < ruoli.length - 1) System.out.print(", ");
            }
            System.out.print("): ");

            String inputRuolo = this.input.nextLine().replaceAll("\\s+", "").toUpperCase();

            if (opzionale && inputRuolo.isEmpty()) {
                return null;
            }

            try {
                ruolo = UtilsView.parseRuolo(inputRuolo);
                flag = true;
            } catch (RuoloNonValidoException e) {
                System.out.println(e.getMessage());
            }

        } while (!flag);

        return ruolo;
    }

    public Ruolo leggiRuolo(String s) {
        return leggiRuolo(s, false);
    }

    public Ruolo leggiNuovoRuolo(String s) {
        return leggiRuolo(s, true);
    }

    private double leggiStipendioConValidazione(String messaggio, double min, double max, double stipendioAttuale, boolean isUpdate) {
        if (isUpdate) {
            try {
                double newStipendio = this.leggiValore(messaggio + " (tra " + min + " e " + max + ") [lascia 0 per mantenere " + stipendioAttuale + "]:");
                if (newStipendio == 0) {
                    return stipendioAttuale;
                }
                if (newStipendio >= min && newStipendio <= max) {
                    return newStipendio;
                } else {
                    mess("Errore: stipendio fuori dal range valido (" + min + " - " + max + ").");
                    return stipendioAttuale;
                }
            } catch (StipendioNonValidoException e) {
                mess("Errore: inserire un numero valido.");
                return stipendioAttuale;
            }
        } else {
            return leggiStipendio(messaggio, min, max);
        }
    }

    public double leggiStipendio(String messaggio, double min, double max) {
        double stipendio;
        boolean valido = false;
        do {
            stipendio = leggiValore(messaggio + " (tra " + min + " e " + max + "):");
            if (stipendio >= min && stipendio <= max) {
                valido = true;
            } else {
                mess("Errore: lo stipendio deve essere compreso tra " + min + " e " + max + ".");
            }
        } while (!valido);
        return stipendio;
    }

    private LocalDate leggiDataNascita(String messaggio, int etaMinima) {
        LocalDate data = null;
        while (data == null) {
            data = this.leggiData(this.leggiString(messaggio));
            if (data != null) {
                try {
                    UtilsView.validaEta(data, etaMinima);
                } catch (EtaNonValidaException e) {
                    this.mess("Errore: " + e.getMessage());
                    data = null;
                }
            } else {
                this.mess("Riprova.");
            }
        }
        return data;
    }

    private LocalDate leggiDataAssunzione(String messaggio, LocalDate birthDate, int etaMinima, LocalDate dataAttuale) {
        LocalDate dataAssunzione = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (dataAssunzione == null) {
            String input = (dataAttuale != null)
                    ? leggiString(messaggio + " [lascia vuoto per mantenere \"" +  formatter.format(dataAttuale) + "\"]:")
                    : leggiString(messaggio + ":");

            if (dataAttuale != null && input.trim().isEmpty()) {
                return dataAttuale;
            }

            dataAssunzione = this.leggiData(input);
            if (dataAssunzione != null) {
                try {
                    UtilsView.validaDataAssunzione(birthDate, dataAssunzione, etaMinima);
                } catch (EtaNonValidaException e) {
                    this.mess("Errore: " + e.getMessage());
                    dataAssunzione = null;
                }
            } else {
                mess("Riprova.");
            }
        }
        return dataAssunzione;
    }

    private void compilaFormPersonaBase(Persona p, int etaMinima) {
        p.setNome(this.leggiString("Inserisci il nome:"));
        p.setCognome(this.leggiString("Inserisci il cognome:"));

        LocalDate data = leggiDataNascita("Inserisci la data di nascita (formato: dd/MM/yyyy):", etaMinima);
        p.setBirthDate(data);

        p.setCf(this.leggiString("Inserisci il codice fiscale:"));
    }

    public void formInsert(Persona p) {
        compilaFormPersonaBase(p, 16);
    }

    public void formInsert(Dipendente d) {
        compilaFormPersonaBase(d, 16);
        d.setStipendio(leggiStipendio("Inserisci stipendio", 1200, 1500));

        LocalDate dataAssunzione = leggiDataAssunzione(
                "Inserisci data di assunzione (formato: dd/MM/yyyy)",
                d.getBirthDate(),
                16,
                null
        );
        d.setDataDiAssunzione(dataAssunzione);
    }

    public void formInsert(Manager m) {
        try {
            compilaFormPersonaBase(m, 18);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("età non valida")) {
                throw e;
            }
        }

        LocalDate dataAssunzione = leggiDataAssunzione(
                "Inserisci data di assunzione (formato: dd/MM/yyyy)",
                m.getBirthDate(),
                18,
                null
        );
        m.setDataDiAssunzione(dataAssunzione);

        m.setStipendio(leggiStipendio("Inserisci stipendio", 1500, 2200));
        m.setRuolo(leggiRuolo("Inserisci ruolo:"));
    }

    public void stampaPersona(Persona p) {
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Persona non presente");
        }
    }

    public void stampaPersona(Collection<Persona> col) {
        if (col != null && !col.isEmpty()) {
            for (Persona p : col) {
                System.out.println(p);
            }
        } else {
            System.out.println("Lista vuota!");
        }
    }

    public LocalDate leggiData(String input) {
        if (input != null && !input.trim().isEmpty()) {
            try {
                LocalDate data = UtilsView.parseData(input);
                if (UtilsView.dataTroppoVecchia(data)) {
                    this.mess("Errore: la data è troppo vecchia (più di 140 anni fa).");
                    return null;
                } else {
                    return data;
                }
            } catch (DateTimeParseException e) {
                this.mess("Errore: formato data non valido o data inesistente.");
                return null;
            }
        }
        return null;
    }

    private Persona aggiornaPersonaBase(Persona p) {
        System.out.println("Aggiornamento dati per: " + p.getNome() + " " + p.getCognome());

        Persona copia = new Persona(p.getNome(), p.getCognome(), p.getBirthDate(), p.getCf());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String newNome = this.leggiString("Inserisci il nuovo nome [lascia vuoto per mantenere \"" + copia.getNome() + "\"]:").trim();
        if (!newNome.isEmpty()) copia.setNome(newNome);

        String newCognome = this.leggiString("Inserisci il nuovo cognome [lascia vuoto per mantenere \"" + copia.getCognome() + "\"]:").trim();
        if (!newCognome.isEmpty()) copia.setCognome(newCognome);

        LocalDate newData = null;
        while (newData == null) {
            String dataInput = this.leggiString("Inserisci la nuova data di nascita (formato: dd/MM/yyyy) [lascia vuoto per mantenere \"" + formatter.format(copia.getBirthDate()) + "\"]:").trim();
            if (dataInput.isEmpty()) {
                newData = copia.getBirthDate();
            } else {
                newData = this.leggiData(dataInput);
                if (newData == null) this.mess("Riprova.");
            }
        }
        copia.setBirthDate(newData);

        String newCf = this.leggiString("Inserisci il nuovo codice fiscale [lascia vuoto per mantenere \"" + copia.getCf() + "\"]:").trim();
        if (!newCf.isEmpty()) copia.setCf(newCf);

        return copia;
    }

    public Persona formUpdate(Persona p) {
        return aggiornaPersonaBase(p);
    }

    public Dipendente formUpdate(Dipendente d) {
        Persona baseAggiornata = aggiornaPersonaBase(d);
        Dipendente copia = new Dipendente(
                baseAggiornata.getNome(),
                baseAggiornata.getCognome(),
                baseAggiornata.getBirthDate(),
                baseAggiornata.getCf(),
                d.getStipendio(),
                d.getDataDiAssunzione()
        );

        double nuovoStipendio = leggiStipendioConValidazione(
                "Inserisci il nuovo stipendio",
                1200,
                1500,
                copia.getStipendio(),
                true
        );
        copia.setStipendio(nuovoStipendio);

        LocalDate nuovaDataAssunzione = leggiDataAssunzione(
                "Inserisci nuova data di assunzione (formato: dd/MM/yyyy)",
                copia.getBirthDate(),
                16,
                d.getDataDiAssunzione()
        );
        copia.setDataDiAssunzione(nuovaDataAssunzione);

        return copia;
    }

    public Manager formUpdate(Manager m) {
        Persona pAggiornato = aggiornaPersonaBase(m);

        double stipendioFinale = leggiStipendioConValidazione(
                "Inserisci il nuovo stipendio",
                1500,
                2200,
                m.getStipendio(),
                true
        );

        LocalDate nuovaDataAssunzione = leggiDataAssunzione(
                "Inserisci nuova data di assunzione (formato: dd/MM/yyyy)",
                pAggiornato.getBirthDate(),
                18,
                m.getDataDiAssunzione()
        );

        Ruolo nuovoRuolo = this.leggiNuovoRuolo("Inserisci il nuovo ruolo [lascia vuoto per mantenere \"" + m.getRuolo() + "\"]");
        Ruolo ruoloFinale = (nuovoRuolo != null) ? nuovoRuolo : m.getRuolo();

        return new Manager(
                pAggiornato.getNome(),
                pAggiornato.getCognome(),
                pAggiornato.getBirthDate(),
                pAggiornato.getCf(),
                stipendioFinale,
                nuovaDataAssunzione,
                ruoloFinale
        );
    }

    public Dipendente formPromozione(Dipendente d) {
        mess("Inserimento dati per il nuovo Dipendente");

        double stipendio = leggiStipendio("Inserisci stipendio", 1200, 1500);
        LocalDate dataAssunzione = leggiDataAssunzione(
                "Inserisci data di assunzione (formato: dd/MM/yyyy)",
                d.getBirthDate(),
                16,
                null
        );

        d.setStipendio(stipendio);
        d.setDataDiAssunzione(dataAssunzione);

        return d;
    }

    public Manager formPromozione(Manager m) {
        mess("Inserimento dati per il nuovo Manager");

        double stipendio = leggiStipendio("Inserisci stipendio", 1500, 2200);
        LocalDate dataAssunzione = leggiDataAssunzione(
                "Inserisci data di assunzione (formato: dd/MM/yyyy)",
                m.getBirthDate(),
                18,
                null
        );
        Ruolo ruolo = leggiRuolo("Inserisci il ruolo del manager");

        m.setStipendio(stipendio);
        m.setDataDiAssunzione(dataAssunzione);
        m.setRuolo(ruolo);

        return m;
    }


    public Persona promuoviPersona (Persona p){
        if (p instanceof Manager) {
            mess("La persona è già un manager. Nessuna promozione disponibile (QUANTI SOLDI VUOI).");
            return p;
        }

        LocalDate birthDate = p.getBirthDate();
        try {
            if (p instanceof Dipendente) {
                mess("Promozione da Dipendente a Manager");

                UtilsView.validaEta(birthDate, 18);
                Dipendente d = (Dipendente) p;

                Manager nuovoManager = new Manager(
                        d.getNome(),
                        d.getCognome(),
                        d.getBirthDate(),
                        d.getCf(),
                        d.getStipendio(),
                        d.getDataDiAssunzione(),
                        Ruolo.TOPMANAGER
                );

                nuovoManager = formPromozione(nuovoManager);
                mostraRiepilogoModifiche(p, nuovoManager);

                return confermaModifiche() ? nuovoManager : p;
            }

            mess("Promozione da Persona a (1) Dipendente (2) Manager");
            int scelta = leggiValore("Inserisci la scelta (1 o 2)");

            switch (scelta) {
                case 1 -> {
                    UtilsView.validaEta(birthDate, 16);

                    Dipendente d = new Dipendente(
                            p.getNome(),
                            p.getCognome(),
                            p.getBirthDate(),
                            p.getCf(),
                            0,
                            LocalDate.now()
                    );
                    formPromozione(d);
                    mostraRiepilogoModifiche(p, d);
                    return confermaModifiche() ? d : p;
                }
                case 2 -> {
                    UtilsView.validaEta(birthDate, 18);

                    Manager nuovoManager = new Manager(
                            p.getNome(),
                            p.getCognome(),
                            p.getBirthDate(),
                            p.getCf(),
                            0,
                            LocalDate.now(),
                            Ruolo.TOPMANAGER
                    );
                    formPromozione(nuovoManager);
                    mostraRiepilogoModifiche(p, nuovoManager);
                    return confermaModifiche() ? nuovoManager : p;
                }
                default -> {
                    mess("Scelta non valida. Nessuna promozione eseguita.");
                    return p;
                }
            }
        } catch (EtaNonValidaException e) {
            mess("Errore: " + e.getMessage());
            return p;
        }
    }


    public void mostraRiepilogoModifiche (Persona personaOriginale, Persona personaModificata){
        mess("\nRIEPILOGO MODIFICHE");
        mess("DATI ATTUALI:");
        stampaPersona(personaOriginale);
        mess("\nNUOVI DATI:");
        stampaPersona(personaModificata);
    }

    public boolean confermaModifiche () {
        return leggiString("Confermare le modifiche (s/n)").equalsIgnoreCase("s");
    }

    public int menu () {
        System.out.println("\n========== GESTIONE PERSONA ==========\n");

        System.out.println("📥 INSERIMENTO:");
        System.out.println("  1) Inserisci Persona");
        System.out.println("  2) Inserisci Dipendente");
        System.out.println("  3) Inserisci Manager");

        System.out.println("\n📄 VISUALIZZAZIONE:");
        System.out.println("  4) Stampa Tutti");
        System.out.println("  5) Ricerca per Codice Fiscale");
        System.out.println("  6) Ricerca per ID");

        System.out.println("\n🛠️  GESTIONE DATI:");
        System.out.println("  7) Elimina per ID");
        System.out.println("  8) Modifica Persona");
        System.out.println("  9) Promozione Ruolo per ID");

        System.out.println("\n❌  0) ESCI");

        return this.leggiValore("\n➡️  Fai una scelta: ");
    }


    public void mess (String s){
        System.out.println(s);
    }
}