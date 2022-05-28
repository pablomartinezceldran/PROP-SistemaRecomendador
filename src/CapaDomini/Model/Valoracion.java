package CapaDomini.Model;

/**
 * Valoracion defineix la valoració d’un usuari per un ítem.
 */
public class Valoracion {

    private Usuario usuario;
    private Item item;
    private double puntuacion;

    /**
     * Constructora de la classe Valoracion.
     * Metode que crea una valoracio rebent com a parametres un usuari, un item i un double.
     * @param usuario usuari que valora el item.
     * @param item item que es valora.
     * @param puntuacion puntuacio de la valoracio.
     */
    public Valoracion(Usuario usuario, Item item, double puntuacion) {
        this.usuario = usuario;
        this.item = item;
        this.puntuacion = puntuacion;
    }

    /**
     * Metode que retorna l'usuari de la valoració.
     * @return usuari de la valoracio.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Metode que retorna l'item de la valoració.
     * @return item de la valoracio.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Metode que retorna la puntuacio de la valoració.
     * @return puntuacio de la valoracio.
     */
    public double getPuntuacion() {
        return puntuacion;
    }

    /**
     * Metode que modifica la puntuacio d'una valoracio.
     * @param puntuacion nova puntuacio de la valoracio.
     */
    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
}
