/**
 * @author Equipo: Los Threads.
 * Integrantes: Emilio Franscisco Sánchez Martínez
 * Clase main en donde se ejecutara nuestra interfaz de usuario.
 */
package kass.concurrente;

import kass.concurrente.interfazdeusuario.ControladorIdiomas;
import kass.concurrente.comandos.Comandos;
import java.util.Scanner;

public class Main {

    /*
     * Método donde se lleva acabo la ejecución de la interfaz
     */
    public static void main(String[] args) {
        //Variables
        Comandos comandos = new Comandos();
        Scanner scanner = new Scanner(System.in);
        ControladorIdiomas idiomas = new ControladorIdiomas();    
        idiomas.cargarIdioma("es");
        comandos.setIdioma("es");

        //Mensaje de bienvenida
        System.out.println(idiomas.obtenerMensaje("ascii"));
        System.out.println(idiomas.obtenerMensaje("bienvenida"));
        System.out.println(idiomas.obtenerMensaje("instrucciones"));

        //Ejecución de Minigit
        while (true) {
            System.out.print("minigit> ");
            String input = scanner.nextLine().trim();
            
            //Cierra el programa.
            if ("exit".equals(input)) {
                break;
            }

            //Cambia el idoma al español.
            if("lang es".equals(input)){
                idiomas.cargarIdioma("es");
                comandos.setIdioma("es");
            }

            //Cambia el idioma a ingles.
            if("lang en".equals(input)){
                idiomas.cargarIdioma("en");
                comandos.setIdioma("en");               
            }
            //Ejecuta los comandos de la clase comandos.
            comandos.ejecutarComando(input);
        }
    
        scanner.close();
    }
}