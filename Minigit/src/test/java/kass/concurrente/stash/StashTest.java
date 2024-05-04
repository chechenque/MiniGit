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

    private static Stash stash;
    private static Random rnd;
    private static List<Archivo> archivos; 
    private static Carpeta carpetaTest;
    private final String ramaNombre = "ramaTest";
    private static final String carpetaNombre = "carpetaTest";
    private static final String rutaTest = "ruta" + File.separator + 
                                    "para" + File.separator + "test";
    private final String rutaStash = System.getProperty("user.dir") 
                                     + File.separator + "stashes";

    
    @BeforeAll
    static void setUp() {
        stash = new Stash();
        rnd = new Random();
        archivos = generaArchivos(true);
        carpetaTest = generaCarpeta(archivos);
    }

    private static List<Archivo> generaArchivos(boolean vacios) {
        int numArchivos = rnd.nextInt(7) + 1; // Genera un número aleatorio entre 1 y 7
        List<Archivo> result = new ArrayList<>();
    
        for(int i = 0; i < numArchivos; i++){
            Archivo archTemp = new Archivo(rutaTest, "archivo" + i);
            result.add(archTemp);
        }

        return result;
    }

    private static Carpeta generaCarpeta(List<Archivo> archivos) { 
        Carpeta dirTemp = new Carpeta(rutaTest, carpetaNombre);
        dirTemp.setArchivos(archivos);

        return dirTemp;
    }

    @Test
    void guardarStashTest() {
        try {
            stash.guardarStash(ramaNombre, carpetaTest);
        } catch (IOException | NullPointerException npe) {
            fail("El stash debió ser guardado correctamente.");
        }
    }

    @Test 
    void getNombresTest() {
        String stashes = stash.getNombres(ramaNombre);
        String[] nombres = stashes.trim().split("\n");
        if (nombres.length != archivos.size())
            fail(); // Tentativo: si no le prestamos atencion a archivos vacios.
        for (int i = 0; i < nombres.length; i++) 
            assertEquals(nombres[i], archivos.get(i).getNombre());
    }

    @Test
    void getUltimoStashTest() {
        assertNotEquals("", stash.getUltimoStash(ramaNombre));        
    }

    @Test
    void getRutaStashTest() {
        String stashInex = "inexistente";
        //assertEquals(rutaStash + File.separator + ramaNombre, stash.getRutaStash(ramaNombre, stashInex));
        assertEquals("", stash.getRutaStash(ramaNombre, stashInex));
    }

    @Test
    void borraStashTest() {
        String stashInex = "inexistente";
        boolean result = stash.borraStash(ramaNombre, stashInex);
        assertFalse(result);
    }
}
