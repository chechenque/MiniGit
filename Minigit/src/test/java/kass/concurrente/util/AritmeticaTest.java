package kass.concurrente.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AritmeticaTest {
    Aritmetica a;

    @BeforeEach
    void setUp(){
        a = new Aritmetica();
    }

    @Test
    void sumaTest1(){
        assertEquals(a.suma(10, 5),15.0);
    }

    @Test
    void multiplicaTest1(){
        assertEquals(a.multiplica(2.0, 2.0), 4.0);
    }
}
