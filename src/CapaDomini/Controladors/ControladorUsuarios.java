package CapaDomini.Controladors;

import CapaDomini.Model.Usuario;

import java.util.ArrayList;

/**
 * Controlador d'Usuaris
 */
public class ControladorUsuarios {

    private ArrayList<Usuario> usuarios;
    private Usuario usuarioActivo;

    /*CONSTRUCTORA*/

    /**
     * Constructora del ControladorUsuarios
     * Inicialitza la llista d'usuaris buida i el usuari actiu a null.
     */
    public ControladorUsuarios() {
        usuarioActivo = null;
        usuarios = new ArrayList<>();
    }

    /*METODOS PUBLICOS*/

    //se crea un nuevo usuario

    /**
     * Metode per crear un usuari nou.
     * Crea un nou Usuari amb un identificador generat pel sistema i l'afegeix a la llista d'usuaris.
     * @return usuari que hem generat.
     */
    public Usuario nuevoUsuario() {
        Usuario u = new Usuario(getNuevoIdentificador());
        usuarios.add(u);
        return u;
    }

    //Borra el usuario con id dado

    /**
     * Metode per eliminar un usuari.
     * @param u usuari que volem eliminar.
     */
    public void borrarUsuario(Usuario u) {
        usuarios.remove(u);
    }

    //comprueba si existe un usuario con identificador userID

    /**
     * Metode que comprova si existeix un usuari amb el identificador donat.
     * @param userID identificador del usuari.
     * @return true si existeix l'usuari, false si no existeix.
     */
    public boolean existeUsuario(int userID) {
        for (Usuario u : usuarios) {
            if(u.getIdentificador() == userID) return true;
        }
        return false;
    }

    //pone como usuario activo del sistema al usuario con id userID
    //pre: el usuario con id userID existe

    /**
     * Metode que modifica el usuari actiu del sistema.
     * Rep com a parametre el identificador d'un usuari que ha d'existir.
     * @param userID identificador del usuari que volem posar com a usuari actiu.
     */
    public void setUsuarioActivo(int userID) {
        usuarioActivo = getUsuario(userID);
    }

    //devuelve el usuario activo

    /**
     * Metode que retorna l'usuari actiu del sistema.
     * @return usuari que esta com a usuari_actiu.
     */
    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    //pone el conjunto de usuarios

    /**
     * Metode que modifica la llista d'usuaris (usuarios).
     * @param usuarios ArrayList dels identificadors dels usuaris que volem afegir a la llista d'usuaris.
     */
    public void setUsuarios(ArrayList<Integer> usuarios) {
        for(Integer userId : usuarios) this.usuarios.add(new Usuario(userId));
    }
    //devuelve el conjunto de usuarios

    /**
     * Metode que retorna la llista d'usuaris.
     * @return ArrayList dels usuaris del sistema.
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    //devuelve el usuario con id userID

    /**
     * Metode que retorna l'usuari amb el id donat.
     * @param userID identificador del usuari que volem obtenir.
     * @return usuari que te com a identificador el parametre obtingut.
     */
    public Usuario getUsuario(int userID) {
        for (Usuario u : usuarios) {
            if(userID == u.getIdentificador()) {
                return u;
            }
        }
        return null;
    }

    /**
     * Metode que retorna els usuaris en un format que ens facilita guardar-los en un fitxer.
     * @return ArrayList amb els usuaris.
     */
    public ArrayList<Integer> getUsuarios_guardar() {
        ArrayList<Integer> res = new ArrayList<>();
        for (Usuario u: getUsuarios()) {
            res.add(u.getIdentificador());
        }
        return res;
    }

    /*METODOS PRIVADOS*/

    //busca un identificador nuevo para un usuario
    private Integer getNuevoIdentificador() {
        boolean encontrado = false;
        Integer id = 1;
        while(!encontrado) {
            if(getUsuario(id) == null) encontrado = true;
            else ++id;
        }
        return id;
    }

}
