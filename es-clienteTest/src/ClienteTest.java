public class ClienteTest {
    public static void main (String args[]) {
        
        Cliente mioCliente = new Cliente();
        mioCliente.clienteID = 12345;
        mioCliente.clienteDescrizione = false;
        mioCliente.importoTotale = 27.99;
        
        mioCliente.displayInfoCliente(); 
    }
}