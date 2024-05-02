package kass.concurrente.util;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    // Metodo ejemplo para generar numeros 'unicos' para los commits
    private static long numeroCommit() {
        long timestamp = Instant.now().toEpochMilli();
        int numAleatorio = (int)(Math.random() * 65536);
        long numCommit = (timestamp << 16) | (numAleatorio & 0xFFFF);
        return numCommit;
    }

    /**
     * Método para Add
     * @param sourceDirectory
     * @param stagingDirectory
     * @throws IOException
     */
    public static void gitAdd(String sourceDirectory, String stagingDirectory) throws IOException {
        Path sourcePath = Paths.get(sourceDirectory);
        Path stagingPath = Paths.get(stagingDirectory);
        Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = sourcePath.relativize(file);
                Path targetFile = stagingPath.resolve(relativePath);
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Método para el status
     * @param directoryPath
     * @throws IOException
     */
    public static String gitStatus(String directoryPath) {
        // Mensaje inicial
        StringBuilder statusBuilder = new StringBuilder("Files to commit:\n");

        try (Stream<Path> filesStream = Files.walk(Paths.get(directoryPath))) {
            // Recorre los archivos en el directorio
            String fileNames = filesStream
                    // Filtra solo los archivos regulares
                    .filter(Files::isRegularFile)
                    // Obtiene solo el nombre de archivo de cada Path
                    .map(Path::getFileName)
                    // Convierte el nombre de archivo a String
                    .map(Path::toString)
                    // Convierte a una sola cadena separada por nuevas líneas
                    .collect(Collectors.joining("\n"));

            // Agrega los nombres de archivo al StringBuilder
            statusBuilder.append(fileNames);

            // Devuelve el resultado como String
            return statusBuilder.toString();
        } catch (IOException exception) {
            exception.getMessage();
        }

        return statusBuilder.toString();
    }

    /**
     * Método para el commit
     * @param stagingDirectory
     * @param commitDirectory
     * @throws IOException
     */
    public static void gitCommit(String stagingDirectory, String commitDirectory) throws IOException {
        // Crear un archivo que represente el commit actual
        String commitFileName = "commit_" + Long.toString(numeroCommit()) + ".txt";
        Path commitFilePath = Paths.get(commitDirectory, commitFileName);

        try (BufferedWriter writer = Files.newBufferedWriter(commitFilePath);
            Stream<Path> filesStream = Files.walk(Paths.get(stagingDirectory))) {
            // Escribir la lista de archivos en el área de preparación al archivo de commit
            filesStream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .forEach(path -> {
                        try {
                            writer.write(path);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException exception) {
            exception.getMessage();
        }
    }

}
