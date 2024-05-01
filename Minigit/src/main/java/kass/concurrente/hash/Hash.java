package kass.concurrente.hash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

/**
 * Clase Hash que proporciona métodos para generar un hash SHA-1 y guardar ese hash en un archivo.
 * @author Isaías Castrejón
 * @author Julieta Vargas
 * @author Gerardo Castellanos
 * @version 1.0
 */
public class Hash {
    public static final String LOG = "Hash";

    private static final Logger logger = Logger.getLogger(Hash.class.getName());

    static {
        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, "Error setting up console handler.", e);
        }
    }

    /**
     * Genera un hash SHA-1 a partir de los parámetros proporcionados.
     * @param rama La rama de código sobre la que se está trabajando.
     * @param descripcion Descripción del commit.
     * @return El hash SHA-1 como una cadena de texto hexadecimal.
     */
    public static String generaHash(String rama, String descripcion) {
        try {
            String input = rama + descripcion;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xff & messageDigest[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "SHA-1 algorithm not available.", e);
            return null;
        }
    }

    /**
     * Guarda el hash generado en un archivo dentro del directorio especificado.
     * @param hash El hash SHA-1 que se debe guardar.
     * @param directorio El directorio donde se guardará el archivo del hash.
     */
    public static void guardaHash(String hash, String directorio) {
        File dir = new File(directorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = directorio + "/" + hash + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(hash);
            logger.log(Level.INFO, "Hash guardado en {0}", filename);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ocurrió un error al escribir el hash en el archivo: {0}", filename);
        }
    }

    /**
     * Ejemplo de uso de la clase Hash.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        String hash = generaHash("bug1", "Arregla bug 1");
        guardaHash(hash, "./commits");
    }
}
