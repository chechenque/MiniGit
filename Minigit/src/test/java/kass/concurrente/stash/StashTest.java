package kass.concurrente.stash;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private Stash stash;
    private Random rnd;
    private final String rutaTest = "ruta" + File.separator + 
                                    "para" + File.separator + "test";
    private final String rutaStash = System.getProperty("user.dir") 
                                     + File.separator + "stashes";
    
    @BeforeEach
    void setUp() {
        this.stash = new Stash();
        rnd = new Random();
    }

    @Test
    void guardarStashTest() {
        Integer numArchivos = rnd.nextInt(1, 7);
        List<Archivo> archivos = new ArrayList<>();

        for(int i = 0; i < numArchivos; i++){
            Archivo archTemp = new Archivo(rutaTest, "archivo" + i);
            archivos.add(archTemp);
        }
        Carpeta dirTemp = new Carpeta(rutaTest, "carpetaTest");
        dirTemp.setArchivos(archivos);

        try {
            stash.guardarStash("ramaTest", dirTemp);
        } catch (IOException | NullPointerException npe) {
            fail("El stash debio ser guardado correctamente*");
        }
    }
}
