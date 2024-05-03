package kass.concurrente.busqueda;
/**
 * Clase para probar el buscador de archivos
 */
public class PruebaBusqueda {
    public static void main(String[] args) {
        try {
            String nombreArchivo = "WFSnapshotList.java";
            BuscadorDeArchivos.buscaArchivo(nombreArchivo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}