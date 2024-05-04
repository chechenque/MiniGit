package kass.concurrente.util;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
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
     * Método para verificar el estado de los archivos, identificando cambios entre
     * el área de preparación y el directorio de trabajo.
     * 
     * @param sourceDirectory  Directorio de origen donde se encuentran los archivos originales.
     * @param workingDirectory Directorio de trabajo donde se encuentran los archivos actuales.
     * @return Una cadena que describe los cambios entre el directorio de origen y el directorio de trabajo.
     */
    public static String gitStatus(String sourceDirectory, String workingDirectory) {
        final String log = "gitStatus";
        StringBuilder statusBuilder = new StringBuilder("Changes:\n\n");

        try (Stream<Path> sourceFilesStream = Files.walk(Paths.get(sourceDirectory));
                Stream<Path> workingFilesStream = Files.walk(Paths.get(workingDirectory))) {

            // Obtiene los checksums de los archivos en el directorio de origen y el
            // directorio de trabajo
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

    /**
     * Método que genera un mapa de checksums para los archivos dados en un flujo de archivos.
     *
     * @param filesStream Flujo de archivos.
     * @return Mapa que mapea rutas relativas de archivos a sus checksums.
     */
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
                        }));
    }

    /**
     * Método para obtener la ruta relativa de un archivo hasta que el padre sea 
     * "git/source" o "git/working".
     *
     * @param file Ruta absoluta del archivo.
     * @return Ruta relativa del archivo.
     */
    private static String relativizePath(Path file) {
        Path parent = file.getParent();
        StringBuilder relativePath = new StringBuilder();

        boolean foundGitSourceOrWorking = false;

        // Iterar hacia arriba hasta encontrar "git/source" o "git/working"
        while (parent != null) {
            if (parent.endsWith("git/source") || parent.endsWith("git/working")) {
                foundGitSourceOrWorking = true;
                break;
            }
            parent = parent.getParent();
        }

        // Si se encontró "git/source" o "git/working", agregar la parte restante de la ruta relativa
        if (foundGitSourceOrWorking) {
            Path relative = parent.relativize(file);
            relativePath.append("/").append(relative);
        } else {
            // Si no se encontró ninguno de los directorios, usar la ruta absoluta completa
            relativePath.append(file.toString());
        }

        return relativePath.toString();
    }

    /**
     * Método para calcular el checksum de un archivo usando SHA-256.
     *
     * @param file Ruta absoluta del archivo.
     * @return Checksum SHA-256 del archivo en formato Base64.
     * @throws IOException              Si hay un error de lectura del archivo.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo de hash especificado.
     */
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
     * Método para agregar archivos al área de preparación (staging area).
     * 
     * @param sourceDirectory  Directorio de origen donde se encuentran los archivos.
     * @param workingDirectory Directorio de trabajo donde se encuentran los archivos.
     * @param stagingDirectory Directorio de área de preparación donde se agregarán los archivos.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    public static void gitAdd(String sourceDirectory, String workingDirectory, String stagingDirectory)
            throws IOException {
        final String log = "gitAdd";
        Map<String, String> changes = getChanges(sourceDirectory, workingDirectory);

        for (Map.Entry<String, String> entry : changes.entrySet()) {
            String file = entry.getKey();
            String changeType = entry.getValue();
            String markedFileName = markFileName(file, changeType);
            // Si es un archivo nuevo o modificado, lo copia desde el working directory
            Path sourceFile = Paths.get(workingDirectory, file); 
            // Si es un archivo a borrar, lo copia desde el source directory
            if (changeType.equals("d")) {
                sourceFile = Paths.get(sourceDirectory, file); 
            }
            Path targetFile = Paths.get(stagingDirectory, markedFileName);
            // Asegurar que el directorio de destino exista
            Path parentDir = targetFile.getParent();
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Obtiene los cambios entre el directorio de origen y el directorio de trabajo.
     *
     * @param sourceDirectory  Directorio de origen donde se encuentran los archivos originales.
     * @param workingDirectory Directorio de trabajo donde se encuentran los archivos actuales.
     * @return Mapa que describe los cambios entre los directorios.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static Map<String, String> getChanges(String sourceDirectory, String workingDirectory) throws IOException {
        Map<String, String> changes = new HashMap<>();
        try (Stream<Path> sourceFilesStream = Files.walk(Paths.get(sourceDirectory));
                Stream<Path> workingFilesStream = Files.walk(Paths.get(workingDirectory))) {

            Map<String, String> sourceChecksums = checksumsMap(sourceFilesStream);
            Map<String, String> workingChecksums = checksumsMap(workingFilesStream);

            // Compara los checksums para identificar cambios
            for (Map.Entry<String, String> entry : sourceChecksums.entrySet()) {
                String file = entry.getKey();
                String checksum = entry.getValue();
                if (!workingChecksums.containsKey(file)) {
                    // Archivo a eliminar
                    changes.put(file, "d");
                } else if (!workingChecksums.get(file).equals(checksum)) {
                    // Archivo modificado
                    changes.put(file, "m");
                }
            }

            // Identifica archivos nuevos en el directorio de trabajo
            for (Map.Entry<String, String> entry : workingChecksums.entrySet()) {
                String file = entry.getKey();
                if (!sourceChecksums.containsKey(file)) {
                    changes.put(file, "n"); // Nuevo archivo
                }
            }
        }
        return changes;
    }

    /**
     * Marca el nombre del archivo según el tipo de cambio.
     *
     * @param fileName Nombre del archivo.
     * @param changeType Tipo de cambio ("d" para eliminar, "m" para modificar, "n" para nuevo).
     * @return Nombre del archivo marcado.
     */
    private static String markFileName(String fileName, String changeType) {
        String markedFileName;
        switch (changeType) {
            // Marca para archivo a eliminar
            case "d":
                markedFileName = fileName + "_d"; 
                break;
            // Marca para archivo modificado
            case "m":
                markedFileName = fileName + "_m";
                break;
            // Marca para nuevo archivo
            case "n":
                markedFileName = fileName + "_n";
                break;
            // No hay marca
            default:
                markedFileName = fileName;
        }
        return markedFileName;
    }

    /**
     * Método para realizar un commit de los cambios en el área de preparación y
     * actualizar el directorio de origen. Además de vaciar los archivos y carpetas del 
     * área de preparación.
     * 
     * @param stagingDirectory Directorio de área de preparación donde se encuentran los archivos para el commit.
     * @param sourceDirectory  Directorio de origen donde se actualizarán los archivos después del commit.
     * @param commitDirectory  Directorio donde se almacenarán los commits.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    public static void gitCommit(String stagingDirectory, String sourceDirectory, String commitDirectory)
            throws IOException {
        final String log = "gitCommit";
        // Crear un archivo que represente el commit actual
        String commitFileName = "commit_" + Long.toString(numeroCommit()) + ".txt";
        Path commitFilePath = Paths.get(commitDirectory, commitFileName);
        writeChangesToCommitFile(stagingDirectory, commitFilePath);
        processStagedFiles(stagingDirectory, sourceDirectory);
        clearStagingDirectory(stagingDirectory);
    }

    /**
     * Método para escribir los cambios del área de preparación en el archivo del commit.
     * 
     * @param stagingDirectory Directorio de área de preparación donde se encuentran los archivos para el commit.
     * @param commitFilePath  Ruta para el archivo del commit.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    private static void writeChangesToCommitFile(String stagingDirectory, Path commitFilePath) throws IOException {
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

    /**
     * Método para procesar los archivos que están en el área de preparación y que estos cambios se vean
     * reflejados en el directorio de origen.
     * 
     * @param stagingDirectory Directorio de área de preparación donde se encuentran los archivos para el commit.
     * @param sourceDirectory  Directorio de origen donde se actualizarán los archivos después del commit.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    private static void processStagedFiles(String stagingDirectory, String sourceDirectory) throws IOException {
        // Procesar los archivos en el área de preparación
        Files.walkFileTree(Paths.get(stagingDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relativePath = Paths.get(stagingDirectory).relativize(file);
                Path targetFile = Paths.get(sourceDirectory).resolve(relativePath);

                // Elimina la marca "_d" del nombre del archivo y lo borra de la carpeta source
                if (file.getFileName().toString().endsWith("_d")) {
                    String newFileName = file.getFileName().toString().replace("_d", "");
                    Path newFilePath = targetFile.resolveSibling(newFileName);
                    Files.deleteIfExists(newFilePath);

                    // Verifica si las carpetas padre están vacías y las borra si es así (excepto el directorio raíz)
                    Path parentDir = newFilePath.getParent();
                    deleteEmptyParentDirectories(parentDir, sourceDirectory);
                }
                // Elimina la marca "_n" o "_m" del nombre del archivo y lo copia desde la carpeta staging a source
                else {
                    String newFileName = "";
                    if (file.getFileName().toString().endsWith("_n")) {
                        newFileName = file.getFileName().toString().replace("_n", "");
                    } else if (file.getFileName().toString().endsWith("_m")) {
                        newFileName = file.getFileName().toString().replace("_m", "");
                    }

                    Path newFilePath = targetFile.resolveSibling(newFileName);
                    // Asegurar que el directorio de destino exista
                    Path parentDir = newFilePath.getParent();
                    if (!Files.exists(parentDir)) {
                        Files.createDirectories(parentDir);
                    }
                    Files.copy(file, newFilePath, StandardCopyOption.REPLACE_EXISTING);    
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Elimina directorios padres vacíos hasta llegar al directorio raíz especificado.
     *
     * @param directory Directorio a verificar y, si está vacío, eliminar.
     * @param rootDirectory Directorio raíz.
     * @throws IOException Si ocurre un error al acceder o eliminar directorios.
     */
    private static void deleteEmptyParentDirectories(Path directory, String rootDirectory) throws IOException {
        while (!directory.equals(Paths.get(rootDirectory))) {
            try (Stream<Path> stream = Files.list(directory)) {
                if (stream.count() == 0) {
                    Files.deleteIfExists(directory);
                } else {
                    break;
                }
            }
            directory = directory.getParent();
        }
    }

    /**
     * Método para vaciar el directorio de área de preparación.
     * 
     * @param stagingDirectory Directorio de área de preparación donde se encuentran los archivos para el commit.
     * @throws IOException Si ocurre un error al acceder o manipular los archivos.
     */
    private static void clearStagingDirectory(String stagingDirectory) throws IOException { 
        // Vaciar el directorio de área de preparación después del commit
        Files.walkFileTree(Paths.get(stagingDirectory), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (!dir.equals(Paths.get(stagingDirectory))) {
                    Files.delete(dir); // Elimina el directorio una vez que todos los archivos y subdirectorios han sido procesados
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
