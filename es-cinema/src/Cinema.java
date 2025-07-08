import java.util.ArrayList;
import java.util.List;

public class Cinema {
    public int numeroSpettatori;
    private ArrayList<Integer> zonaSinistra;
    private ArrayList<Integer> zonaCentrale;
    private ArrayList<Integer> zonaDestra;

    private int spettatoriSinistra;  
    private int spettatoriCentro; 
    private int spettatoriDestra;

    public Cinema() {
        zonaSinistra = new ArrayList<>();
        zonaCentrale = new ArrayList<>();
        zonaDestra = new ArrayList<>();
    }

    public void setNumeroSpettatori(int numeroSpettatori) {
       if (numeroSpettatori > 300) {
            System.out.println("La capienza massima è di 300 posti.");
            this.numeroSpettatori = 300;
        } else {
            this.numeroSpettatori = numeroSpettatori;
        }
    }

    public void setAssegnaPosto() {
        int spettatoriDivisi = numeroSpettatori / 3;
        int spettatoriRimasti = numeroSpettatori % 3;
       
        int sinistra = spettatoriDivisi;
        if (spettatoriRimasti > 0) {
            sinistra++;
            spettatoriRimasti--;
        }
        spettatoriSinistra = sinistra;

        int centro = spettatoriDivisi;
        if (spettatoriRimasti > 0) {
            centro++;
            spettatoriRimasti--;
        }
        spettatoriCentro = centro;

        int destra = spettatoriDivisi;
        if (spettatoriRimasti > 0) {
            destra++;
            spettatoriRimasti--;
        }
        spettatoriDestra = destra;

        int biglietti = 1;
        int indiceZona = 0;

        for (int i = 0; i < numeroSpettatori; i++) {
            if (indiceZona == 0) {
                zonaSinistra.add(biglietti++);
            } else if (indiceZona == 1) {
                zonaCentrale.add(biglietti++);
            } else {
                zonaDestra.add(biglietti++);
            }
            
            indiceZona++;
            if (indiceZona == 3) {
                indiceZona = 0;  
            }
        }
    }

    public List<Integer> getZonaSinistra() {
        return zonaSinistra;
    }

    public List<Integer> getZonaCentrale() {
        return zonaCentrale;
    }

    public List<Integer> getZonaDestra() {
        return zonaDestra;
    }

    public int getSpettatoriSinistra(){
        return spettatoriSinistra;
    }

    public int getSpettatoriCentro(){
        return spettatoriCentro;
    }

    public int getSpettatoriDestra(){
        return spettatoriDestra;
    }

    public void display() {
        System.out.println("Assegnazione zona:");
        System.out.println("Numero di posti assegnati nella zona sinistra: " + getZonaSinistra().size());
        System.out.println("Numero di posti assegnati nella zona centrale: " + getZonaCentrale().size());
        System.out.println("Numero di posti assegnati nella zona destra: " + getZonaDestra().size());
        System.out.print("Posti assegnati a sinistra: ");
            for (int posto : zonaSinistra) {
                System.out.print(posto + " ");
            }
        System.out.println();
        System.out.print("Posti assegnati al centro: ");
            for (int posto : zonaCentrale) {
                System.out.print(posto + " ");
            }
        System.out.println();
        System.out.print("Posti assegnati a destra: ");
            for (int posto : zonaDestra) {
                System.out.print(posto + " ");
            }
        System.out.println(); 
        System.out.println("Totale spettatori: " + (getSpettatoriSinistra() + getSpettatoriCentro() + getSpettatoriDestra()));
    }
}