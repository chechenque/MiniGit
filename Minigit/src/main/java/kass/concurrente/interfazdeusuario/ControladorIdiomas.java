package kass.concurrente.interfazdeusuario;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ControladorIdiomas {
    private static Map<String, String> messages = new HashMap<>();

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

    public String obtenerMensaje(String key) {
        return messages.getOrDefault(key, "¡Error!");
    }
}