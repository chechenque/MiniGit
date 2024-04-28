package kass.concurrente.stamped;

/**
 * Clase que modela un StampedValue
 * @author Kassandra Mirael
 * @version 1.0
 */
public class StampedValue<T> {
    public static final String LOG = "StampedValue";

    private long stamp;
    private T value;
    
    public static StampedValue MIN_VALUE = new StampedValue(null);

    /**
     * Metodo constructor inicial
     * @param init El valor a registrar
     */
    public StampedValue(T init){
        this.stamp = 0;
        this.value = init;
    }

    /**
     * Metodo constructor general
     * @param stamp El identificador del stamp
     * @param value El valor a registrar
     */
    public StampedValue(long stamp, T value){
        this.stamp = stamp;
        this.value = value;
    }

    /**
     * Metoodo que retorna la estampa mas grande
     * @param x La estampa x
     * @param y La estampa y
     * @return El mas grande de las 2
     */
    public static StampedValue max(StampedValue x, StampedValue y){
        return (x.stamp > y.stamp) ? x:y;
    }

    /**
     * Metodo que retorna la estampas
     * @return La estampa
     */
    public long getStamp(){
        return stamp;
    }

    /**
     * Metood que retorna el valor
     * @return El valor
     */
    public T value(){
        return value;
    }

    /**
     * Metodo que asigna una nueva stampa
     * @param stamp La nueva estampa
     */
    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    /**
     * Metodo quee asigna un nuevo valor
     * @param value El nuevo valor
     */
    public void setValue(T value){
        this.value = value;
    }
}
