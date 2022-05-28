package CapaDomini.Model.Auxiliares;

import CapaDomini.Model.Usuario;

import java.util.ArrayList;
import java.util.Random;

/**
 * Cluster es una classe auxiliar per a fer el calcul de k-means.
 */
public class Cluster {

    // Atributos de la clase
    private ArrayList<Usuario> Usuarios = new ArrayList<>();
    private ArrayList<Double> Centroide = new ArrayList<>();

    // Constructora de la clase

    /**
     *Constructora de la classe Cluster.
     * Metode que crea un cluster rebent com a parametres un ArrayList d'usuaris i un ArrayList de valoracions.
     * @param usuarios ArrayList dels usuaris que estaran al Cluster
     * @param valoraciones ArrayList de valoracions que sera el centroide del cluster.
     */
    public Cluster(ArrayList<Usuario> usuarios, ArrayList<Double> valoraciones) {
        this.Usuarios = usuarios;
        this.Centroide = valoraciones;
    }

    /**
     * Constructora de la classe Cluster
     * Metode que crea un Cluster a partir de copiar els valors d'un altre.
     * @param antic Cluster que copiarem.
     */
    public Cluster(Cluster antic) {
        this.Usuarios = (ArrayList<Usuario>)antic.getUsuarios().clone();
        this.Centroide = (ArrayList<Double>)antic.getCentroide().clone();
    }

    /**
     * Constructora de la classe Cluster.
     * Metode que crea un Cluster sense usuaris, rebent com a parametre el centroide.
     * @param centroide ArrayList de valoracions que sera el centroide del cluster.
     */
    public Cluster(ArrayList<Double> centroide) {
        this.Centroide = centroide;
    }

    // Añade un usuario al cluster

    /**
     * Metode que modifica la llista d'usuaris del cluster.
     * Afegeix el usuari que rebem com a parametre a la lista d'usuaris del cluster.
     * @param usuario usuari que afegim al cluster.
     */
    public void AddUsuario(Usuario usuario) {
        Usuarios.add(usuario);
    }

    // Devuelve los usuarios del cluster

    /**
     * Metode que retorna els usuaris del cluster.
     * @return ArrayList amb tots els usuaris del cluster.
     */
    public ArrayList<Usuario> getUsuarios() {
        return this.Usuarios;
    }

    // Devuelve el centroide del cluster

    /**
     * Metode que retorna els centroide del cluster.
     * @return ArrayList amb el centroide del cluster.
     */
    public ArrayList<Double> getCentroide() { return this.Centroide; }

    // Devuelve la valoracion del centroide en la posicion 'index' del ArrayList de valoraciones

    /**
     * Metode que retorna el valor d'un element del centroide.
     * @param index index del element del centroidedel que volem saber el valor.
     * @return valor del element del centroide en la posicio del index.
     */
    public double getValoracionCentroide(int index) {
        if (index < Centroide.size()) return this.Centroide.get(index);
        return -1.0;
    }

    // Cambia el centroide

    /**
     * Metode que modifica el centroide d'un cluster.
     * @param newCentroide ArrayList que sera el nou centroide.
     */
    public void setCentroide(ArrayList<Double> newCentroide) { this.Centroide = newCentroide; }
}