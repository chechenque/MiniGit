package kass.concurrente.util;

import java.io.File;
import java.io.IOException;

/**
 * Clase para probar o ver el funcionamietno de Util.java
 * @author Equipo DIA
 */

public class PruebaUtil {
    public static void main(String[] args) throws IOException {
        Util util = new Util();
        String rutaRamas = System.getProperty("user.dir") + File.separator + "ramas"; // Forma de obtener la carpeta .../ramas
        String rama = rutaRamas + File.separator + "ramaB"; // Forma de obtener la rama
        String version = getUltimaVersion(rama); // Forma de obtener la ultima version basado en la fecha de modificacion
        String archivo = "ejemplo.txt";
        String nuevoArchivo = "ejemplo2.txt";
        String contenidoNuevo = "Ejemplo de un contenido nuevo\nVersion nueva";

        System.out.println(util.leerArchivo(version + File.separator + archivo));


        // Esta prueba necesita comentar la linea 33 y descomentar las lineas 34 y 35 de Util.java
        // util.crearCarpeta(rama);
        // version = getUltimaVersion(rama);
        // util.guardarArchivo(version + File.separator + nuevoArchivo, contenidoNuevo);
        
        // System.out.println(util.leerArchivo(version + File.separator + nuevoArchivo));
    }

    /**
     * Metodo auxiliar que regresa la ruta de la version mas reciente de la rama
     * @param rama es la ruta completa de la rama en la que se trabaja
     * @return la ruta de la version mas reciente de la rama
     */
    public static String getUltimaVersion(String rama) {
        File directorio = new File(rama);
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
        if (ruta.equals("")) {
            System.out.println("ERROR: no hay versiones - PruebaUtil.getUltimaVersion");
        }
        return ruta;
    }
}