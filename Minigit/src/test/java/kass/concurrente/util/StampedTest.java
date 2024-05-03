package kass.concurrente.util;

import kass.concurrente.stamped.StampedSnap;
import kass.concurrente.stamped.StampedValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class StampedTest {

    @Test
    public void testStampedSnap() {
        
        StampedSnap<String> stampedSnap = new StampedSnap<>("Inicial");
        
        
        assertEquals("Inicial", stampedSnap.getValue());
        assertEquals(0, stampedSnap.getStamp());
        assertNull(stampedSnap.getSnap());
        
        stampedSnap.setValue("Modificado");
        stampedSnap.setStamp(1);
        String[] snap = {"Cambio1", "Cambio2"};
        stampedSnap.setSnap(snap);
        
        
        assertEquals("Modificado", stampedSnap.getValue());
        assertEquals(1, stampedSnap.getStamp());
        assertArrayEquals(snap, stampedSnap.getSnap());
    }

    @Test
    public void testStampedValue() {
        
        StampedValue<Integer> stampedValue = new StampedValue<>(10);
        
        
        assertEquals(10, (int) stampedValue.value());
        assertEquals(0, stampedValue.getStamp());
        
        
        stampedValue.setValue(20);
        stampedValue.setStamp(1);
        
    
        assertEquals(20, (int) stampedValue.value());
        assertEquals(1, stampedValue.getStamp());
    }
}
