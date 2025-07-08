
import java.util.Scanner;

public class CinemaTest {
   
    public static void main(String[] args) {
        Scanner numeri = new Scanner(System.in);
        Cinema mioCinema = new Cinema();
        
        int numeroSpettatori;
        boolean ancora = true;

        do {
            System.out.print("Quanti spettatori ci sono? ");
            numeroSpettatori = numeri.nextInt();
            mioCinema.setNumeroSpettatori(numeroSpettatori);

            if (numeroSpettatori > 0) {
                mioCinema.setAssegnaPosto();
                mioCinema.display();
                ancora = false;
            } else {
                System.out.println("Inserisci un numero di spettatori valido (1-300).");
            }

        } while (ancora); 

        numeri.close();
        System.exit(0);
    }
}
