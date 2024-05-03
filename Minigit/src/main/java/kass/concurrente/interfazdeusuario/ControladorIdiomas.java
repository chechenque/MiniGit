/**
 * @author Equipo Los Threads.
 * Integrantes: Emilio Francisco Sánchez Martínez
 * Clase que se encarga de controlar que idioma hay que desplegar en la interfaz de usuario.
 */

package kass.concurrente.interfazdeusuario;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ControladorIdiomas {
    private static Map<String, String> messages = new HashMap<>();

    /**
     * Métddo para establecer el idioma, si no es encontrado marcará un error.
     * @param idioma - idioma que se va a usar
     *                  es - para español
     *                  en - para ingles
     */
    public void cargarIdioma(String idioma) {
        try {
            InputStream input = ControladorIdiomas.class.getClassLoader().getResourceAsStream("i18n/" + idioma + ".json");
            Scanner scanner = new Scanner(input).useDelimiter("\\A");
            String jsonString = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            Map<String, String> parsedMessages = parseJsonString(jsonString);
            messages = parsedMessages;
        } catch (Exception e) {
            System.err.println("Error: " + idioma + ": " + e.getMessage());
        }
    }

    /**
     * Método estatico que agarra los caracteres de una rchivo JSON y analiza obtener las claves.
     * @param jsonString -archivo JSON que se pasará para analizar.
     * @return devuelve las distintas claves y valores del archivo JSON.
     */
    private static Map<String, String> parseJsonString(String jsonString) {
        Map<String, String> parsedMessages = new HashMap<>();
        int currentIndex = 0;
        int openBraces = 0;
        String key = null;
        StringBuilder value = new StringBuilder();
        boolean inString = false;
    
        while (currentIndex < jsonString.length()) {
            char currentChar = jsonString.charAt(currentIndex);
    
            if (currentChar == '{') {
                openBraces++;
            } else if (currentChar == '}') {
                openBraces--;
            } else if (currentChar == '"') {
                if (inString) {
                    if (jsonString.charAt(currentIndex - 1) != '\\') {
                        inString = false;
                        if (key != null) {
                            parsedMessages.put(key, value.toString());
                            key = null;
                            value = new StringBuilder();
                        }
                    } else {
                        value.append('"');
                    }
                } else {
                    inString = true;
                }
            } else if (inString) {
                if (currentChar == '\\') {
                    currentIndex++;
                    char escapedChar = jsonString.charAt(currentIndex);
                    switch (escapedChar) {
                        case 'n':
                            value.append('\n');
                            break;
                        case 'r':
                            value.append('\r');
                            break;
                        case 't':
                            value.append('\t');
                            break;
                        default:
                            value.append(escapedChar);
                    }
                } else {
                    value.append(currentChar);
                }
            } else if (currentChar == ':') {
                key = value.toString();
                value = new StringBuilder();
            }
    
            currentIndex++;
        }
    
        return parsedMessages;
    }

    /**
     * Método para obtener el valor de un clave o llave de un archivo JSON y no encuentra dicha frase devuelve el mesanje de: ¡Error!.
     * @param key - llave que será analizada.
     * @return - mensaje o valor de la llave.
     */
    public String obtenerMensaje(String key) {
        return messages.getOrDefault(key, "¡Error!");
    }
}