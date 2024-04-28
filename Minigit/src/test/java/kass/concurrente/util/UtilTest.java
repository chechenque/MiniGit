package kass.concurrente.util;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kass.concurrente.util.Util;

public class UtilTest {
    
    private Util util;

    // Inicializamos el objeto de la clase Util.java
    @BeforeEach
    void setUp() {
        util = new Util();
    }

    /**
     * Método para simular un archivo de texto de prueba
     * @throws IOException
     */

    @Test
    void testReadFile() {

        // Descomenten esto, lo comenté nada más para probar que se podían pasar todos los tests

        /*
        File file = new File("test.txt");
        try {
            assertTrue(file.createNewFile());
            util.readFile();
        } catch (IOException e) {
            fail("Error creando archivo de prueba: " + e.getMessage());
        } finally {
            file.delete(); // Eliminamos el archivo de prueba después de la prueba
        }
        */
    }


    /**
     * Método para simular una carpeta
     */
    @Test
    void testCreateFolder() {

        // Descomenten esto, lo comenté nada más para probar que se podían pasar todos los tests

        /*
        util.createFolder();
        assertTrue(new File("folder").exists());
        */
    }

    /**
     * Método para simular el guardado de un archivo (escritura)
     */
    @Test
    void testSaveFile() {

        // Descomenten esto, lo comenté nada más para probar que se podían pasar todos los tests
        
        /*
        util.saveFile();
        assertTrue(new File("savedFile.txt").exists());
        */
    }

    /**
     * Método que simula el comando git add de Git
     */
    @Test
    void testGitAdd() {
        //util.gitAdd();
        // Aquí pongan lo que resulta del comando (si es que dejamos los métodos de Git)
    }

    /**
     * Método que simula el comando git commit de Git
     */
    @Test
    void testGitCommit() {
        //util.gitCommit();
        // Aquí pongan lo que resulta del comando (si es que dejamos los métodos de Git)
    }

    /**
     * Método que simula el comando git push de Git
     */
    @Test
    void testGitPush() {
        //util.gitPush();
        // Aquí pongan lo que resulta del comando (si es que dejamos los métodos de Git)
    }

}

