package CapaDomini.Controladors;

import CapaDomini.Model.*;
import CapaDomini.Model.Auxiliares.Pair;
import CapaPersistencia.ControladorPersistencia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Dominio
 */
public class ControladorDominio {

    private static ControladorPersistencia ctrlPersistencia;
    private Atributos atributos;
    private static ControladorUsuarios ctrlUsuarios;
    private static ControladorItems ctrlItems;
    private static ControladorValoraciones ctrlValoraciones;
    private static ControladorRecomendaciones ctrlRecomendaciones;

    /**
     * Constructora de ControladorDominio.
     */
    public ControladorDominio() {
        ctrlPersistencia = new ControladorPersistencia();
        atributos = Atributos.getInstancia();
        atributos.setNombres(ctrlPersistencia.leerAtributos());
        atributos.setPesos(ctrlPersistencia.leerPesoAtributos());
        ctrlUsuarios = new ControladorUsuarios();
        ctrlItems = new ControladorItems();
    }

    // GETTERS

    /**
     * Metode que retorna els items del sistema
     * @return ArrayList amb els items
     */
    public static ArrayList<Item> getItems() {
        return ctrlItems.getItems();
    }

    /**
     * Metode que retorna els usuaris del sistema
     * @return ArrayList amb els usuaris
     */
    public static ArrayList<Usuario> getUsuarios() {
        return ctrlUsuarios.getUsuarios();
    }

    /**
     * Metode que retorna els noms dels atributs
     * @return ArrayList dels noms dels atributs dels items
     */
    public static ArrayList<String> getNombresAtributos() {
        return Atributos.getNombres();
    }

     /**
     * Metode que retorna els usuaris de testing del sistema
     * @return ArrayList amb els usuaris
     */
    public static ArrayList<Integer> getUsuariosknown() {
        return ctrlPersistencia.getUsersKnown();
    }

    /**
     * Metode que actualitza l'usuari actiu amb l'id donat
     * @param userID id del usuari que passa a ser l'usuari actiu
     * @return Boolean que indica si s'ha realitzat el canvi
     */
    public boolean setUsuarioActivo(int userID) {
        if (ctrlUsuarios == null) ctrlUsuarios = new ControladorUsuarios();
        if (ctrlUsuarios.existeUsuario(userID)) {
            ctrlUsuarios.setUsuarioActivo(userID);
            return true;
        }
        return false;
    }


    //guarda los usuarios de ratings en users

    /**
     * Metode que completa el fitxer users.csv amb els usuaris dels fitxers de ratings
     */
    public void completarUsuarios() {
        ctrlPersistencia.completarUsers();
    }

    /**
     * Metode que llegeix els usuaris de users.csv i actualitza els usuaris del sistema
     */
    public void setUsuarios() {
        ctrlUsuarios.setUsuarios(ctrlPersistencia.leerUsuarios());
    }

    /**
     * Metode que llegeix els items de items.csv i actualitza els items del sistema
     */
    public void setItems() {
        ctrlItems.setItems(ctrlPersistencia.leerItems());
    }

    /**
     * Metode que llegeix les valoracions de ratings.db.csv i de ratings.test.known i actualitza les valoracions del sistema
     */
    public void setValoraciones() {
        ctrlValoraciones = new ControladorValoraciones();
        ctrlValoraciones.setValoraciones(ctrlPersistencia.leerValoraciones());
        ctrlValoraciones.setValoracionesUnknown(ctrlPersistencia.leerValoracionesUnknown());
    }


    /**
     * Metode que actualitza els fitxers de ratings amb les valoracions del sistema
     */
    public void guardarCambiosVal() {
      ctrlPersistencia.guardarValoraciones(ctrlValoraciones.getVal_guardar());

    }

    /**
     * Metode que actualitza el fitxer de recomanacions amb les recomanacions guardades del sistema
     */
    public void guardarCambiosRec() {
        ctrlPersistencia.guardarRecomendaciones(ctrlRecomendaciones.getRecomendacionesGuardadas());
    }

