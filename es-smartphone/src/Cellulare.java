import java.util.Scanner;

public class Cellulare {
    private double creditoDisponibile;
    private double tariffa;
    private int chiamateEffettuate;
    
    Scanner numeri = new Scanner(System.in);
    Scanner testi = new Scanner(System.in);
    
    public void avvia() {
        boolean ancora = true;
        while (ancora) {
            menu();
            int scelta = numeri.nextInt();

            switch (scelta) {
                case 1:
                    setRicarica();
                    break;
                case 2:
                    setImpostaTariffa();
                    break;
                case 3:
                    setChiamata();
                    break;
                case 4:
                    setCredito();
                    break;
                case 5:
                    setContatore();
                    break;
                case 6:
                    setAzzera();
                    break;
                case 7:
                    System.out.println("Spento");
                    ancora = false;
                    break;
                
                default:
                    System.out.println("Scelta non valida. Riprova.");

            }
        }
    }

    public void menu() {
        System.out.println("Digita il numero corrispondente per avviare l'operazione:");
        System.out.println("1 Ricarica");
        System.out.println("2 Imposta tariffa");
        System.out.println("3 Chiama");
        System.out.println("4 Credito");
        System.out.println("5 Contatore chiamate");
        System.out.println("6 Azzera chiamate");
        System.out.println("7 Spegni");
    }

    public void setRicarica(){
        
        System.out.println("Digita l'importo da ricaricare (tra 5, 10, 25)");
        int importo = numeri.nextInt();
        boolean ancora = true;

        do {
            switch (importo) {
            case 5:
            creditoDisponibile= creditoDisponibile + 5;  
            System.out.println("La ricarica è avvenuta con successo!");
            ancora = false;  
            break;
            case 10:
            creditoDisponibile = creditoDisponibile + 10;
            System.out.println("La ricarica è avvenuta con successo!");
            ancora = false;    
            break;
            case 25:
            creditoDisponibile = creditoDisponibile + 25;
            System.out.println("La ricarica è avvenuta con successo!");
            ancora = false;    
            break;
        
            default:
            System.out.println("Importo non correto:");
            System.out.print("inserisci di nuovo:");
            importo = numeri.nextInt();
            }
        } while (ancora);
        
    
    }
    public double getCreditoDisponibile() {
        return creditoDisponibile;
    }

    public void setImpostaTariffa(){
        System.out.println("Imposta una tariffa in centesimi");
        double tariffa = numeri.nextDouble();
        boolean ancora = true;
        do {
            if (tariffa >0) {
                this.tariffa = tariffa;
                System.out.println( "La tariffa è di " + this.tariffa + " cent/min");
                ancora = false;
            } else {
                System.out.println("Il valore deve essere maggiore di 0. Inserisci di nuovo: ");
                tariffa = numeri.nextDouble();
            }
        } while (ancora);
    
    }
    public double getTariffa(){
        return tariffa;
    }

    public void setChiamata(){
        if (tariffa == 0) {
            System.out.println("Devi impostare la tariffa prima, nell'opzione 2 del menu");

        }else if (creditoDisponibile == 0) {
            System.out.println("Il credito è pari a zero.Ricarica");
        
        } else{
            System.out.println("Digita il numero da comporre: ");
            String numeroChiamata = testi.nextLine(); 

            System.out.println("Digita la durata: ");
            int durata = numeri.nextInt();
            boolean ancora = true;
            
            do {
                if (durata > 0) {
                    ancora = false;
                } else {
                    System.out.println("La durata deve essere maggiore di 0.");
                    durata = numeri.nextInt();
                }
            } while (ancora);
            
            double costo = tariffa * durata;

            if (creditoDisponibile < costo ) {
                int minutiConteggiati = (int) (creditoDisponibile / (tariffa));
                System.out.println("Credito non sufficiente! La chiamata si è interrotta dopo " + minutiConteggiati + " minuti.");
                creditoDisponibile = 0;
                
            }else {
                creditoDisponibile = creditoDisponibile - costo;
                System.out.println("Chiamata avviata verso " + numeroChiamata + " per " + durata + " minuti");
                System.out.println("Costo: " + (Math.round(100*costo))/100.0 + " euro. Credito rimanente: " + (Math.round(100*(getCreditoDisponibile())))/100.0 + " euro");
            }

            chiamateEffettuate++;
        }
    }
    public int getChiamateEffettuate(){
        return chiamateEffettuate;
    }


    public void setCredito(){
        System.out.println("Il credito disponibile è: " + (Math.round(100*(getCreditoDisponibile())))/100.0 + " euro");
    }

    public void setContatore(){
        System.out.println("Il numero delle chiamate effettuate è: " + getChiamateEffettuate());

    }

    public void setAzzera(){
        if(getChiamateEffettuate() > 0){
            System.out.println("Adesso il numero di chiamate effettuate è 0 ");
            chiamateEffettuate = 0;
        } else{
            System.out.println("Non ci sono ancora chiamate effettuate.Non c'è bisogno di azzerare!");
        }
        
        testi.close();
        numeri.close();
        System.exit(0);
    }
}