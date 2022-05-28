package CapaDomini.Controladors;

import CapaDomini.Model.*;
import CapaDomini.Model.Auxiliares.Cluster;
import CapaDomini.Model.Auxiliares.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Controlador de Recomanacions
 */
public class ControladorRecomendaciones {

    private static int K = 25;
    private static int NItemsPorRecomendacion = 10;
    private static double val_maxima = 5.0;
    private ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
    private Map<Usuario, ArrayList<List<String>>> RecomendacionesGuardadas = new HashMap<>();
    private ArrayList<Recomendacion> RecomendacionesCalculadas = new ArrayList<>();

    // Constructora

    /**
     *Constructora del ControladorRecomendaciones
     * Llegeix el valor de les variables K, NItemsPorRecomendacion, val_maxima i també inicialitza els clusters per al collaborative filtering.
     */
    public ControladorRecomendaciones() {
        // Calculo de los kmeans

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            K = Integer.parseInt(properties.getProperty("VALOR_K"));
            NItemsPorRecomendacion = Integer.parseInt(properties.getProperty("ITEMS_POR_RECOMENDACION"));
            val_maxima = Integer.parseInt(properties.getProperty("VALORACION_MAXIMA"));
        } catch (IOException e) {
            //e.printStackTrace();
//            System.out.println("NO existe el archivo config.properties");
        }

