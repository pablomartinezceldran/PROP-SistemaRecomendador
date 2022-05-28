package CapaPersistencia;

import CapaDomini.Controladors.ControladorDominio;

import java.io.*;
import java.util.*;

/**
 * Controlador de Persistencia
 */
public class ControladorPersistencia {

    ArrayList<Integer> idsItemsKnown = new ArrayList<>();
    ArrayList<Integer> idsUsersKnown = new ArrayList<>();

    /**
     *Constructora del ControladorPersistencia
     */
    public ControladorPersistencia(){}


    /**
     * Metode que llegeix els usuaris de ratings i els guarda en users.csv
     */
    public void completarUsers() {
        String valor;
        ArrayList<Integer> usuarios = new ArrayList<>();
        int index_usr = -1;
        int index_item = -1;
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.db.csv"))) {
            valor = br.readLine();
            int i = 0;
            for (String s :Arrays.asList(valor.split(","))) {
                if (s.equals("userId")) index_usr = i;
                else if (s.equals("itemId")) index_item = i;
                i++;
            }
            while ((valor = br.readLine()) != null) {
                int userid = Integer.parseInt(Arrays.asList(valor.split(",")).get(index_usr));
                if (!usuarios.contains(userid)) usuarios.add(userid);
            }
        } catch (IOException ignored) {}

        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.test.known.csv"))) {
            br.readLine();
            while((valor = br.readLine()) != null) {
                int userid = Integer.parseInt(Arrays.asList(valor.split(",")).get(index_usr));
                if (!usuarios.contains(userid)) usuarios.add(userid);
                if (!idsUsersKnown.contains(userid)) idsUsersKnown.add(userid);
                int itemid = Integer.parseInt(Arrays.asList(valor.split(",")).get(index_item));
                if (!idsItemsKnown.contains(itemid)) idsItemsKnown.add(itemid);
            }
        } catch (IOException ignored) {}


        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "users.csv"))) {
            writer.write("userID\n");
            for( Integer userID : usuarios) {
                writer.write(userID.toString() + "\n");
            }
        } catch (IOException ignored) {}

    }

    /**
     * Metode que llegeix els atributs del fitxer items.csv
     * @return ArrayList amb tots els atributs dels items
     */
    public ArrayList<String> leerAtributos() {
        String cabecera;
        ArrayList<String> atributos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "items.csv"))) {
            cabecera = br.readLine();
            atributos.addAll(Arrays.asList(cabecera.split(",")));
        } catch (IOException e) {
            System.out.println("Error en cargar atributos");
        }
        return atributos;
    }

    /**
     * Metode que llegeix els usuaris del fitxer users.csv
     * @return ArrayList amb tots els usuaris
     */
    public ArrayList<Integer> leerUsuarios() {
        String valor;
        ArrayList<Integer> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "users.csv"))) {
            br.readLine();
            while((valor = br.readLine()) != null) {
                int userid = Integer.parseInt(valor);
                if (!usuarios.contains(userid)) usuarios.add(userid);
            }
        } catch (IOException ignored) {}
        return usuarios;
    }

    /**
     * Metode que llegeix les recomanacions del fitxer recomendaciones.csv
     * @return ArrayList amb totes les recomanacions
     */
    public ArrayList<List<String>> leerRecomendaciones() {
        String recomendacion;
        ArrayList<List<String>> recomendaciones = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "recomendaciones.csv"))) {
            while((recomendacion = br.readLine()) != null) {
                List<String> valores = Arrays.asList(recomendacion.split(","));
                recomendaciones.add(valores);
            }
            try {
                recomendaciones.remove(0);
            }
            catch (Exception ignored) {}
        }
        catch (IOException ignored) {}
        return recomendaciones;
    }

    /**
     * Metode que llegeix les valoracions dels fitxers ratings.db.csv i ratings.test.known.csv
     * @return ArrayList amb totes les valoracions
     */
    public ArrayList<List<String>> leerValoraciones() {
        String valoracion;
        ArrayList<List<String>> valoraciones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.db.csv"))) {
            while ((valoracion = br.readLine()) != null) {
                List<String> valores = Arrays.asList(valoracion.split(","));
                valoraciones.add(valores);
            }


        } catch (IOException ignored) {}
        int ratingsDBsize = valoraciones.size();
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.test.known.csv"))) {
            while ((valoracion = br.readLine()) != null) {
                List<String> valores = Arrays.asList(valoracion.split(","));
                try {
                    int userid = Integer.parseInt(valores.get(0));
                    if (!idsUsersKnown.contains(userid)) idsUsersKnown.add(userid);
                    int itemid = Integer.parseInt(valores.get(1));
                    if (!idsItemsKnown.contains(itemid)) idsItemsKnown.add(itemid);
                } catch (Exception ignored) {}
                valoraciones.add(valores);
            }
            valoraciones.remove(ratingsDBsize);
        } catch (IOException ignored) {}
        return valoraciones;
    }

    /**
     * Metode que llegeix els items del fitxer items.csv
     * @return ArrayList amb tots els items
     */
    public ArrayList<List<String>> leerItems() {
        String item;
        ArrayList<List<String>> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "items.csv"))) {
            while ((item = br.readLine()) != null) {
                List<String> valores = Arrays.asList(item.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                if (items.size() > 0 && valores.size() != items.get(0).size()) {
                    ArrayList<String> aux = new ArrayList<>(valores);
                    aux.add("");
                    valores = aux;
                }
                items.add(valores);
            }
            items.remove(0);
        } catch (IOException ignored) {}
        return items;
    }


    /**
     * Metode que llegeix els pesos dels atributs del fitxer peso_atributos.csv
     * @return ArrayList amb tots els pesos dels atributs
     */
    public ArrayList<Double> leerPesoAtributos() {
        String valor;
        ArrayList<Double> pesos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "peso_atributos.csv"))) {

            while((valor = br.readLine()) != null) {
                double peso = Double.parseDouble(valor);
                pesos.add(peso);
            }
        } catch (IOException ignored) {}
        return pesos;
    }

    /**
     * Metode que llegeix els tipus dels atributs del fitxer tipo_atributos.csv
     * @return ArrayList amb tots els tipus dels atributs
     */
    public ArrayList<String> leerTiposAtributos() {

        String tipos;
        ArrayList<String> Tipos = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "tipo_atributos.csv"))) {
            while((tipos = br.readLine()) != null) {
                Tipos.add(tipos);
            }
        }
        catch (IOException ignored) {}
        return Tipos;
    }


    /**
     * Metode que llegeix les valoracions del fitxer ratings.test.unknown.csv
     * @return ArrayList de totes les valoracions
     */
    public ArrayList<List<String>> leerValoracionesUnknown() {
        String valoracion;
        ArrayList<List<String>> valoracionesUnknown = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.test.unknown.csv"))) {
            while((valoracion = br.readLine()) != null) {
                List<String> valores = Arrays.asList(valoracion.split(","));
                valoracionesUnknown.add(valores);
            }
        }
        catch (IOException ignored) {}
        return valoracionesUnknown;
    }


    /**
     * Metode que actualitza el fitxer users.csv amb els usuaris donats
     * @param usuarios ArrayList de tots els id dels usuaris
     */
    public void guardarUsuarios(ArrayList<Integer> usuarios) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "users.csv"))) {
            writer.write("userID\n");
            for( Integer userID : usuarios) {
                writer.write(userID.toString() + "\n");
            }
        } catch (IOException ignored) {}
    }

    /**
     * Metode que actualitza el fitxer items.csv amb els items donats
     * @param items ArrayList amb els items
     */
    public void guardarItems(ArrayList<List<String>> items) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "items.csv"))) {
            writer.write(String.join(",",ControladorDominio.getNombresAtributos()) + "\n");
            for( List<String> item : items) {
                writer.write(String.join(",",item) + "\n");
            }
        } catch (IOException e) {}
    }

    /**
     * Metode que actualitza els fitxers ratings.db.csv i ratings.test.known.csv amb les valoracions donades
     * @param valoraciones ArrayList amb les valoracions
     */
    public void guardarValoraciones(ArrayList<List<String>> valoraciones) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "ratings.db.csv"))) {
            String header;
            try(BufferedReader br = new BufferedReader(new FileReader("FicherosEntrada/" + "ratings.test.known.csv"))) {
                header = br.readLine();
            }
            writer.write(header + "\n");
            ArrayList<String> headerParts = new ArrayList<>(Arrays.asList(header.split(",")));
            int index_usr = 0, index_item = 1, index_rating = 2;
            for (int i = 0; i < 3; i++) {
                if (headerParts.get(i).equals("userId")) index_usr = i;
                else if (headerParts.get(i).equals("itemId")) index_item = i;
                else index_rating = i;
            }
            for( List<String> valoracion : valoraciones) {
                List<String> valoracionesInOrder = new ArrayList<String>(Arrays.asList("", "", ""));
                valoracionesInOrder.set(index_usr, valoracion.get(0));
                valoracionesInOrder.set(index_item, valoracion.get(1));
                valoracionesInOrder.set(index_rating, valoracion.get(2));
                writer.write(String.join(",",valoracionesInOrder) + "\n");
            }
        } catch (IOException ignored) {}
    }

    /**
     * Metode que actualitza el fitxer recomendaciones.csv amb les recomanacions donades
     * @param recomendaciones ArrayList amb les recomanacions
     */
    public void guardarRecomendaciones(ArrayList<List<String>> recomendaciones) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "recomendaciones.csv"))) {
            writer.write("userID,Recomendacion\n");
            for(List<String> recomendacion : recomendaciones) {

                for (int i = 0; i < recomendacion.size(); i++) {
                    if (i == recomendacion.size() - 1) writer.write(recomendacion.get(i));
                    else writer.write(recomendacion.get(i) + ",");
                }
                writer.write("\n");
            }
        } catch (IOException ignored) {}
    }

    /**
     * Metode que actualitza el fitxer tipo_atributos.csv amb els tipus donats
     * @param tipos ArrayList amb els tipus
     */
    public void guardarTiposAtributos(ArrayList<String> tipos) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "tipo_atributos.csv"))) {
            for( String tipo : tipos) {
                writer.write(tipo + "\n");
            }
        } catch (IOException ignored) {}
    }

    /**
     * Metode que actualitza el fitxer peso_atributos.csv amb els pesos donats
     * @param pesos ArrayList amb els pesos
     */
    public void guardarPesosAtributos(ArrayList<Double> pesos) {
        try (Writer writer = new BufferedWriter(new FileWriter("FicherosEntrada/" + "peso_atributos.csv"))) {
            for( Double peso : pesos) {
                writer.write(peso + "\n");
            }
        } catch (IOException ignored) {}
    }


    /**
     * Metode que retorna els usuaris del fitxer ratings.test.known.csv
     * @return  ArrayList amb els id dels usuaris
     */
    public ArrayList<Integer> getUsersKnown() {
        return idsUsersKnown;
    }

    /**
     * Metode que retorna els items del fitxer ratings.test.known.csv
     * @return  ArrayList amb els id dels items
     */
    public ArrayList<Integer> getItemsKnown() {
        return idsItemsKnown;
    }
}
