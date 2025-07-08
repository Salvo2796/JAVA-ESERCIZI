package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return super.toString()+  ", data di assunzione=" + dataAssunzione.format(formatter) + ", stipendio=" + stipendio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stipendio, dataAssunzione);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        Dipendente other = (Dipendente) obj;
        return Double.compare(other.getStipendio(), getStipendio()) == 0
                && Objects.equals(getDataAssunzione(), other.getDataAssunzione());
    }

}
