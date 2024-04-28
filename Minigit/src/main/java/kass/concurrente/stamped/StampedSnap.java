package kass.concurrente.stamped;
/**
 * Clase que modela un StampedSnap
 * @author Kassandra Mirael
 * @version 1.0
 */
public class StampedSnap<T>{
    public static final String LOG = "StampedSnap";

    private long stamp;
    private T value;
    private T[] snap;

    /**
     * Meotod constructor inicial
     * @param value El valorr a registrar
     */
    public StampedSnap(T value){
        this.stamp = 0;
        this.value = value;
        this.snap = null;
    }

    /**
     * Metodo constructor general
     * @param label El label o estampa
     * @param value El valor a registrar
     * @param snap Los cambios de este
     */
    public StampedSnap(long label, T value, T[] snap){
        this.stamp = label;
        this.value = value;
        this.snap = snap;
    }

    /**
     * Metodo que retorna la estampa
     * @return La estampa
     */
    public long getStamp(){
        return stamp;
    }

    /**
     * Metodo que retorna el valor
     * @return El valor
     */
    public T getValue(){
        return value;
    }

    /**
     * Metodo que retorna el arreglo de snaps
     * @return El arreglo
     */
    public T[] getSnap(){
        return snap;
    }

    /**
     * Metodo que asigna una nueva estampa
     * @param stamp La nueva estampa
     */
    public void setStamp(long stamp){
        this.stamp = stamp;
    }

    /**
     * Metodo que asigna un nuevo valor
     * @param value El nuevo valor
     */
    public void setValue(T value){
        this.value = value;
    }

    /**
     * Metodo que asigna un nuevo arreglo de snapss
     * @param snap El nuevo arreglo
     */
    public void setSnap(T[] snap){
        this.snap = snap;
    }
}