import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

public class BadBreakFast {
    private ArrayList<Prenotazione> listaPrenotazioni;

    public BadBreakFast() {
        listaPrenotazioni = new ArrayList<>();
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        listaPrenotazioni.add(p);
    }

    public void cercaPerNomeEStampa(String nomeCompleto) {
        Prenotazione prenotazioneTrovata = null;
        boolean ancora = false;
        int index = 0;
    
        do {
            if (index < listaPrenotazioni.size()) {
                Prenotazione p = listaPrenotazioni.get(index);
                if (p.getNomeCognome().equalsIgnoreCase(nomeCompleto)) {
                    prenotazioneTrovata = p;
                    ancora = true;
                }
                index++; 
            } else {
                ancora = true;
            }
        } while (!ancora); 
    
        if (prenotazioneTrovata != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
            System.out.println("----- Dettagli Prenotazione -----");
            System.out.println("Nome e Cognome: " + prenotazioneTrovata.getNomeCognome());
            System.out.println("Telefono: " + prenotazioneTrovata.getTelefono());
            System.out.println("Data prenotazione: " + prenotazioneTrovata.getDataPrenotazione().format(formatter));
            System.out.println("Data uscita: " + prenotazioneTrovata.getDataUscita().format(formatter));
            System.out.println("------------------------------");
        } else {
            System.out.println("Nessuna prenotazione trovata per: " + nomeCompleto);
        }
    }
    
    

    public void stampaTutteLePrenotazioni() {
        if (listaPrenotazioni.isEmpty()) {
            System.out.println("Nessuna prenotazione presente.");
            return;
        }

         DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);

        for (Prenotazione p : listaPrenotazioni) {
            System.out.println("----- Prenotazione -----");
            System.out.println("Nome e Cognome: " + p.getNomeCognome());
            System.out.println("Telefono: " + p.getTelefono());
            System.out.println("Data prenotazione: " + p.getDataPrenotazione().format(formatter1));
            System.out.println("Data uscita: " + p.getDataUscita().format(formatter1));
            System.out.println("------------------------");
        }
    }
}