        k_means(K);
    }


    /* METODOS PUBLICOS*/

    /**
     *Metode que carrega les recomanacions guardades.
     * Carrega a la variable RecomendacionesGuardadas les recomanacions que llegeix del fitxer on estan les recomanacions guardades.
     * @param recomendacionesLeidas recomanacions llegides del fitxer.
     */
    public void leerRecomendacionesGuardadas(ArrayList<List<String>> recomendacionesLeidas) {
        for (List<String> l: recomendacionesLeidas) {
            Usuario u = ControladorDominio.getUsuario(Integer.parseInt(l.get(0)));
            if (RecomendacionesGuardadas.get(u) == null) {
                ArrayList<List<String>> nuevaR = new ArrayList<>();
                nuevaR.add(l.subList(1, l.size()));
                RecomendacionesGuardadas.put(u,nuevaR);
            }
            else RecomendacionesGuardadas.get(u).add(l.subList(1, l.size()));
        }
    }

    // Devuelve las recomendaciones guardadas de todos los usuarios con el usuario delante para escribirlo en recomendaciones.csv

    /**
     * Metode que retorna les recomanacions guardades.
     * @return ArrayList amb les recomanacions que han guardat els usuaris.
     */
    public ArrayList<List<String>> getRecomendacionesGuardadas() {
        ArrayList<List<String>> todas = new ArrayList<>();
        for (Usuario u: ControladorDominio.getUsuarios()) {
            if (RecomendacionesGuardadas.get(u) != null) {
                for (List<String> t: RecomendacionesGuardadas.get(u)) {
                    List<String> l = new ArrayList<String>();
                    l.add(String.valueOf(u.getIdentificador()));
                    l.addAll(t);
                    todas.add(l);
                }
            }
        }
        return todas;
    }

    // Devuelve las tres ultimas recomendaciones que ha guardado el usuario activo

    /**
     * Metode que retorna les 3 ultimes recomanacions que ha guardat el usuari actiu.
     * Retorna les 3 ultimes recomanacions guardades per l'usuari actiu per poder mostrarli quan ens demani veure les recomanacions que ha guardat.
     * @return ArrayList amb les 3 ultimes recomanacions del usuari actiu.
     */
    public ArrayList<List<String>> getRecomendacionesGuardadasMostrar() {
        Usuario u = ControladorDominio.getUsuarioActivo();
        if (RecomendacionesGuardadas.get(u) == null) return new ArrayList<List<String>>();
        ArrayList<List<String>> a= new ArrayList<List<String>> (RecomendacionesGuardadas.get(u).subList(Math.max(0,RecomendacionesGuardadas.get(u).size()-3),RecomendacionesGuardadas.get(u).size()));
        ArrayList<List<String>> r = new ArrayList<>();
        int i = 0;
        for (List<String> l: a) {
            List<String> t = new ArrayList<>();
            for (String s: l) {
                if (i % 2 == 0) {
                    t.add(s);
                    t.add(ControladorDominio.getItem(Integer.parseInt(s)).getNombre());
                }
                i++;
            }
            r.add(t);
        }
        return r;
    }

    // Devuelve las recomendaciones calculadas



    // Devuelve una recomendacion usando la tecnica de recomendacion especificada (0 -> Collaborative, 1 -> Content based, 2 -> Hybrid)

    /**
     * Metode que retorna una recomanacio.
     * Retorna una recomanació que es calcula amb el métode que indica el parametre, si es 0 collaborative filtering, si es 1 content based i si es 3 hybrid approach.
     * @param tipoRecomendacion enter que indica amb quin metode calculem la recomanacio.
     * @return recomanacio calculada amb el metode indicat.
     */
    public Recomendacion calculaRecomendacion(int tipoRecomendacion) {
        if (tipoRecomendacion == 0) return collaborative_filtering();
        else if (tipoRecomendacion == 1) return content_based_filtering();
        else return hybrid_approaches();
    }

    /**
     * Metode que guarda una recomanacio a la variable RecomendacionesGuardadas.
     * @param tipo enter que indica la recomanacio de quin dels 3 metodes volem guardar, si es 0 collaborative filtering, si es 1 content based i si es 3 hybrid approach.
     */
    public void guardaRecomendacion(int tipo) { //CUANDO SE PULSE EL BOTON DE GUARDAR SE LLAMA A ESTA FUNCION
        Recomendacion r = RecomendacionesCalculadas.get(tipo);
        List<String> s = new ArrayList<>();
        //s.add(String.valueOf(r.getUsuario().getIdentificador()));
        for (Pair p: r.getItems()) {
            s.add( String.valueOf(((Item)p.getFirst()).getIdentificador()));
            s.add( String.valueOf(p.getSecond()));
        }
        if (RecomendacionesGuardadas.get(ControladorDominio.getUsuarioActivo()) == null) {
            ArrayList<List<String>> nuevaR = new ArrayList<>();
            nuevaR.add(s);
            RecomendacionesGuardadas.put(ControladorDominio.getUsuarioActivo(), nuevaR);
        }
        else RecomendacionesGuardadas.get(ControladorDominio.getUsuarioActivo()).add(s);
    }

    // Devuelve la calidad de una recomendacion

    /**
     * Metode que retorna una recomanacio per calcular el DCG.
     * Aquest metode retorna una recomanacio on els items seran els mateixos que a la recomanació ideal del usuari, que trobem al unknown.
     * @param recomendacion recomanacio amb tots els items que es poden recomanar al usuari.
     * @return recomanacio amb els items de la recomanacio ideal.
     */
    public Recomendacion recomanacioDCG (Recomendacion recomendacion){
        Map<Item, Double> rec = new HashMap<>();
        for (Pair p: recomendacion.getItems()) {
            rec.put((Item)p.getFirst(), (double)p.getSecond());
        }

        ArrayList<Valoracion> Unknown_i= ControladorDominio.getNuevos_items_unknown().get(recomendacion.getUsuario());
        ArrayList<Pair<Item,Double>> n_rec = new ArrayList<>();
        for (Valoracion val: Unknown_i) {
            Item i = val.getItem();
            Pair<Item, Double> p = new Pair<>(i, rec.get(i));
            if (p.getSecond() != null) n_rec.add(p);
        }
        n_rec = ordenar(n_rec);
        Recomendacion r = new Recomendacion(recomendacion.getUsuario(), n_rec);
        return r;
    }

    // Devuelve la calidad de una recomendacion

    /**
     * Metode que retorna el valor del algorisme DCG.
     * @param recomendacion recomanacio que volem evaluar.
     * @return valor del DCG.
     */
    public Double DCG (Recomendacion recomendacion){
        recomendacion = new Recomendacion(recomendacion.getUsuario(), new ArrayList<Pair<Item, Double>> (recomendacion.getItems().subList(0,Math.min(NItemsPorRecomendacion, recomendacion.getItems().size()))));
        double dcg = 0;
        int pos = 1;
        for (int i = 0; i < recomendacion.getItems().size(); i++) {
            Pair<Item,Double> item_valorado = recomendacion.getItems().get(i);
            Double valor;
            boolean encontrado = false;
            int j = 0;
            ArrayList<Valoracion> Unknown_i= ControladorDominio.getNuevos_items_unknown().get(recomendacion.getUsuario());
            while(!encontrado & j < Unknown_i.size()) {
                Valoracion val = Unknown_i.get(j);
                if (item_valorado.getFirst().getIdentificador() == val.getItem().getIdentificador()) {
                    encontrado = true;
                    dcg += ((Math.pow(2,(val.getPuntuacion())))- 1)/(Math.log(pos + 1) / Math.log(2));//+1????
                }
                j++;
            }
            if (!(i + 1 == recomendacion.getItems().size()) && !Objects.equals(Math.min(val_maxima,item_valorado.getSecond()), Math.min(val_maxima,recomendacion.getItems().get(i + 1).getSecond()))) {
                pos++;
            }
        }
        return dcg;
    }

    /**
     * Metode que retorna el valor ideal del DCG per al usuari donat.
     * @param usr usuari del que volem obtenir el DCG ideal.
     * @return valor del DCG ideal.
     */
    public Double IDCG (Usuario usr) {
        double idcg = 0; //mirar com declarem aixo
        ArrayList<Valoracion> vali = ControladorDominio.getNuevos_items_unknown().get(usr);
        int pos = 0;
        double puntuacion_aux = -1.0;
        for (Valoracion i : vali) {
            if (i.getPuntuacion() != puntuacion_aux ) pos++;
            idcg += ((Math.pow(2, i.getPuntuacion())) - 1) / (Math.log(pos + 1) / Math.log(2)); //revisar tema pos
            puntuacion_aux = i.getPuntuacion();
        }
        return idcg;
    }

    /**
     * Metode que elimina les recomanacions guardades del usuari donat.
     * @param u usuari del que volem eliminar les recomanacions guardades.
     */
    public void borrarUsuarioRecGuardadas(Usuario u) {//Borra las recomendaciones guardadas del usuario que borramos y recalcula k_means
        RecomendacionesGuardadas.remove(u);
        k_means(K);
    }

    /**
     * Metode que retorna els Items recomanats de la recomanació calculada amb el metode indicat.
     *
     * @param tipoRecomendacion enter que indica la recomanacio amb quin dels 3 metodes volem calcular la recomanacio, si es 0 collaborative filtering, si es 1 content based i si es 3 hybrid approach.
     * @return recomanacio generada amb el metode indicat.
     */
    public ArrayList<Pair<Integer, String>> getItemsRecomendados(int tipoRecomendacion) {
        Recomendacion recomendacion = calculaRecomendacion(tipoRecomendacion);
        recomendacion = new Recomendacion(recomendacion.getUsuario(), new ArrayList<Pair<Item, Double>> (recomendacion.getItems().subList(0,Math.min(NItemsPorRecomendacion, recomendacion.getItems().size()))));
        if (RecomendacionesCalculadas.size() < 3) {
            RecomendacionesCalculadas.add(recomendacion);
        }
        else RecomendacionesCalculadas.set(tipoRecomendacion, recomendacion);
        ArrayList<Pair<Item,Double>> items = recomendacion.getItems();
        ArrayList<Pair<Integer,String>> result = new ArrayList<>();
        for(Pair<Item,Double> r : items) result.add(new Pair<>(r.getFirst().getIdentificador(),r.getFirst().getNombre()));
        return result;
    }
    // Calcula los diferentes tipos de usuarios utilizando el algoritmo de k-means

    /**
     *Metode que calcula els clusters seguint K_means.
     *
     * @param k numero de clusters a generar.
     */
    public void k_means(int k) {
        Clusters.clear();
//      int length = ctrlDominio.getItems().size();
        ArrayList<Usuario> usr_cogidos = new ArrayList<>();
        while(usr_cogidos.size() < k) {
            int rand = (int) (ControladorDominio.getUsuarios().size() * new Random().nextDouble());
            Usuario randUser = ControladorDominio.getUsuarios().get(rand);
            if (!usr_cogidos.contains(randUser)) usr_cogidos.add(randUser);
        }
        for (Usuario usr: usr_cogidos) {
            ArrayList<Double> centroide = new ArrayList<>();
            for (Valoracion v : ControladorDominio.getValoraciones().get(usr)) {
                centroide.add(v.getPuntuacion());
            }
            Clusters.add(new Cluster(centroide));
        }
        ArrayList<Cluster> clusters_antics = new ArrayList<>();
        while (!clustersIguales(Clusters, clusters_antics)) {
            clusters_antics.clear();
            for (Cluster cluster : Clusters) {
                Cluster cluster_i = new Cluster(cluster);
                clusters_antics.add(cluster_i); //Fa clusters_antics = Clusters
                cluster.getUsuarios().clear(); //Treu tots els usuaris dels clusters
            }
            for (Usuario usuario : ControladorDominio.getUsuarios()) {
                asignar_cluster_cercano(usuario, Clusters); //assigna tots els usuaris al cluster mes proper
            }
            Clusters = recalcular_clusters(Clusters);//recalcula els centroides dels clusters
        }
    }

    /**
     * Metode que modifica el valor de les variables val_max, NItRec i k.
     * @param Val_max numero que indica el valor maxim de les valoracions.
     * @param NItRec valor que indica el numero d'items que hem de recomanar.
     * @param k valor que indica el numero de clusters per k_means.
     */
    public void setVarsRec(int Val_max, int NItRec, int k) {
        val_maxima = Val_max; NItemsPorRecomendacion = NItRec; K = k;
    }

    /**
     * Metode que retorna el valor de K (numero de clusters)
     * @return valor de K
     */
    public int getK() { return K; }

    /**
     * Metode que retorna el valor de val_maxima (valor maxim de les valoracions).
     * @return valor de val_maxima.
     */
    public double getval_maxima(){ return val_maxima;}


    /* METODOS PRIVADOS */


    // Calcula una recomendacion para el usuario activo utilizando collaborative filtering
    private Recomendacion collaborative_filtering() {
        return slopeOne(getClusterActivo());
    }



    // Retorna true si dos conjuntos de clusters del mismo size son iguales
    private boolean clustersIguales(ArrayList<Cluster> c1, ArrayList<Cluster> c2) {
        boolean iguales = true;
        if (c1.size() == c2.size()) {
            for (int i = 0; i < c1.size(); i++) {
                Cluster c11 = c1.get(i);
                Cluster c22 = c2.get(i);
                if (!c11.getUsuarios().equals(c22.getUsuarios()) || !c11.getCentroide().equals(c22.getCentroide())) {
                    iguales = false;
                    break;
                }
            }
        }
        else iguales = false;
        return iguales;
    }

    // Asigna el cluster mas cercano para un usuario
    private ArrayList<Cluster> asignar_cluster_cercano(Usuario usr, ArrayList<Cluster> clusters) {
        double min_distancia = val_maxima;
        int min_index = -1;
        for (int i = 0; i < Clusters.size(); i++) {
            double nueva_distancia = calcula_distancia(usr, clusters.get(i));
            if (nueva_distancia < min_distancia) {
                min_distancia = nueva_distancia;
                min_index = i;
            }
        }

        if(min_index == -1) {
            min_index = usr.getIdentificador() % K;
        }
        clusters.get(min_index).AddUsuario(usr);
        return clusters;
    }

    //Devuelve la distancia entre un usuario y un cluster
    private double calcula_distancia (Usuario user, Cluster cluster) {
        double distancia = 0;
        int interseccio = 0;
        ArrayList<Item> items = ControladorDominio.getItems();
        if (items == null) return -1;
        for (int i = 0; i < items.size(); ++i) {
            if (ControladorDominio.getValoraciones().get(user).get(i).getPuntuacion() != -1 && cluster.getValoracionCentroide(i) != -1) {
                distancia += Math.pow(ControladorDominio.getValoraciones().get(user).get(i).getPuntuacion() - cluster.getValoracionCentroide(i), 2.0);
                interseccio++;
            }
        }
        if (interseccio > 0) return (Math.sqrt(distancia)) / interseccio;
        else return val_maxima;
    }


    // Recalcula los centroides de los clusters
    private ArrayList<Cluster> recalcular_clusters (ArrayList<Cluster> clusters) { //MILLORA
        for (Cluster cluster: clusters) {
            ArrayList<Pair<Double, Integer>> mitja = new ArrayList<>(); //arraylist on per cada item tenim la suma de valoracions i el num de val
            for (Item i: ControladorDominio.getItems()) mitja.add(new Pair<>(0.0,0));
            for (Usuario usr : cluster.getUsuarios()) {
                for (int i = 0; i < ControladorDominio.getItems().size(); i++) {
                    double punt = ControladorDominio.getValoraciones().get(usr).get(i).getPuntuacion();
                    if (punt != -1) {
                        mitja.get(i).setFirst(mitja.get(i).getFirst() + punt);
                        mitja.get(i).setSecond(mitja.get(i).getSecond() + 1);
                    }
                }
            }
            int j = 0;
            for (Pair p: mitja) {
                if ((Integer)p.getSecond() != 0) {
                    cluster.getCentroide().set(j, (Double)p.getFirst()/(Integer)p.getSecond());
                }
                j++;
            }
        }
        return clusters;
    }

    // Devuelve un arraylist con los usuarios del cluster en el que se encuentra el usuario activo
    private ArrayList<Usuario> getClusterActivo(){
        for(Cluster cluster : Clusters) {
            ArrayList<Usuario> users = cluster.getUsuarios();
            for (Usuario usr : users) {
                if(usr.equals(ControladorDominio.getUsuarioActivo())) {
                    return users;
                }
            }
        }
        return new ArrayList<Usuario>();
    }

    // Calcula una recomendacion a partir de ordenar las valoraciones estimadas de los diferentes items que se han
    // valorado en el cluster donde se encuentra el usuario activo
    private Recomendacion slopeOne(ArrayList<Usuario> Cluster) {
        double media_activo = 0;
        ArrayList<Valoracion> valoraciones_activo = ControladorDominio.getValoraciones().get(ControladorDominio.getUsuarioActivo());
        int numValoraciones = 0;
        for (Valoracion valoraciones : valoraciones_activo) { //calculamos la emdia del usuario activo
            if (valoraciones.getPuntuacion() != -1) {
                media_activo += valoraciones.getPuntuacion();
                numValoraciones++;
            }
        }
        if (numValoraciones != 0) media_activo = media_activo/numValoraciones;
        Set<Item> items_grupo = new HashSet<>();

        for (Usuario usuario : Cluster) {
            for (Valoracion valoraciones : ControladorDominio.getValoraciones().get(usuario)) {
                if (valoraciones.getPuntuacion() != -1) items_grupo.add(valoraciones.getItem());
            }
        }

        for(Valoracion valoracion : valoraciones_activo) {
            if (valoracion.getPuntuacion() != -1) items_grupo.remove(valoracion.getItem());
        }

        ArrayList<Pair<Item, Double>> Predicciones = new ArrayList<>();
        for (Item j : items_grupo) {
            double prediccion = 0;
            int card_r = 0;
            for (Valoracion i : valoraciones_activo) {
                if (i.getPuntuacion() != -1) {
                    int card_s = 0;
                    double desviacion_media = 0;
                    for (Usuario usr : Cluster) {
                        int indice_i = ControladorDominio.getItemIndex(i.getItem().getIdentificador());
                        int indice_j = ControladorDominio.getItemIndex(j.getIdentificador());
                        double puntuacion_i = ControladorDominio.getValoraciones().get(usr).get(indice_i).getPuntuacion();
                        double puntuacion_j = ControladorDominio.getValoraciones().get(usr).get(indice_j).getPuntuacion();
                        if (puntuacion_j != -1 && puntuacion_i != -1) {
                            desviacion_media += (puntuacion_j - puntuacion_i);
                            card_s++;
                        }
                    }
                    if (card_s != 0) {
                        prediccion += desviacion_media / card_s;
                        card_r++;
                    }
                }
            }

            if (card_r != 0) {
                prediccion /= card_r;
            }
            prediccion += media_activo;
            Predicciones.add(new Pair(j, prediccion));
        }
        Predicciones = ordenar(Predicciones);

        return new Recomendacion(ControladorDominio.getUsuarioActivo(), Predicciones);

    }

    // Calcula una recomendacion para el usuario activo utilizando content based filtering
    private Recomendacion content_based_filtering() {
        ArrayList<Valoracion> valoracionesActivo = new ArrayList<>(ControladorDominio.getValoraciones().get(ControladorDominio.getUsuarioActivo()));
        int i;
        for (i = 0; i < valoracionesActivo.size(); i++) {
            if (valoracionesActivo.get(i).getPuntuacion() != -1) break;
        }
        if (i == valoracionesActivo.size()) {
            ArrayList<Pair<Item,Double>> recomanacio = new ArrayList<>();
            for (Valoracion v : valoracionesActivo) {
                recomanacio.add(new Pair<Item, Double>(v.getItem(), 0.0));
            }
            return new Recomendacion(ControladorDominio.getUsuarioActivo(), recomanacio);
        }
        Map<Item, ArrayList<Pair<Item, Double>>> k_nn_items = k_nn(valoracionesActivo);
        return  recomendacion_knn( k_nn_items, valoracionesActivo);
    }

    private Map<Item, ArrayList<Pair<Item, Double>>> k_nn(ArrayList<Valoracion> valoracionesActivo) {
        Map<Item, ArrayList<Pair<Item, Double>>> k_nn_items = new HashMap<>();
        ArrayList<Item> items_no_val = new ArrayList<>();
        ArrayList<Item> items_val = new ArrayList<>();
        for (Valoracion val: valoracionesActivo) {
            if (val.getPuntuacion() == -1) items_no_val.add(val.getItem());
            else items_val.add(val.getItem());
        }
        for (Item i : items_val) {
            ArrayList<Pair<Item, Double>> sim = new ArrayList<>();
            for (Item in : items_no_val) {
                sim.add(new Pair<Item, Double>(in,calcular_similitud(i,in)));
            }
            if (sim.size() > 1)  {
                sim = ordenar(sim);
                sim = new ArrayList<Pair<Item, Double>> (sim.subList(0,Math.min(ControladorDominio.getItems().size(), sim.size())));
            }
            k_nn_items.put(i, sim);
        }
        return k_nn_items;

    }

