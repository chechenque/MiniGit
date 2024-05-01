package kass.concurrente.stash;

import kass.concurrente.modelos.Carpeta;
import kass.concurrente.modelos.Archivo;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase para probar o ver el funcionamietno de Stash.java
 * @author Equipo DIA
 */

public class PruebaStash {
    public static void main(String[] args) throws IOException {
        Stash s = new Stash();

        Carpeta c = new Carpeta();
        c.setNombre("2");
        Archivo a = new Archivo();
        a.setNombre("ejemplo.txt");
        a.setContenido(new File(a.getNombre()));
        try {
            FileWriter escritor = new FileWriter(a.getContenido(), true);
            escritor.write("Contenido stash 2 de la rama A\n");
            escritor.close();
        } catch (IOException e) {
            System.out.println("Ocurrio un error al intentar escribir en el archivo: " + e.getMessage());
        }
        List<Archivo> archivos = new ArrayList<>();
        archivos.add(a);
        c.setArchivos(archivos);
        s.guardarStash("ramaA", c);
        System.out.println("++++++++++++ s.getNombres\n");
        System.out.println(s.getNombres("ramaA"));

        System.out.println("++++++++++++ s.getUltimoStash\n");
        System.out.println(s.getUltimoStash("ramaA"));

        System.out.println("++++++++++++ s.getRutaStash\n");
        System.out.println(s.getRutaStash("ramaA", "2"));

        // s.borraStash("ramaA", "2");
    }
}