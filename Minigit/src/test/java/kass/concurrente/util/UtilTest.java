package kass.concurrente.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class UtilTest {

    // Test para revisar si se creó correctamente una carpeta.
    @Test
    public void testCrearCarpeta() {
        Util util = new Util();
        assertTrue(util.crearCarpeta("testFolder"));
    }

    // Test para verificar que se puede guardar y leer un archivo en concreto.
    @Test
    public void testGuardarYLeerArchivo() throws IOException {
        Util util = new Util();
        String filePath = "testFile.txt";
        String content = "Test content";
        util.guardarArchivo(filePath, content);
        assertEquals(content, util.leerArchivo(filePath));
    }

}
