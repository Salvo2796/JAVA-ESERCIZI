package controller;

import Utils.Ruolo;
import model.Persona;
import model.Dipendente;
import model.Manager;
import model.RepositoryPersona;
import view.View;

import java.time.format.DateTimeFormatter;

public class Avvio {
    public static void main(String[] args) {
        boolean flag = true;
        View v = new View();
        RepositoryPersona repo = new RepositoryPersona();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //	Persona p1 = new Persona("Mario", "Rossi", LocalDate.parse("10/04/1980", formatter), "MR");
        //	Dipendente d1 = new Dipendente("Giulia", "Bianchi", LocalDate.parse("25/12/1990", formatter), "GB", 35000, LocalDate.parse("25/12/2010", formatter));
        //	Manager m1 = new Manager("Luca", "Verdi", LocalDate.parse("15/05/1975", formatter), "LV", 55000, LocalDate.parse("25/12/2000", formatter), Ruolo.TOPMANAGER);

        //	repo.insertPersona(p1);
        //	repo.insertPersona(d1);
        //	repo.insertPersona(m1);

        do {
            switch (v.menu()) {
                case 0 -> {
                    flag = false;
                    v.mess("Programma Terminato!");
                }

                case 1 -> {
                    Persona p = new Persona();
                    v.formInsert(p);
                    if (!repo.checkCf(p.getCf())) {
                        repo.insertPersona(p);
                        v.mess("Persona inserita con ID: " + p.getId());
                    } else {
                        v.mess("Codice fiscale già presente, inserimento annullato.");
                    }
                }

                case 2 -> {
                    Dipendente d = new Dipendente();
                    v.formInsert(d);
                    if (!repo.checkCf(d.getCf())) {
                        repo.insertPersona(d);
                        v.mess("Dipendente inserito con ID: " + d.getId());
                    } else {
                        v.mess("Codice fiscale già presente, inserimento annullato.");
                    }
                }

                case 3 -> {
                    Manager m = new Manager();
                    v.formInsert(m);
                    if (!repo.checkCf(m.getCf())) {
                        repo.insertPersona(m);
                        v.mess("Manager inserito con ID: " + m.getId());
                    } else {
                        v.mess("Codice fiscale già presente, inserimento annullato.");
                    }
                }

                case 4 -> v.stampaPersona(repo.selectAll());

                case 5 -> {
                    if (!repo.HashMapEmpty()) {
                        String cf = v.leggiString("Inserisci il codice fiscale della persona da ricercare:");
                        Persona found = repo.getPersonaByCf(cf);
                        v.stampaPersona(found);
                    } else {
                        v.mess("Nessuna persona presente nel sistema.");
                    }
                }

                case 6 -> {
                    if (!repo.HashMapEmpty()) {
                        int id = v.leggiValore("Inserisci l'id della persona da ricercare:");
                        Persona found = repo.getPersonaById(id);
                        v.stampaPersona(found);
                    } else {
                        v.mess("Nessuna persona presente nel sistema.");
                    }
                }

                case 7 -> {
                    if (!repo.HashMapEmpty()) {
                        int id = v.leggiValore("Inserisci ID della persona da eliminare:");
                        if (repo.eliminaPersona(id)) {
                            v.mess("Eliminazione avvenuta!");
                        } else {
                            v.mess("ID non trovato, eliminazione fallita.");
                        }
                    } else {
                        v.mess("Nessuna persona presente nel sistema.");
                    }
                }

                case 8 -> {
                    int id = v.leggiValore("Inserisci ID della persona da modificare:");
                    Persona oldP = repo.getPersona(id);

                    if (oldP == null) {
                        v.mess("Persona non trovata.");
                    } else {
                        Persona newP = switch (oldP) {
                            case Manager m -> v.formUpdate(m);
                            case Dipendente d -> v.formUpdate(d);
                            default -> v.formUpdate(oldP);
                        };

                        v.mostraRiepilogoModifiche(oldP, newP);

                        if (!newP.equals(oldP)) {
                            if (v.confermaModifiche()) {
                                repo.updatePersona(id, newP);
                                v.mess("Modifica completata.");
                            } else {
                                v.mess("Modifica annullata.");
                            }
                        } else {
                            v.mess("Nessuna modifica effettuata.");
                        }
                    }
                }

                case 9 -> {
                    int id = v.leggiValore("Inserisci ID della persona da promuovere:");
                    Persona persona = repo.getPersona(id);
                    if (persona == null) {
                        v.mess("ID non trovato.");
                    } else {
                        Persona promossa = v.promuoviPersona(persona);
                        if (promossa != persona) {
                            repo.updatePersona(id, promossa);
                            v.mess("Promozione effettuata.");
                        } else {
                            v.mess("Promozione annullata.");
                        }
                    }
                }

                default -> v.mess("Scelta non valida, riprova.");
            }
        } while (flag);
    }
}
