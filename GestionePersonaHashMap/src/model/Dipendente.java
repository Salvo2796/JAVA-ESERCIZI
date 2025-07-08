package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dipendente extends Persona{
    private LocalDate dataAssunzione;
    private double stipendio;

    public LocalDate getDataAssunzione() {
        return dataAssunzione;
    }

    public void setDataAssunzione(LocalDate dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }



    public double getStipendio() {
        return stipendio;
    }

    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }

    @Override
    public String toString() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        long age= ChronoUnit.YEARS.between(birthDate, now);
        return super.toString()+  ", data di assunzione=" + dataAssunzione.format(formatter) + ", stipendio=" + stipendio;
    }
}
