package kass.concurrente.snapshot.snapshot_imp;

import java.util.ArrayList;
import java.util.List;

import kass.concurrente.snapshot.Snapshot;
import kass.concurrente.stamped.StampedSnap;

/**
 * Clase generica que modela una Snapshot que implementa la interfaz Snapshot
 * @author 3 cores
 */
public class WFSnapshotList<T> implements Snapshot<T> {
    public static final String LOG = "WFSnapshotLis";
    // Tabla de valores
    private List<StampedSnap<T>> aTable;
    // Valor inicial
    private T init;

    /**
     * Constructor que recibe la capacidad maxima de valores a guardar
     * y el valor inicial.
     * @param capacidad la capacidad maxima de valores a guardar.
     * @param inicial el valor inicial a guardar.
     */
    public WFSnapshotList(Integer capacity, T init){
        aTable = new ArrayList<>();
        this.init = init;

        for(Integer i = 0; i < capacity; i++){
            this.aTable.add(new StampedSnap<>(init));
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
        while (this.aTable.size() <= id) {
            this.aTable.add(new StampedSnap<>(this.init));
        }
        ArrayList<T> snap = scan();
        StampedSnap<T> oldValue = this.aTable.get(id);
        StampedSnap<T> newValue = new StampedSnap<>(oldValue.getStamp()+1, value, snap);
        this.aTable.set(id, newValue);
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
        Boolean[] moved = new Boolean[this.aTable.size()];
        oldCopy = collect();
        collect : while(true){
            newCopy = collect();
            for(Integer j = 0; j < this.aTable.size(); j++){
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
            for(Integer j = 0; j < this.aTable.size(); j++){
                result.add( newCopy[j].getValue());
            }
            return result;
        }
    }
    
    /**
     * Metodo que obtiene una copia de los valores del arreglo
     * @return La copia de los valores del arreglo
     */
    @SuppressWarnings("unchecked")
    private StampedSnap<T>[] collect(){
        StampedSnap<T>[] copia = (StampedSnap<T>[]) new Object[this.aTable.size()];
        for(Integer i = 0; i < this.aTable.size(); i++){
            copia[i] = this.aTable.get(i);
        }
        return copia;
    }

    /**
     * Regresa la tabla de valores.
     * @return la tabla de valores.
     */
    public List<StampedSnap<T>> getATable(){
        return this.aTable;
    }

    /**
     * Regresa el valor que esta en la posicion indicada.
     * @param position la poscion de la que se quiere conocer el valor.
     * @return el valor que esta en la posicion indicada.
     */
    public T getStampedSnap(Integer position){
        return this.aTable.get(position).getValue();
    }
}
