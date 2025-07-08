public class VacanzeTest {
    public static void main (String args[]) {
        Vacanze mieVacanze = new Vacanze();
        
        mieVacanze.setVacanze();
        mieVacanze.anniDiServizio = 1;
        mieVacanze.mostraGiorniVacanze();
        mieVacanze.anniDiServizio = 5;
        mieVacanze.mostraGiorniVacanze();
        mieVacanze.anniDiServizio = 10;
        mieVacanze.mostraGiorniVacanze();
    } 
}