    /**
     * Metode que actualitza el fitxer d'items amb els items del sistema
     */
    public void guardarCambiosItem() {
      ctrlPersistencia.guardarItems(ctrlItems.getItems_guardar());
    }

    /**
     * Metode que actualitza el fitxer d'usuaramb els usuaris del sistema
     */
    public void guardarCambiosUsr() {
        ctrlPersistencia.guardarUsuarios(ctrlUsuarios.getUsuarios_guardar());
    }

    /**
     * Metode que retorna l'usuari amb l'id donat
     * @param userID id d'un usuari
     * @return Usuario amb id=userID
     */
    public static Usuario getUsuario(int userID) {
        return ctrlUsuarios.getUsuario(userID);
    }

    /**
     * Metode que retorna l'usuari actiu del sistema
     * @return Usuari actiu
     */
    public static Usuario getUsuarioActivo() {
        return ctrlUsuarios.getUsuarioActivo();
    }

    /**
     * Metode que retorna l'index de l'item amb l'id donat
     * @param itemID id d'un item
     * @return index de l'item
     */
    public static int getItemIndex(int itemID) {
        if (ctrlItems == null) ctrlItems = new ControladorItems();
        return ctrlItems.getItemIndex(itemID);
    }

    /**
     * Metode que retorna les valoracions del sistema
     * @return Map amb les valoracions de cada usuari
     */
    public static Map<Usuario, ArrayList<Valoracion>> getValoraciones() {
        return ctrlValoraciones.getValoraciones();
    }

    /**
     * Metode que retorna les valoracions del fitxer ratings.test.unknown.csv
     * @return Map amb les valoracions de cada usuari
     */
    public static Map<Usuario, ArrayList<Valoracion>> getNuevos_items_unknown() {
        return ctrlValoraciones.getNuevos_items_unknown();
    }

    /**
     * Metode que retorna el tipus del atribut amb index donat.
     * @param index index de l'atribut.
     * @return String amb tipus de l'atribut.
     */
    public static String getTipo(int index) {
        return Atributos.getTipo(index);
    }

    /**
     * Metode que llegeix el fitxer tipo_atributos.csv  i actualitza els tipus d'atributs del sistema
     */
    public void setTipos() {
        atributos.setTipos(ctrlPersistencia.leerTiposAtributos());
    }

    /**
     * Metode que actualitza el fitxer de tipus d'atributs amb els tipus del sistema
     * @param s tipus dels atributs.
     */
    public void guardarTipos(ArrayList<String> s) {
        ctrlPersistencia.guardarTiposAtributos(s);
    }

    /**
     * Metode que retorna l'item amb identificador donat.
     * @param itemID identificador del item que volem obtenir.
     * @return Item amb identificador igual a itemID.
     */
    public static Item getItem(int itemID) {
        return ctrlItems.getItem(itemID);
    }

    /**
     * Metode que retorna el valor maxim que te un atribut en tot el conjunt d'items del sistema.
     * @param nombreAtributo atribut del que volem obtenir el valor maxim.
     * @return valor maxim de l'atribut.
     */
    public static double get_Maxval(String nombreAtributo) { return ctrlItems.get_Maxval(nombreAtributo); }

    /**
     *Metode que inicialitza el controlador de recomanacions si no esta inicialitzat i si ho esta recalcula k_means.
     */
    public void inicializarRecomendaciones() {
        if (ctrlRecomendaciones == null) ctrlRecomendaciones = new ControladorRecomendaciones();
        else {
            int k = ctrlRecomendaciones.getK();
            ctrlRecomendaciones.k_means(k);
        }
        ctrlRecomendaciones.leerRecomendacionesGuardadas(ctrlPersistencia.leerRecomendaciones());
    }

    /**
     * Metode que retorna el valor de val_maxima (valor maxim de les valoracions).
     * @return valor de val_maxima.
     */
    public double getval_maxima() {return ctrlRecomendaciones.getval_maxima();}


