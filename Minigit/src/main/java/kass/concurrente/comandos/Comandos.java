package kass.concurrente.comandos;

import kass.concurrente.interfazdeusuario.ControladorIdiomas;
import java.util.Scanner;

public class Comandos {

    ControladorIdiomas idiomas = new ControladorIdiomas(); 

    /**
     * Metodo agregado por el equipo los Threads, resto del código hecho por el equipo de Gorilas Furiosos
     * Metodo para establecer el idioma a cada una de las ejecuciones
     * @param idioma - idioma a establecer
     */
    public void setIdioma(String idioma){
        idiomas.cargarIdioma(idioma);
    }

    /**
     * Método modificado por el equipo de los Threads para que los mensajes puedan tener diferentes traducciones a varios idiomas.
     * Método que ejecuta un comando
     * @param comando - comando que se va a ejecutar.
     */
    public void ejecutarComando(String comando) {
        switch(comando) {
            case "git push":
                System.out.println(idiomas.obtenerMensaje("ejecutando")+"'git push'...");
                break;
            case "git add":
                System.out.println(idiomas.obtenerMensaje("ejecutando")+"'git add'...");
                break;
            case "git branch":
                System.out.println(idiomas.obtenerMensaje("ejecutando")+"'git branch'...");
                break;
            case "git help":
                showHelp();
                break;
            case "git status":
                System.out.println(idiomas.obtenerMensaje("ejecutando")+"'git status'...");
                break;

            case "lang es":
                System.out.println(idiomas.obtenerMensaje("lang_es"));
                break;

            case "lang en":
                System.out.println(idiomas.obtenerMensaje("lang_en"));
                break;

            default:
                System.out.println(idiomas.obtenerMensaje("comandoNoRec"));
                break;
        }
    }

    public void showHelp() {
     
        System.out.println(idiomas.obtenerMensaje("comandosDisponibles"));
        System.out.println(idiomas.obtenerMensaje("git_push"));
        System.out.println(idiomas.obtenerMensaje("git_add"));
        System.out.println(idiomas.obtenerMensaje("git_branch"));
        System.out.println(idiomas.obtenerMensaje("git_status"));
        System.out.println(idiomas.obtenerMensaje("git_help"));
        System.out.println(idiomas.obtenerMensaje("lang_help"));  
    }

    public static void helpComando(String comando) {
        String quitarHelp = comando.substring(5);
        System.out.println(Mensajes.getMensaje(quitarHelp)); 
    }

    public static void showMenu() {
        System.out.println("Comandos disponibles:");
        System.out.println("1.- git push" );
        System.out.println("2.- git add");
        System.out.println("3.- git branch");
        System.out.println("4.- git status");
        System.out.println("5.- help");
        System.out.println("6.- exit");
    }

    public static void main(String[] args) {
        Comandos gitTerminal = new Comandos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese sus comandos git (type 'exit' para salir):");
        
        while (true) {
            showMenu();
            String input = scanner.nextLine().trim();
            if ("exit".equals(input)) {
                break;
            }
            if (input.startsWith("help") && input.split("\\s+").length > 1) {
                helpComando(input);
                //System.out.println(Mensajes.getMensaje(input.split("\\s+")[1]));
            } else {
                gitTerminal.ejecutarComando(input);
            }
        }

        scanner.close();
    }
}
