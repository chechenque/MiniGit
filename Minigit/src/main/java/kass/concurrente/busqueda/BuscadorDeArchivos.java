package kass.concurrente.busqueda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

/**
 * Clase que implementa la búsqueda de un archivo en un árbol de directorios.
 * Se exploran todas las carpetas y subcarpetas en busca de un archivo en específico y se genera el árbol de directorios.
 * @author Carlos Daniel Cortés Jiménez
 */
public class BuscadorDeArchivos {

    private BuscadorDeArchivos() {
        // Constructor privado vacío
    }
	
    /**
     * Buscamos un archivo en el árbol directorios a partir de una ruta base.
     * Imprime en pantalla la estructura del árbol de directorios.
     * @param nombreArchivo Nombre del archivo a buscar.
     * @throws InterruptedException Si la ejecución es interrumpida mientras se espera la finalización de las tareas.
     */
    public static void buscaArchivo(String nombreArchivo) throws InterruptedException {
        String rutaBase = "."; // Ruta base (donde nos encontramos actualemente)
        List<String> arbol = new ArrayList<>(); // Árbol de directorios 
        List<String> rutasArchivo = new ArrayList<>(); // lista para guardar las rutas de los archivos encontrados
        busquedaArchivo(rutaBase, "", arbol, rutasArchivo, nombreArchivo, true); // Busacamos el archivos y generamos el arbol

        for (String linea : arbol) { 
            System.out.println(linea); // Se imprime el arbol 
        }

        if (!rutasArchivo.isEmpty()) {
            System.out.println("\nSe encontró el archivo " + nombreArchivo);
        } else {
            System.out.println("\nNo se encontró el archivo " + nombreArchivo);
        }
    }

    /**
     * Buscamos todos los archivos en el árbol de directorios a partir de una ruta base.
     * Imprime en pantalla la estructura del árbol de directorios.
     * @throws InterruptedException Si la ejecución es interrumpida mientras se espera la finalización de las tareas.
     */
    public static void buscaTodosArchivos() throws InterruptedException {
        String rutaBase = "."; // Ruta base (donde nos encontramos actualemente)
        List<String> arbol = new ArrayList<>(); // Árbol de directorios 
        List<String> rutasArchivo = new ArrayList<>(); // lista para guardar las rutas de los archivos encontrados
        busquedaArchivo(rutaBase, "", arbol, rutasArchivo, "", true); // Buscamos todos los archivos y generamos el árbol

        for (String linea : arbol) { 
            System.out.println(linea); // Se imprime el árbol 
        }
    }

