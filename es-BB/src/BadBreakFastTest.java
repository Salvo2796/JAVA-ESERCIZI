import java.util.Scanner;

public class BadBreakFastTest {
    public static void main(String[] args) {
        Scanner numeri = new Scanner(System.in);
        Scanner testi = new Scanner(System.in);
        BadBreakFast mioBadBreakFast = new BadBreakFast(); 
        boolean ancora = true;

        while (ancora) {
            System.out.println("Seleziona un'opzione:");
            System.out.println("1. Inserisci nuova prenotazione");
            System.out.println("2. Cerca una prenotazione");
            System.out.println("3. Visualizza tutte le prenotazioni");
            System.out.println("4. Esci");

            System.out.print("Opzione: ");
            
            try {
                int scelta = numeri.nextInt();
                numeri.nextLine();

                switch (scelta) {
                    case 1:
                        Prenotazione miaPrenotazione = new Prenotazione();
                        miaPrenotazione.setNomeCognome();
                        miaPrenotazione.setTelefono();
                        miaPrenotazione.setDataPrenotazione();
                        miaPrenotazione.setDataUscita();
                        mioBadBreakFast.aggiungiPrenotazione(miaPrenotazione); 
                        System.out.println("Prenotazione aggiunta con successo.");
                        break;

                    case 2:
                        System.out.print("Inserisci nome e cognome da cercare: ");
                        String nomeRicerca = testi.nextLine();
                        mioBadBreakFast.cercaPerNomeEStampa(nomeRicerca);
                        break;

                    case 3:
                        mioBadBreakFast.stampaTutteLePrenotazioni();
                        break;

                    case 4:
                        ancora = false;
                        System.out.println("Programma terminato.");
                        break;

                    default:
                        System.out.println("Opzione non valida. Riprova.");
                        break;
                }
            } catch (Exception exp) {
                System.out.println("Input non valido. Riprova.");
                numeri.nextLine(); 
            }
        }

        testi.close();
        numeri.close();
    }
}
