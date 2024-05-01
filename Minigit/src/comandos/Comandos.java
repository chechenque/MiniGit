package comandos;
import java.util.HashMap;
import java.util.Scanner;

public class Comandos {
    private HashMap<String, String> comandoHelp;

    public Comandos() {
        comandoHelp = new HashMap<>();
        comandoHelp.put("push", "Envía los cambios realizados en el repositorio local a un repositorio remoto. " +
                                "Esto permite que otros colaboradores vean los cambios y mantengan " +
                                "sincronizado el estado del repositorio compartido.");
        comandoHelp.put("add", "Agrega cambios al área de preparación para ser incluidos en el próximo commit. " +
                               "Antes de realizar un commit, los cambios deben ser añadidos al área de preparación " +
                               "utilizando este comando.");
        comandoHelp.put("help", "Muestra información de ayuda sobre los comandos disponibles.");
    }

    public void ejecutarComando(String comando) {
        switch(comando) {
            case "push":
                System.out.println("Ejecutando 'git push'...");
                break;
            case "add":
                System.out.println("Ejecutando 'git add'...");
                break;
            case "help":
                showHelp();
                break;
            default:
                System.out.println("Comando no reconocido. Use 'git help' para obtener ayuda.");
                break;
        }
    }

    public void showHelp() {
        System.out.println("Comandos disponibles:");
        comandoHelp.forEach((comando, description) -> System.out.println("'" + comando + "': " + description));
    }

    public static void main(String[] args) {
        Comandos gitTerminal = new Comandos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese sus comandos git (type 'exit' para salir):");
        
        while (true) {
            System.out.print("git ");
            String input = scanner.nextLine().trim();
            if ("exit".equals(input)) {
                break;
            }
            if (input.startsWith("help") && input.split("\\s+").length > 1) {
                gitTerminal.getComandoHelp(input.split("\\s+")[1]);
            } else {
                gitTerminal.ejecutarComando(input);
            }
        }

        scanner.close();
    }

    public void getComandoHelp(String comando) {
        if (comandoHelp.containsKey(comando)) {
            System.out.println("Ayuda para 'git " + comando + "': " + comandoHelp.get(comando));
        } else {
            System.out.println("No se encontró ayuda para el comando 'git " + comando + "'.");
        }
    }
}
