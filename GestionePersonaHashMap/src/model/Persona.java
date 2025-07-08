package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Persona{

    private static int nextId = 1;// contatore condiviso da tutte le istanze

    private int id;
    private String nome;
    private String cognome;
    //private int eta;
    LocalDate birthDate;
    private String cf; //deve essere unico

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = nextId++;
    }

    // nuovo metodo per promozione e update)
    public void setId(int id) {
        this.id = id;
    }
    //setter
    public void setNome(String nome)
    {
        //this.nome fa riferimento alla variabile della classe corrente
        this.nome=nome;
    }

    public void setCognome(String cognome)
    {
        this.cognome=cognome;
    }

    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate=birthDate;
    }



    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    //getter
    public String getNome()
    {
        return this.nome;
    }

    public String getCognome()
    {
        return this.cognome;
    }

    public LocalDate getBirthDate()
    {
        return this.birthDate;
    }



    //Costruttori
    public Persona(String nome, String cognome, LocalDate birthDate, String cf)
    {
        this.nome=nome;
        this.cognome=cognome;
        this.birthDate=birthDate;
        this.cf=cf;
    }

    //polimorfismo: overload = abbiamo due metodi con lo stesso nome ma firma diversa
    //la firma � l insieme del nome + parametri in input
    public Persona()
    {

    }

    //polimorfismo: override = abbiamo due metodi con la stessa firma(il metodo toString si trova nella classe padre)
    //sovrascriviamo il primo metodo toString con questo

    @Override
    public String toString() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        long age= ChronoUnit.YEARS.between(birthDate, now);

        return getClass().getSimpleName() + ": id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", birthDate=" + birthDate.format(formatter) + ", cf=" + cf + " age=" + age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, cf, cognome, nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        return Objects.equals(birthDate, other.birthDate) && Objects.equals(cf, other.cf)
                && Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome);
    }

}


