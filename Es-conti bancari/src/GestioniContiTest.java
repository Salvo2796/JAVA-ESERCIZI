public class GestioniContiTest {
    public static void main(String[] args) {
       
        GestioneConti mioconto = new GestioneConti();
        
        mioconto.nomeCognome.append("Salvatore").append(" ").append("Mistretta");
        mioconto.saldoAttuale = 1000;
        mioconto.mostraConto();
        
        mioconto.prelievo = 200;
        mioconto.prelevare();

        mioconto.versamento = 400;
        mioconto.versare();
        
        mioconto.tassoInteresse = 6; 
        mioconto.calcolareInteresse();
    }
}