package CapaPresentacio.Vistas;

import CapaPresentacio.Controlador.ControladorPresentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Vista que mostra un missatge informatiu
 */
public class Aviso extends JDialog {

    private final ControladorPresentacion iCtrlPresentacion;
    private JLabel mensaje;

    /**
     * Constructora de la vista
     * @param ctrlPresentacion controlador de presentacion
     */
    public Aviso(ControladorPresentacion ctrlPresentacion) {
        this.iCtrlPresentacion = ctrlPresentacion;
        this.setTitle("Aviso");
        iniMarcoBasico();
    }

    /**
     * Metode que permet modificar el missatge que es mostra
     * @param mensaje missatge que es mostra
     */
    public void setMensaje(String mensaje) {
        this.mensaje.setText(mensaje);
    }

    private void iniMarcoBasico() {
        setSize(300, 150);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        mensaje = new JLabel("");
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(mensaje, BorderLayout.CENTER);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(e -> setVisible(false));
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
    }
}
