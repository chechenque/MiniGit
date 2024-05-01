package kass.concurrente.busqueda;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/** 
 *Clase para probar el buscador de archivos  
 */
public class PruebaBusqueda {

    private static final Logger logger = Logger.getLogger(PruebaBusqueda.class.getName());

    public static void main(String[] args) throws InterruptedException {
        String nombreArchivo = "WFSnapshotList.java"; 
        String rutaCarpeta = "kass";

        List<String> resultados = BuscadorDeArchivos.buscaArchivo(nombreArchivo, rutaCarpeta);

        logger.log(Level.INFO, "Resultados de la búsqueda:");
        for (String resultado : resultados) {
            logger.log(Level.INFO, resultado);
        }
    }
}