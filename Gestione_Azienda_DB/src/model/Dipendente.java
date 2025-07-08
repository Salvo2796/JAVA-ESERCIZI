package model;

import java.time.LocalDate;

public class Dipendente extends Persona {
    private double stipendio;
    private LocalDate dataDiAssunzione;

    public Dipendente() {
        super();
    }

    public Dipendente(String nome, String cognome, LocalDate birthDate, String cf, double stipendio, LocalDate dataDiAssunzione) {
        super(nome, cognome, birthDate, cf);
        this.stipendio = stipendio;
        this.dataDiAssunzione = dataDiAssunzione;
    }

    public double getStipendio() {
        return stipendio;
    }

    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }

    public LocalDate getDataDiAssunzione() {
        return dataDiAssunzione;
    }

    public void setDataDiAssunzione(LocalDate dataDiAssunzione) {
        this.dataDiAssunzione = dataDiAssunzione;
    }

    @Override
    public String toString() {
        return super.toString() + ", Dipendente [stipendio=" + stipendio + ", dataDiAssunzione=" + dataDiAssunzione + "]";
    }
}
