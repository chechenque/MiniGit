package kass.concurrente.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kass.concurrente.busqueda.BuscadorDeArchivos;

class BusquedaTest {

    @Test
    void busquedaArchivoTest() {
        try {
            // "buscaArchivo" debera regresar True, demostrando que el archivo si se
            // encuentra en el directorio:
            assertTrue(BuscadorDeArchivos.buscaArchivo("WFSnapshotList.java"));

            assertFalse(BuscadorDeArchivos.buscaArchivo("ElXokas.txt"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
