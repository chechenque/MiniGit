package kass.concurrente.modelos;

import java.io.File;

/**
 * Clase que modela un archivo
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Archivo {
    public static final String LOG = "Archivo";

    private String ruta;
    private String nombre;
    private File contenido;

    /**
     * Metodo constructor
     */
    public Archivo(){}

    /**
     * Metodo constructor
     * @param ruta La ruta del archivo
     * @param nombre El nombre del archivo
     */
    public Archivo(String ruta, String nombre){
        this.ruta = ruta;
        this.nombre = nombre;
        //this.contenido = null; Se corrige con Util.java
    }

    /**
     * Metodo constructor
     * @param ruta La ruta del archivo
     * @param nombre El nombre del archivo
     * @param contenido El contenido del archivo
     */
    public Archivo(String ruta, String nombre, File contenido) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    /**
     * Metodo que retorna la ruta
     * @return La ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Metodo que retorna el nombre
     * @return El nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que regresa el contenido
     * @return El contenido
     */
    public File getContenido(){
        return contenido;
    }

    /**
     * Metodo que asigna una nueva ruta
     * @param ruta La nueva ruta
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Metodo que asign un nuevo nombre
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que asgina un nuevo contenido
     * @param contenido El nuevo contenido
     */
    public void setContenido(File contenido) {
        this.contenido = contenido;
    }
    
}
