package controllers;

import models.Colors;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

public class YaparAnalyzer {
    // Contenido procesado y almacenado por el yapar
    private HashMap<String, String> tokensYapar;    
    // Tokens Generados por el scanner
    private HashMap<String, String> tokensOfScanner;
    // Producciones generadas
    private ArrayList<ArrayList<String>> productions;    
    // Auxiliares
    private boolean hasErrors;
    // Caracteres en ascii auxiliares
    private final int firstLetterLowerCase = (int)'a', lastLetterLowerCase = (int)'z';
    private final int firstCapitalLetter = (int)'A', lastCapitalLetter = (int)'Z';
    private final int spaceLetter = (int)' ';
    
    public YaparAnalyzer(ArrayList<String> yaparContent, HashMap<String, String> tokensOfScanner){        
        this.tokensOfScanner = tokensOfScanner;
        hasErrors = false;
        tokensYapar = new HashMap<String, String>();
        productions = new ArrayList<>();
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
        List<Character> ignoreSpaces = Arrays.asList((char)9, (char)10);
        List<Character> ignoreAllSpaces = Arrays.asList((char)32, (char)9, (char)10);
        boolean initialProduction = true;
        boolean insertProductions = false;
        boolean foundProduction = false, foundToken = false;
        String buffer = "";
        
        for (int i = jContinue; i < content.length(); i++) {
            char c = content.charAt(i);

            if(initialProduction){
                if(!ignoreAllSpaces.contains(c)){
                    if(c == ':'){
                        // ! Guardar *NUEVA PRODUCTION*
                        System.out.println("NEW PRODUCTION: " + buffer);
                        initialProduction = false;
                        insertProductions = true;
                        buffer = "";
                        continue;
                    }

                    // Se espera que solamente se ingresen letras minusculas para ingresar una produccion
                    if((int)c >= firstLetterLowerCase && (int)c <= lastLetterLowerCase){
                        buffer += c;
                    }
                }
            }

            if(insertProductions){                
                if(c == ' ' && foundProduction){
                    // ! Guardar *PRODUCTION*
                    System.out.println("\n+" + buffer + "+");
                    buffer = "";
                    foundProduction = false;
                    continue;
                }

                if(c == ' ' && foundToken){
                    // ! Guardar *TOKEN*
                    System.out.println("\n-" + buffer + "-");
                    buffer = "";
                    foundToken = false;
                    continue;
                }
                                
                if(!ignoreSpaces.contains(c)){ // Por si no se a detectado nada
                    // Para finalizar el ingreso de la produccion
                    if(c == ';'){
                        // ! Guardar *PRODUCTION* O *TOKEN*
                        System.out.println("\n;" + buffer + ";");
                        initialProduction = true;
                        insertProductions = false;
                        buffer = "";
                        foundToken = false;
                        foundProduction = false;
                        continue;
                    }

                    if(foundProduction){ // Por si se encontro una produccion a guardar                    
                        if((int)c >= firstLetterLowerCase && (int)c <= lastLetterLowerCase) {
                            buffer += c;
                            continue;
                        }
                        System.out.println(Colors.RED + "ERROR: El caracter " + c + " es invalido con la secuencia de letras minusculas (Para una PRODUCCION)" + Colors.RESET);
                        return false;
                    }
    
                    if(foundToken){ // Por si se encontro un Token a procesar
                        if((int)c >= firstCapitalLetter && (int)c <= lastCapitalLetter) {
                            buffer += c;
                            continue;
                        }
                        System.out.println(Colors.RED + "ERROR: El caracter " + c + " es invalido con la secuencia de letras mayusculas (Para un TOKEN)" + Colors.RESET);
                        return false;
                    }

                    if(c == '|'){
                        // ! Guardar *OR*
                        System.out.println(">" + c + "<");
                        continue;
                    }

                    // Si se detecto una letra minuscula (Representa PRODUCCIONES)
                    if((int)c >= firstLetterLowerCase && (int)c <= lastLetterLowerCase){
                        foundProduction = true;
                        buffer += c;
                    }

                    // Si se detecto una letra mayuscula (Representa TOKENS)
                    if((int)c >= firstCapitalLetter && (int)c <= lastCapitalLetter){
                        foundToken = true;
                        buffer += c;
                    }
                }
            }
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

                    // Se espera que el primer char sea una letra mayuscula y el ultimo tambien
                    if((int)buffer.charAt(0) >= lastCapitalLetter || (int)buffer.charAt(0) <= firstCapitalLetter){
                        System.out.println(Colors.RED + "\nERROR: Se esperaba una letra mayuscula para el token a definir\n" + Colors.RESET);
                        return 0;
                    }

                    // Vamos a validar si todas las letras son iniciales mayusculas, si se detecta
                    // un espacio entonces este procedera a guardar el token
                    String bufferTokenDetected = "";
                    for (int index = 0; index < buffer.length(); index++) {
                        char cBuffer = buffer.charAt(index);
                        int cNumber = (int)cBuffer;

                        if(cNumber == spaceLetter){ // Detectar espacio
                            if(!saveToken(bufferTokenDetected)) return 0;
                            bufferTokenDetected = "";
                            continue;
                        }
                        if(cNumber >= firstCapitalLetter && cNumber <= lastCapitalLetter){ // Si es mayuscula
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
