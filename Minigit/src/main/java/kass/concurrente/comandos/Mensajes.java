package kass.concurrente.comandos;
import java.util.HashMap;

/**
 * La clase Mensajes mantiene una colección de descripciones de ayuda para varios comandos.
 * Esta clase utiliza un patrón de diseño Singleton para asegurarse de que solo existe una instancia de los mensajes de ayuda.
 * @author Gorilas Furiosos
 */
public class Mensajes {
    // HashMap para almacenar los mensajes de ayuda. Es privado y estático, ya que pertenece a la clase y no a una instancia específica.
    private static HashMap<String, String> ayudaComandos = new HashMap<>();

    // Bloque estático para inicializar el HashMap con mensajes de ayuda para diferentes comandos.
    static {
        ayudaComandos.put("git push", "git push: Envía los cambios realizados en el repositorio local a un repositorio remoto. " +
                                      "Esto permite que otros colaboradores vean los cambios y mantengan " +
                                      "sincronizado el estado del repositorio compartido.");
        ayudaComandos.put("git add", "git add: Agrega cambios al área de preparación para ser incluidos en el próximo commit. " +
                                     "Antes de realizar un commit, los cambios deben ser añadidos al área de preparación " +
                                     "utilizando este comando.");
        ayudaComandos.put("git branch", "git branch: Lista las ramas locales en el repositorio actual, con la rama actual resaltada. " +
                                        "También se puede usar para crear.");
        ayudaComandos.put("git status", "git status: ver qué cambios se han realizado en tu área de trabajo y cuáles están preparados para ser incluidos en el próximo commit.");
        ayudaComandos.put("git init", "Crea un nuevo repositorio en un directorio específico.");
        ayudaComandos.put("git commit", "Confirma los cambios en el área de preparación y los guardar en el repositorio local.");
        ayudaComandos.put("git pull", "Recupera los cambios de un repositorio remoto y los fusiona automáticamente con la rama actual del repositorio local.");
        ayudaComandos.put("git diff", "Muestra la diferencia entre el contenido actual y el último commit. ");
        ayudaComandos.put("git checkout", "Carga el commit o rama sobre la que se quiere trabajar.");
        ayudaComandos.put("git merge", "Fusiona una o más ramas dentro de la rama que se tiene activa");
        ayudaComandos.put("help", "help: Muestra información de ayuda sobre los comandos disponibles.");
    }

    /**
     * Devuelve un mensaje de ayuda asociado a un comando específico.
     * Si el comando no está registrado, devuelve un mensaje indicando que no se encontró ayuda.
     *
     * @param comando El comando para el cual se solicita el mensaje de ayuda.
     * @return El mensaje de ayuda correspondiente, o un mensaje de error si el comando no está definido.
     */
    public static String getMensaje(String comando) {
        return ayudaComandos.getOrDefault(comando, "No se encontró ayuda para el comando " + comando + "'.");
    }
}
