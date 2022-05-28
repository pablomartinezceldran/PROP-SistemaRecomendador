package CapaPresentacio.Vistas;

import CapaPresentacio.Controlador.ControladorPresentacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Vista que mostra els items i les valoracions de l'usuari
 */
public class VistaBuscadorItems extends JDialog {

    private final ControladorPresentacion iCtrlPresentacion;

    private final JPanel contentPanel = new JPanel();
    private JTextField txtBuscarUnItem;
    private JPanel panel_1;
    private JTable table;

    private Object[][] valores;

    /**
     * Constructora de la vista
     * @param ctrlPresentacion controlador de presentacion
     */
    public VistaBuscadorItems(ControladorPresentacion ctrlPresentacion) {
        this.iCtrlPresentacion = ctrlPresentacion;
        this.setTitle("Valoraciones");
        iniMarcoBasico();
    }

    private void iniMarcoBasico() {
        setSize(600, 600);
        setMinimumSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        panel_1 = new JPanel(new BorderLayout());
        contentPanel.add(panel_1, BorderLayout.CENTER);

        table = new JTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollBar sb = new JScrollBar();
        sb.setUnitIncrement(25);
        scroll.setVerticalScrollBar(sb);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        valores = iCtrlPresentacion.getItems();
        table.setModel(new DefaultTableModel(
                valores,
                new String[]{
                        "Item ID", "Nombre", "Valoracion"
                }
        ) {
            Class[] columnTypes = new Class[]{
                    Integer.class, String.class, Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            boolean[] columnEditables = new boolean[]{
                    false, false, true
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(0).setMinWidth(70);
        table.getColumnModel().getColumn(0).setMaxWidth(70);
        table.getColumnModel().getColumn(1).setMinWidth(120);
        table.getColumnModel().getColumn(2).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMaxWidth(75);
        panel_1.add(scroll, BorderLayout.CENTER);

        JPanel bottomMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(e -> {
            if (table.getCellEditor() != null) {
                table.getCellEditor().stopCellEditing();
            }
            Object[][] res = new Object[valores.length][valores[0].length];
            for (int i = 0; i < valores.length; ++i){
                for(int j = 0; j < valores[0].length; ++j) {
                    res[i][j] = table.getValueAt(i,j);
                }
            }
            boolean correcto = true;
            for(Object[] valor : res) {
                if (valor[2] != null && Double.parseDouble(valor[2].toString()) > iCtrlPresentacion.getval_maxima()) {
                    correcto = false;
                    break;
                }
            }
            if (correcto) {
                iCtrlPresentacion.actualizaValoraciones(res);
                iCtrlPresentacion.recalcularRecomendaciones();
                iCtrlPresentacion.guardarCambiosVal();
                setVisible(false);
            }
            else {
                iCtrlPresentacion.aviso("Hay valoraciones erroneas.");
            }
        });
        bottomMenu.add(guardar);
        contentPanel.add(bottomMenu, BorderLayout.SOUTH);
    }
}
