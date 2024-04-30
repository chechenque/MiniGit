package kass.concurrente.util;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant; //Biblioteca para ejecutar numeroCommit()

/**
 * Clase encargada de la persistencia de archivos
 * @author Equipo DIA
 */

public class Util {
    public static final String LOG = "Util";
    // Ejemplo de distribucion de carpetas de git
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
        File carpeta = new File(rutaCarpeta);
        // String nuevaVersion = "" + numeroCommit();
        // File carpeta = new File(rutaCarpeta + File.separator + nuevaVersion); // en este caso rutaCarpeta llegaria hasta ramas/ 
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

    // Metodo ejemplo para generar numeros 'unicos' para los commits
    private long numeroCommit() {
        long timestamp = Instant.now().toEpochMilli();
        int numAleatorio = (int)(Math.random() * 65536);
        long numCommit = (timestamp << 16) | (numAleatorio & 0xFFFF);
        return numCommit;
    }
}
