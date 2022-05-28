package CapaPresentacio.Vistas;

import CapaDomini.Model.Auxiliares.Pair;
import CapaPresentacio.Controlador.ControladorPresentacion;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista que mostra la pagina principal de l'aplicacio
 */
public class VistaMain extends JFrame {

    private final ControladorPresentacion iCtrlPresentacion;


    /**
     * Constructora de la vista
     * @param ctrlPresentacion controlador de presentacion
     */
    public VistaMain(ControladorPresentacion ctrlPresentacion) {
        iCtrlPresentacion = ctrlPresentacion;
        setTitle("P\u00E1gina principal");
        iniMarco();
    }

    private void iniMarco() {
        setMinimumSize(new Dimension(800, 600));
        setSize( 1200, 900);
        setResizable(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel menuPanel = new JPanel();
        FlowLayout fl_menuPanel = (FlowLayout) menuPanel.getLayout();
        fl_menuPanel.setAlignment(FlowLayout.RIGHT);
        contentPane.add(menuPanel, BorderLayout.NORTH);

        JLabel userId = new JLabel("USER: " + iCtrlPresentacion.getUsuarioActivoId());
        userId.setFont(new Font("Serif", Font.PLAIN, 20));
        menuPanel.add(userId);

        JButton mis_valoraciones = new JButton("Mis valoraciones");
        mis_valoraciones.setFont(UIManager.getFont("Button.font"));
        mis_valoraciones.addActionListener(e -> {
            consultarItems();
        });
        menuPanel.add(mis_valoraciones);

        JPanel mainPanel = new JPanel();
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JPanel itemsParecidosPanel = new JPanel();
        itemsParecidosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(itemsParecidosPanel);
        itemsParecidosPanel.setLayout(new BorderLayout(0, 0));

        JLabel tituloIPP = new JLabel("Collaborative filtering");
        itemsParecidosPanel.add(tituloIPP, BorderLayout.NORTH);
        tituloIPP.setFont(new Font("Tahoma", Font.PLAIN, 25));

        JPanel itemsIPPPanel = new JPanel(new GridLayout(1, 10, 5, 0));
        JScrollPane paneI = new JScrollPane(itemsIPPPanel);
        paneI.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        itemsParecidosPanel.add(paneI, BorderLayout.CENTER);
        ArrayList<Pair<Integer,String>> itemsRecomendadosI = iCtrlPresentacion.getItemsRecomendados(0);
        for (Pair<Integer, String> item : itemsRecomendadosI) {
            JPanel cartel = new JPanel(new BorderLayout());
            cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
            cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
            cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
            itemsIPPPanel.add(cartel);
        }
        JPanel botCF = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardarRecCF = new JButton("Guardar");
        guardarRecCF.addActionListener(e -> {
            iCtrlPresentacion.guardarRecomendacion(0);
            iCtrlPresentacion.guardarCambiosRec();
            guardarRecCF.setEnabled(false);
        });
        JLabel NDCGcfLabel = new JLabel();
        double scale = Math.pow(10, 3);
        if (iCtrlPresentacion.getTesting()) {
            double NDCGcf = iCtrlPresentacion.getNDCG(0);
            if (NDCGcf != 0 ) {
                NDCGcf = Math.round(NDCGcf * scale) / scale;
                NDCGcfLabel.setText("NDCG: " + NDCGcf);
                botCF.add(NDCGcfLabel);
            }
        }
        botCF.add(guardarRecCF);
        itemsParecidosPanel.add(botCF,BorderLayout.SOUTH);

        JPanel usersParecidosPanel = new JPanel();
        usersParecidosPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(usersParecidosPanel);
        usersParecidosPanel.setLayout(new BorderLayout(0, 0));

        JLabel tituloUPP = new JLabel("Content-based filtering");
        tituloUPP.setFont(new Font("Tahoma", Font.PLAIN, 25));
        usersParecidosPanel.add(tituloUPP, BorderLayout.NORTH);

        JPanel itemsUPPPanel = new JPanel(new GridLayout(1, 10, 5, 0));
        JScrollPane paneU = new JScrollPane(itemsUPPPanel);
        paneU.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        usersParecidosPanel.add(paneU, BorderLayout.CENTER);
        ArrayList<Pair<Integer,String>> itemsRecomendadosU = iCtrlPresentacion.getItemsRecomendados(1);
        for (Pair<Integer, String> item: itemsRecomendadosU) {
            JPanel cartel = new JPanel(new BorderLayout());
            cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
            cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
            cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
            itemsUPPPanel.add(cartel);
        }
        JPanel botCB = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardarRecCB = new JButton("Guardar");
        guardarRecCB.addActionListener(e -> {
            iCtrlPresentacion.guardarRecomendacion(1);
            iCtrlPresentacion.guardarCambiosRec();
            guardarRecCB.setEnabled(false);
        });
        JLabel NDCGcbLabel = new JLabel();
        if (iCtrlPresentacion.getTesting()) {
            double NDCGcb = iCtrlPresentacion.getNDCG(1);
            if (NDCGcb != 0) {
                NDCGcb = Math.round(NDCGcb * scale) / scale;
                NDCGcbLabel.setText("NDCG: " + NDCGcb);
                botCB.add(NDCGcbLabel);
            }
        }
        botCB.add(guardarRecCB);
        usersParecidosPanel.add(botCB,BorderLayout.SOUTH);

        JPanel HAPP = new JPanel();
        HAPP.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPanel.add(HAPP);
        HAPP.setLayout(new BorderLayout(0, 0));

        JLabel tituloHAPP = new JLabel("Hybrid Approach filtering");
        tituloHAPP.setFont(new Font("Tahoma", Font.PLAIN, 25));
        HAPP.add(tituloHAPP, BorderLayout.NORTH);

        JPanel itemsHAPPanel = new JPanel(new GridLayout(1, 10, 5, 0));
        JScrollPane HAPPpaneU = new JScrollPane(itemsHAPPanel);
        HAPPpaneU.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        HAPP.add(HAPPpaneU, BorderLayout.CENTER);
        ArrayList<Pair<Integer,String>> itemsRecomendadosHAPP = iCtrlPresentacion.getItemsRecomendados(2);
        for (Pair<Integer, String> item: itemsRecomendadosHAPP) {
            JPanel cartel = new JPanel(new BorderLayout());
            cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
            cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
            cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
            itemsHAPPanel.add(cartel);
        }
        JPanel botHA = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardarRecHA = new JButton("Guardar");
        guardarRecHA.addActionListener(e -> {
            iCtrlPresentacion.guardarRecomendacion(2);
            iCtrlPresentacion.guardarCambiosRec();
            guardarRecHA.setEnabled(false);
        });
        JLabel NDCGhaLabel = new JLabel();
        if (iCtrlPresentacion.getTesting()) {
            double NDCGh = iCtrlPresentacion.getNDCG(2);
            if (NDCGh != 0) {
                NDCGh = Math.round(NDCGh * scale) / scale;
                NDCGhaLabel.setText("NDCG: " + NDCGh);
                botHA.add(NDCGhaLabel);
            }
        }
        botHA.add(guardarRecHA);
        HAPP.add(botHA,BorderLayout.SOUTH);

        JPanel BotPanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) BotPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        contentPane.add(BotPanel, BorderLayout.SOUTH);

        JButton Refresh = new JButton("Recalcular recomendaciones");
        BotPanel.add(Refresh);
        Refresh.addActionListener(e -> {
            itemsIPPPanel.removeAll();
            itemsUPPPanel.removeAll();
            itemsHAPPanel.removeAll();
            ArrayList<Pair<Integer,String>> itemsRecomendadosI2 = iCtrlPresentacion.getItemsRecomendados(0);
            for (Pair<Integer, String> item: itemsRecomendadosI2) {
                JPanel cartel = new JPanel(new BorderLayout());
                cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                itemsIPPPanel.add(cartel);
            }
            itemsIPPPanel.updateUI();
            ArrayList<Pair<Integer,String>> itemsRecomendadosU2 = iCtrlPresentacion.getItemsRecomendados(1);
            for (Pair<Integer, String> item: itemsRecomendadosU2) {
                JPanel cartel = new JPanel(new BorderLayout());
                cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                itemsUPPPanel.add(cartel);
            }
            itemsUPPPanel.updateUI();
            ArrayList<Pair<Integer,String>> itemsRecomendadosH2 = iCtrlPresentacion.getItemsRecomendados(2);
            for (Pair<Integer, String> item: itemsRecomendadosH2) {
                JPanel cartel = new JPanel(new BorderLayout());
                cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                itemsHAPPanel.add(cartel);
            }
            itemsHAPPanel.updateUI();
            guardarRecCF.setEnabled(true);
            guardarRecCB.setEnabled(true);
            guardarRecHA.setEnabled(true);

            if (iCtrlPresentacion.getTesting()) {
                double NDCGcf = iCtrlPresentacion.getNDCG(0);
                double NDCGcb = iCtrlPresentacion.getNDCG(1);
                double NDCGh = iCtrlPresentacion.getNDCG(2);
                if (NDCGcf != 0) {
                    NDCGcf = Math.round(NDCGcf * scale) / scale;
                    NDCGcfLabel.setText("NDCG: " + NDCGcf);
                }
                if (NDCGcb != 0) {
                    NDCGcb = Math.round(NDCGcb * scale) / scale;
                    NDCGcbLabel.setText("NDCG: " + NDCGcb);
                }
                if (NDCGh != 0) {
                    NDCGh = Math.round(NDCGh * scale) / scale;
                    NDCGhaLabel.setText("NDCG: " + NDCGh);
                }
            }
        });

        JButton Guardadas = new JButton("Recomendaciones guardadas");
        BotPanel.add(Guardadas);
        Guardadas.addActionListener(e -> {
            if (Guardadas.getText().equals("Recomendaciones guardadas")) {
                Guardadas.setText("Calcular recomendaciones");
                ArrayList<List<String>> recomendacionesGuardadas = iCtrlPresentacion.getRecomendacionesGuardadasMostrar();
                tituloIPP.setText("Recomendacion guardada 1");
                tituloUPP.setText("Recomendacion guardada 2");
                tituloHAPP.setText("Recomendacion guardada 3");
                itemsIPPPanel.removeAll();
                itemsUPPPanel.removeAll();
                itemsHAPPanel.removeAll();
                botCF.setVisible(false);
                botCB.setVisible(false);
                botHA.setVisible(false);
                Refresh.setVisible(false);

                if (recomendacionesGuardadas.size() != 0) {
                    for (int i = 0; i < recomendacionesGuardadas.get(0).size(); i += 2) {
                        String itemID = recomendacionesGuardadas.get(0).get(i);
                        String itemTitle = recomendacionesGuardadas.get(0).get(i + 1);
                        JPanel cartel = new JPanel(new BorderLayout());
                        cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                        cartel.add(new JLabel("ID: " + itemID, SwingConstants.RIGHT), BorderLayout.SOUTH);
                        cartel.add(new JLabel(itemTitle, SwingConstants.CENTER), BorderLayout.NORTH);
                        itemsIPPPanel.add(cartel);
                    }
                    recomendacionesGuardadas.remove(0);
                }
                if (recomendacionesGuardadas.size() != 0) {
                    for (int i = 0; i < recomendacionesGuardadas.get(0).size(); i += 2) {
                        String itemID = recomendacionesGuardadas.get(0).get(i);
                        String itemTitle = recomendacionesGuardadas.get(0).get(i + 1);
                        JPanel cartel = new JPanel(new BorderLayout());
                        cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                        cartel.add(new JLabel("ID: " + itemID, SwingConstants.RIGHT), BorderLayout.SOUTH);
                        cartel.add(new JLabel(itemTitle, SwingConstants.CENTER), BorderLayout.NORTH);
                        itemsUPPPanel.add(cartel);
                    }
                    recomendacionesGuardadas.remove(0);
                }
                if (recomendacionesGuardadas.size() != 0) {
                    for (int i = 0; i < recomendacionesGuardadas.get(0).size(); i += 2) {
                        String itemID = recomendacionesGuardadas.get(0).get(i);
                        String itemTitle = recomendacionesGuardadas.get(0).get(i + 1);
                        JPanel cartel = new JPanel(new BorderLayout());
                        cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                        cartel.add(new JLabel("ID: " + itemID, SwingConstants.RIGHT), BorderLayout.SOUTH);
                        cartel.add(new JLabel(itemTitle, SwingConstants.CENTER), BorderLayout.NORTH);
                        itemsHAPPanel.add(cartel);
                    }
                    recomendacionesGuardadas.remove(0);
                }
            }
            else {
                Guardadas.setText("Recomendaciones guardadas");
                botCF.setVisible(true);
                botCB.setVisible(true);
                botHA.setVisible(true);
                Refresh.setVisible(true);
                itemsIPPPanel.removeAll();
                itemsUPPPanel.removeAll();
                itemsHAPPanel.removeAll();
                tituloIPP.setText("Collaborative filtering");
                tituloUPP.setText("Content-based filtering");
                tituloHAPP.setText("Hybrid Approach filtering");
                for (Pair<Integer, String> item: itemsRecomendadosI) {
                    JPanel cartel = new JPanel(new BorderLayout());
                    cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                    cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                    cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                    itemsIPPPanel.add(cartel);
                }
                for (Pair<Integer, String> item: itemsRecomendadosU) {
                    JPanel cartel = new JPanel(new BorderLayout());
                    cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                    cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                    cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                    itemsUPPPanel.add(cartel);
                }
                for (Pair<Integer, String> item: itemsRecomendadosHAPP) {
                    JPanel cartel = new JPanel(new BorderLayout());
                    cartel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
                    cartel.add(new JLabel("ID: " + String.valueOf(item.getFirst()), SwingConstants.RIGHT), BorderLayout.SOUTH);
                    cartel.add(new JLabel(item.getSecond(), SwingConstants.CENTER), BorderLayout.NORTH);
                    itemsHAPPanel.add(cartel);
                }
            }
        });

        JButton Salir = new JButton("Salir");
        BotPanel.add(Salir);
        Salir.addActionListener(e -> {
            setVisible(false);
            iCtrlPresentacion.iniciarLogIn();
        });
    }

    private void consultarItems() {
        iCtrlPresentacion.visibleBuscadorItems();
    }
}
