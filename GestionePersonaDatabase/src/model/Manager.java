package model;

import exceptions.Ruolo;
import java.util.Objects;

public class Manager extends Dipendente{
    private Ruolo ruolo;

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String toString() {
        return super.toString() + ", ruolo=" + ruolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ruolo);
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
        Manager other = (Manager) obj;
        return Objects.equals(getRuolo(), other.getRuolo());
    }


}
