package kass.concurrente.comandos;
import java.util.Scanner;

public class Comandos {
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
            default:
                System.out.println("Comando no reconocido. Use 'git help' para obtener ayuda.");
                break;
        }
    }

    public void showHelp() {
     
        System.out.println("Comandos disponibles:");
        System.out.println(Mensajes.getMensaje("git push"));
        System.out.println(Mensajes.getMensaje("git add"));
        System.out.println(Mensajes.getMensaje("git branch"));
        System.out.println(Mensajes.getMensaje("git status"));
        System.out.println(Mensajes.getMensaje("git help")); 
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
