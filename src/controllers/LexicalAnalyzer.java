package controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import models.RuleContent;

public class LexicalAnalyzer {
    private ArrayList<String> code;
    private Map<String, String> ids = new LinkedHashMap<String, String>();
    private Map<String, RuleContent> rules = new LinkedHashMap<String, RuleContent>();
        
    public LexicalAnalyzer(ArrayList<String> code){
        this.code = code;
    }
    
    public void process(){
        String identifier = "";
        for (String string : code) { // Obtener linea por linea
            String buff = "";
            boolean isOpen = false;
            boolean ignore = false;
            // Manejadores que nos ayudaran a controlar let
            int letController = 0;
            boolean allowEmptyEntry = false;

            for (int i = 0; i < string.length(); i++) { // Analizar linea en especifico
                char letter = string.charAt(i);
                // System.out.println(letter); // ! Para DEBUG
                // Por si el buffer tiene elementos dentro entonces se analizara si la estructura es valida
                boolean allow = (letter == ' ' && allowEmptyEntry == false);                
                if(allow && isOpen == false && buff.length() != 0){
                    // System.out.println("COMPLETE: " + buff); // ! Para DEBUG
                                        
                    switch (buff) {
                        // nueva variable
                        case "let":
                            // Analizar si coincide con ["id", "=", "contenido"] // Son 3 elementos
                            letController = 3;
                            buff = "";
                            break;
                        
                        // nueva funcion tipo rule
                        case "rule":
                            // empezar a guardar el resto de elementos

                            // se tiene que velar que sea ["id", "="] o ["id", "=", "resto de elementos y acciones"]
                            
                            break;
                        
                        // abrir parentesis
                        case "(*":
                            ignore = true;
                            buff = "";                            
                            break;

                        // Cerrar parentesis
                        case "*)":
                            ignore = false;
                            buff = "";                            
                            break;
                        
                        default: // Ninguno de los tokens iniciales son validos
                            // Verificar si algo se ira a let
                            if(letController != 0){
                                switch (letController) {
                                    case 3: // se espera un nombre de id para let
                                        if(SymbolTable.tokens.contains(buff)){ // revisar que el id no sea parte de los tokens
                                            System.out.println("Id invalido porque se ingreso un token");
                                            return;
                                        }                                        
                                        // ids.put(buff, ""); // asignar nombre de id
                                        identifier = buff;
                                        letController -= 1;
                                        break;
                                    case 2: // se espera recibir un signo de asignacion
                                        if(!buff.equals("=")){
                                            System.out.println("se esperaba un signo de asignacion =");
                                            return;
                                        }                                        
                                        letController -= 1; // Continuar ya que todo esta en orden
                                        allowEmptyEntry = true;
                                        break;
                                    case 1: // se espera recibir contenido
                                        addId(identifier, buff);
                                        identifier = ""; // reset name id
                                        letController -= 1;
                                        allowEmptyEntry = false;
                                        break;
                                }
                                buff = "";
                            }

                            // Si nada coincide entonces estamos ante un token invalido
                            else System.out.println("Token invalido");
                            break;
                    }                    
                }                
                else{
                    if(ignore == false) buff += letter;                    
                }
            }

            // Si dado caso el buffer termino lleno y falto procesar informacion
            if(buff.length() != 0){
                // System.out.println(letController + " COMPLETE JEJE: " + buff); // ! Para DEBUG
                if(letController != 0){
                    // Para obtener lo que hizo falta del contenido del operador
                    if(letController == 1){
                        addId(identifier, buff);
                        identifier = ""; // reset name id
                    }
                }
            }
        }
    }

    private void addId(String id, String content){
        String processContent = "";
        boolean isOpen = false;
        int countOrs = 0;
        // ! --> Se procesara lo que hay dentro para ver si hay algo especial
        for (int i = 0; i < content.length(); i++) {
            char letterOfContent = content.charAt(i);
            // Detectar si estamos ante un conjunto
            if(letterOfContent == '['){
                processContent += "(";
            }
            else if(letterOfContent == ']'){
                processContent += ")";
            }            
            else if(letterOfContent == '\'' && isOpen == false){
                isOpen = true;
            }
            // Detectar si estamos ante un rango a implementar
            else if(letterOfContent == '-' && isOpen == false){
                char beforeRange = content.charAt(i-2);
                char afterRange = content.charAt(i+2);
                int numBeforeRange = (int)beforeRange;
                int numAfterRange = (int)afterRange;
                for (int j = numBeforeRange+1; j < numAfterRange; j++) {
                    processContent += "|" + j;
                }
            }
            else if(letterOfContent == '\'' && isOpen == true){
                isOpen = false;
                countOrs++;
            }            
            else{
                if(isOpen == true){ // Esto quiere decir que se esta ingresando elementos a un conjunto
                    if(letterOfContent == '\\'){
                        char nextLetter = content.charAt(i+1);
                        char letterJ = ' ';
                        if(nextLetter == 't'){
                            i++;
                            letterJ = '\t';
                        }
                        if(nextLetter == 'n'){
                            i++;
                            letterJ = '\n';
                        }
                        if(countOrs > 0 && processContent.charAt(processContent.length()-1) != '('){ 
                            processContent += '|';
                        }
                        processContent += (int)letterJ;
                    }
                    else{
                        if(countOrs > 0 && processContent.charAt(processContent.length()-1) != '('){ 
                            processContent += '|';             
                        }
                        processContent  += (int)letterOfContent;
                    }
                }
                else{ // Ingresar elemento al buffer de forma normal
                    processContent += letterOfContent;
                }
            }
        }
        
        // ! --> Se guardara el nuevo contenido
        // System.out.println("AGREGAR: " + id); // ! Para DEBUG
        ids.put(id, processContent);
    }
    
    public void seeIds(){
        for(Map.Entry<String, String> id: ids.entrySet()){
            String nameId = id.getKey();
            String contentId = id.getValue();
            System.out.println(nameId + " = " + contentId);
        }
    }

    public void seeRules(){
        for(Map.Entry<String, RuleContent> rule: rules.entrySet()){
            String ruleName = rule.getKey();
            RuleContent ruleContent = rule.getValue();
            System.out.println("============================");
            System.out.println(ruleName + " " + ruleContent.toString());
        }
    }
}
