import CapaPresentacio.Controlador.ControladorPresentacion;

public class Main {
    public static void main (String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        javax.swing.SwingUtilities.invokeLater (
                () -> {
                    ControladorPresentacion ctrlPresentacion = new ControladorPresentacion();
                    ctrlPresentacion.iniciarAplicacion();
                }
        );
    }
}