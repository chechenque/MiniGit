
package kass.concurrente.Stamped;

import org.junit.jupiter.api.Test;

import kass.concurrente.stamped.StampedSnap;
import kass.concurrente.stamped.StampedValue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList; // Para guardar lista de cambios por StampedSnap


/**
 * Clase de prueba para la clase StampedSnap y StampedValue.
 * @author PaoPatrol / Vargas Bravo Paola
 * @version 1.0
 */
class StampedSnapTest {

    /**
     * Prueba el constructor de StampedSnap.
     */
    @Test
    void testStampedSnapConstructor() {
        StampedSnap<Integer> stampedSnap = new StampedSnap<>(5);
        
        assertEquals(0, stampedSnap.getStamp());
        assertEquals(Integer.valueOf(5), stampedSnap.getValue());
        assertEquals(1, stampedSnap.getSnap().size());
        assertEquals(Integer.valueOf(5), stampedSnap.getCommit(0));
    }

    /**
     * Prueba los métodos setters de StampedSnap.
     */
    @Test
    void testStampedSnapSetters() {
        StampedSnap<String> stampedSnap = new StampedSnap<>("Initial");
        ArrayList<String> newSnap = new ArrayList<>();
        newSnap.add("Change1");
        newSnap.add("Change2");
        
        stampedSnap.setStamp(10);
        stampedSnap.setValue("Updated");
        stampedSnap.setSnap(newSnap);
        
        assertEquals(10, stampedSnap.getStamp());
        assertEquals("Updated", stampedSnap.getValue());
        assertEquals(2, stampedSnap.getSnap().size());
        assertEquals("Change1", stampedSnap.getCommit(0));
        assertEquals("Change2", stampedSnap.getCommit(1));
    }

    /**
     * Prueba el método addCommit de StampedSnap.
     */
    @Test
    void testStampedSnapAddCommit() {
        StampedSnap<Double> stampedSnap = new StampedSnap<>(3.14);
        
        stampedSnap.addCommit(2.71);
        stampedSnap.addCommit(1.618);
        
        assertEquals(3, stampedSnap.getSnap().size());
        assertEquals(Double.valueOf(2.71), stampedSnap.getCommit(1));
        assertEquals(Double.valueOf(1.618), stampedSnap.getCommit(2));
    }


    /**
     * Prueba el constructor de StampedValue.
     */
    @Test
    void testStampedValueConstructor() {
        StampedValue<Integer> stampedValue = new StampedValue<>(10);
        
        assertEquals(0, stampedValue.getStamp());
        assertEquals(Integer.valueOf(10), stampedValue.value());
    }

    /**
     * Prueba los métodos setters de StampedValue.
     */
    @Test
    void testStampedValueSetters() {
        StampedValue<String> stampedValue = new StampedValue<>("Initial");
        
        stampedValue.setStamp(5);
        stampedValue.setValue("Updated");
        
        assertEquals(5, stampedValue.getStamp());
        assertEquals("Updated", stampedValue.value());
    }

    /**
     * Prueba el método estático max de StampedValue.
     */
    @Test
    void testStampedValueMax() {
        StampedValue<Integer> stampedValue1 = new StampedValue<>(5);
        StampedValue<Integer> stampedValue2 = new StampedValue<>(10,10);
        
        StampedValue<Integer> maxStampedValue = StampedValue.max(stampedValue1, stampedValue2);
        
        assertEquals(10, maxStampedValue.getStamp());
        assertEquals(Integer.valueOf(10), maxStampedValue.value());
    }
}
