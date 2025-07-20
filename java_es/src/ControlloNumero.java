import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ControlloNumero {
    public int num;
    public String str;

    Scanner input = new Scanner(System.in);

    public int menu() {
        System.out.println("Scegli:");
        System.out.println("1.Controllo numero Pari Dispari");
        System.out.println("2.Controllo Pari Positivo");
        System.out.println("3.Controllo String Vuota/Null");
        System.out.println("4.Prodotto scontato");
        System.out.println("5.Valore massimo");
        System.out.println("6.Valore minimo");
        System.out.println("0.Uscita");

        int scelta = input.nextInt();
        input.nextLine();
        return scelta;
    }

    public void controlloPariDispari() {

        System.out.println("Inserisci numero:");
        num = input.nextInt();
        String risultato;

        if ((num % 2) == 0 && num != 0) {

            risultato = "Numero Pari";

        } else if ((num % 2) != 0) {
            risultato = "Numero Dispari";
        } else {
            risultato = "Il numero è 0";
        }
        System.out.println(risultato);
        input.nextLine();
    }

    public void controlloPariPositivo() {

        System.out.println("Inserisci numero: ");
        num = input.nextInt();
        String risultato;

        if ((num % 2 == 0) && (num > 0))
            risultato = "Numero positivo e pari";
        else if (num % 2 != 0 && (num > 0))
            risultato = "Numero Dispari ";
        else
            risultato = "Numero Negativo";

        System.out.println(risultato);
    }

    public void controlloStringa() {

        System.out.println("Inserisci Stringa");
        str = input.nextLine();
        String risultato;

        if (str.isEmpty()) {
            risultato = "Vuota";
        } else if (str == null) {
            risultato = "Null";
        } else {
            risultato = str;
        }

        System.out.println(risultato);
    }

    public void sconto() {

        System.out.println("Inserisci importo:");
        double importo = input.nextDouble();
        System.out.println("Inserisci sconto");
        num = input.nextInt();

        double prodottoScontato = importo - ((importo * num) / 100);
        System.out.println("Prodotto scontato: " + prodottoScontato);
    }

    public void valoreMassimo() {
        System.out.println("Inserisci primo numero");
        int PNum = input.nextInt();
        System.out.println("Inserisci Secondo numero");
        int SNum = input.nextInt();

        str = (PNum > SNum) ? PNum + " è maggiore di " + SNum : SNum + " è maggiore di " + PNum;
        System.out.println(str);
    }

    public void trovaMinore() {
        System.out.println("Inserisci primo numero");
        int PNum = input.nextInt();
        System.out.println("Inserisci secondo numero");
        int Snum = input.nextInt();
        System.out.println("Inserisci terzo numero");
        int TNum = input.nextInt();

        int valore = (PNum < Snum) ? ((PNum < TNum) ? PNum : TNum)
                : (Snum < TNum) ? Snum : TNum;
        System.out.println("Il valore minimo è: " + valore);
    }

    public void ciclo() {
        /*
         * Calcolare la somma dei numeri da 1 a 100 utilizzando un ciclo "for":
         * int somma = 0;
         * for(int i = 1; i <=100; i++){
         * somma += i;
         * }
         * System.out.println(somma);
         */
        /*
         * Stampare i numeri pari da 1 a 20 utilizzando un ciclo "for":
         * for(int i = 1; i <=200; i++){
         * int num = (i % 2);
         * if (num == 0) {
         * System.out.println(i);
         * }
         * }
         */

        /*
         * Calcolare il prodotto dei numeri dispari da 1 a 15 utilizzando un ciclo "for"
         * int molt = 1;
         * for (int i = 1; i < 16; i++) {
         * 
         * if (i % 2 != 0) {
         * molt *= i;
         * }
         * }
         * System.out.println(molt);
         */

        // for(int i = 1; i<=50; i++){
        // if (i % 5 == 0) {
        // System.out.println(i);
        // }
        // }

        // Stampare i caratteri di una stringa uno alla volta utilizzando un ciclo
        // "for":
        // System.out.println("inserisci:");
        // str = input.nextLine();
        // for(int i = 0; i<str.length(); i++){
        // char carattere = str.charAt(i);
        // System.out.println(carattere);
        // }

        // Stampare i numeri in ordine decrescente da 10 a 1 utilizzando un ciclo "for":
        // for(int i=10; i>0; i--){
        // System.out.println(i);
        // }

        // System.out.println("inserisci");
        // num = input.nextInt();

        // for(int i = 0; i <= num; i++){
        // System.out.println(i);
        // }

        // int [] numeri = {2,3,4,5,6};
        // for(int numero : numeri){
        // System.out.println(numero);
        // }

        // double somma = 0.0;
        // double [] numeri = {2.5,4.5,5.7};
        // for(double numero : numeri){
        // somma +=numero;
        // }
        // System.out.println(somma);

        // str = "ciao";
        // for(char carattere : str.toCharArray()){
        // System.out.println(carattere);
        // }

        // str = "salvo";
        // int count=0;
        // for(char carattere : str.toCharArray()){
        // count++;
        // }
        // System.out.println(count);

        // int count = 0;
        // String [] lista = {"salvo","giuseppe","alberto"};
        // for(String nomi : lista){
        // count++;
        // }
        // System.out.println(count);

        // Calcolare la media dei voti in un array di interi utilizzando il ciclo
        // "foreach":
        // double [] numeri = {2,5,9,11};
        // double somma = 0.0;
        // for(double numero : numeri){
        // somma += numero;
        // }
        // System.out.println(somma / numeri.length);

        // List<Persona> lista = new ArrayList<>();
        // //se uso costruttore personalizzato
        // lista.add(new Persona("giuseppe"));
        // lista.add(new Persona("Salvo"));
        // lista.add(new Persona("alberto"));

        // // se uso costruttore personalizzato
        // Persona p = new Persona();
        // p.setNome("Antonio");
        // lista.add(p);

        // for (Persona persone : lista) {
        //     System.out.println(persone);
        // }


        // Map <Integer,Persona> lista = new HashMap<>();
        // lista.put(1,new Persona("Silvio"));
        // lista.put(2, new Persona("Davide"));
        // lista.put(3, new Persona("Roberto"));

        // for (Entry<Integer, Persona> entry : lista.entrySet()) {
        //     System.out.println(entry.getKey() + " - " + entry.getValue());
        // }

    }
}
