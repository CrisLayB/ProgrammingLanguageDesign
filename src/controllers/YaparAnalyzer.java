package controllers;

import java.util.ArrayList;
import java.util.HashMap;

public class YaparAnalyzer {
    public YaparAnalyzer(ArrayList<String> yaparContent, HashMap<String, String> tokensOfScanner){        
        processYapar(yaparContent, tokensOfScanner);
    }

    private void processYapar(ArrayList<String> yaparContent, HashMap<String, String> tokensOfScanner){
        String allContent = deleteComments(yaparContent); // Eliminar comentarios antes de empezar con el analisis
                
        // Empezar con el analisis
        String buffer = "";
        for (int index = 0; index < allContent.length(); index++) {
            char c = allContent.charAt(index);
            switch (buffer) {
                case "%token ": // Almacenar uno o varios tokens
                    System.out.println("==> Guardar TOKEN(S)");
                    buffer = "";
                    break;
            
                case "%%": // Indicar que se empezara a realizar el analisis de producciones
                    System.out.println("==> Analizar producciones");
                    buffer = "";
                    break;
                
                default: // Verificar si vamos a guardar un token
                    break;
            }
            buffer += c;
            System.out.print(c);
        }
    }

    private String deleteComments(ArrayList<String> yaparContent){
        String allContent = "";
        boolean ignore = false;
        for (String string : yaparContent) { // Se limpiara el contenido yapar y se omitiran los comentarios
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if(c == '/'){ // Verificar si estamos ante un nuevo comentario
                    if(i+1 < string.length()){
                        char nextC = string.charAt(i+1);
                        if(nextC == '*') {
                            ignore = true;
                            continue;
                        }
                    }
                }
                if(c == '*'){ // Verificar si estamos ante un nuevo comentario
                    if(i+1 < string.length()){
                        char nextC = string.charAt(i+1);
                        if(nextC == '/') {
                            ignore = false;
                            i++;
                            continue;
                        }
                    }
                }
                // Si todo esta en orden entonces se guardara de forma normal
                if(ignore != true) {
                    allContent += c;
                }
            }
            // Ingresar enter si hay contenido
            if(allContent.length() != 0){
                allContent += "\n";
            }
        }
        return allContent;
    }
}
