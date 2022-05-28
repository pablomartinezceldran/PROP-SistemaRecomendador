package CapaDomini.Model;

/**
 * Usuario representa un usuari del sistema.
 */
public class Usuario {

    // Atributos de clase
    private final int identificador;

    /**
     * Constructora de la classe Usuario.
     * Metode que crea un usuari rebent com a parametre el seu identificador.
     * @param identificador identificador del usuari.
     */
    public Usuario(int identificador) {

        this.identificador = identificador;
    }

    // Getters

    /**
     * Metode que retorna l'identificador del usuari.
     * @return l'identificador del usuari.
     */
    public int getIdentificador() {
        return identificador;
    }

}