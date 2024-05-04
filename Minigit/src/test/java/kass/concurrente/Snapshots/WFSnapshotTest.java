package kass.concurrente.Snapshots;

import org.junit.jupiter.api.Test;

import kass.concurrente.snapshot.snapshot_imp.WFSnapshotArray;
import kass.concurrente.snapshot.snapshot_imp.WFSnapshotList;
import kass.concurrente.stamped.StampedSnap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class WFSnapshotTest {

    @Test
    public void testWFSnapshotArray() {
        WFSnapshotArray<Integer> snapshot = new WFSnapshotArray<>(5, 0);

        // Test update
        snapshot.update(1);
        snapshot.update(2);
        snapshot.update(3);

        StampedSnap<Integer>[] aTable = snapshot.getATable();
        assertEquals(1, aTable[0].getValue());
        assertEquals(2, aTable[1].getValue());
        assertEquals(3, aTable[2].getValue());

        // Test scan
        ArrayList<Integer> result = snapshot.scan();
        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    public void testWFSnapshotList() {
        WFSnapshotList<String> snapshot = new WFSnapshotList<>(3, "Initial");

        // Test update
        snapshot.update("Value1");
        snapshot.update("Value2");
        snapshot.update("Value3");

        List<StampedSnap<String>> aTable = snapshot.getATable();
        assertEquals("Value1", aTable.get(0).getValue());
        assertEquals("Value2", aTable.get(1).getValue());
        assertEquals("Value3", aTable.get(2).getValue());

        // Test scan
        ArrayList<String> result = snapshot.scan();
        assertEquals(3, result.size());
        assertEquals("Value1", result.get(0));
        assertEquals("Value2", result.get(1));
        assertEquals("Value3", result.get(2));
    }
}
