package model;

import exceptions.Ruolo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Manager extends Dipendente{
    private Ruolo ruolo;

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String toString() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        long age= ChronoUnit.YEARS.between(birthDate, now);
        return super.toString() + ", ruolo=" + ruolo;
    }

}
