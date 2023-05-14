package controllers;

import models.Colors;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class YaparAnalyzer {
    // Contenido procesado y almacenado por el yapar
    private HashMap<String, String> tokensYapar;    
    // Tokens Generados por el scanner
    private HashMap<String, String> tokensOfScanner;
    // Auxiliares
    private boolean hasErrors;
    
    public YaparAnalyzer(ArrayList<String> yaparContent, HashMap<String, String> tokensOfScanner){        
        this.tokensOfScanner = tokensOfScanner;
        hasErrors = false;
        tokensYapar = new HashMap<String, String>();
        processYapar(yaparContent);
    }

    public boolean getHasErrors(){
        return hasErrors;
    }

    private void processYapar(ArrayList<String> yaparContent){
        String allContent = deleteComments(yaparContent); // Eliminar comentarios antes de empezar con el analisis
                        
        int jContinue = getTokensForYapar(allContent); // Empezar con el analisis de obtener TOKENS

        // Si es -1 quiere decir que no se cerro con "%%"
        // Si es 0 quiere decir que hubo un error con los nombres de los tokens
        if(jContinue == -1 || jContinue == 0) {
            hasErrors = true;
            return;
        }
    
        if(!getProductionsFromYapar(allContent, jContinue)){ // Obtener todas las producciones del yapar
            hasErrors = true;
            return;
        }
    }

    private boolean getProductionsFromYapar(String content, int jContinue){
        for (int i = jContinue; i < content.length(); i++) {
            char c = content.charAt(i);
            System.out.print(c);
        }
        return true; // Retornara true si no se encontraron errores
    }

    private int getTokensForYapar(String allContent){
        String buffer = "";
        boolean tokenActivate = false;
        for (int i = 0; i < allContent.length(); i++) {
            char c = allContent.charAt(i);

            if(c == '\n'){
                if(tokenActivate){ // Vamos a procesar todos los tokens que se quisieron ingresar
                    // Dara error si dado caso no se ingreso nada en el %token
                    if(buffer.length() == 0){
                        System.out.println(Colors.RED + "\nERROR: Se esperaba contenido para el token a definir\n" + Colors.RESET);
                        return 0;
                    }
                    // Tomando en cuenta las consideraciones de yalex solo se admiten letras mayusculas y un spacio
                    int firstLetter = (int)'A', lastLetter = (int)'Z', space = (int)' ';

                    // Se espera que el primer char sea una letra mayuscula y el ultimo tambien
                    if((int)buffer.charAt(0) >= lastLetter || (int)buffer.charAt(0) <= firstLetter){
                        System.out.println(Colors.RED + "\nERROR: Se esperaba una letra mayuscula para el token a definir\n" + Colors.RESET);
                        return 0;
                    }

                    // Vamos a validar si todas las letras son iniciales mayusculas, si se detecta
                    // un espacio entonces este procedera a guardar el token
                    String bufferTokenDetected = "";
                    for (int index = 0; index < buffer.length(); index++) {
                        char cBuffer = buffer.charAt(index);
                        int cNumber = (int)cBuffer;

                        if(cNumber == space){ // Detectar espacio
                            if(!saveToken(bufferTokenDetected)) return 0;
                            bufferTokenDetected = "";
                            continue;
                        }
                        if(cNumber >= firstLetter && cNumber <= lastLetter){ // Si es mayuscula
                            bufferTokenDetected += cBuffer;
                        }
                        else{
                            System.out.println(Colors.RED + "\nERROR: Se esperaba una letra mayuscula para el token defino\n" + Colors.RESET);
                            return 0;
                        }
                    }
                    
                    // Guardar idToken si dado caso el buffer del token detectado no esta vacio
                    if(bufferTokenDetected.length() != 0){
                        if(!saveToken(bufferTokenDetected)) return 0;
                    }
                }
                tokenActivate = false;
                buffer = "";
                continue;
            }

            buffer += c;            
            switch (buffer) {
                case "%token ": // Almacenar uno o varios tokens
                    tokenActivate = true;
                    buffer = "";
                    break;
                    
                case "%%": // Indicar que se empezara a realizar el analisis de producciones
                    buffer = "";                    
                    return i+1;
            }
        }

        System.out.println(Colors.RED + "\nERROR: No se encontro '%%', esto es vital para empezar a detectar PRODUCCIONES\n" + Colors.RESET);
        return -1; // Esto quiere decir que no se finalizo con "%%" si se retorna 0 entonces hay errores
    }

    private boolean saveToken(String tokenDetected){
        if(tokensOfScanner.containsKey(tokenDetected)){
            tokensYapar.put(tokenDetected, tokensOfScanner.get(tokenDetected));
            return true;
        }
        System.out.println(Colors.RED + "\nERROR: Token [" + tokenDetected + "] definido en Yapar no existe en los tokens definidos del scanner\n" + Colors.RESET);
        return false;
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

    public void seeTokensYapar(){        
        for(Map.Entry<String, String> id: tokensYapar.entrySet()){
            String nameId = id.getKey();
            String contentId = id.getValue();
            System.out.println(nameId + " = " + contentId);
        }
    }
}
