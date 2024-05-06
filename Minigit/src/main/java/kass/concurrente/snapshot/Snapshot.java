package kass.concurrente.snapshot;

import java.util.ArrayList;

/**
 * Interfaz que modela un snapshot
 * @author Kassandra Mirael
 */
public interface Snapshot<T> {
    
    /**
     * Metodo que escribe el valor v en registro del proceso
     * que realiza la llamada
     * @param value El valor a escribir en dicho registro
     */
    public void update(T value);

    /**
     * Metodo que construye una vista instantanea
     * del arreglo de registros
     * @return El arreglo de registros
     */
    public ArrayList<T> scan();
}