    /**
     * Metode que calcula el ndcg de una recomanacio.
     * Calcula una recomanacio amb el metode especificat i a partir del dcg d'aquesta recomanacio i el idcg del usuari calcula el ndcg.
     * @param tipo tipus de la recomanacio.
     * @return Double amb el ndcg calculat.
     */
    public double calcula_ndcg(int tipo) {
        if (!ControladorDominio.getNuevos_items_unknown().containsKey(getUsuarioActivo())) return 0;
        Recomendacion r = ctrlRecomendaciones.calculaRecomendacion(tipo);
        r = ctrlRecomendaciones.recomanacioDCG(r);
        return ctrlRecomendaciones.DCG(r) / ctrlRecomendaciones.IDCG(ctrlUsuarios.getUsuarioActivo());
    }

    /**
     * Metode que modifica el valor de les variables val_max, NItRec i k.
     * @param Val_max numero que indica el valor maxim de les valoracions.
     * @param NItRec valor que indica el numero d'items que hem de recomanar.
     * @param k valor que indica el numero de clusters per k_means.
     */
    public void setVarsRec(int Val_max, int NItRec, int k) {
        ctrlRecomendaciones.setVarsRec(Val_max, NItRec, k);
    }

    /**
     * Metode que retorna els Items recomanats de la recomanació calculada amb el metode indicat.
     *
     * @param tipoRecomendacion enter que indica la recomanacio amb quin dels 3 metodes volem calcular la recomanacio, si es 0 collaborative filtering, si es 1 content based i si es 3 hybrid approach.
     * @return recomanacio generada amb el metode indicat.
     */
    public ArrayList<Pair<Integer, String>> getItemsRecomendados(int tipoRecomendacion) {
        return ctrlRecomendaciones.getItemsRecomendados(tipoRecomendacion);
    }

    /**
     * Metode que retorna el identificador de l'usuari actiu.
     * @return Integer amb l'identificador de l'usuari actiu.
     */
    public Integer getUsuarioActivoId() {
        return ctrlUsuarios.getUsuarioActivo().getIdentificador();
    }

    /**
     * Metode que guarda al archiu peso_atributos.csv els pesos introduits per l'administrador, i actualitza els pesos al sistema.
     * @param pesos nova configuracio de pesos.
     */
    public void setPesos(ArrayList<Double> pesos) {
        ctrlPersistencia.guardarPesosAtributos(pesos);
        atributos.setPesos(pesos);
    }

    /**
     * Metode que retorna els pesos dels atributs.
     * @return pesos del atributs.
     */
    public ArrayList<Double> getPesos() {
        return atributos.getPesos();
    }

    /**
     * Metode que retorna les dades de tots els items. Aquestes dades son l'identificador de l'item, el nom de l'item i la seva valoracio.
     * @return dades dels items.
     */
    public Object[][] getDatosItems() {
        ArrayList<Item> items = ctrlItems.getItems();
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        for (Item item : items) {
            ArrayList<Object> valores = new ArrayList<>();
            valores.add(item.getIdentificador());
            valores.add(item.getNombre());
            valores.add(ctrlValoraciones.getPuntuacion(ctrlUsuarios.getUsuarioActivo(), item));
            result.add(valores);
        }
        Object[][] array = result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
        return array;
    }

    /**
     * Metode que actualitza el sistema amb les valoracions dels usuaris.
     * @param res valoracions dels usuaris.
     */
    public void actualizaValoraciones(Object[][] res) {
        ArrayList<List<String>> vals = new ArrayList<>();
        ArrayList l = new ArrayList();
        l.add("userId");
        l.add("itemId");
        l.add("rating");
        vals.add(l);
        //AÑADIR CABECERA
        for(Object[] v : res) {
            List<String> list = new ArrayList<>();
            if(v[2] != null) {
                list.add(String.valueOf(ctrlUsuarios.getUsuarioActivo().getIdentificador()));
                list.add(v[0].toString());
                list.add(v[2].toString());
                vals.add(list);
            }
            else {
                list.add(String.valueOf(ctrlUsuarios.getUsuarioActivo().getIdentificador()));
                list.add(v[0].toString());
                list.add("-1");
                vals.add(list);
            }
        }
        ctrlValoraciones.setValoraciones(vals);
    }
    /**
     * Metode que guarda una recomanacio.
     * @param tipo enter que indica la recomanacio de quin dels 3 metodes volem guardar, si es 0 collaborative filtering, si es 1 content based i si es 3 hybrid approach.
     */
    public void guardarRecomendacion(int tipo) {
            ctrlRecomendaciones.guardaRecomendacion(tipo);
    }

