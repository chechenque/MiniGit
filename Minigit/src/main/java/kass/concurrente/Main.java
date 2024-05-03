package kass.concurrente;

import kass.concurrente.interfazdeusuario.ControladorIdiomas;
import kass.concurrente.comandos.Comandos;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Comandos comandos = new Comandos();
        Scanner scanner = new Scanner(System.in);
        ControladorIdiomas idiomas = new ControladorIdiomas();    
        idiomas.cargarIdioma("es");
        comandos.setIdioma("es");

        System.out.println(idiomas.obtenerMensaje("ascii"));
        System.out.println(idiomas.obtenerMensaje("bienvenida"));
        System.out.println(idiomas.obtenerMensaje("instrucciones"));

        while (true) {
            System.out.print("minigit> ");
            String input = scanner.nextLine().trim();
    
            if ("exit".equals(input)) {
                break;
            }

            if("lang es".equals(input)){
                idiomas.cargarIdioma("es");
                comandos.setIdioma("es");
            }

            if("lang en".equals(input)){
                idiomas.cargarIdioma("en");
                comandos.setIdioma("en");               
            }
    
            comandos.ejecutarComando(input);
        }
    
        scanner.close();
    }
}