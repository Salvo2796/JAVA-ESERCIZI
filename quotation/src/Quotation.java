public class Quotation { 
    /* Questa classe la creo per avere un testo utilizzabile in maniera "public" 
    cioè altre classi potranno accedere alla variabile quote, in modo che sia 
    indipendente dalla classe test e possa essere usata da più programmi */
    public String quote = "Sono arrivato!"; // è una variabile globale
    public void display() { // display è il metodo
    System.out.println(quote);
    }
}
    