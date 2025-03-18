public class Clock {

    public int minuti;
    public int ora;

    public void mostraPeriodo() {
        if (minuti >= 0 && minuti <= 59) {
            if (ora >= 6 && ora <= 11) { 
                System.out.println("Periodo: Mattina");
            } else if (ora >= 12 && ora < 17) {
                System.out.println("Periodo: Pomeriggio");
            } else if (ora >= 18 && ora < 22) {
                System.out.println("Periodo: Sera");
            } else if ((ora == 23) || (ora >= 0 && ora < 6)) {
                System.out.println("Periodo: Notte");
            } else {
                System.out.println("Ora non valida!");
            }
        } else {
            System.out.println("Minuti non validi!");
        }
    }
}