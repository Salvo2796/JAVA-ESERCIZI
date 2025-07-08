package controller;

import model.Dipendente;
import model.Manager;
import model.Persona;
import model.RepositoryPersona;
import view.View;

public class Main {

    public static void main(String[] args) {

        boolean flag=true;
        View v=new View();
        String cf;
        int chiave;
        Persona p = null;
        RepositoryPersona repo=new RepositoryPersona();

        do {
            switch (v.menu())
            {
                case 1:
                    //Inserimento
                    //Object obj=new Persona(); polimorfismo degli oggetti
                    int scelta=v.menuPersona();
                    switch (scelta) {
                        case 1:
                            System.out.println("Inserisci una persona");
                            p = new Persona();
                            v.formInsert(p);
                            if(!repo.checkCf(p.getCf()))
                                repo.insertPersona(p);
                            v.stampaPersona(p);
                            break;
                        case 2:
                            System.out.println("Inserisci un dipendente");
                            p = new Dipendente();
                            v.formInsert(p);
                            if(!repo.checkCf(p.getCf()))
                                repo.insertPersona(p);
                            v.stampaPersona(p);
                            break;
                        case 3:
                            System.out.println("Inserisci un manager");
                            p = new Manager();
                            v.formInsert(p);
                            if(!repo.checkCf(p.getCf()))
                                repo.insertPersona(p);
                            v.stampaPersona(p);
                            break;
                        default:
                            System.out.println("Scelta Menu Non valida");
                            break;
                    }

                    break;
                case 2:
                    //Stampa tutte le persone

                    v.stampaPersona(repo.selectAll());

                    break;
                case 3:

                    int sceltaRicerca = v.menuRicerca();

                    if (sceltaRicerca == 1) {
                        cf = v.leggiString("Inserisci il CF della persona da ricercare:");
                        p = repo.getPersona(cf);

                        if (p != null) {
                            v.stampaPersona(p);
                        } else {
                            v.mess("CF non trovato.");
                        }

                    } else if (sceltaRicerca == 2) {

                        chiave = v.leggiValore("Inserisci la chiave della persona da ricercare:");
                        p = repo.checkPersonaId(chiave);

                        if (p != null) {
                            v.stampaPersona(p);
                        } else {
                            v.mess("Chiave non trovata.");
                        }

                    } else {
                        v.mess("Scelta ricerca non valida.");
                    }

                    break;

                case 4:
                    int sceltaEliminazione = v.menuEliminazione();

                    if (sceltaEliminazione == 1) {
                        cf = v.leggiString("Inserisci il CF della persona da eliminare:");
                        p = repo.getPersona(cf);

                        if (p == null) {
                            v.mess("CF non trovato. Ritorno al menu principale.");
                            break;
                        }

                    } else if (sceltaEliminazione == 2) {
                        chiave = v.leggiValore("Inserisci la chiave (ID) della persona:");
                        p = repo.checkPersonaId(chiave);

                        if (p == null) {
                            v.mess("Nessuna persona trovata con questa chiave. Ritorno al menu principale.");
                            break;
                        }

                    } else {
                        v.mess("Scelta non valida.");
                        break;
                    }

                    String conferma = v.leggiString("Vuoi procedere all'eliminazione? (si/no):");
                    if (conferma.equalsIgnoreCase("si")) {
                        if (repo.eliminaPersona(p)) {
                            v.mess("Eliminazione avvenuta!");
                        } else {
                            v.mess("Errore durante l'eliminazione.");
                        }
                    } else {
                        v.mess("Eliminazione annullata.");
                    }

                    break;



                case 5:
                    //modifica
                    String sceltaModifica = v.leggiString("Vuoi cercare la persona da modificare per [1] Codice Fiscale o [2] Chiave ID?");

                    Persona personaDaModificare = null;

                    if (sceltaModifica.equals("1")) {
                        cf = v.leggiString("Inserisci codice fiscale della persona da modificare:");
                        personaDaModificare = repo.getPersona(cf);
                    } else if (sceltaModifica.equals("2")) {
                        chiave = v.leggiValore("Inserisci la chiave della persona da modificare:");
                        personaDaModificare = repo.checkPersonaId(chiave);
                    } else {
                        v.mess("Scelta non valida.");
                    }

                    if (personaDaModificare == null) {
                        v.mess("Persona non trovata.");
                    } else {
                        Persona personaModificata;
                        if (personaDaModificare instanceof Manager)
                            personaModificata = v.formUpdate(personaDaModificare, new Manager());
                        else if (personaDaModificare instanceof Dipendente)
                            personaModificata = v.formUpdate(personaDaModificare, new Dipendente());
                        else
                            personaModificata = v.formUpdate(personaDaModificare, new Persona());

                        if (!personaModificata.equals(personaDaModificare)) {
                            if (!personaModificata.getCf().equalsIgnoreCase(personaDaModificare.getCf())) {
                                if (repo.checkCf(personaModificata.getCf())) {
                                    // codice fiscale già presente
                                    return;
                                }
                            }

                            repo.updatePersona(personaDaModificare, personaModificata);
                            v.mess("Modifica completata.");
                        } else {
                            v.mess("Non sono state effettuate modifiche.");
                        }
                    }
                    break;

                case 6:
                    cf = v.leggiString("Inserisci codice fiscale della persona da promuovere:");
                    p = repo.getPersona(cf);

                    if (p == null) {
                        v.mess("Persona non trovata.");
                    } else {
                        Persona promossa = v.promuoviPersona(p);
                        repo.updatePersona(p, promossa);
                    }
                    break;


                case 0:
                    //Esci
                    flag=false;
                    v.mess("Programma Terminato!");
                    break;

                default:
                    v.mess("non hai fornito un valore valido riprova!");
            }

        } while(flag);

    }
}
