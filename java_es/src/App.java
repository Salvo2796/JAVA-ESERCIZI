public class App {
    public static void main(String[] args) throws Exception {
        ControlloNumero cn = new ControlloNumero();
        
        boolean flag = true;

        do {
            int scelta = cn.menu();
            switch (scelta) {
                case 1:
                    cn.controlloPariDispari();
                    break;
                case 2:
                    cn.controlloPariPositivo();
                    break;
                case 3:
                    cn.controlloStringa();
                    break;
                case 4:
                    cn.sconto();
                    break;
                case 5:
                    cn.valoreMassimo();
                    break;
                case 6:
                    cn.trovaMinore();
                    break;
                case 7:
                    cn.ciclo();
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    break;
            }
        } while (flag);
    }
    
}
