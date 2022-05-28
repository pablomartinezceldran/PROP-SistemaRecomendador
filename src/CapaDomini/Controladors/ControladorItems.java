package CapaDomini.Controladors;

import CapaDomini.Model.Atributos;
import CapaDomini.Model.Auxiliares.Pair;
import CapaDomini.Model.Item;
import CapaDomini.Model.Usuario;
import CapaDomini.Model.Valoracion;

import java.util.*;

/**
 * Controlador d'Items
 */
public class ControladorItems {

    private ArrayList<Item> items;
    private final Atributos atributos;

    /**
     * Constructora de la classe ControladorItems
     * Inicialitza la variable atributos i la llista de items.
     */
    public ControladorItems() {
        atributos = Atributos.getInstancia();
        items = new ArrayList<>();
    }

    /**
     * Metode que crea un nou Item i l'afegeix a la llista d'items.
     * Genera un identificador i obte els atributs per parametre.
     * @param atributosNuevos atributs del nou item.
     * @return nou item creat.
     */
    public Item nuevoItem(Map<String, Object> atributosNuevos) {

        int id = getNuevoIdentificador();
        String nom = (String) atributosNuevos.get(atributos.getNombre());
        atributosNuevos.put(atributos.getId(), id);

        Item item = new Item(id, nom, atributosNuevos);
        items.add(item);
        return item;
    }

    /**
     * Metode que elimina un item de la llista de items
     * @param item item que s'ha d'eliminar.
     */
    public void borrarItem(Item item) { //Borra el item dado
        items.remove(item);
    }



    // Pone los items como itemsLeidos

    /**
     * Metode que modifica la llista d'items.
     * Com a parametre rep la llista d'items que llegim del fitxer d'items, els cambia de format generant els items i els afegeix a la llista.
     * @param itemsLeidos ArrayList dels items en el format en el que els llegim del fitxer.
     */
    public void setItems(ArrayList<List<String>> itemsLeidos) {

        for (List<String> item : itemsLeidos) {
            Integer id = 0;
            String nombre = "";
            Map<String, Object> atributosItem = new HashMap<>();
            for (int i = 0; i < item.size(); ++i) {
                String nombre_atributo = atributos.getNombre(i);
                String valor_item = item.get(i);
                if (nombre_atributo.equals(atributos.getId())) {
                    id = Integer.parseInt(valor_item);
                }
                if (nombre_atributo.equals(atributos.getNombre())) nombre = valor_item;
                if (esNumericInt(valor_item)) atributosItem.put(nombre_atributo, Integer.parseInt(valor_item));
                else if(esNumericDouble(valor_item))atributosItem.put(nombre_atributo, Double.parseDouble(valor_item));
                else atributosItem.put(nombre_atributo, valor_item);
            }
            items.add(new Item(id, nombre, atributosItem));
        }
    }

    /**
     * Metode que retorna el valor maxim que te un atribut en tot el conjunt d'items del sistema.
     * @param nombreAtributo atribut del que volem obtenir el valor maxim.
     * @return valor maxim de l'atribut.
     */
    public double get_Maxval(String nombreAtributo) {
        double max = 0.0;

        for (Item i : items) {
            try {
                if ((double) ((Integer) i.getAtributo(nombreAtributo)) > max) {
                    max = (double) ((Integer) i.getAtributo(nombreAtributo));
                }
            } catch (Exception e) {
                if (i.getAtributo(nombreAtributo) != null && i.getAtributo(nombreAtributo).getClass() == Double.class && (double)i.getAtributo(nombreAtributo) > max) {
                    max = (double)i.getAtributo(nombreAtributo);
                }
            }
        }
        return max;
    }

    /**
     * Metode que retorna la llista d'items.
     * @return ArrayList amb tots els items.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    // Retorna la posicion del item dentro del vector. Si no se encuentra en el vector retorna -1

    /**
     * Metode que retorna la posicio d'un item a la llista d'items.
     * @param itemId identificador del item del que volem obtenir el index.
     * @return posicio del item amb id donat.
     */
    public int getItemIndex(Integer itemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIdentificador() == itemId) return i;
        }
        return -1;
    }

    /**
     * Metode que retorna l'item amb identificador donat.
     * @param itemID identificador del item que volem obtenir.
     * @return Item amb identificador igual a itemID.
     */
    public Item getItem(int itemID) {
        for (Item i : items) {
            if (itemID == i.getIdentificador()) {
                return i;
            }
        }
        return null;
    }

    /**
     * Metode que retorna la llista d'items en un format que ens facilita guardar-los en un fitxer.
     * @return ArrayList amb els items.
     */
    public ArrayList<List<String>> getItems_guardar() {
        ArrayList<List<String>> res = new ArrayList<>();
        for (Item i : getItems()) {
            List<String> l = new ArrayList<>();
            for (String s : Atributos.getNombres()) {
                l.add(String.valueOf(i.getAtributos().get(s)));
            }
            res.add(l);
        }
        return res;
    }




    /* METODOS PRIVADOS */

    //busca un identificador nuevo para un item
    private Integer getNuevoIdentificador() {
        Integer max = 1;
        for (Item i : items) {
            if (i.getIdentificador() > max) max = i.getIdentificador();
        }
        return max + 1;
    }

    private boolean esNumericInt(String s) {
        if (s == null) return false;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean esNumericDouble(String s){
        if (s == null) return false;
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
