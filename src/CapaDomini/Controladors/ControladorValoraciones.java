package CapaDomini.Controladors;

import CapaDomini.Model.Item;
import CapaDomini.Model.Usuario;
import CapaDomini.Model.Valoracion;

import java.util.*;

/**
 *Controlador de Valoracions
 */
public class ControladorValoraciones {

    private Map<Usuario, ArrayList<Valoracion>> valoraciones = new HashMap<>();

    private Map<Usuario, ArrayList<Valoracion>> nuevos_items_unknown;


    /**
     * Constructora de ControladorValoraciones
     * Inicialitza les valoracions posant les valoracions de tots els usuaris per tots els items a -1 i també inicialitza nuevos_items_unknown com un Map buit.
     */
    public ControladorValoraciones() {
        //todos los usuarios tienen todos los items valorados a -1
        for (Usuario usr : ControladorDominio.getUsuarios()) {
            ArrayList<Valoracion> valoraciones_usr = new ArrayList<>();
            for (Item item : ControladorDominio.getItems()) {
                valoraciones_usr.add(new Valoracion(usr, item, -1));
            }
            valoraciones.put(usr, valoraciones_usr);
        }

        nuevos_items_unknown = new HashMap<>();
    }

    /**
     * Metode que modifica el valor de les valoracions
     * @param valoracionesLeidas ArrayList de valoracions modificades.
     */
    public void setValoraciones(ArrayList<List<String>> valoracionesLeidas) {
        ArrayList<List<String>> valoraciones_items = valoracionesLeidas;
        int index_usr = -1;
        int index_item = -1 ;
        int index_rating = -1;
        for (int i = 0; i < 3; i ++){
            if (valoraciones_items.get(0).get(i).equals("userId")) index_usr = i;
            else if (valoraciones_items.get(0).get(i).equals("itemId")) index_item = i;
            else index_rating = i;
        }
        valoraciones_items.remove(0);
        for (List<String> valoracion : valoraciones_items) {
            int userID = Integer.parseInt(valoracion.get(index_usr));
            int itemID = Integer.parseInt(valoracion.get(index_item));
            double puntuacion = Double.parseDouble(valoracion.get(index_rating));
            Usuario usr = ControladorDominio.getUsuario(userID);

            ArrayList<Valoracion> valoraciones_usuario = valoraciones.get(usr);
            for (Valoracion val : valoraciones_usuario) {
                if (val.getItem().getIdentificador() == itemID) {
                    val.setPuntuacion(puntuacion);
                    break;
                }
            }
        }
    }

    /**
     * Metode que inicialitza les valoracions llegides del fitxer unknown.
     * @param valoracionesUnknown valoraciones llegides del fitxer unknown.
     */
    public void setValoracionesUnknown(ArrayList<List<String>> valoracionesUnknown) {
        ArrayList<List<String>> valoraciones_items = valoracionesUnknown;
        int index_usr = -1;
        int index_item = -1 ;
        int index_rating = -1;
        for (int i = 0; i < 3; i ++){
            if (valoraciones_items.get(0).get(i).equals("userId")) index_usr = i;
            else if (valoraciones_items.get(0).get(i).equals("itemId")) index_item = i;
            else index_rating = i;
        }
        valoraciones_items.remove(0);
        for (List<String> valoracion : valoraciones_items) {
            int userID = Integer.parseInt(valoracion.get(index_usr));
            int itemID = Integer.parseInt(valoracion.get(index_item));
            double puntuacion = Double.parseDouble(valoracion.get(index_rating));


            Usuario usr = ControladorDominio.getUsuario(userID);
            Valoracion val = new Valoracion(usr, ControladorDominio.getItem(itemID), puntuacion);
            if (nuevos_items_unknown.get(usr) == null) nuevos_items_unknown.put(usr, new ArrayList<>());
            nuevos_items_unknown.get(usr).add(val);
        }
        for(Map.Entry<Usuario, ArrayList<Valoracion>> unknown_val : nuevos_items_unknown.entrySet()) {
            unknown_val.getValue().sort(new Comparator<Valoracion>() {
                @Override
                public int compare(Valoracion o1, Valoracion o2) {
                    if (o1.getPuntuacion() > o2.getPuntuacion()) return -1;
                    else if (o1.getPuntuacion() == o2.getPuntuacion()) return 0;
                    return 1;
                }
            });
        }
    }

