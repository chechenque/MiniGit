package kass.concurrente.modelos;

import java.util.Date;

/**
 * Clase que modela informacion cuando se realiza un commit
 * @version 1.1
 * @author Kassandra Mirael
 */
public class InfoCommit {
    public static final String LOG = "InfoCommit";

    private String nombre;
    private String descripcion;
    private Date fecha;
    private String version;//Sujeto a cambios

    /**
     * Metodo constructor
     */
    public InfoCommit(){}

    /**
     * Metodo constructor
     * @param nombre El nombre del commit
     * @param descripcion La descripcion del commit
     * @param fecha La fecha del commit
     * @param version La version del commit
     */
    public InfoCommit(String nombre, String descripcion, Date fecha, String version) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.version = version;
    }

    /**
     * Metodo que retorna el nombre
     * @return El nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que retorna la descripcion
     * @return La descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que retorna la fecha
     * @return La fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Metodo que retorna la version
     * @return La version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Metodo que asigna un nuevo nombre
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que asigna una nueva descripcion
     * @param descripcion La nueva descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo que asigna una nueva fecha
     * @param fecha La nueva fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Metodo que asgina una nueva version
     * @param version La nueva version
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
}
