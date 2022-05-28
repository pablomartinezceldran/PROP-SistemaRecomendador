package CapaPresentacio.Vistas;

import CapaPresentacio.Controlador.ControladorPresentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;
import java.util.Map;

/**
 * Vista que mostra la pagina de configuracio de l'aplicacio
 */
public class VistaConfiguracion extends JFrame {

    private final ControladorPresentacion iCtrlPresentacion;
    private JComboBox comboBox;
    private JComboBox comboBox_1;
    private JPanel panel_5;
    private ArrayList<String> atributos;
    private JDialog nuevoItem;
    private JTextField textField_4;
    private JTextField textField_5;


    /**
     * Constructora de la vista
     * @param ctrlPresentacion controlador de presentacion
     */
    public VistaConfiguracion(ControladorPresentacion ctrlPresentacion) {
        this.iCtrlPresentacion = ctrlPresentacion;
        this.setTitle("Configuraci\u00F3n");
        atributos = iCtrlPresentacion.getNombresAtributos();
        nuevoItem = new JDialog();
        iniMarcoBasico();
    }

    private void iniMarcoBasico() {
        setSize(1000, 800);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel contentPane;
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel BotPanel = new JPanel();
        FlowLayout fl_BotPanel = (FlowLayout) BotPanel.getLayout();
        fl_BotPanel.setAlignment(FlowLayout.RIGHT);
        contentPane.add(BotPanel, BorderLayout.SOUTH);

        JButton btnNewButton = new JButton("Guardar");
        BotPanel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Cancelar");
        BotPanel.add(btnNewButton_1);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 0, 10));

        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_1 = new JLabel("Atributos filtro");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel_2.add(lblNewLabel_1, BorderLayout.NORTH);

        panel_5 = new JPanel();
        panel_2.add(panel_5, BorderLayout.CENTER);
        panel_5.setLayout(new GridLayout(0, 4, 20, 0));
        actualizarPanelFiltro();

        JPanel panel_3 = new JPanel();
        panel.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_2 = new JLabel("Atributos importantes");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel_3.add(lblNewLabel_2, BorderLayout.NORTH);

        JPanel panel_3_1 = new JPanel();
        panel_3.add(panel_3_1, BorderLayout.WEST);

        JPanel panel_4_1 = new JPanel();
        panel_3_1.add(panel_4_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_4_1 = new GridBagLayout();
        gbl_panel_4_1.columnWidths = new int[] {70, 200};
        gbl_panel_4_1.rowHeights = new int[] {0, 0, 0, 0, 0};
        gbl_panel_4_1.columnWeights = new double[]{0.0, 1.0};
        gbl_panel_4_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        panel_4_1.setLayout(gbl_panel_4_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Identificador:");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_1_1_1 = new GridBagConstraints();
        gbc_lblNewLabel_1_1_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_1_1_1.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_1_1_1.insets = new Insets(0, 5, 0, 5);
        gbc_lblNewLabel_1_1_1.gridx = 0;
        gbc_lblNewLabel_1_1_1.gridy = 0;
        panel_4_1.add(lblNewLabel_1_1_1, gbc_lblNewLabel_1_1_1);

        comboBox = new JComboBox(atributos.toArray());
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 5, 5, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        panel_4_1.add(comboBox, gbc_comboBox);

        JLabel lblNewLabel_2_1_1 = new JLabel("Nombre:");
        lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_2_1_1 = new GridBagConstraints();
        gbc_lblNewLabel_2_1_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_2_1_1.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_2_1_1.insets = new Insets(0, 5, 0, 5);
        gbc_lblNewLabel_2_1_1.gridx = 0;
        gbc_lblNewLabel_2_1_1.gridy = 1;
        panel_4_1.add(lblNewLabel_2_1_1, gbc_lblNewLabel_2_1_1);

        comboBox_1 = new JComboBox(atributos.toArray());
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 5, 5, 0);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 1;
        gbc_comboBox_1.gridy = 1;
        panel_4_1.add(comboBox_1, gbc_comboBox_1);

        int valoracionMax = 0;
        int itemsPorRec = 0;
        int k = 0;
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            String p = properties.getProperty("VALORACION_MAXIMA");
            if (p != null && !p.equals("")) valoracionMax = Integer.parseInt(p);
            p = properties.getProperty("ITEMS_POR_RECOMENDACION");
            if (p != null && !p.equals("")) itemsPorRec = Integer.parseInt(p);
            p = properties.getProperty("VALOR_K");
            if (p != null && !p.equals("")) k = Integer.parseInt(p);
        } catch (IOException ignored) {}

        JLabel lblNewLabel_3_1_1 = new JLabel("Valoracion maxima:");
        lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
        gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_3_1_1.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_3_1_1.insets = new Insets(0, 5, 0, 5);
        gbc_lblNewLabel_3_1_1.gridx = 0;
        gbc_lblNewLabel_3_1_1.gridy = 2;
        panel_4_1.add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);

        JTextField tf = new JTextField();
        tf.setColumns(5);
        tf.setText(String.valueOf(valoracionMax));
        tf.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_tf = new GridBagConstraints();
        gbc_tf.anchor = GridBagConstraints.EAST;
        gbc_tf.fill = GridBagConstraints.VERTICAL;
        gbc_tf.insets = new Insets(0, 5, 5, 0);
        gbc_tf.gridx = 1;
        gbc_tf.gridy = 2;
        panel_4_1.add(tf, gbc_tf);

        JLabel lblNewLabel_8 = new JLabel("Items por Recomendacion:");
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_8.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_8.insets = new Insets(0, 5, 0, 5);
        gbc_lblNewLabel_8.gridx = 0;
        gbc_lblNewLabel_8.gridy = 3;
        panel_4_1.add(lblNewLabel_8, gbc_lblNewLabel_8);

        textField_4 = new JTextField();
        textField_4.setHorizontalAlignment(SwingConstants.RIGHT);
        textField_4.setText(String.valueOf(itemsPorRec));
        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.anchor = GridBagConstraints.EAST;
        gbc_textField_4.insets = new Insets(0, 5, 5, 0);
        gbc_textField_4.gridx = 1;
        gbc_textField_4.gridy = 3;
        panel_4_1.add(textField_4, gbc_textField_4);
        textField_4.setColumns(5);

        JLabel lblNewLabel_9 = new JLabel("k (k-means):");
        GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
        gbc_lblNewLabel_9.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_9.insets = new Insets(0, 5, 0, 5);
        gbc_lblNewLabel_9.gridx = 0;
        gbc_lblNewLabel_9.gridy = 4;
        panel_4_1.add(lblNewLabel_9, gbc_lblNewLabel_9);

        textField_5 = new JTextField();
        textField_5.setHorizontalAlignment(SwingConstants.RIGHT);
        textField_5.setText(String.valueOf(k));
        GridBagConstraints gbc_textField_5 = new GridBagConstraints();
        gbc_textField_5.anchor = GridBagConstraints.EAST;
        gbc_textField_5.insets = new Insets(0, 5, 0, 0);
        gbc_textField_5.gridx = 1;
        gbc_textField_5.gridy = 4;
        panel_4_1.add(textField_5, gbc_textField_5);
        textField_5.setColumns(5);

        JPanel panel_7 = new JPanel();
        panel.add(panel_7);
        panel_7.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_1_2 = new JLabel("Gestion de Items y Usuarios");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel_7.add(lblNewLabel_1_2, BorderLayout.NORTH);

        JPanel panel_8 = new JPanel();
        panel_7.add(panel_8, BorderLayout.CENTER);
        GridBagLayout gbl_panel_8 = new GridBagLayout();
        gbl_panel_8.columnWidths = new int[] {50, 50, 50, 50, 50, 50, 500};
        gbl_panel_8.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panel_8.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        gbl_panel_8.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_8.setLayout(gbl_panel_8);

        JLabel lblNewLabel_3 = new JLabel("Borrar usuario :");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(10, 10, 5, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 0;
        panel_8.add(lblNewLabel_3, gbc_lblNewLabel_3);

        JComboBox comboBox_2 = new JComboBox(new DefaultComboBoxModel<>( iCtrlPresentacion.getIdsUsuarios().toArray() ));
        comboBox_2.setSelectedIndex(-1);
        GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
        gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_2.insets = new Insets(10, 0, 5, 5);
        gbc_comboBox_2.gridx = 1;
        gbc_comboBox_2.gridy = 0;
        panel_8.add(comboBox_2, gbc_comboBox_2);

        JButton btnNewButton_3 = new JButton("Borrar");
        btnNewButton_3.setVisible(false);
        btnNewButton_3.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
        gbc_btnNewButton_3.anchor = GridBagConstraints.WEST;
        gbc_btnNewButton_3.fill = GridBagConstraints.VERTICAL;
        gbc_btnNewButton_3.insets = new Insets(10, 0, 5, 5);
        gbc_btnNewButton_3.gridx = 2;
        gbc_btnNewButton_3.gridy = 0;
        panel_8.add(btnNewButton_3, gbc_btnNewButton_3);

        JLabel lblNewLabel_6 = new JLabel("Seguro?");
        lblNewLabel_6.setVisible(false);
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.insets = new Insets(10, 0, 5, 5);
        gbc_lblNewLabel_6.gridx = 3;
        gbc_lblNewLabel_6.gridy = 0;
        panel_8.add(lblNewLabel_6, gbc_lblNewLabel_6);

        JButton btnNewButton_5 = new JButton("Si");
        btnNewButton_5.setVisible(false);
        GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
        gbc_btnNewButton_5.insets = new Insets(10, 0, 5, 5);
        gbc_btnNewButton_5.gridx = 4;
        gbc_btnNewButton_5.gridy = 0;
        panel_8.add(btnNewButton_5, gbc_btnNewButton_5);

        JButton btnNewButton_6 = new JButton("No");
        btnNewButton_6.setVisible(false);
        GridBagConstraints gbc_btnNewButton_6 = new GridBagConstraints();
        gbc_btnNewButton_6.insets = new Insets(10, 0, 5, 5);
        gbc_btnNewButton_6.gridx = 5;
        gbc_btnNewButton_6.gridy = 0;
        panel_8.add(btnNewButton_6, gbc_btnNewButton_6);

        JLabel lblNewLabel_4 = new JLabel("Borrar item :");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_4.gridx = 0;
        gbc_lblNewLabel_4.gridy = 1;
        panel_8.add(lblNewLabel_4, gbc_lblNewLabel_4);

        JComboBox comboBox_3 = new JComboBox(new DefaultComboBoxModel<>( iCtrlPresentacion.getIdsItems().toArray() ));
        comboBox_3.setSelectedIndex(-1);
        GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
        gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_3.gridx = 1;
        gbc_comboBox_3.gridy = 1;
        panel_8.add(comboBox_3, gbc_comboBox_3);

        JButton btnNewButton_4 = new JButton("Borrar");
        btnNewButton_4.setVisible(false);
        GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
        gbc_btnNewButton_4.fill = GridBagConstraints.VERTICAL;
        gbc_btnNewButton_4.anchor = GridBagConstraints.WEST;
        gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_4.gridx = 2;
        gbc_btnNewButton_4.gridy = 1;
        panel_8.add(btnNewButton_4, gbc_btnNewButton_4);

        JLabel lblNewLabel_7 = new JLabel("Seguro?");
        lblNewLabel_7.setVisible(false);
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_7.gridx = 3;
        gbc_lblNewLabel_7.gridy = 1;
        panel_8.add(lblNewLabel_7, gbc_lblNewLabel_7);

        JButton btnNewButton_7 = new JButton("Si");
        btnNewButton_7.setVisible(false);
        GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
        gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_7.gridx = 4;
        gbc_btnNewButton_7.gridy = 1;
        panel_8.add(btnNewButton_7, gbc_btnNewButton_7);

        JButton btnNewButton_8 = new JButton("No");
        btnNewButton_8.setVisible(false);
        GridBagConstraints gbc_btnNewButton_8 = new GridBagConstraints();
        gbc_btnNewButton_8.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_8.gridx = 5;
        gbc_btnNewButton_8.gridy = 1;
        panel_8.add(btnNewButton_8, gbc_btnNewButton_8);

        JLabel lblNewLabel_5 = new JLabel("Crear item :");
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_5.gridx = 0;
        gbc_lblNewLabel_5.gridy = 2;
        panel_8.add(lblNewLabel_5, gbc_lblNewLabel_5);

        JButton btnNewButton_2 = new JButton("Nuevo");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.anchor = GridBagConstraints.WEST;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_2.gridx = 1;
        gbc_btnNewButton_2.gridy = 2;
        panel_8.add(btnNewButton_2, gbc_btnNewButton_2);

        //boton nuevo
        btnNewButton_2.addActionListener(e -> {
            nuevoItem = new JDialog();
            nuevoItem.setSize(600, 450);
            nuevoItem.setLocationRelativeTo(null);
            nuevoItem.getContentPane().setLayout(new BorderLayout());

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new GridLayout(0,2,10,0));
            contentPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
            JScrollPane paneI = new JScrollPane(contentPanel);
            paneI.getVerticalScrollBar().setUnitIncrement(10);
            paneI.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            nuevoItem.getContentPane().add(paneI, BorderLayout.CENTER);
            {
                for (String atributo : atributos) {
                    if (!atributo.equals("id")) {
                        contentPanel.add(new JLabel(atributo));
                        contentPanel.add(new JTextField(30));
                    }
                }
            }
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            nuevoItem.getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                buttonPane.add(okButton);
                okButton.addActionListener(e1 -> {
                    Map<String, Object> atributosItemNuevo = new HashMap<>();
                    String nombreAtributo = "";
                    for (Component c : contentPanel.getComponents()) {
                        if (c instanceof JLabel) {
                            nombreAtributo = ((JLabel) c).getText();
                        }
                        if (c instanceof JTextField) {
                            atributosItemNuevo.put(nombreAtributo, ((JTextField) c).getText());
                        }
                    }
                    int nuevoId = iCtrlPresentacion.nuevoItem(atributosItemNuevo);
                    iCtrlPresentacion.aviso("Item con identificador " + nuevoId + " creado correctamente.");
                    comboBox_3.setModel(new DefaultComboBoxModel<>( iCtrlPresentacion.getIdsItems().toArray() ));
                    comboBox_3.setSelectedIndex(-1);
                    nuevoItem.setVisible(false);
                });
                JButton cancelButton = new JButton("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(e1 -> nuevoItem.setVisible(false));
            }
            nuevoItem.setVisible(true);
        });

        //combobox borrar usuario
        comboBox_2.addActionListener(e -> {
            if (comboBox_2.getSelectedIndex() != -1) {
                btnNewButton_3.setVisible(true);
            }
        });

        //boton borrar usuario
        btnNewButton_3.addActionListener(e ->  {
            lblNewLabel_6.setVisible(true);
            btnNewButton_5.setVisible(true);
            btnNewButton_6.setVisible(true);
        });

        //boton <si> borrar usuario
        btnNewButton_5.addActionListener(e -> {
            iCtrlPresentacion.borrarUsuario(Integer.parseInt(Objects.requireNonNull(comboBox_2.getSelectedItem()).toString()));
            btnNewButton_3.setVisible(false);
            lblNewLabel_6.setVisible(false);
            btnNewButton_5.setVisible(false);
            btnNewButton_6.setVisible(false);
            comboBox_2.setModel(new DefaultComboBoxModel<>( iCtrlPresentacion.getIdsUsuarios().toArray() ));
            comboBox_2.setSelectedIndex(-1);
        });

        //boton <no> borrar usuario
        btnNewButton_6.addActionListener(e -> {
            lblNewLabel_6.setVisible(false);
            btnNewButton_5.setVisible(false);
            btnNewButton_6.setVisible(false);
        });

        //combobox borrar item
        comboBox_3.addActionListener(e -> {
            if (comboBox_3.getSelectedIndex() != -1) {
                btnNewButton_4.setVisible(true);
            }
        });

        //boton borrar item
        btnNewButton_4.addActionListener(e -> {
            lblNewLabel_7.setVisible(true);
            btnNewButton_7.setVisible(true);
            btnNewButton_8.setVisible(true);
        });

        //boton <si> borrar item
        btnNewButton_7.addActionListener(e -> {
            iCtrlPresentacion.borrarItem(Integer.parseInt(Objects.requireNonNull(comboBox_3.getSelectedItem()).toString()));
            btnNewButton_4.setVisible(false);
            lblNewLabel_7.setVisible(false);
            btnNewButton_7.setVisible(false);
            btnNewButton_8.setVisible(false);
            comboBox_3.setModel(new DefaultComboBoxModel<>( iCtrlPresentacion.getIdsItems().toArray() ));
            comboBox_3.setSelectedIndex(-1);
        });

        //boton <no> borrar item
        btnNewButton_8.addActionListener(e -> {
            lblNewLabel_7.setVisible(false);
            btnNewButton_7.setVisible(false);
            btnNewButton_8.setVisible(false);
        });

        //boton guardar
        btnNewButton.addActionListener(e -> {
            iCtrlPresentacion.setPropiedad("VALORACION_MAXIMA", tf.getText());
            iCtrlPresentacion.setPropiedad("ITEMS_POR_RECOMENDACION", textField_4.getText());
            iCtrlPresentacion.setPropiedad("VALOR_K", textField_5.getText());

            iCtrlPresentacion.setVarsRec(Integer.parseInt(tf.getText()), Integer.parseInt(textField_4.getText()), Integer.parseInt(textField_5.getText()));

            boolean advertencia = false;

            if(comboBox.getSelectedIndex() != -1)   iCtrlPresentacion.setPropiedad("IDENTIFICADOR", atributos.get(comboBox.getSelectedIndex()));
            else advertencia = true;

            if(comboBox_1.getSelectedIndex() != -1) iCtrlPresentacion.setPropiedad("NOMBRE", atributos.get(comboBox_1.getSelectedIndex()));
            else advertencia = true;

            iCtrlPresentacion.guardaTipos(leerTipos());
            iCtrlPresentacion.setTipos();
            iCtrlPresentacion.setPesos(leerPesos());
            iCtrlPresentacion.guardarCambios();
            iCtrlPresentacion.recalcularRecomendaciones();

            if(advertencia) iCtrlPresentacion.aviso("Hay campos obligatorios sin completar");
            else setVisible(false);
        });

        //boton cancelar
        btnNewButton_1.addActionListener(e -> {
            setVisible(false);
        });
        cargarComboBoxes();
    }

    private void actualizarPanelFiltro() {
        ArrayList<Double> pesos = iCtrlPresentacion.getPesos();
        DefaultComboBoxModel<Object> model1;
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("String");
        opciones.add("Numeric");
        for(String atributo : atributos) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel(atributo),BorderLayout.WEST);
            double valor = pesos.get(atributos.indexOf(atributo));
            SpinnerNumberModel model = new SpinnerNumberModel(valor, 0.0, 1.0, 0.1);
            JSpinner spinner = new JSpinner(model);
            model1 = new DefaultComboBoxModel<>( opciones.toArray() );
            JComboBox cb = new JComboBox(model1);
            cb.setSize(100,0);
            cb.setSelectedItem(iCtrlPresentacion.getTipo(atributos.indexOf(atributo)));
            JPanel panel1 = new JPanel(new BorderLayout());
            panel1.add(cb, BorderLayout.CENTER);
            panel1.add(spinner, BorderLayout.EAST);
            panel.add(panel1, BorderLayout.EAST);
            panel_5.add(panel);
        }
    }

    private ArrayList<String> leerTipos() {
        ArrayList<String> tipos = new ArrayList<>();
        for (Component c : panel_5.getComponents()) {
            if (c instanceof JPanel) {
                for (Component c1 : ((JPanel) c).getComponents()) {
                    if(c1 instanceof JPanel) {
                        for (Component c2 : ((JPanel) c1).getComponents()) {
                            if (c2 instanceof JComboBox) {
                                tipos.add((String) ((JComboBox<?>) c2).getSelectedItem());
                            }
                        }
                    }
                }
            }
        }
        return tipos;
    }

    private ArrayList<Double> leerPesos() {
        ArrayList<Double> pesos = new ArrayList<>();
        for (Component c : panel_5.getComponents()) {
            if (c instanceof JPanel) {
                for (Component c1 : ((JPanel) c).getComponents()) {
                    if (c1 instanceof JPanel) {
                        for (Component c2 : ((JPanel) c1).getComponents()) {
                            if (c2 instanceof JSpinner) {
                                pesos.add((Double) ((JSpinner) c2).getValue());
                            }
                        }
                    }
                }
            }
        }
        return pesos;
    }

    private void cargarComboBoxes() {
        int index = -1;
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            String p = properties.getProperty("IDENTIFICADOR");
            if (p != null && !p.equals("")) index = atributos.indexOf(p);
        } catch (IOException ignored) {}
        comboBox.setSelectedIndex(index);

        index = -1;
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            String p = properties.getProperty("NOMBRE");
            if (p != null && !p.equals("")) index = atributos.indexOf(p);
        } catch (IOException ignored) {}
        comboBox_1.setSelectedIndex(index);
    }
}