package kass.concurrente.snapshot.snapshot_imp;

import java.util.ArrayList;
import java.util.Arrays;

import kass.concurrente.snapshot.Snapshot;
import kass.concurrente.stamped.StampedSnap;

/**
 * Clase generica que modela una Snapshot que implementa la interfaz Snapshot
 * @author
 */
public class WFSnapshotArray<T> implements Snapshot<T> {
    // Tabla de valores
    private StampedSnap<T>[] aTable;
    // Valor inicial
    private T init;

    /**
     * Constructor que recibe la capacidad maxima de valores a guardar
     * y el valor inicial.
     * @param capacidad la capacidad maxima de valores a guardar.
     * @param inicial el valor inicial a guardar.
     */
    @SuppressWarnings("unchecked")
    public WFSnapshotArray(Integer capacity, T init){
        this.aTable = new StampedSnap[capacity];
        this.init = init;

        for(Integer i = 0; i < this.aTable.length; i++){
            this.aTable[i] = new StampedSnap<>(init);
        }
    }

    /**
     * Metodo que escribe el valor v en el registro
     * del proceso que realiza la llamada.
     * @param value La variable a escribir en el arreglo.
     */
    @Override
    public void update(T value) {
        Integer id = Integer.parseInt(Thread.currentThread().getName());
        if (id >= this.aTable.length) {
            expandirArreglo();
        }
        ArrayList<T> snap = scan();
        StampedSnap<T> oldValue = this.aTable[id];
        StampedSnap<T> newValue = new StampedSnap<>(oldValue.getStamp()+1, value, snap);
        this.aTable[id] = newValue;
    }

    /**
     * Metodo auxiliar que aumenta el tamanio al doble de aTable,
     * cuando este se queda sin espacio.
     */
    @SuppressWarnings("unchecked")
    private void expandirArreglo() {
        StampedSnap<T>[] nuevoArraglo = new StampedSnap[this.aTable.length * 2];
        for (int i = 0; i < this.aTable.length; i++) {
            if (i <= this.aTable.length) {
                nuevoArraglo[i] = this.aTable[i];
            } else {
                nuevoArraglo[i] = new StampedSnap<>(this.init);
            }
        }
        this.aTable = nuevoArraglo;
    }

    /**
     * Metodo que construye una vista instantanea
     * del arreglo de registros.
     * @return El arreglo de registros.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<T> scan() {
        StampedSnap<T>[] oldCopy;
        StampedSnap<T>[] newCopy;
        Boolean[] moved = new Boolean[this.aTable.length];
        oldCopy = collect();
        collect : while(true){
            newCopy = collect();
            for(Integer j = 0; j < this.aTable.length; j++){
                if(oldCopy[j].getStamp() != newCopy[j].getStamp()){
                    if(moved[j].equals(true)){
                        return oldCopy[j].getSnap();
                    }else{
                        moved[j] = true;
                        oldCopy = newCopy;
                        continue collect;
                    }
                }
            }
            ArrayList<T> result = new ArrayList<>();
            for(Integer j = 0; j < this.aTable.length; j++){
                result.add(newCopy[j].getValue());
            }
            return result;
        }
    }
    
    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * @return La copia de los valores del arreglo
     */
    private StampedSnap<T>[] collect(){
        return Arrays.copyOf(this.aTable, this.aTable.length);
    }

    /**
     * Regresa la tabla de valores.
     * @return la tabla de valores.
     */
    public StampedSnap<T>[] getATable(){
        return this.aTable;
    }

    /**
     * Regresa el valor que esta en la posicion indicada.
     * @param position la poscion de la que se quiere conocer el valor.
     * @return el valor que esta en la posicion indicada.
     */
    public T getStampedSnap(Integer position){
        return this.aTable[position].getValue();
    }
}
