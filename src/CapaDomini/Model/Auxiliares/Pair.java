package CapaDomini.Model.Auxiliares;

/**
 * Pair representa una parella de objectes.
 * @param <L> Primer objecte de la parella.
 * @param <R> Segon objecte de la parella.
 */
public class Pair<L, R> {
    private L l;
    private R r;

    /**
     * Constructora de la classe Pair.
     * @param l primer objecte de la parella.
     * @param r segon objecte de la parella.
     */
    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    /**
     * Metode que retorna el primer element de la parella.
     * @return el primer element de la parella.
     */
    public L getFirst() {
        return l;
    }

    /**
     * Metode que retorna el segon element de la parella.
     * @return el segon element de la parella.
     */
    public R getSecond() {
        return r;
    }

    /**
     * Metode que modifica el primer element de la parella.
     * @param l nou valor del primer element de la parella.
     */
    public void setFirst(L l) {
        this.l = l;
    }

    /**
     * Metode que modifica el segon element de la parellla.
     * @param r nou valor del segon element de la parella.
     */
    public void setSecond(R r) {
        this.r = r;
    }
}
