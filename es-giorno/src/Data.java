public class Data {
    public int giorno;

    public void mostraGiorno() {
        switch (giorno) {
            case 1:
                System.out.println("Giorno della settimana: Lunedì");
                break;
            case 2:
                System.out.println("Giorno della settimana: Martedì");
                break;
            case 3:
                System.out.println("Giorno della settimana: Mercoledì");
                break;
            case 4:
                System.out.println("Giorno della settimana: Giovedì");
                break;
            case 5:
                System.out.println("Giorno della settimana: Venerdì");
                break;
            case 6:
                System.out.println("Giorno della settimana: Sabato");
                break;
            case 7:
                System.out.println("Giorno della settimana: Domenica");
                break;
            default:
            System.out.println("Giorno inserito non valido!");
        }
    }
}
