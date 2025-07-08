package view;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import exceptions.AgeException;
import exceptions.DataDiAssunzioneException;
import exceptions.Ruolo;
import model.Dipendente;
import model.Manager;
import model.Persona;

public class View {


    private Scanner input =new Scanner(System.in);

    public int leggiValore(String s) throws NumberFormatException
    {
        int val=0;
        boolean flag;

        do {
            System.out.println(s);
            flag=false;
            try {
                val=Integer.parseInt(input.nextLine());

            } catch (NumberFormatException e) {
                //e.printStackTrace();
                System.out.println("Non hai fornito un numero!");
                flag=true;

            }catch (Exception e) {
                // TODO: handle exception
            }

        } while (flag);

        return val;
    }

    public String leggiString(String s)
    {
        System.out.println(s);
        return input.nextLine();

    }

    public Ruolo checkRuolo(String s) {
        Ruolo ruolo = null;
        boolean flag = false;

        do {

            try {
                ruolo = Ruolo.valueOf(s);
                flag = true;
            } catch (IllegalArgumentException e) {
                mess("Ruolo non valido. Riprova.");
                s = leggiString("Inserisci un ruolo valido (TOPMANAGER, PROJECTMANAGER, TEAMLEADER):").toUpperCase().replaceAll(" ","");

            }

        } while (!flag);

        return ruolo;
    }

    public LocalDate checkDataAssunzione(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dataAssunzione = LocalDate.parse(input, formatter);
            LocalDate oggi = LocalDate.now();

            if (dataAssunzione.isBefore(oggi)) {
                throw new DataDiAssunzioneException("La data di assunzione non può essere precedente a quella di oggi!");
            }

            return dataAssunzione;

        } catch (DataDiAssunzioneException e) {
            mess(e.getMessage());
        }

