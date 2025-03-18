public class GestioneConti {
    public StringBuilder nomeCognome = new StringBuilder();
    public double saldoAttuale;
    public double saldoInteresse;

    public void mostraConto() {
        System.out.println("Nome e cognome: " + nomeCognome);
        System.out.println("Saldo iniziale: " + saldoAttuale + " euro");
    }

    public double versamento;
    public void versare() {
        saldoAttuale += versamento; 
        System.out.println("Versamento: " + versamento + "euro");
        System.out.println("Saldo attuale: " + saldoAttuale + " euro");
    }

    public double prelievo;
    public void prelevare() {
        saldoAttuale -= prelievo;
        System.out.println("Prelievo: " + prelievo + " euro");
        System.out.println("Saldo attuale: " + saldoAttuale + " euro");
    }

    public double tassoInteresse;
    public void calcolareInteresse() {
        saldoInteresse = (saldoAttuale * tassoInteresse /100) + saldoAttuale; 
        System.out.println("Saldo con il tasso di interesse al " + tassoInteresse + " %: " + saldoInteresse + " euro");
    }    
}