//
    private Recomendacion recomendacion_knn(Map<Item, ArrayList<Pair<Item, Double>>> k_nn_items, ArrayList<Valoracion> valoracionesActivo) {
        ArrayList<Pair<Item,Double>> recomanacio = new ArrayList<>();
        for (Valoracion val: valoracionesActivo) {
            if (val.getPuntuacion() !=-1) {
                for (Pair p : k_nn_items.get(val.getItem())) {
                    recomanacio = addRecomanacio(recomanacio, (Item) p.getFirst(), (double) p.getSecond() * val.getPuntuacion());
                }
            }
        }
        if (recomanacio.size() > 1) {
            recomanacio = ordenar(recomanacio);
            recomanacio = new ArrayList<Pair<Item, Double>> (recomanacio.subList(0,Math.min(ControladorDominio.getItems().size(), recomanacio.size())));
        }

        return new Recomendacion(ControladorDominio.getUsuarioActivo(), recomanacio);
    }



    // Ordena el vector de similitudes
    private ArrayList<Pair<Item, Double>> ordenar (ArrayList<Pair<Item, Double>> similitudes) {
        similitudes.sort(new Comparator<Pair<Item, Double>>() {
            @Override

            public int compare(Pair<Item, Double> o1, Pair<Item, Double> o2) {
                if ((double) o1.getSecond() > (double) o2.getSecond()) return -1;
                return 1;
            }
        });
        return similitudes;
    }

    // Añade una recomendacion a la lista de content si el item no se habia recomendado, si ya se recomendabase pondera su prediccion
    private ArrayList<Pair<Item, Double>> addRecomanacio(ArrayList<Pair<Item, Double>> recomanacio, Item item, Double prediccion) {
        boolean esta = false;
        int index = 0;
        for (Pair p:recomanacio) {
            if (p.getFirst().equals(item)) {
                esta = true;
                break;
            }
            index++;
        }
        Pair p = new Pair<Item, Double>(item, prediccion);
        if (esta) {
            p = recomanacio.get(index);
            p.setSecond(Math.min(val_maxima, Math.max(prediccion, (double)p.getSecond()))); //si esta repetida em quedo amb la prediccio millor caldria sumar algo maybe
            recomanacio.set(index,p);
        }
        else recomanacio.add(p);
        return recomanacio;
    }

    // Devuelve una estimación de la similitud entre dos items
    private double calcular_similitud (Item item1, Item item2) { //s'ha de fer algo rollo atribut rellevants pqq sino no te sentit la similitud
        double similitud = 0;
        int suma_pesos = 0;
        for(int i = 0; i < Atributos.getNombres().size(); i++) {
            Object atribItem1 = item1.getAtributo(Atributos.getNombre(i));
            Object atribItem2 = item2.getAtributo(Atributos.getNombre(i));
            if (atribItem1 != null && atribItem2 != null) {
                if (Objects.equals(ControladorDominio.getTipo(i), "String")) {
                    List<String> str1 = Arrays.asList(atribItem1.toString().split(";"));
                    List<String> str2 = Arrays.asList(atribItem2.toString().split(";"));
                    double atrib_sim = 0;
                    for (String s1 : str1) {
                        for (String s2 : str2) {
                            if (s1.equals(s2)) atrib_sim++;
                        }
                    }
                    similitud += Atributos.getPeso(i)*(atrib_sim/(str1.size() + str2.size() - atrib_sim));
                    suma_pesos += Atributos.getPeso(i);
                }
                else if (Objects.equals(Atributos.getTipo(i), "Numeric")) {
                    double maxval = ControladorDominio.get_Maxval(Atributos.getNombre(i));
                    double distance = 0.0;
                    try {
                        distance = Math.abs((double) ((Integer) atribItem1) - (double) ((Integer) atribItem2));
                    }
                    catch (Exception e) {
                        distance = Math.abs((double) (atribItem1) - (double) (atribItem2));
                    }
                    similitud += Atributos.getPeso(i)*(1-(distance/maxval));
                    suma_pesos += Atributos.getPeso(i);
                }

            }
        }

        return similitud/suma_pesos;
    }



    // Devuelve una recomendacion utilizando collaborative filtering + content based filtering

    private Recomendacion hybrid_approaches() {
        Recomendacion rec_cf = collaborative_filtering();

        Recomendacion rec_cbf = content_based_filtering();
        double max = rec_cbf.getItems().get(0).getSecond();
        for (Pair<Item, Double> p : rec_cbf.getItems()) {
            p.setSecond(p.getSecond()*(val_maxima/max));
        }

        ArrayList<Pair<Item, Double>> recomendacionhybrid = new ArrayList<>();
        recomendacionhybrid.addAll(rec_cf.getItems());
        for (Pair<Item, Double> p : rec_cbf.getItems()) {
            boolean found = false;
            for (Pair<Item, Double> p2 : recomendacionhybrid) {
                if (p2.getFirst() == p.getFirst()) {
                    p2.setSecond((double) Math.max(p.getSecond(), p2.getSecond()));
                    found = true;
                    break;
                }
            }
            if (!found) recomendacionhybrid.add(p);
        }
        recomendacionhybrid = ordenar(recomendacionhybrid);
        return new Recomendacion(ControladorDominio.getUsuarioActivo(), recomendacionhybrid);
    }
}