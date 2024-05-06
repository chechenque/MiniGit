package kass.concurrente.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que modela una carpeta
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Carpeta {
    public static final String LOG = "Carpeta";

    private String ruta;
    private String nombre;
    private List<Carpeta> carpetas;
    private List<Archivo> archivos;

    /**
     * Metodo constructor
     */
    public Carpeta(){}

    /**
     * Metodo constructor
     * @param ruta La ruta de la carpeta
     * @param nombre El nombre de la carpeta
     */
    public Carpeta(String ruta, String nombre){
        this.ruta = ruta;
        this.nombre = nombre;
        this.carpetas = new ArrayList<>();
        this.archivos = new ArrayList<>();
    }

    /**
     * Metodo constructor
     * @param ruta La ruta de la carpeta
     * @param nombre El nombre de la carpeta
     * @param carpetas La lista de carpetas
     * @param archivos La lista de archivos
     */
    public Carpeta(String ruta, String nombre, List<Carpeta> carpetas, List<Archivo> archivos) {
        this.ruta = ruta;
        this.nombre = nombre;
        this.carpetas = carpetas;
        this.archivos = archivos;
    }
    
    /**
     * Metodo que regresa la ruta de la carpeta
     * @return La ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Metodo que regresa el nombre de la carpeta
     * @return El nombre de la carpeta
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Metodo que retorna las carpetas
     * @return Las carpetas
     */
    public List<Carpeta> getCarpetas() {
        return carpetas;
    }

    /**
     * Metodo que retorn los archivos
     * @return Los archivos
     */
    public List<Archivo> getArchivos() {
        return archivos;
    }

    /**
     * Metodo que asigna una nueva ruta
     * @param ruta La nueva ruta
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    /**
     * Metodo que asigna el nuevo nombre
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Metodo que asigna nuevas carpetas
     * @param carpetas Las nuevas carpetas
     */
    public void setCarpetas(List<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }
    
    /**
     * Metodo que asigna nuevos archivos
     * @param archivos Los nuevos archivos
     */
    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }
    
}
