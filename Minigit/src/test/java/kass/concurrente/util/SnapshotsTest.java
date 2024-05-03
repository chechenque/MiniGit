package kass.concurrente.util;

import kass.concurrente.snapshot.snapshot_imp.WFSnapshotArray;
import kass.concurrente.snapshot.snapshot_imp.WFSnapshotList;
import kass.concurrente.stamped.StampedSnap;
import kass.concurrente.stamped.StampedValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class SnapshotsTest {

    @Test
    public void testUpdateAndGetATableArray() {
       
        WFSnapshotArray<String> snapshot = new WFSnapshotArray<>(3, "Inicial");
        
     
        assertEquals(3, snapshot.getATable().length);     
        snapshot.update("Nuevo valor");
        assertEquals("Nuevo valor", snapshot.getATable()[0].getValue());
    }

    @Test
    public void testScanArray() {
    
        WFSnapshotArray<String> snapshot = new WFSnapshotArray<>(2, "Inicial");
        
       
        snapshot.update("Valor 1");
        snapshot.update("Valor 2");
        
       
        String[] result = snapshot.scan();
        
    
        assertArrayEquals(new String[]{"Valor 1", "Valor 2"}, result);
    }

    @Test
    public void testUpdateAndGetATableList() {
    
        WFSnapshotList<String> snapshot = new WFSnapshotList<>(3, "Inicial");
        
        
        assertEquals(3, snapshot.getATable().size());
        
        
        snapshot.update("Nuevo valor");
        
        
        assertEquals("Nuevo valor", snapshot.getATable().get(0).getValue());
    }

    @Test
    public void testScanList() {
        
        WFSnapshotList<String> snapshot = new WFSnapshotList<>(2, "Inicial");
        
        
        snapshot.update("Valor 1");
        snapshot.update("Valor 2");
        
       
        String[] result = snapshot.scan();
        
        assertArrayEquals(new String[]{"Valor 1", "Valor 2"}, result);
    }
}