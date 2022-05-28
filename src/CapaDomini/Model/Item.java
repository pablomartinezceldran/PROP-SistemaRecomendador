package CapaDomini.Model;

import java.util.Map;

/**
 * Item representa un item del sistema.
 *
 *
 */
public class Item {

    private int identificador;
    private String nombre;
    private Map<String, Object> atributos;

    /**
     * Constructora de la classe Item.
     * Metode que crea un item rebent com a parametres el seu identificador, el nom que volem mostrar d'aquest item i tots els seus atributs.
     * @param identificador identificador de l'item.
     * @param nom nom de l'item.
     * @param atributos conjunt d'atributs de l'item en un Map on la clau es el nom del atribut i com a valor el valor d'aquest atribut.
     */
    public Item(int identificador, String nom, Map<String, Object> atributos) {
        this.identificador = identificador;
        this.atributos = atributos;
        this.nombre = nom;
    }

    /**
     * Metode que retorna l'identificador d'un item.
     * @return  l'identificador de l'item.
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Metode que retorna el nom d'un item.
     * @return el nom de l'item.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metode que retorna els atributs de l'item.
     * @return un Map on les claus són el nom dels atributs de l'item i els valors el valor de cada atribut.
     */
    public Map<String, Object> getAtributos() {
        return atributos;
    }

    /**
     * Metode que retorna el valor d'un atribut de l'item.
     * @param key nom de l'atribut del que volem obtenir el valor.
     * @return  el valor de l'atribut en l'item.
     */
    public Object getAtributo(String key) { return atributos.get(key); }

    /**
     * Metode que modifica el valor d'un atribut en l'item.
     * @param key nom de l'atribut que volem modificar.
     * @param atributo valor que volem que tingui l'atribut.
     */
    public void setAtributo(String key , Object atributo) {
        this.atributos.replace(key, atributo);
    }

}