package model;

import Utils.Ruolo;

import java.time.LocalDate;

public class Manager extends Dipendente {
    private Ruolo ruolo;

    public Manager() {
        super();
    }

    public Manager(String nome, String cognome, LocalDate birthDate, String cf, double stipendio, LocalDate dataDiAssunzione, Ruolo ruolo) {
        super(nome, cognome, birthDate, cf, stipendio, dataDiAssunzione);
        this.ruolo = ruolo;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return super.toString() + ", Manager [ruolo=" + ruolo + "]";
    }
}
