public class Cliente {
    
    public int clienteID;
    public boolean clienteDescrizione;
    public double importoTotale;

    public void displayInfoCliente() {
        System.out.println("ID del cliente: " + clienteID);
        if (clienteDescrizione) {
            System.out.println("Nuovo cliente");
        } else {
            System.out.println("Cliente Preregistrato");
        }
        System.out.println("Importo totale degli acquisti: " + importoTotale + " euro");
    }
}