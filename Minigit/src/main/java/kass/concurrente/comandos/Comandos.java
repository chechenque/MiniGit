package kass.concurrente.comandos;
import java.util.Scanner;

/**
 * Clase Comandos que simula un terminal de comandos git y ofrece ayuda relacionada.
 * @author Gorilas Furiosos
 */
public class Comandos {
    
    /**
     * Ejecuta un comando específico mostrando un mensaje en consola.
     * @param comando El comando git o 'help' que el usuario quiere ejecutar.
     */
    public void ejecutarComando(String comando) {
        switch(comando) {
            case "git push":
                System.out.println("Ejecutando 'git push'...");
                break;
            case "git add":
                System.out.println("Ejecutando 'git add'...");
                break;
            case "git branch":
                System.out.println("Ejecutando 'git branch'...");
                break;
            case "help":
                showHelp();
                break;
            case "git status":
                System.out.println("Ejecutando 'git status'...");
                break;
            case "git init":
                System.out.println("Ejecutando 'git init'...");
                break;
            case "git pull":
                System.out.println("Ejecutando 'git pull'...");
                break;
            case "git commit":
                System.out.println("Ejecutando 'git commit'...");
                break;
            case "git diff":
                System.out.println("Ejecutando 'git diff'...");
                break;
            case "git checkout":
                System.out.println("Ejecutando 'git checkout'...");
                break;
            case "git merge":
                System.out.println("Ejecutando 'git merge'...");
                break;
            default:
                System.out.println("Comando no reconocido. Use 'help' para obtener ayuda.");
                break;
        }
    }

    /**
     * Muestra ayuda para todos los comandos disponibles.
     */
    public void showHelp() {
        System.out.println("Comandos disponibles:");
        System.out.println(Mensajes.getMensaje("git push"));
        System.out.println(Mensajes.getMensaje("git add"));
        System.out.println(Mensajes.getMensaje("git branch"));
        System.out.println(Mensajes.getMensaje("git status"));
        System.out.println(Mensajes.getMensaje("git init"));
        System.out.println(Mensajes.getMensaje("git pull"));
        System.out.println(Mensajes.getMensaje("git commit"));
        System.out.println(Mensajes.getMensaje("git diff"));
        System.out.println(Mensajes.getMensaje("git checkout"));
        System.out.println(Mensajes.getMensaje("git merge")); 
        System.out.println(Mensajes.getMensaje("help")); 
    }

    /**
     * Proporciona ayuda para un comando específico a partir de una entrada que comienza con 'help'.
     * @param comando El comando completo incluyendo la palabra 'help'.
     */
    public static void helpComando(String comando) {
        String quitarHelp = comando.substring(5);
        System.out.println(Mensajes.getMensaje(quitarHelp)); 
    }

    /**
     * Muestra un menú con las opciones de comandos disponibles para el usuario.
     */
    public static void showMenu() {
        System.out.println("Comandos disponibles:");
        System.out.println("1.- git push" );
        System.out.println("2.- git add");
        System.out.println("3.- git branch");
        System.out.println("4.- git status");
        System.out.println("5.- git init" );
        System.out.println("6.- git pull");
        System.out.println("7.- git commit");
        System.out.println("8.- git diff");
        System.out.println("9.- git checkout");
        System.out.println("10.- git merge");
        System.out.println("11.- help");
        System.out.println("12.- exit");
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
            } else {
                gitTerminal.ejecutarComando(input);
            }
        }

        scanner.close();
    }
}
