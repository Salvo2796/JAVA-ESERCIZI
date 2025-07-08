public class Vacanze {

    public int []giorniVacanze;
    public int anniDiServizio;

    public Vacanze() {
        giorniVacanze = new int[4];
    }

    public void setVacanze(){
        giorniVacanze[0] = 10;
        giorniVacanze[1] = 15;
        giorniVacanze[2] = 20;
        giorniVacanze[3] = 25;
    }

    public void mostraGiorniVacanze() {
        int index = 0;
        if (anniDiServizio > 0 && anniDiServizio <= 3) {
            index = 1;
        } else if (anniDiServizio >= 4 && anniDiServizio <= 5) {
            index = 2;
        } else if (anniDiServizio > 5) {
            index = 3;
        }
        
        if (anniDiServizio >= 0) {
            System.out.println("Giorni spettanti: " + giorniVacanze[index]);
        } else {
            System.out.println("Valore non valido!");
        }
    }
}