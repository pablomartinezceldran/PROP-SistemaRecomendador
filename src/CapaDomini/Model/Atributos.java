package CapaDomini.Model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Atributos
 * Singleton que s’utilitza per a guardar els noms dels atributs dels ítems, els noms del atributs seleccionats per l’administrador per al filtratge, els tipus dels atributs (si un atribut és string o numèric) i els pes de cada atribut pel càlcul de les recomanacions.
 */
public class Atributos {

    private static ArrayList<String> Nombres;
    private static ArrayList<String> Tipos; //faltan setters (Allowed values: string, int ,double
    private static ArrayList<Double> Pesos;  //faltan setters
    private static Atributos instancia = null;
    private String id;
    private String nombre;

    private Atributos() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            id = properties.getProperty("IDENTIFICADOR");
            nombre = properties.getProperty("NOMBRE");
        } catch (Exception e) {}
    }

    /**
     * Metode que retorna l'unica instancia de la classe
     * @return l'unica instancia de la classe
     */
    public static Atributos getInstancia() {
        if (instancia == null) Atributos.instancia = new Atributos();
        return instancia;
    }

    /**
     * Metode que retorna els noms dels atributs
     * @return conjunt dels noms dels atributs dels items
     */
    public static ArrayList<String> getNombres() {
        return Nombres;
    }

    /**
     * Metode que retorna el nom d'un atribut en concret
     * @param index index del nom del atribut que es vol
     * @return el nom del atribut
     */
    public static String getNombre(int index) {
        if (Nombres != null && index < Nombres.size()) return Nombres.get(index);
        return null;
    }

    /**
     * Metode que retorna el tipus d'un atribut
     * @param index index del nom del atribut
     * @return nom del tipus del nom del atribut
     */
    public static String getTipo(int index) {
        if (Tipos != null && index < Tipos.size()) return Tipos.get(index);
        return null;
    }

    /**
     * Metode que retorn el pes d'un atribut
     * @param index index del nom del atribut
     * @return pes del atribut
     */
    public static Double getPeso(int index) {
        if (Pesos != null && index < Pesos.size()) return Pesos.get(index);
        return null;
    }

    /**
     * Metode que modifica els nombres dels atributs
     * @param nombres conjunt dels noms
     */
    public void setNombres(ArrayList<String> nombres) {
        Nombres = nombres;
    }

    /**
     * Metode que retorna el nom de l'atribut que identifica els items
     * @return el nom de l'atribut que identifica els items
     */
    public String getId() {
        return id;
    }

    /**
     * Metode que retorna el nom de l'atribut del nom del items
     * @return el nom de l'atribut del nom dels items
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Metode que retorna els pesos dels atributs
     * @return pesos del atributs
     */
    public ArrayList<Double> getPesos() {
        return Pesos;
    }

    /**
     * Metode que modifica els pesos dels atributs
     * @param pesos pesos dels atributs
     */
    public void setPesos(ArrayList<Double> pesos) {
        Pesos = pesos;
    }

    /**
     * Metode que modifica els tipus dels atributs
     * @param tipos tipus dels atributs
     */
    public void setTipos(ArrayList<String> tipos) {
        Tipos = tipos;
    }
}
