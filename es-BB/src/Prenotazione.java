import java.time.LocalDate;
import java.util.InputMismatchException;import java.util.Scanner;

public class Prenotazione {
    private String nomeCognome;
    private String telefono;  

    private int giorno, mese, anno;
    private LocalDate dataPrenotazione;

    private int giornoUscita, meseUscita, annoUscita;
    private LocalDate dataUscita;

    Scanner numeri = new Scanner(System.in);
    Scanner testi = new Scanner(System.in);

    public void setNomeCognome() {
        boolean ancora = false;
        do {
            System.out.print("Inserisci nome e cognome: ");
            String nomeCognome = testi.nextLine();
            if (nomeCognome.contains(" ")) {
                this.nomeCognome = nomeCognome;
                ancora = true;
            } else {
                System.out.println("Inserisci sia nome che cognome separati da spazio.");
            }
        } while (!ancora);
    }

    public String getNomeCognome() {
        return nomeCognome;
    }

    public void setTelefono() {
        System.out.println("Inserisci numero di telefono: ");
        telefono = testi.nextLine(); 
    }

    public String getTelefono() {
        return telefono;
    }

    public void setGiorno() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci giorno: ");
                giorno = numeri.nextInt();
                numeri.nextLine(); 
                if (giorno >= 1 && giorno <= 31) {
                    ancora = true;
                } else {
                    System.out.println("Il giorno deve essere tra 1 e 31.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per il giorno.");
                numeri.nextLine(); 
            }
        } while (!ancora);
    }

    
    public int getGiorno() { 
        return giorno; 
    }

    public void setMese() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci mese: ");
                mese = numeri.nextInt();
                numeri.nextLine();
                if (mese >= 1 && mese <= 12) {
                    ancora = true;
                } else {
                    System.out.println("Il mese deve essere tra 1 e 12.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per il mese.");
                numeri.nextLine(); 
            }
        } while (!ancora);
    }

    
    public int getMese() { 
        return mese; 
    }

    public void setAnno() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci anno: ");
                anno = numeri.nextInt();
                numeri.nextLine(); 
                if (anno >= 2025) { 
                    ancora = true;
                } else {
                    System.out.println("L'anno deve essere almeno 2025.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per l'anno.");
                numeri.nextLine();
            }
        } while (!ancora);
    }

    public int getAnno() { 
        return anno; 
    }


    public void setDataPrenotazione() {
        boolean ancora = false;
        do {
            try {
                setGiorno();
                setMese();
                setAnno();
                dataPrenotazione = LocalDate.of(getAnno(), getMese(), getGiorno());
                ancora = true;
            } catch (java.time.DateTimeException de) {
                System.out.println("Data non valida. Riprova.");
                
            }
        } while (!ancora);
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setGiornoUscita() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci giorno di uscita: ");
                giornoUscita = numeri.nextInt();
                numeri.nextLine(); 
                if (giornoUscita >= 1 && giornoUscita <= 31) {
                    ancora = true;
                } else {
                    System.out.println("Il giorno di uscita deve essere tra 1 e 31.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per il giorno di uscita.");
                numeri.nextLine(); 
            }
        } while (!ancora);
    }

    public int getGiornoUscita() { 
        return giornoUscita; 
    }

    public void setMeseUscita() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci mese di uscita: ");
                meseUscita = numeri.nextInt();
                numeri.nextLine();
                if (meseUscita >= 1 && meseUscita <= 12) {
                    ancora = true;
                } else {
                    System.out.println("Il mese di uscita deve essere tra 1 e 12.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per il mese di uscita.");
                numeri.nextLine(); 
            }
        } while (!ancora);
    }

    public int getMeseUscita() { 
        return meseUscita; 
    }

    public void setAnnoUscita() {
        boolean ancora = false;
        do {
            try {
                System.out.print("Inserisci anno di uscita: ");
                annoUscita = numeri.nextInt();
                numeri.nextLine(); 
                if (annoUscita >= 2025) {
                    ancora = true;
                } else {
                    System.out.println("L'anno di uscita deve essere almeno 2025.");
                }
            } catch (InputMismatchException i) {
                System.out.println("Inserisci un numero valido per l'anno di uscita.");
                numeri.nextLine(); 
            }
        } while (!ancora);
    }

    public int getAnnoUscita() { 
        return annoUscita; 
    }


    public void setDataUscita() {
        boolean ancora = false;
        do {
            try {
                setGiornoUscita();
                setMeseUscita();
                setAnnoUscita();
                dataUscita = LocalDate.of(getAnnoUscita(), getMeseUscita(), getGiornoUscita());
                if (dataUscita.isBefore(getDataPrenotazione())) {
                    System.out.println("La data di uscita deve essere successiva alla data di ingresso. Riprova.");
                } else {
                    ancora = true;
                }
            } catch (java.time.DateTimeException de) {
                System.out.println("Data di uscita non valida. Riprova.");
            }
        } while (!ancora);
    }

    public LocalDate getDataUscita() {
        return dataUscita;
    }
}