        return null;
    }

    public void formInsert(Persona p) //dobbiamo dirgli che tipo di istanza è p(dipendente, manager)
    {
        //prima mettiamo gli attributi comuni a tutti
        p.setNome(leggiString("Inserisci il nome:"));
        p.setCognome(leggiString("Inserisci il cognome:"));

        LocalDate data = null;

        while (data == null) {
            String input = leggiString("Inserisci la data di nascita (formato: dd-MM-yyyy):");

            if (input == null || input.trim().isEmpty()) {
                mess("Input vuoto. Riprova.");
                continue;
            }

            data = checkDataDiNascita(input);

            if (data == null) {
                mess("Riprova.");
                continue;
            }

            if (!checkAge(data, 16)) {
                mess("Età inferiore a 16 anni. Riprova.");
                data = null;
                continue;
            }

            if (p instanceof Dipendente && !checkAge(data, 18)) {
                mess("I dipendenti devono avere almeno 18 anni. Riprova.");
                data = null;
            }
        }

        p.setBirthDate(data);

        p.setCf(leggiString("Inserisci il codice fiscale:"));

        //partiamo da quello più specifico con gli instance of,e poi si va a quello più generico(come i try/catch). Manager è contenuta in dipendenti con Manager extends Dipendente
        /*if(p instanceof Manager) {
        	Manager m = (Manager) p;
            m.setRuolo(leggiString("Inserisci il ruolo:"));
        } else
        if(p instanceof Dipendente) {
        	Dipendente d = (Dipendente) p; // facciamo il cast da p generica a dipendente
        	d.setStipendio(leggiValore("Inserisci il stipendio:"));
            d.setDataAssunzione(checkDataDiNascita(leggiString("Inserisci data di assunzione:")));
        }*/

        if (p instanceof Manager) {
            Manager m = (Manager) p;
            // se mette solo setRuolo() poi non mi fa mettere stipendo e data di assunzione,non entra nel else if.Quindi ho dovuto ripetere stipendio e data.Soluzione doppio if
            m.setStipendio(checkStipendio (m,null));

            LocalDate dataAssunzione = null;
            do {
                String input = leggiString("Inserisci la data di assunzione (formato: dd-MM-yyyy):");
                dataAssunzione = checkDataAssunzione(input);
            } while (dataAssunzione == null);

            m.setDataAssunzione(dataAssunzione);

            m.setRuolo(checkRuolo(leggiString("Inserisci il ruolo (TOPMANAGER, PROJECTMANAGER, TEAMLEADER):").toUpperCase().replaceAll(" ","")));


        } else if (p instanceof Dipendente) {
            Dipendente d = (Dipendente) p;
            d.setStipendio(checkStipendio (d,null));
            d.setDataAssunzione(checkDataAssunzione(leggiString("Inserisci data di assunzione:")));
        }

    }

    public void stampaPersona(Persona p) {
        System.out.println(p);
    }


    public void stampaPersona(HashMap<Integer, Persona> map) {
        if (!map.isEmpty()) {
            for (Map.Entry<Integer, Persona> entry : map.entrySet()) {
                System.out.println("ID: " + entry.getKey() + " - " + entry.getValue());
            }
        } else {
            System.out.println("Lista vuota!");
        }
    }


    public LocalDate checkDataDiNascita(String input) {
        if (input == null || input.trim().isEmpty()) return null; //facciamo cosi perche altrimenti mostra un messaggio di errore nella sostituzione
        try {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate data = LocalDate.parse(input,formatter); // formato: dd-MM-yyyy

            //LocalDate data = LocalDate.parse(input); // formato: yyyy-MM-dd
            LocalDate oggi = LocalDate.now();
            LocalDate limiteMin = oggi.minusYears(140);

            if (data.isAfter(oggi)) {
                mess("Errore: la data di nascita � nel futuro.");
                return null;
            }

            if (data.isBefore(limiteMin)) {
                mess("Errore: la data di nascita è troppo vecchia (più di 140 anni fa).");
                return null;
            }

            return data;

        } catch (DateTimeParseException e) {
            mess("Errore: formato data non valido o data inesistente.");
            return null;
        }
    }

    public boolean checkAge(LocalDate dataNascita,int etaMinima) {
        boolean flag = true;

        try {
            LocalDate oggi = LocalDate.now();  //prendo la data di oggi
            LocalDate dataLimite = oggi.minusYears(etaMinima); //sottraggo 16 anni

            if (dataNascita.isAfter(dataLimite)) { // Se la data di nascita è dopo la data limite, la persona NON ha 16 anni
                throw new AgeException("L'età non può essere inferiore ai " + etaMinima + " anni");
            }

            flag = true;
            return flag;

        } catch (AgeException a) {
            a.printStackTrace();
            flag = false;
            return flag;
        }
    }

    public double checkStipendio(Persona p, String input) {
        double stipendio = 0;
        boolean flag = false;

        do {
            try {
                // Se input è null o vuoto, chiedi all'utente
                if (input == null || input.isEmpty()) {
                    input = leggiString("Inserisci stipendio:");
                }
                stipendio = Double.parseDouble(input);

                if (p instanceof Manager) {
                    if (stipendio >= 1500 && stipendio <= 2000) {
                        flag = true;
                    } else {
                        mess("Lo stipendio per un manager deve essere compreso tra 1500 e 2000. Riprova.");
                        input = null; // forza il ciclo per chiedere di nuovo
                    }
                } else if (p instanceof Dipendente) {
                    if (stipendio >= 1300 && stipendio <= 1500) {
                        flag = true;
                    } else {
                        mess("Lo stipendio per un dipendente deve essere compreso tra 1300 e 1500. Riprova.");
                        input = null; // forza il ciclo
                    }
                } else {
                    mess("Tipo di persona non riconosciuto.");
                    break;
                }

            } catch (NumberFormatException e) {
                mess("Input non valido. Inserisci un numero valido.");
                input = null; // forza il ciclo
            }
        } while (!flag);

        return stipendio;
    }







    public Persona formUpdate(Persona pOld, Persona pNew) {
        // --- Attributi comuni ---
        String nome = leggiString("nome [" + pOld.getNome() + "]:");
        pNew.setNome(nome.isEmpty() ? pOld.getNome() : nome);

        String cognome = leggiString("cognome [" + pOld.getCognome() + "]:");
        pNew.setCognome(cognome.isEmpty() ? pOld.getCognome() : cognome);

        String inputData = leggiString("Data di nascita [" + pOld.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "]:");

        if (inputData.trim().isEmpty()) {
            pNew.setBirthDate(pOld.getBirthDate());
        } else {
            LocalDate data = null;
            boolean valida = false;

            while (!valida) {
                data = checkDataDiNascita(inputData);
                if (data == null) {
                    mess("Data non valida.");
                } else if (!checkAge(data, 16)) {
                    mess("Età inferiore a 16 anni.");
                } else if (pNew instanceof Dipendente && !checkAge(data, 18)) {
                    mess("I dipendenti devono avere almeno 18 anni.");
                } else {
                    valida = true;
                    break;
                }

                inputData = leggiString("Inserisci data di nascita (formato: dd-MM-yyyy):");
                if (inputData.trim().isEmpty()) {
                    data = pOld.getBirthDate(); // fallback se lascia vuoto
                    break;
                }
            }

            pNew.setBirthDate(data);
        }

        String cf = leggiString("Codice fiscale [" + pOld.getCf() + "]:");
        pNew.setCf(cf.isEmpty() ? pOld.getCf() : cf);

        // --- Attributi specifici ---
        if (pOld instanceof Manager && pNew instanceof Manager) {
            Manager Mold = (Manager) pOld;
            Manager Mnew = (Manager) pNew;

            // Ruolo
            Ruolo ruolo = null;
            while (ruolo == null) {
                String input = leggiString("Ruolo [" + Mold.getRuolo() + "]:");
                if (input.isEmpty()) {
                    ruolo = Mold.getRuolo();
                } else {
                    try {
                        ruolo = Ruolo.valueOf(input.toUpperCase().replace(" ", ""));
                    } catch (IllegalArgumentException e) {
                        mess("Ruolo non valido. Riprova (TOPMANAGER, PROJECTMANAGER, TEAMLEADER).");
                    }
                }
            }
            Mnew.setRuolo(ruolo);

            String stipendioStr = leggiString("Stipendio [" + Mold.getStipendio() + "]:");
            if (stipendioStr.isEmpty()) {
                Mnew.setStipendio(Mold.getStipendio());
            } else {
                // Passi il valore inserito come parametro, se è valido il ciclo non si ripete
                double stipendioValido = checkStipendio(Mnew, stipendioStr);
                Mnew.setStipendio(stipendioValido);
            }

            String dataAssStr = leggiString("Data assunzione [" + Mold.getDataAssunzione().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "]:");
            if (dataAssStr.isEmpty()) {
                Mnew.setDataAssunzione(Mold.getDataAssunzione());
            } else {
                LocalDate dataAss = checkDataAssunzione(dataAssStr);
                while (dataAss == null) {
                    mess("Data non valida. Riprova.");
                    dataAssStr = leggiString("Inserisci data assunzione (formato: dd-MM-yyyy):");
                    if (dataAssStr.isEmpty()) {
                        dataAss = Mold.getDataAssunzione(); // fallback
                        break;
                    }
                    dataAss = checkDataAssunzione(dataAssStr);
                }
                Mnew.setDataAssunzione(dataAss);
            }

        } else if (pOld instanceof Dipendente && pNew instanceof Dipendente) {
            Dipendente Dold = (Dipendente) pOld;
            Dipendente Dnew = (Dipendente) pNew;

            String stipendioStr = leggiString("Stipendio [" + Dold.getStipendio() + "]:");
            if (stipendioStr.isEmpty()) {
                Dnew.setStipendio(Dold.getStipendio());
            } else {
                double stipendioValido = checkStipendio(Dnew, stipendioStr);
                Dnew.setStipendio(stipendioValido);
            }

            String dataAssStr = leggiString("Data assunzione [" + Dold.getDataAssunzione() + "]:");
            if (dataAssStr.isEmpty()) {
                Dnew.setDataAssunzione(Dold.getDataAssunzione());
            } else {
                LocalDate dataAss = checkDataAssunzione(dataAssStr);
                while (dataAss == null) {
                    mess("Data non valida. Riprova.");
                    dataAssStr = leggiString("Inserisci data assunzione (formato: dd-MM-yyyy):");
                    if (dataAssStr.isEmpty()) {
                        dataAss = Dold.getDataAssunzione();
                        break;
                    }
                    dataAss = checkDataAssunzione(dataAssStr);
                }
                Dnew.setDataAssunzione(dataAss);
            }
        }

        // --- Conferma modifiche ---
        String risposta = leggiString("Vuoi confermare le modifiche? (si/no):");
        return risposta.equalsIgnoreCase("si") ? pNew : pOld;
    }

    public Persona promuoviPersona(Persona p) {
        Persona nuova = null;

        if (!checkAge(p.getBirthDate(), 18)) {
            mess("La persona deve avere almeno 18 anni per essere promossa a Dipendente o Manager.");
            return p;
        }

        String scelta = leggiString("Vuoi promuovere a D (Dipendente) o M (Manager)? ").toUpperCase();

        switch (scelta) {
            case "D":
                nuova = new Dipendente();
                break;
            case "M":
                nuova = new Manager();
                break;
            default:
                mess("Scelta non valida. Operazione annullata.");
                return p;
        }

        nuova.setId(p.getId());
        nuova.setNome(p.getNome());
        nuova.setCognome(p.getCognome());
        nuova.setBirthDate(p.getBirthDate());
        nuova.setCf(p.getCf());

        if (nuova instanceof Dipendente) {
            Dipendente d = (Dipendente) nuova;
            d.setStipendio(checkStipendio(d,null));
            LocalDate dataAss = null;
            while (dataAss == null) {
                String input = leggiString("Inserisci data assunzione (dd-MM-yyyy):");
                dataAss = checkDataAssunzione(input);
            }
            d.setDataAssunzione(dataAss);

        }

        if (nuova instanceof Manager) {
            Manager m = (Manager) nuova;
            m.setRuolo(checkRuolo(leggiString("Inserisci ruolo (TOPMANAGER, PROJECTMANAGER, TEAMLEADER):").toUpperCase()));
        }

        mess("Promozione avvenuta con successo.");
        return nuova;
    }

    /*MENU*/
    public int menu()
    {
        System.out.println("GESTIONE PERSONA");
        System.out.println("1) Inserimento");
        System.out.println("2) Stampa ArrayList");
        System.out.println("3) Ricerca cf");
        System.out.println("4) Elimina");
        System.out.println("5) Modifica");
        System.out.println("6) Promozione");


        System.out.println("0) ESCI");
        return leggiValore("Fai una scelta: ");

    }

    public int menuPersona() {
        System.out.println("Scegli cosa inserire:");
        System.out.println("1) Persona");
        System.out.println("2) Dipendente");
        System.out.println("3) Manager");
        return leggiValore("Fai una scelta: ");
    }

    public int menuRicerca() {
        System.out.println("Scegli come cercare:");
        System.out.println("1) Ricerca con Codice Fiscale");
        System.out.println("2) Ricerca con Chiave");
        return leggiValore("Fai una scelta: ");
    }

    public int menuEliminazione() {
        System.out.println("1) Elimina tramite Codice Fiscale");
        System.out.println("2) Elimina tramite Chiave");
        return leggiValore("Scegli un'opzione:");
    }

    public void mess(String s)
    {
        System.out.println(s);
    }

}
