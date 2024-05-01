package kass.concurrente.busqueda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService; // Nos permite administrar un conjunto de hilos.
import java.util.concurrent.Executors; // Es un objeto que ejecuta tareas Runnable enviadas y proporciona una forma de desacoplar la presentación de tareas de los detalles de cómo se ejecutará cada tarea.
import java.util.concurrent.Future; // Representa el resultado de una computación asincrónica y proporciona métodos para verificar si la computación está completa, esperar su finalización y recuperar el resultado de la computación.
import java.util.concurrent.TimeUnit; // Proporciona métodos para convertir entre unidades,también nos permite realizar operaciones de temporización y de retraso en estas unidades.
import java.util.concurrent.ExecutionException;

/**
 * 
 * Clase que implementa la búsqueda de un archivo en un árbol de carpetas.
 * Se exploran todas las carpetas y subcarpetas en busca de un archivo en específico.
 * @author Carlos Daniel Cortés Jiménez
 */
public class BuscadorDeArchivos {

	private BuscadorDeArchivos() {
        // Constructor privado vacío
    }

	/**
	 * Busacamos el archivo en las carpetas
	 * @param nombreArchivo Nombre del archivo a buscar
	 * @param rutaCarpeta ruta de la carpeta donde esta el archivo 
	 * @return Lista de ruta de los archvios buscados
	 * @throws InterruptedException 
	 */ 
	public static List<String> buscaArchivo(String nombreArchivo, String rutaCarpeta) throws InterruptedException{
		List<String> busquedaArchivos = new ArrayList<>();
		File rutaArchivo = new File(rutaCarpeta);

		if(!rutaArchivo.isDirectory()){ /*Nos aseguramos que sea un ruta */
			return busquedaArchivos;
		}
	
		File[] archivos = rutaArchivo.listFiles(); /*Obtenemos la lista de archivos */
		if (archivos == null){
			return busquedaArchivos;
		}

    	ExecutorService executor = Executors.newFixedThreadPool(5); /*Creamos un conjunto de hilos fijo*/

		List<Future<List<String>>> futures = new ArrayList<>(); /*Almacenamos las rutas obtenidos de la busqueda de archivos en una lista de resultados futuros*/
	
    	for (File archivo : archivos) { /*Iteraramos sobre los archivos y subcarpetas que se encuentran dentro de la carpeta*/
        	if (archivo.isDirectory()) {
            
            	Future<List<String>> future = executor.submit(() -> buscaArchivo(nombreArchivo, archivo.getAbsolutePath())); // Si es una subcarpeta, enviamos una tarea de búsqueda 
            	futures.add(future); // Agregamos la tarea a la lista de resultados futuros
        	} else if (archivo.getName().equals(nombreArchivo)) {
            	busquedaArchivos.add(archivo.getAbsolutePath()); // Si se encuentra el archivo buscado, agregamos su ruta a la lista de busqueda
        	}
    	}

    	for (Future<List<String>> future : futures) { // Iteramos para obtener las rutas de la busqueda de las tareas
        	try {
            	busquedaArchivos.addAll(future.get()); // Obtenemos los resultados y se agregan a la lista de busqueda
        	} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
    	}

    	executor.shutdown(); // Apagamos el ExecutorService
    	try {
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // Esperamos a que todas las tareas se completen
    	} catch (InterruptedException e) {
        	Thread.currentThread().interrupt();
            throw e;
    	}

    	return busquedaArchivos; // Regresamos la lista de busqueda de archvios
	}
}
