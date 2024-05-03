package kass.concurrente.comandos;
import java.util.HashMap;

public class Mensajes {
    private static HashMap<String, String> ayudaComandos = new HashMap<>();

    static {
        ayudaComandos.put("git push", "git push: Envía los cambios realizados en el repositorio local a un repositorio remoto. " +
                                  "Esto permite que otros colaboradores vean los cambios y mantengan " +
                                  "sincronizado el estado del repositorio compartido.");
        ayudaComandos.put("git add", "git add: Agrega cambios al área de preparación para ser incluidos en el próximo commit. " +
                                 "Antes de realizar un commit, los cambios deben ser añadidos al área de preparación " +
                                 "utilizando este comando.");
        ayudaComandos.put("git branch", "git branch: Lista las ramas locales en el repositorio actual, con la rama actual resaltada. " +
                                    "También se puede usar para crear o eliminar ramas.");
        ayudaComandos.put("git status", "git status: ver qué cambios se han realizado en tu área de trabajo y cuáles están preparados para ser incluidos en el próximo commit.");
        ayudaComandos.put("git help", "help: Muestra información de ayuda sobre los comandos disponibles.");

    }

    public static String getMensaje(String comando) {
        return ayudaComandos.getOrDefault(comando, "No se encontró ayuda para el comando " + comando + "'.");
    }
}
