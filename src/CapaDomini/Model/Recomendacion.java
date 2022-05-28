package CapaDomini.Model;

import CapaDomini.Model.Auxiliares.Pair;

import java.util.ArrayList;

/**
 *
 * Recomendacion representa un conjunt d’items recomanats a un usuari, amb tants items com definim al sistema i l’estimacio de la seva puntuacio.
 */
public class Recomendacion {

    private Usuario usuario;
    private ArrayList<Pair<Item,Double>> items;

    /**
     * Constructora de la classe Recomendacion.
     * Metode que crea una recomanacio rebent com a parametres un usuari i un arraylist de Pair(Item,Double)
     * @param usuario usuari de la recomanacio
     * @param items arraylist de Pair(Item,Double) on cada Pair te un Item i la puntuacio esperada.
     */
    public Recomendacion(Usuario usuario, ArrayList<Pair<Item,Double>> items) {
        this.usuario = usuario;
        this.items = items;

    }

    //Getters

    /**
     * Metode que retorna el usuari de la recomanacio.
     * @return un objecte de la classe Usuario que es el usuari de la recomanacio.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Metode que retorna els items de la recomanacio amb la seva prediccio de valoracio.
     * @return un arraylist de Pair(Item,Double) on cada Pair te un Item de la recomanacio i la seva puntuacio esperada.
     */
    public ArrayList<Pair<Item,Double>> getItems() { return items;}
}