    /**
     * Metodo auxiliar que busca un archivo en el árbol de directorios desde una ruta base.
     * @param rutaCarpeta ruta actual.
     * @param prefijo representación jerarquica "├──", "└──" y " " del árbol de directorios.
     * @param arbol árbol de directorios y archivos.
     * @param rutasArchivo rutas de los archivos.
     * @param nombreArchivo nombre del archivo.
     * @param esRaiz nos dice si la carpeta actual es la raiz del arbol.
     * @throws InterruptedException Si la ejecución es interrumpida mientras se espera la finalización de las tareas.
     */
    private static void busquedaArchivo(String rutaCarpeta, String prefijo, List<String> arbol, List<String> rutasArchivo, String nombreArchivo, boolean esRaiz) throws InterruptedException {
        File rutaArchivo = new File(rutaCarpeta);

		/*Nos aseguramos que sea un ruta, agremaos el archivo y su ruta al árbol 
		 * y comprobamos si es el archivo buscado
		 */
        if (!rutaArchivo.isDirectory()) { 
            agregarArchivo(rutaArchivo, prefijo, arbol);
            if (rutaArchivo.getName().equals(nombreArchivo)) {
                rutasArchivo.add(rutaArchivo.getAbsolutePath());
            }
            return;
        }

		/* Obtenemos la lista de archivos, si no encontramos 
		 * carpetas ni archivos, salimos del metodo
		 */
        File[] archivos = rutaArchivo.listFiles(); 
        if (archivos == null) {
            return;
        }
		/*
		 * Comproamos si la ruta actual es la raiz del arbol, si es así agregamos la
		 * carpeta donde estamos al arbol, en caso contrario aunmentamos el prefijo.
		 */
        if (!esRaiz) {
            arbol.add(prefijo + "└── " + rutaArchivo.getName());
            prefijo += "    ";
        }

        ExecutorService executor = Executors.newFixedThreadPool(5); /*Creamos un conjunto de hilos fijo*/
        List<Future<List<String>>> futures = new ArrayList<>(); /*Almacenamos las rutas obtenidos de la busqueda de archivos en una lista de resultados futuros*/

        procesarArchivosYSubcarpetas(archivos, prefijo, arbol, rutasArchivo, futures, executor, nombreArchivo);

        almacenarTareas(futures, arbol); 

        executor.shutdown(); // Apagamos el ExecutorService
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // Esperamos a que todas las tareas se completen
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    /**
     * Agregamos un archivo al árbol de directorios.
     * @param archivo un archivo.
     * @param prefijo representación jerarquica "├──", "└──" y " " del árbol de directorios.
     * @param arbol árbol de directorios.
     */
    private static void agregarArchivo(File archivo, String prefijo, List<String> arbol) {
        arbol.add(prefijo + "├── " + archivo.getName()); // Agregamos el archivo al árbol con su nivel jerarquico
    }

    /**
     * Procesa los archivos y subcarpetas en el arbol, y asigna tareas futuras para las subcarpetas.
     * @param archivos Arreglo de archivos y subcarpetas.
     * @param prefijo representa la estructura del árbol.
     * @param arbol árbol de carpetas y archivos.
     * @param rutasArchivo almacena las rutas de los archivos que se encuentran.
     * @param futures almacena las tareas futuras.
     * @param executor conjunto de hilos para ejecutar las tareas futuras.
     * @param nombreArchivo nombre del archivo a buscar.
     */
    private static void procesarArchivosYSubcarpetas(File[] archivos, String prefijo, List<String> arbol, List<String> rutasArchivo, List<Future<List<String>>> futures, ExecutorService executor, String nombreArchivo) {
        for (int i = 0; i < archivos.length; i++) {
            String nuevoPrefijo = prefijo + (i == archivos.length - 1 ? "    " : "│   "); // Signamos una nuevo prefijo según el nivel jerarquico del archivo o subcarpeta
            File archivo = archivos[i];

            if (archivo.isDirectory()) { // Si es una subcarpeta, creamos una tarea futura
                Future<List<String>> future = executor.submit(() -> {
                    List<String> subArbol = new ArrayList<>();
                    List<String> subRutasArchivo = new ArrayList<>();
                    busquedaArchivo(archivo.getAbsolutePath(), nuevoPrefijo, subArbol, subRutasArchivo, nombreArchivo, false);
                    rutasArchivo.addAll(subRutasArchivo);
                    return subArbol;
                });
                futures.add(future); // Agregamos la tarea futura a la lista de tareas futuras

            } else {  // En caso contrario, agreamos el archivo al árbol y comprobamos si es el archivo buscado
                String linea = nuevoPrefijo + "├── " + archivo.getName();
                arbol.add(linea);
                if (archivo.getName().equals(nombreArchivo)) {
                    rutasArchivo.add(archivo.getAbsolutePath()); // agregamos la ruta del archivo encontrado a la lista de rutas
                }
            }
        }
    }

    /**
     * Almacenamos los resultados de las búsquedas futuras al árbol de directorios.
     * @param futures tareas futuras.
     * @param arbol árbol de directorios.
     */
    private static void almacenarTareas(List<Future<List<String>>> futures, List<String> arbol) {

		for (Future<List<String>> future : futures) { // Iteramos sobre las tareas futuras
            try {
                arbol.addAll(future.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
