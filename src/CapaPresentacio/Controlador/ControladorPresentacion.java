package CapaPresentacio.Controlador;

import CapaDomini.Controladors.ControladorDominio;
import CapaDomini.Model.Auxiliares.Pair;
import CapaPresentacio.Vistas.*;

import java.io.*;
import java.util.*;

/**
 * Controlador de Presentacio.
 * Controla les vistes i la connexio amb domini
 */
public class ControladorPresentacion {

    //Controladores
    private ControladorDominio ctrlDominio;

    //Atributos
    private Properties properties;

    //Vistas
    private VistaLogIn vistaLogIn;
    private VistaConfiguracion vistaConfiguracion;
    private VistaMain vistaMain;
    private VistaBuscadorItems vistaBuscadorItems;
    private Aviso aviso;

    private boolean testing;

    /**
     * Constructora
     */
    public ControladorPresentacion() {
        properties = new Properties();
    }

    /**
     * Metode que inicia l'aplicacio
     */
    public void iniciarAplicacion() {
        boolean b;
        try {
            File usuarios = new File("FicherosEntrada/users.csv");
             b = usuarios.createNewFile();
        } catch (IOException ignored) {}
        try {
            File usuarios = new File("FicherosEntrada/recomendaciones.csv");
            b = usuarios.createNewFile();
        } catch (IOException ignored) {}
        try {
            File pesoAtributos = new File("FicherosEntrada/peso_atributos.csv");
            b = pesoAtributos.createNewFile();
        } catch (IOException ignored) {}
        try {
            File tipoAtributos = new File("FicherosEntrada/tipo_atributos.csv");
            b = tipoAtributos.createNewFile();
        } catch (IOException ignored) {}

        try {
            File config = new File("src/config.properties");
            b = config.createNewFile();
        } catch (IOException ignored) {}
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            String p = properties.getProperty("VALORACION_MAXIMA");
            if (p == null || p.equals("")) setPropiedad("VALORACION_MAXIMA", String.valueOf(5));
            p = properties.getProperty("ITEMS_POR_RECOMENDACION");
            if (p == null || p.equals("")) setPropiedad("ITEMS_POR_RECOMENDACION", String.valueOf(10));
            p = properties.getProperty("VALOR_K");
            if (p == null || p.equals("")) setPropiedad("VALOR_K", String.valueOf(25));
            p = properties.getProperty("IDENTIFICADOR");
            if (p == null || p.equals("")) setPropiedad("IDENTIFICADOR", "id");
            p = properties.getProperty("NOMBRE");
            if (p == null || p.equals("")) setPropiedad("NOMBRE", "title");
        } catch (Exception ignored) {}