    /**
     * Metode que retorna les valoracions del sistema.
     * @return Map amb les valoracions de tots els usuaris.
     */
    public Map<Usuario, ArrayList<Valoracion>> getValoraciones() {
        return valoraciones;
    }

    /**
     * Metode que retorna les valoracions llegides del fitxer unknown.
     * @return Map amb les valoracions llegides del fitxer unknown.
     */
    public Map<Usuario, ArrayList<Valoracion>> getNuevos_items_unknown() {
        return nuevos_items_unknown;
    }

    /**
     * Metode que afegeix un item a totes les valoracions.
     * Inicialitza el valor de les valoracions del item a -1.
     * @param i item que afegim a les valoracions
     */
    public void addValNuevoItem (Item i) {//En todas las valoraciones añade el nuevo item con valoracion -1
        for (Usuario usr : ControladorDominio.getUsuarios()) {
            Valoracion val = new Valoracion(usr,i,-1);
            valoraciones.get(usr).add(val);
        }
    }

    /**
     * Metode que elimina les valoracions d'un item.
     * @param item item del que eliminarem les valoracions.
     */
    public void borraItemValoraciones (Item item) { // Borra las valoraciones del item borrado
        for (Usuario usr : ControladorDominio.getUsuarios()){
            for (int i = 0; i < valoraciones.get(usr).size(); i++) {
                Valoracion val = valoraciones.get(usr).get(i);
                if (val.getItem() == item) {
                    valoraciones.get(usr).remove(val);
                }
            }
        }
    }

    /**
     * Metode que inicialitza les valoracions d'un usuari.
     * Inicialitza la valoracio de tots els items a -1.
     * @param usr usuari del que inicialitzarem les valoracions.
     */
    public void addValNuevoUsuario (Usuario usr) {//inicializa todas las valoraciones del usuario a -1
        ArrayList<Valoracion> valoraciones_usr = new ArrayList<>();
        for (Item item : ControladorDominio.getItems()) {
            valoraciones_usr.add(new Valoracion(usr, item, -1));
        }
        valoraciones.put(usr, valoraciones_usr);
    }

    /**
     * Metode que elimina totes les valoracions d'un usuari.
     * @param usr usuari del que volem eliminar totes les valoracions.
     */
    public void borrarValUsuario (Usuario usr) { //borra todas las valoraciones del usuario
        valoraciones.remove(usr);
    }

    /**
     * Metode que retorna la puntuacio d'una valoracio.
     * @param usuarioActivo usuari que ha fet la valoracio.
     * @param item item de la valoracio.
     * @return valor de la puntuacio de la valoracio.
     */
    public Object getPuntuacion(Usuario usuarioActivo, Item item) {
        ArrayList<Valoracion> puntuaciones = valoraciones.get(usuarioActivo);
        for (Valoracion v : puntuaciones) {
            if (v.getItem() == item && v.getPuntuacion() != -1) return v.getPuntuacion();
        }
        return null;
    }

    /**
     * Metode que retorna les valoracions en un format que ens permet escriure-les en un fitxer.
     * @return ArrayList amb les valoracions.
     */
    public ArrayList<List<String>> getVal_guardar () {//Falta mirar usr known
        ArrayList<List<String>> res = new ArrayList<>();
        for(Map.Entry<Usuario, ArrayList<Valoracion>> val : valoraciones.entrySet()) {
            if (!ControladorDominio.getUsuariosknown().contains(val.getKey().getIdentificador())) {
                for (Valoracion val2 : val.getValue()) {
                    if (val2.getPuntuacion() != -1) {
                        List<String> l = new ArrayList<>();
                        l.add(String.valueOf(val.getKey().getIdentificador()));
                        l.add(String.valueOf(val2.getItem().getIdentificador()));
                        l.add(String.valueOf(val2.getPuntuacion()));
                        res.add(l);
                    }
                }
            }
        }
        return res;
    }



    /* METODOS PRIVADOS */

    // Retorna la posicion de la valoracion realizada por el usuario usr hacia el item con id itemID dentro del vector.
    // Si no se encuentra en el vector retorna -1
    private int itemValorado(int itemID, Usuario usr) {
        ArrayList<Valoracion> valoracions = valoraciones.get(usr);
        if (valoracions == null) return -2;
        else {
            for (int i = 0; i < valoracions.size(); i++) {
                Valoracion valoracion = valoraciones.get(usr).get(i);
                if (valoracion.getItem().getIdentificador() == itemID) return i;
            }
        }
        return -1;
    }



}
