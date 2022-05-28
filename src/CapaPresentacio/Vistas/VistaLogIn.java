package CapaPresentacio.Vistas;

import CapaPresentacio.Controlador.ControladorPresentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

/**
 * Vista que mostra la pagina de login de l'aplicacio
 */
public class VistaLogIn extends JFrame {

    private final ControladorPresentacion iCtrlPresentacion;

    /**
     * Constructora de la vista
     * @param iCtrlPresentacion controlador de presentacion
     */
    public VistaLogIn(ControladorPresentacion iCtrlPresentacion) {
        this.iCtrlPresentacion = iCtrlPresentacion;
        this.setTitle("Log in");
        iniComponentes();
    }

    private void iniComponentes() {
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception ignored){}

        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        contentPane.add(panel, BorderLayout.NORTH);

        JCheckBox chckbxNewCheckBox = new JCheckBox("Evaluar recomendacion");
        panel.add(chckbxNewCheckBox);
        iCtrlPresentacion.setTesting(chckbxNewCheckBox.isSelected());
        chckbxNewCheckBox.addActionListener(e -> {
            iCtrlPresentacion.setTesting(chckbxNewCheckBox.isSelected());
        });

        JButton btnNewButton = new JButton("Config");
        btnNewButton.addActionListener(e -> {
            iCtrlPresentacion.visibleConfiguracion();
        });
        panel.add(btnNewButton);

        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
        flowLayout_1.setAlignment(FlowLayout.RIGHT);
        contentPane.add(panel_1, BorderLayout.SOUTH);

        JLabel lblNewLabel_1 = new JLabel();
        panel_1.add(lblNewLabel_1);

        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        JFormattedTextField formattedTextField = new JFormattedTextField(formatter);

        JButton btnNewButton_1 = new JButton("Validar");
        btnNewButton_1.setEnabled(false);

        JButton btnNewButton_2 = new JButton("Salir");
        btnNewButton_2.addActionListener(e -> {
            System.exit(0);
        });

        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel_2.add(panel_3, BorderLayout.CENTER);

        JLabel lblNewLabel = new JLabel("User ID:");
        panel_3.add(lblNewLabel);

        formattedTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!formattedTextField.getText().isEmpty()) btnNewButton_1.setEnabled(true);
            }
        });
        formattedTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                formattedTextField.setValue(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        formattedTextField.addActionListener(e -> {
            if (formattedTextField.getValue() != null) {
                int usuarioActivoID = (int) formattedTextField.getValue();
                if (iCtrlPresentacion.setUsuarioActivo(usuarioActivoID)) {
                    setVisible(false);
                    iCtrlPresentacion.iniciarMainPage();
                    lblNewLabel_1.setText("");
                }
                else lblNewLabel_1.setText("Usuario no encontrado.");
            }
        });
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                formattedTextField.requestFocusInWindow();
            }
        });
        formattedTextField.setColumns(10);
        panel_3.add(formattedTextField);

        JButton nuevoUsuario = new JButton("Nuevo Usuario");
        panel_1.add(nuevoUsuario);
        panel_1.add(btnNewButton_2);

        nuevoUsuario.addActionListener(e -> {
            iCtrlPresentacion.aviso("Se te ha asignado el identificador " + iCtrlPresentacion.nuevoUsuario() + ".");
            iCtrlPresentacion.recalcularRecomendaciones();
            iCtrlPresentacion.guardarCambios();
        });

        btnNewButton_1.addActionListener(e -> {
            if (formattedTextField.getValue() != null) {
                int usuarioActivoID = (int) formattedTextField.getValue();

                if (iCtrlPresentacion.setUsuarioActivo(usuarioActivoID)) {
                    setVisible(false);
                    iCtrlPresentacion.iniciarMainPage();
                    lblNewLabel_1.setText("");
                }
                else lblNewLabel_1.setText("Usuario no encontrado.");
            }
        });
        panel_3.add(btnNewButton_1);

        Component verticalStrut = Box.createVerticalStrut(75);
        panel_2.add(verticalStrut, BorderLayout.NORTH);
    }
}
