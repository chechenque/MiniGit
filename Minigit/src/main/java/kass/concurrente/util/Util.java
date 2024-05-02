package kass.concurrente.util;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

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
     * Método para agregar archivos al área de preparación (staging area).
     * @param workingDirectory Directorio de trabajo donde se encuentran los archivos.
     * @param stagingDirectory Directorio de área de preparación donde se agregarán los archivos.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    public static void gitOrionAdd(String workingDirectory, String stagingDirectory) throws IOException {
        Path workingPath = Paths.get(workingDirectory);
        Path stagingPath = Paths.get(stagingDirectory);
        // Recorre recursivamente los archivos en el directorio de trabajo
        Files.walkFileTree(workingPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // Obtiene la ruta relativa del archivo
                Path relativePath = workingPath.relativize(file);
                // Calcula la ruta de destino en el área de preparación
                Path targetFile = stagingPath.resolve(relativePath);
                // Copia el archivo al área de preparación, reemplazando si ya existe
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Método para verificar el estado de los archivos, identificando cambios entre el área de preparación y el directorio de trabajo.
     * @param sourceDirectory Directorio de origen donde se encuentran los archivos originales.
     * @param workingDirectory Directorio de trabajo donde se encuentran los archivos actuales.
     * @return Una cadena que describe los cambios entre el área de preparación y el directorio de trabajo.
     */
    public static String gitStatus(String sourceDirectory, String workingDirectory) {
        StringBuilder statusBuilder = new StringBuilder("Changes:\n\n");

        try (Stream<Path> sourceFilesStream = Files.walk(Paths.get(sourceDirectory));
             Stream<Path> workingFilesStream = Files.walk(Paths.get(workingDirectory))) {

            // Obtiene los checksums de los archivos en el directorio de origen y el directorio de trabajo
            Map<String, String> sourceChecksums = checksumsMap(sourceFilesStream);
            Map<String, String> workingChecksums = checksumsMap(workingFilesStream);

            // Compara los checksums para identificar cambios
            for (Map.Entry<String, String> entry : sourceChecksums.entrySet()) {
                String file = entry.getKey();
                String checksum = entry.getValue();
                // Verifica si el archivo está presente en el directorio de trabajo
                if (!workingChecksums.containsKey(file)) {
                    statusBuilder.append("Deleted: ").append(file).append("\n");
                // Verifica si el archivo ha sido modificado
                } else if (!workingChecksums.get(file).equals(checksum)) {
                    statusBuilder.append("Modified: ").append(file).append("\n");
                }
            }

            // Identifica archivos nuevos en el directorio de trabajo
            for (Map.Entry<String, String> entry : workingChecksums.entrySet()) {
                String file = entry.getKey();
                // Verifica si el archivo no está presente en el directorio de origen
                if (!sourceChecksums.containsKey(file)) {
                    statusBuilder.append("Added: ").append(file).append("\n");
                }
            }

            return statusBuilder.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return statusBuilder.toString();
    }

    // Método para calcular los checksums de los archivos en un directorio
    private static Map<String, String> checksumsMap(Stream<Path> filesStream) {
        return filesStream.filter(Files::isRegularFile)
                .collect(Collectors.toMap(
                        Util::relativizePath, // Función para obtener la ruta relativa del archivo
                        path -> {
                            try {
                                return calculateChecksum(path); // Calcula el checksum del archivo
                            } catch (IOException | NoSuchAlgorithmException e) {
                                e.printStackTrace();
                                return "";
                            }
                        }
                ));
    }
    
    // Método para obtener la ruta relativa de un archivo
    private static String relativizePath(Path file) {
        return file.getParent().relativize(file).toString();
    }

    // Método para calcular el checksum de un archivo usando SHA-256
    private static String calculateChecksum(Path file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream inputStream = Files.newInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] checksumBytes = digest.digest();
        return Base64.getEncoder().encodeToString(checksumBytes);
    }

    /**
     * Método para realizar un commit de los cambios en el área de preparación y actualizar el directorio de origen.
     * @param stagingDirectory Directorio de área de preparación donde se encuentran los archivos para el commit.
     * @param sourceDirectory Directorio de origen donde se actualizarán los archivos después del commit.
     * @param commitDirectory Directorio donde se almacenarán los commits.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    public static void gitCommit(String stagingDirectory, String sourceDirectory, String commitDirectory) throws IOException {
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

        // Copiar archivos del área de preparación al directorio fuente
        Files.walkFileTree(Paths.get(stagingDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = Paths.get(stagingDirectory).relativize(file);
                Path targetFile = Paths.get(sourceDirectory).resolve(relativePath);
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });

        // Vaciar el directorio de área de preparación después del commit
        Files.walkFileTree(Paths.get(stagingDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
