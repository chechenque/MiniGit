package kass.concurrente.stash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kass.concurrente.modelos.Archivo;
import kass.concurrente.modelos.Carpeta;

/**
 * Clase que implementa pruebas para la clase Stash.java.
 * @author Alberto Natanael Medel Piña
 * @version 1.0
 */
class StashTest {

    /** Instancia de Stash a usar */
    private static Stash stash;
    /** Instancia de random */
    private static Random rnd;
    /** Archivos del stash de prueba */
    private static List<Archivo> archivos; 
    /** Carpeta de prueba para stash */
    private static Carpeta carpetaTest;
    /** Nombre de la rama prueba */
    private final String ramaNombre = "ramaTest";
    /** Nombre de la carpeta prueba */
    private static final String carpetaNombre = "carpetaTest";
    /** Ruta de prueba */
    private static final String rutaTest = "ruta" + File.separator + 
                                    "para" + File.separator + "test";
    /** Ruta general */
    private final String rutaStash = System.getProperty("user.dir") 
                                     + File.separator + "stashes";

    
    @BeforeAll
    static void setUp() {
        stash = new Stash();
        rnd = new Random();
        archivos = generaArchivos(true);
        carpetaTest = generaCarpeta(archivos);
    }

    /**
     * Método para generar archivos prueba.
     * @param vacios si los archivos deben estar vacíos
     * @return una lista de instancias de Archivo.
     */
    private static List<Archivo> generaArchivos(boolean vacios) {
        int numArchivos = rnd.nextInt(7) + 1; // Genera un número aleatorio entre 1 y 7
        List<Archivo> result = new ArrayList<>();
    
        for(int i = 0; i < numArchivos; i++){
            Archivo archTemp = new Archivo(rutaTest, "archivo" + i);
            result.add(archTemp);
        }

        return result;
    }

    /**
     * Método para generar una carpeta de prueba.
     * @param archivos los archivos que debe contener la carpeta.
     * @return una instancia Carpeta con estructura.
     */
    private static Carpeta generaCarpeta(List<Archivo> archivos) { 
        Carpeta dirTemp = new Carpeta(rutaTest, carpetaNombre);
        dirTemp.setArchivos(archivos);

        return dirTemp;
    }

    /**
     * Método que prueba que un stash sea correctamente guardado.
     */
    @Test
    void guardarStashTest() {
        try {
            stash.guardarStash(ramaNombre, carpetaTest);
        } catch (IOException | NullPointerException npe) {
            fail("El stash debió ser guardado correctamente.");
        }
    }

    /**
     * Método que prueba que los archivos en el stash sean los 
     * mismos definidos en la lista de archivos creada en estos 
     * tests y con su mismo nombre.
     */
    @Test 
    void getNombresTest() {
        String stashes = stash.getNombres(ramaNombre);
        String[] nombres = stashes.trim().split("\n");
        if (nombres.length != archivos.size())
            fail(); // Tentativo: si no le prestamos atencion a archivos vacios.
        for (int i = 0; i < nombres.length; i++) 
            assertEquals(nombres[i], archivos.get(i).getNombre());
    }

    /**
     * Método que prueba que el último Stash no sea vacío y devuelva el 
     * creado en estos tests.
     */
    @Test
    void getUltimoStashTest() {
        assertNotEquals("", stash.getUltimoStash(ramaNombre));        
    }

    /**
     * Método que prueba que la ruta devuelta sea vacía ante un Stash 
     * inexistente.
     */
    @Test
    void getRutaStashTest() {
        String stashInex = "inexistente";
        //assertEquals(rutaStash + File.separator + ramaNombre, stash.getRutaStash(ramaNombre, stashInex));
        assertEquals("", stash.getRutaStash(ramaNombre, stashInex));
    }

    /**
     * Método que prueba a borraStash.
     */
    @Test
    void borraStashTest() {
        String stashInex = "inexistente";
        boolean result = stash.borraStash(ramaNombre, stashInex);
        assertFalse(result);
    }
}
