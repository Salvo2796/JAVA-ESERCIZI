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
                            break;
                        case 2:
                            System.out.println("Inserisci un dipendente");
                            p = new Dipendente();
                            break;
                        case 3:
                            System.out.println("Inserisci un manager");
                            p = new Manager();
                            break;

                        default:
                            System.out.println("Scelta Menu Non valida");
                            break;
                    }

                    v.formInsert(p);

                    if (repo.checkCf(p.getCf())) {
                        if (repo.insertPersona(p)) {
                            System.out.println("Inserimento avvenuto!");
                            v.stampaPersona(p);
                        } else {
                            System.out.println("Inserimento fallito!");
                        }
                    } else {
                        System.out.println("Codice fiscale già presente nel database. Inserimento annullato.");
                    }


                    break;
                case 2:
                    //Stampa tutte le persone
                    v.stampaLista(repo.getPersone());
                    break;
                case 3:

                    int sceltaRicerca = v.menuRicerca();

                    if (sceltaRicerca == 1) {

                        cf = v.leggiString("Inserisci il CF della persona da ricercare:");
                        p = repo.getPersonaByCf(cf);
                        v.stampaPersona(p);

                    } else if (sceltaRicerca == 2) {

                        int salary = v.leggiValore("Inserisci lo stipendio della persona da ricercare:");
                        v.stampaLista(repo.getPersonaByStipendio(salary));

                    } else {
                        v.mess("Scelta ricerca non valida.");
                    }

                    break;

                case 4:
                        cf = v.leggiString("Inserisci il CF della persona da eliminare:");
                        p = repo.getPersonaByCf(cf);

                        if (p == null) {
                            v.mess("CF non trovato. Ritorno al menu principale.");
                        } else {
                            String conferma = v.leggiString("Vuoi procedere all'eliminazione? (si/no):");
                            if (conferma.equalsIgnoreCase("si")) {
                                boolean esito = repo.eliminaPersona(p.getCf());
                                if (esito) {
                                    v.mess("Eliminazione avvenuta!");
                                } else {
                                    v.mess("Errore durante l'eliminazione.");
                                }
                            } else {
                                v.mess("Eliminazione non confermata");
                            }
                        }
                    break;



                case 5:
                    // Modifica
                    cf = v.leggiString("Inserisci codice fiscale della persona da modificare:");
                    Persona personaDaModificare = repo.getPersonaByCf(cf);

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

                        boolean cfValido = true;

                        // controlla se il codice fiscale della persona modificata è diverso da quello originale
                        if (!personaModificata.getCf().equalsIgnoreCase(personaDaModificare.getCf())) {
                            if (!repo.checkCf(personaModificata.getCf())) {
                                v.mess("Codice fiscale già presente nel database. Modifica annullata.");
                                cfValido = false;
                            }
                        }

                        if (cfValido) {
                            // controlla se la persona modificata è diversa da quella originale
                            if (!personaModificata.equals(personaDaModificare)) {
                                if (repo.updatePersona(personaModificata, cf) > 0) {
                                    v.mess("Modifica completata.");
                                } else {
                                    v.mess("Errore durante la modifica.");
                                }
                            } else {
                                // Se le persone sono uguali non è stata apportata nessuna modifica
                                v.mess("Nessuna modifica effettuata.");
                            }
                        }

                    }
                    break;


                case 6:
                    cf = v.leggiString("Inserisci codice fiscale della persona da promuovere:");
                    p = repo.getPersonaByCf(cf);

                    if (p == null) {
                        v.mess("Persona non trovata.");
                    } else {
                        Persona promossa = v.promuoviPersona(p);
                        if (repo.PromozioneDipendente(promossa)>0)
                            v.mess("Promozione avvenuta.");
                        else
                            v.mess("Promozione non effettuata.");
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