        iniciarDominio();
        iniciarLogIn();
        vistaConfiguracion = new VistaConfiguracion(this);
        aviso = new Aviso(this);
    }

    /**
     * Metode que inicia la finestra de login
     */
    public void iniciarLogIn() {
        vistaLogIn = new VistaLogIn(this);
        vistaLogIn.setVisible(true);
    }

    /**
     * Metode que mostra la finestra de configuracio
     */
    public void visibleConfiguracion() {
        vistaConfiguracion.setVisible(true);
    }

    /**
     * Metode que inicia la finestra principal
     */
    public void iniciarMainPage() {
        vistaMain = new VistaMain(this);
        vistaBuscadorItems = new VistaBuscadorItems(this);
        vistaMain.setVisible(true);
    }

    /**
     * Metode que retorna els noms dels atributs dels items
     * @return nom dels atributs
     */
    public ArrayList<String> getNombresAtributos() {
        return ctrlDominio.getNombresAtributos();
    }

    /**
     * Metode modifica l'usuari actiu
     * @param userID identificador de l'usuari
     * @return indica si s'ha pogut modificar l'usuari
     */
    public boolean setUsuarioActivo(int userID) {
        return ctrlDominio.setUsuarioActivo(userID);
    }

    /**
     * Metode que modifica una propietat del sistema
     * @param key nom de la propietat
     * @param value valor de la propietat
     */
    public void setPropiedad(String key, String value) {
        try (OutputStream output = new FileOutputStream("src/config.properties")) {
            properties.setProperty(key,value);
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Metode que mostra una finestra amb un missatge informatiu
     * @param mensaje missatge que es mostra
     */
    public void aviso(String mensaje) {
        aviso.setMensaje(mensaje);
        aviso.setVisible(true);
    }

    /**
     * Metode que crida a guardar canvis en els arxius de persistencia
     */
    public void guardarCambios() {
        guardarCambiosRec();
        guardarCambiosUsr();
        guardarCambiosItem();
        guardarCambiosVal();
    }

    /**
     * Metode que crida a guardar canvis de les valoracions
     */
    public void guardarCambiosVal() {
        ctrlDominio.guardarCambiosVal();
    }

    /**
     * Metode que crida a guardar canvis de les recomanacions
     */
    public void guardarCambiosRec() {
        ctrlDominio.guardarCambiosRec();
    }

    /**
     * Metode que retorna els items d'una recomanacio
     * @param tipoRecomendacion tipus de recomanacio
     * @return conjunt de nom i identificador dels items de la recomanacio
     */
    public ArrayList<Pair<Integer, String>> getItemsRecomendados(int tipoRecomendacion) {
        return ctrlDominio.getItemsRecomendados(tipoRecomendacion);
    }

    /**
     * Metode que modifica els valors importants del sistema(k, valoracio_maxima, nombre_items_per_recomanacio)
     * @param Val_max valoracio maxima
     * @param NItRec nombre d'items per recomanacio
     * @param k valor de la k
     */
    public void setVarsRec(int Val_max, int NItRec, int k) {
        ctrlDominio.setVarsRec(Val_max, NItRec, k);
    }

    /**
     * Metode que retorna les recomanacions guardades de l'usuari actiu
     * @return conjunt de recomanacions guardades de l'usuari actiu
     */
    public ArrayList<List<String>> getRecomendacionesGuardadasMostrar() { return ctrlDominio.getRecomendacionesGuardadasMostrar(); }

    /**
     * Metode que retorna l'identificador de l'usuari actiu
     * @return identificador de l'usuari actiu
     */
    public Integer getUsuarioActivoId() {
        return ctrlDominio.getUsuarioActivoId();
    }

    /**
     * Metode que crida modificar els pesos dels atributs
     * @param pesos nous pesos dels atributs
     */
    public void setPesos(ArrayList<Double> pesos) {
        ctrlDominio.setPesos(pesos);
    }

    /**
     * Metode que retorna els pesos dels atributs
     * @return pesos dels atributs
     */
    public ArrayList<Double> getPesos() {
        return ctrlDominio.getPesos();
    }

    /**
     * Metode que modifica els tipus dels atributs
     * @param tipos nous tipus dels atributs
     */
    public void guardaTipos(ArrayList<String> tipos) {
        ctrlDominio.guardarTipos(tipos);
    }

    /**
     * Metode que llegeix el fitxer tipo_atributos.csv  i actualitza els tipus d'atributs del sistema.
     */
    public void setTipos() { ctrlDominio.setTipos(); }

    /**
     * Metode que retorna el tipus del atribut amb index donat.
     * @param index index de l'atribut.
     * @return String amb el tipus de l'atribut.
     */
    public String getTipo(int index) { return ControladorDominio.getTipo(index); }

    /**
     * Metode que modifica la variable testing.
     * Testing indica si evaluem les recomanacions.
     * @param b valor al que volem modificar testing.
     */
    public void setTesting(boolean b) {
        testing = b;
    }

    /**
     * Metode que retorna el ndcg de una recomanacio.
     * Calcula una recomanacio amb el metode especificat i a partir del dcg d'aquesta recomanacio i el idcg del usuari calcula el ndcg.
     * @param tipo tipus de la recomanacio.
     * @return Double amb el ndcg calculat.
     */
    public double getNDCG(int tipo) {
        return ctrlDominio.calcula_ndcg(tipo);
    }

    /**
     * Metode que retorna el valor de la variable testing.
     * Testing indica si evaluem les recomanacions.
     * @return retorna true si evaluem les recomanacions i false en cas contrari.
     */
    public boolean getTesting() {
        return testing;
    }

    /**
     *
     */
    public void visibleBuscadorItems() {
        vistaBuscadorItems.setVisible(true);
    }
    /**
     * Metode que retorna les dades de tots els items. Aquestes dades son l'identificador de l'item, el nom de l'item i la seva valoracio.
     * @return dades dels items.
     */
    public Object[][] getItems() {
        return ctrlDominio.getDatosItems();
    }
    /**
     * Metode que actualitza el sistema amb les valoracions dels usuaris.
     * @param res valoracions dels usuaris.
     */
    public void actualizaValoraciones(Object[][] res) {
        ctrlDominio.actualizaValoraciones(res);
    }

    /**
     * Metode que recalcula la configuracio necessaria per realitzar les recomanacions.
     * Obte el valor de k i recalcula els clusters amb k_means.
     */
    public void recalcularRecomendaciones() { ctrlDominio.inicializarRecomendaciones(); }

    /**
     * Metode que realitza el guardat d'una recomanacio.
     * @param i tipus de recomanacio que es guarda (0 per Collaborative filtering, 1 per Content-based filtering i 2 per Hybrid Approach filtering).
     */
    public void guardarRecomendacion(int i) {
        ctrlDominio.guardarRecomendacion(i);
    }
    /**
     * Metode que retorna el identificador de tots els usuaris excepte els del fitxer known.
     * @return Conjunt dels identificadors dels usuaris que no apareixen al fitxer known.
     */
    public ArrayList<Integer> getIdsUsuarios() {
        return ctrlDominio.getIdsUsuarios();
    }

    /**
     * Metode que retorna el identificador de tots els items excepte els del fitxer known.
     * @return Conjunt dels identificadors dels items que no apareixen al fitxer known.
     */
    public ArrayList<Integer> getIdsItems() {
        return ctrlDominio.getIdsItems();
    }

    /**
     * Metode que esborra un usuari
     * @param userID identificador de l'usuari que es vol eliminar
     */
    public void borrarUsuario(int userID) {
        System.out.println("usuario borrado");
        ctrlDominio.borrarUsuario(userID);
    }

    /**
     * Metode que esborra un item
     * @param itemID identificador del item que es vol eliminar
     */
    public void borrarItem(int itemID) {
        System.out.println("item borrado");
        ctrlDominio.borrarItem(itemID);
    }

    /**
     * Metode que crea un nou item
     * @param atributosItemNuevo atributs de l'item
     * @return identificador del nou item creat
     */
    public int nuevoItem(Map<String, Object> atributosItemNuevo) {
        return ctrlDominio.crearItem(atributosItemNuevo);
    }

    /**
     * Metode que crea un nou usuari
     * @return identificador del nou usuari
     */
    public int nuevoUsuario() {
        return ctrlDominio.crearUsuario();
    }

    /**
     * Metode que retorna la puntuacio maxima d'una valoracio
     * @return puntuacio maxima d'una valoracio
     */
    public double getval_maxima() {
        return ctrlDominio.getval_maxima();
    }

    //Metodos privados

    private void iniciarDominio() {
        ctrlDominio = new ControladorDominio();
        File ficheroUsuarios = new File("FicherosEntrada/" + "users.csv");
        if (ficheroUsuarios.length() == 0) ctrlDominio.completarUsuarios();
        int n = ctrlDominio.getNombresAtributos().size();
        File ficheroPesos = new File("FicherosEntrada/" + "peso_atributos.csv");
        if (ficheroPesos.length() == 0) {
            ArrayList<Double> pesosIniciales = new ArrayList<>(n);
            for (int i=0; i<=n; ++i) pesosIniciales.add(1.0);
            ctrlDominio.setPesos(pesosIniciales);
        }
        File ficheroTipos = new File("FicherosEntrada/" + "tipo_atributos.csv");
        if (ficheroTipos.length() == 0) {
            ArrayList<String> tiposIniciales = new ArrayList<>(n);
            for (int i=0; i<=n; ++i) tiposIniciales.add("String");
            ctrlDominio.guardarTipos(tiposIniciales);
        }
        ctrlDominio.setUsuarios();
        ctrlDominio.setItems();
        ctrlDominio.setValoraciones();
        ctrlDominio.setTipos();
        ctrlDominio.inicializarRecomendaciones();
    }

    private void guardarCambiosUsr() { ctrlDominio.guardarCambiosUsr(); }

    private void guardarCambiosItem() {
        ctrlDominio.guardarCambiosItem();
    }

}
