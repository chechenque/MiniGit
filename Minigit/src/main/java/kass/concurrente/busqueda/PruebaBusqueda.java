package kass.concurrente.busqueda;
/**
 * Clase para probar el buscador de archivos
 */
public class PruebaBusqueda {
    public static void main(String[] args) {
        try {
            
            BuscadorDeArchivos.buscaTodosArchivos();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}