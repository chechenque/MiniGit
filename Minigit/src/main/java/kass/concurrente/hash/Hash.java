package kass.concurrente.hash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase Hash que proporciona métodos para generar un hash SHA-1 y guardar ese hash en un archivo.
 */
public class Hash {

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
            System.err.println("SHA-1 algorithm not available.");
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

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(hash);
            writer.close();
            System.out.println("Hash guardado en " + filename);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir el hash en el archivo: " + e.getMessage());
        }

        //TODO: Ver el orden de los commits 
        
    }

    /**
     * Ejemplo de uso de la clase Hash.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        String hash = generaHash("bug1", "Arregla bug 1");
        System.out.println(hash);
        guardaHash(hash, "./commits");
    }

}