    /**
     * Metode que retorna les 3 ultimes recomanacions que ha guardat el usuari actiu.
     * Retorna les 3 ultimes recomanacions guardades per l'usuari actiu per poder mostrarli quan ens demani veure les recomanacions que ha guardat.
     * @return ArrayList amb les 3 ultimes recomanacions del usuari actiu.
     */
    public ArrayList<List<String>> getRecomendacionesGuardadasMostrar() {
        return ctrlRecomendaciones.getRecomendacionesGuardadasMostrar();
    }

    /**
     * Metode que crea un item.
     * Tambe inicialitza les valoracions de tots els usuaris amb el nou item amb una puntuació de -1.
     * @param atributos Map amb els atributs del nou item.
     * @return identificador del nou item.
     */
    public int crearItem(Map<String, Object> atributos){ //crea item con nuevo id y atributos que hay
        Item item = ctrlItems.nuevoItem(atributos);
        ctrlValoraciones.addValNuevoItem(item);
        return item.getIdentificador();
    }

    /**
     * Metode que elimina un item i totes les seves valoracions.
     * @param id identificador del item que volem eliminar.
     */
    public void borrarItem (int id) {//Borra el item dado de la lista de items y de todas las valoraciones siempre que no aparezca en known
        Item item = getItem(id);
        ctrlItems.borrarItem(item);
        ctrlValoraciones.borraItemValoraciones(item);
    }

    /**
     * Metode que crea un nou usuari i inicalitza les seves valoracions.
     * @return identificador del nou usuari.
     */
    public int crearUsuario() { //Crea un nuevo usuario lo añade al controlador de usuarios y genera sus valoraciones a -1 (FALTA ESCRIBIR EN users.csv)
        Usuario u = ctrlUsuarios.nuevoUsuario();
        ctrlValoraciones.addValNuevoUsuario(u);
        return u.getIdentificador();
    }

    /**
     * Metode que elimina un usuari, totes les seves valoracions i les seves recomanacions guardades.
     * També recalculem els clutsers amb k_means.
     * @param id identificador del usuari que volem eliminar.
     */
    public void borrarUsuario(int id) { //Borra un usuario, todas sus valoraciones, recomendaciones guardadas si tenia y recalcula kmeans
            Usuario u = getUsuario(id);
            ctrlValoraciones.borrarValUsuario(u);
            ctrlUsuarios.borrarUsuario(u);
            ctrlRecomendaciones.borrarUsuarioRecGuardadas(u);
    }
    //retorna los ids de los usr que se pueden borrar

    /**
     * Metode que retorna el identificador de tots els usuaris excepte els del fitxer known.
     * @return ArrayList dels identificadors dels usuaris que no apareixen al fitxer known.
     */
    public ArrayList<Integer> getIdsUsuarios() { // HACE FALTA COMPROBAR QUE LOS USUARIOS NO SON DEL KNOWN
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> ids1 = ctrlPersistencia.getUsersKnown();
        for (Usuario u :  ctrlUsuarios.getUsuarios()) {
            int id = u.getIdentificador();
            if (!ids1.contains(id)) {
                ids.add(id);
            }
        }
        return ids;
    }
    //retorna los ids de los items que se pueden borrar
    /**
     * Metode que retorna el identificador de tots els items excepte els del fitxer known.
     * @return ArrayList dels identificadors dels items que no apareixen al fitxer known.
     */
    public ArrayList<Integer> getIdsItems() {  // HACE FALTA COMPROBAR QUE LOS ITEMS NO SON DE KNOWN
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> ids1 = ctrlPersistencia.getItemsKnown();
        for (Item i :  ctrlItems.getItems()) {
            int id = i.getIdentificador();
            if (!ids1.contains(id)) {
                ids.add(id);
            }
        }
        return ids;
    }
}
