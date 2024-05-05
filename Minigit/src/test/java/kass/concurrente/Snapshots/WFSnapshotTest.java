
package kass.concurrente.Snapshots;

import org.junit.jupiter.api.Test;

import kass.concurrente.snapshot.snapshot_imp.WFSnapshotArray;
import kass.concurrente.snapshot.snapshot_imp.WFSnapshotList;
import kass.concurrente.stamped.StampedSnap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList; // Para instantáneas de objetos o valores esperados después de realizar operaciones
import java.util.List; // Lo mismo que con ArrayList


/**
 * Clase de prueba para las implementaciones de WFSnapshotArray y WFSnapshotList.
 * @author PaoPatrol / Vargas Bravo Paola
 * @version 1.0
 */
class WFSnapshotTest {

    /**
     * Prueba la implementación de WFSnapshotArray.
     */
    @Test
    void testWFSnapshotArray() {
        // Se crea un nuevo WFSnapshotArray con un tamaño de 5 y un valor inicial de 0
        WFSnapshotArray<Integer> snapshot = new WFSnapshotArray<>(5, 0);

        // Prueba de actualización
        snapshot.update(1);
        snapshot.update(2);
        snapshot.update(3);

        // Se obtiene la tabla de instantáneas (aTable) y se verifica que los valores sean correctos
        StampedSnap<Integer>[] aTable = snapshot.getATable();
        assertEquals(1, aTable[0].getValue());
        assertEquals(2, aTable[1].getValue());
        assertEquals(3, aTable[2].getValue());

        // Prueba de exploración (scan)
        ArrayList<Integer> result = snapshot.scan();
        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }

    /**
     * Prueba la implementación de WFSnapshotList.
     */
    @Test
    void testWFSnapshotList() {
        // Se crea un nuevo WFSnapshotList con un tamaño de 3 y un valor inicial de "Initial"
        WFSnapshotList<String> snapshot = new WFSnapshotList<>(3, "Initial");

        // Prueba de actualización
        snapshot.update("Value1");
        snapshot.update("Value2");
        snapshot.update("Value3");

        // Se obtiene la tabla de instantáneas (aTable) y se verifica que los valores sean correctos
        List<StampedSnap<String>> aTable = snapshot.getATable();
        assertEquals("Value1", aTable.get(0).getValue());
        assertEquals("Value2", aTable.get(1).getValue());
        assertEquals("Value3", aTable.get(2).getValue());

        // Prueba de exploración (scan)
        ArrayList<String> result = snapshot.scan();
        assertEquals(3, result.size());
        assertEquals("Value1", result.get(0));
        assertEquals("Value2", result.get(1));
        assertEquals("Value3", result.get(2));
    }
}
