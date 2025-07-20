public class Persona {
    

    public String nome;

    public Persona(){}

    public Persona(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Persona [nome=" + nome + "]";
    }

    
}
