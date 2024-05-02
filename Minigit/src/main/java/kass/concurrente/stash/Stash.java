package kass.concurrente.stash;

import kass.concurrente.util.Util;
import kass.concurrente.modelos.Carpeta;
import kass.concurrente.modelos.Archivo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.io.File;
import java.util.logging.Logger;
import java.io.IOException;

/**
 * Clase que guarda un stash
 * @author Equipo DIA
 * @version 1.0
 */

// Ejemplo de distribucion de carpetas de stash (la carpeta stashes es solo un ejemplo de uso)
// |-- stash
// |-- ramas
// |-- stashes
// |   |-- ramaA
// |   |   |-- stash 1
// |   |   |-- stash 2
// |   |-- ramaB

public class Stash {
    public static final String LOG = "Stash";

    private Util util;
    private String rutaStash;

    public Stash() {
        util = new Util();
        rutaStash = System.getProperty("user.dir") + File.separator + "stashes";
    }

    /**
     * Metodo que guarda un Stash
     * @param rama el nombre de la rama sobre la que se quiere guardar un stash
     * @param carpeta que se ve a almacenar en los stash de la rama
     */
    public void guardarStash(String rama, Carpeta carpeta) throws IOException {
        final String log = "guardarStash";
        String nuevoStash = rutaStash + File.separator + rama + File.separator + carpeta.getNombre();
        boolean r = util.crearCarpeta(nuevoStash);
        guardaArchivosStash(nuevoStash, carpeta.getArchivos());
        guardaCarpetasStash(nuevoStash, carpeta.getCarpetas());
    }

    /**
     * Metodo que dada una lista de carpetas los guarda la carpeta y el contenido para un stash
     * @param ruta donde van a estar las carpetas
     * @param carpetas todas las carpetas a guardar
     */
    private void guardaCarpetasStash(String ruta, List<Carpeta> carpetas) throws IOException {
        final String log = "guardaCarpetasStash";
        if (carpetas != null) {
            for (Carpeta carpeta : carpetas) {
                boolean r = util.crearCarpeta(ruta + File.separator + carpeta.getNombre());
                guardaArchivosStash(ruta + File.separator + carpeta.getNombre(), carpeta.getArchivos());
                guardaCarpetasStash(ruta + File.separator + carpeta.getNombre(), carpeta.getCarpetas());
            }
        }
    }

    /**
     * Metodo que guarda archivos para un stash
     * @param ruta donde se guardaran
     * @param archivos lista de los archivos a guardar
     */
    private void guardaArchivosStash(String ruta, List<Archivo> archivos) throws IOException {
        final String log = "guardaArchivosStash";
        if (archivos != null) {
            for (Archivo archivo : archivos) {
                StringBuilder contenido = new StringBuilder();
                File file = archivo.getContenido();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contenido.append(line).append("\n");
                    }
                }
                util.guardarArchivo(ruta + File.separator + archivo.getNombre(), contenido.toString());
            }
        }
    }

    /**
     * Metodo que regresa los nombres de los stashes almacenados de una rama
     * @param rama la rama sobre la que se va a regresar los stashes almacenados
     * @return todos los nombres de los stashes almacenados o una cadena vacia si la rama no tiene stashes
     */
    public String getNombres(String rama) {
        final Logger logAux = Logger.getLogger("kass.concurrente.stash.Stash");
        final String log = "getNombres";
        File carpetaRama = new File(rutaStash + File.separator + rama);
        String stashes = "";
        if (carpetaRama.exists() && carpetaRama.isDirectory()) {
            File[] archivos = carpetaRama.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    stashes += "\n" + archivo.getName();
                }
            } else {
                logAux.info("La rama '" + rama + "' no tiene stashes :0");
            }
        } else {
            logAux.info("La rama '" + rama + "' no tiene stashes :0");
        }
        return stashes;
    }

    /**
     * Metodo que regresa la ruta de un stash en una rama en particular
     * @param rama donde se va a buscar el stash
     * @param stash nombre del stash que se quiere
     * @return la ruta completa del stash de la rama o la cadena vacia si no existe el stash dado
     */
    public String getRutaStash(String rama, String stash) {
        final String log = "getRutaStash";
        String ruta = rutaStash + File.separator + rama + File.separator + stash;
        File carpetaStash = new File(rutaStash + File.separator + rama);
        if (carpetaStash.exists() && carpetaStash.isDirectory()) {
            return ruta;
        } else {
            return "";
        }
    }

    /**
     * Metodo que regresa la ruta del stash mas reciente
     * @param rama la rama de la que se quiere obtener el stash mas reciente
     * @return ruta completa del stash mas reciente
     */
    public String getUltimoStash(String rama) {
        final String log = "getUltimoStash";
        File directorio = new File(rutaStash + File.separator + rama);
        File versionMasNueva = null;
        long fechaMasNueva = Long.MIN_VALUE;

        File[] versiones = directorio.listFiles();
        String ruta = "";
        for (File version : versiones) {
            if (version.isDirectory()) {
                long fechaModificacion = version.lastModified();
                if (fechaModificacion > fechaMasNueva) {
                    fechaMasNueva = fechaModificacion;
                    versionMasNueva = version;
                    ruta = versionMasNueva.getAbsolutePath();
                }
            }
        }
        return ruta;
    }

    /**
     * Metodo que borra el stash dado
     * @param stash es el nombre del stash a borrar
     * @return true si fue borrado, false en otro caso
     */
    public boolean borraStash(String rama, String stash) {
        final String log = "borraStash";
        return util.borrarCarpeta(getRutaStash(rama, stash));
    }
}