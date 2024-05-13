package kass.concurrente.hash;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

/**
 * Clase que implementa pruebas para la clase Hash.java.
 * @author Alberto Natanael Medel Piña
 * @version 1.0
 */
class HashTest {

    /**
     * Método para generar dispersion SHA-1 usando 
     * bibliotecas.
     * @param cadenaPlana la cadena de la que queremos
     * la dispersion.
     * @return una cadena con la dispersion requerida.
     */
    private String generaSHA1(String cadenaPlana) {
        StringBuilder sha1 = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(cadenaPlana.getBytes());

            for (byte b : hash)
                sha1.append(String.format("%02x", b));

        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }

        return sha1.toString();
    }

    /**
     * Método que prueba, para dos pares de cadenas 
     * idénticos, que la dispersion sea distinta.
     */
    @Test
    void testGeneraHashDistintos() {
        String s1 = "Turing";
        String s2 = "Completo";

        String s3 = "turing";
        String s4 = "completo";

        String h1 = Hash.generaHash(s1, s2);
        String h2 = Hash.generaHash(s3, s4);

        assertNotEquals(h1, h2, "Dispersiones idénticas!");
    }

    /**
     * Método para probar si el directorio es creado 
     * exitosamente.
     */
    @Test
    void testCreaDirectorio() {
        String path = "directorio/inexistente";
        String hash = generaSHA1(path);
        Hash.guardaHash(hash, path);

        File f1 = new File(path);
        File f2 = new File(path + "/" + hash + ".txt");
        assertTrue(f1.exists());
        assertTrue(f2.exists());
    }
}