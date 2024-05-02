package kass.concurrente.stamped;

import java.util.ArrayList;

/**
 * Clase que modela un StampedSnap
 * @author Kassandra Mirael. Equipo los hilos del barrio
 * @version 1.gi0
 */
public class StampedSnap<T>{
    public static final String LOG = "StampedSnap";

    private long stamp;
    private T value;
    private  ArrayList<T> snap;

    /**
     * Meotod constructor inicial
     * @param value El valor a registrar
     */
    public StampedSnap(T value){
        this.stamp = 0;
        this.value = value;
        this.snap = new ArrayList<>();
        this.snap.add(value);
    }

    /**
     * Metodo constructor general
     * @param label El label o estampa
     * @param value El valor a registrar
     * @param snap Los cambios de este
     */
    public StampedSnap(long label, T value, ArrayList<T> snap){
        this.stamp = label;
        this.value = value;
        this.snap = snap;
    }

    /**
     * Metodo que retorna la estampa
     * @return La estampa
     */
    public long getStamp(){
        final String log = "getStamp";
        return stamp;
    }

    /**
     * Metodo que retorna el valor
     * @return El valor
     */
    public T getValue(){
        final String log = "getValue";
        return value;
    }

    /**
     * Metodo que retorna el arreglo de snaps
     * @return El arreglo
     */
    public ArrayList<T> getSnap(){
        final String log = "getSnap";
        return snap;
    }

    /**
     * Metodo que asigna una nueva estampa
     * @param stamp La nueva estampa
     */
    public void setStamp(long stamp){
        final String log = "setStamp";
        this.stamp = stamp;
    }

    /**
     * Metodo que asigna un nuevo valor
     * @param value El nuevo valor
     */
    public void setValue(T value){
        final String log = "setValue";
        this.value = value;
    }

    /**
     * Metodo que asigna un nuevo arreglo de snapss
     * @param snap El nuevo arreglo
     */
    public void setSnap(ArrayList<T> snap){
        final String log = "setSnap";
        this.snap = snap;
    }

    /**
     * agrega un commit a la lista de commits.
     * @param value nuevo commit.
     */
    public void addCommit(T value){
        final String log = "addCommit";
        snap.add(value);
    }
    /**
     * Retorna el commit de un índice dado.
     * @param index índice del commit en la lista de commits.
     * @return T commit que está en el índice index
     */
    public T getCommit(int index){
        final String log = "getCommit";
        return snap.get(index);
    }
}