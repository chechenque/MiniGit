package kass.concurrente.util;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Clase encargada de la persistencia de archivos
 * @author Equipo DIA
 * @version 1.0
 */

public class Util {
    public static final String LOG = "Util";
    // Ejemplo de distribucion de carpetas de git (la carpeta ramas es solo un ejemplo de uso)
    // |-- src
    // |-- ramas
    // |   |-- ramaA
    // |   |   |-- Version 1
    // |   |   |-- Version 2
    // |   |-- ramaB
    
    // Por lo que las rutas que se pasen como parametro deben de ser como ../ramas/ramaA/version1/archivo.txt
    /**
     * Crea una carpeta en la ruta de la rama actual, pensado para crear la carpeta que represente una nueva version de la rama.
     * @param rutaCarpeta Ruta de la carpeta a crear.
     * @return true si se logro crear, false en otro caso (la carpeta ya existe)
     */
    public boolean crearCarpeta(String rutaCarpeta) {
        final String log = "crearCarpeta";
        File carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
            return true;
        }
        return false;
    }

    /**
     * Guarda contenido en un archivo.
     * @param rutaArchivo Ruta del archivo a crear o sobrescribir.
     * @param contenido Contenido que se va a escribir en el archivo.
     */
    public void guardarArchivo(String rutaArchivo, String contenido) {
        final String log = "guardarArchivo";
        try {
            File archivo = new File(rutaArchivo);
            FileWriter escritor = new FileWriter(archivo);
            escritor.write(contenido);
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee el contenido de un archivo.
     * @param rutaArchivo Ruta completa del archivo a leer.
     * @return El contenido del archivo como una cadena de caracteres.
     */
    public String leerArchivo(String rutaArchivo) throws IOException {
        final String log = "leerArchivo";
        StringBuilder content = new StringBuilder();
        File file = new File(rutaArchivo);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("El archivo no existe o no es valido D:");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * Metodo que dada una ruta completa borra la carpeta
     * @param ruta es la ruta de la carpeta a borrar
     * @return true si se logro borrar, false en otro caso
     */
    public static boolean borrarCarpeta(String ruta) {
        final String log = "borrarCarpeta";
        File carpeta = new File(ruta);
        if (carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles();
            if (archivos != null && archivos.length == 0) {
                return carpeta.delete();
            }
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    borrarCarpeta(archivo.getAbsolutePath());
                } else {
                    archivo.delete();
                }
            }
            return carpeta.delete();
        }
        return false;
    }
}
