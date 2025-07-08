package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Persona {
    private String nome;
    private String cognome;
    private LocalDate birthDate;
    private String cf;
    private int id; // Chiave univoca associata all'istanza

    public Persona() {
    }

    public Persona(String nome, String cognome, LocalDate birthDate, String cf) {
        this.nome = nome;
        this.cognome = cognome;
        this.birthDate = birthDate;
        this.cf = cf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    @Override
    public String toString() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        long age = ChronoUnit.YEARS.between(this.birthDate, now);
        return "Persona [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", birthDate=" + birthDate.format(formatter) +
                ", cf=" + cf + ", age=" + age + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, cf, cognome, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        return Objects.equals(birthDate, other.birthDate) &&
                Objects.equals(cf, other.cf) &&
                Objects.equals(cognome, other.cognome) &&
                Objects.equals(nome, other.nome);
    }
}
