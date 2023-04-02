package controllers;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Rule;

import models.RuleContent;

import java.util.HashMap;

public class LexicalAnalyzer {
    // Tokends a definir
    /*
     * let
     * letter
     * number
     * .
     * (
     * )
     * *
     * [
     * ]
     * '
     * -
     * "
     * =
     * |
     * rule
     * +
     * ?
     */

    private ArrayList<String> code;
    private Map<String, String> ids = new HashMap<String, String>();
    private Map<String, RuleContent> rules = new HashMap<String, RuleContent>();
        
    public LexicalAnalyzer(ArrayList<String> code){
        this.code = code;
    }
    
    public void process(){
        for (String string : code) { // Obtener linea por linea
            String buff = "";
            boolean isOpen = false;
            int countOps = 0;
            String[] structToken = null;

            for (int i = 0; i < string.length(); i++) { // Analizar linea en especifico
                char letter = string.charAt(i);
                System.out.println(letter);

                // Por si el buffer tiene elementos dentro entonces se analizara si la estructura es valida
                if(letter == ' ' && isOpen == false && buff.length() != 0){
                    System.out.println("COMPLETE: " + buff);

                    // Verificar si el token es valido
                    if(structToken == null){
                        if(!SymbolTable.startingTokens.contains(buff)){
                            System.out.println("Token invalido");
                            return;
                        }
                        structToken = SyntaxicAnalyzer.structOfInitialFunction(buff); // Quiere indicar que todo esta en orden
                    }

                    // Determinar el resto de elementos dentro del structToken
                    processInstruction(structToken, string, i+1, string.length());
                    
                    // Agregar toda la data dentro del let o rule mencionada
                    
                    i = string.length();
                    // Resetear buffer
                    buff = "";
                }                
                else{
                    // if(isOpen == true){ // Esto quiere decir que se esta ingreando elementos a un conjunto
                    //     if(letter == '\\'){
                    //         char nextLetter = string.charAt(i+1);
                    //         char letterJ = ' ';
                    //         if(nextLetter == 't'){
                    //             i++;
                    //             letterJ = '\t';
                    //         }
                    //         if(nextLetter == 'n'){
                    //             i++;
                    //             letterJ = '\n';
                    //         }
                    //         if(countOps > 0) buff += '|';
                    //         buff += (int)letterJ;
                    //     }
                    //     else{
                    //         if(countOps > 0) buff += '|';                 
                    //         buff  += (int)letter;
                    //     }
                    // }
                    // else{ // Ingresar elemento al buffer de forma normal
                    //     buff += letter;
                    // }
                    buff += letter;
                }
            }

            // Si dado caso el buffer termino lleno
            if(buff.length() != 0){
                System.out.println("COMPLETE JEJE: " + buff);
            }
        }
    }

    private void processInstruction(String[] structToken, String string, int start, int limit){     
        String buff = "";
        boolean isOpen = false;
        int countOps = 0;
        int countStruct = 0;
        for (int i = start; i < limit; i++) {
            char letter = string.charAt(i);

            // Por si el buffer tiene elementos dentro entonces se analizara si la estructura es valida
            if(letter == ' ' && isOpen == false && buff.length() != 0){
                System.out.println("VIZUALIDAR: " + buff + " y verificar con " + structToken[countStruct]);
                int res = SyntaxicAnalyzer.letFunction(buff, structToken[countStruct]);
                System.out.println("RES: " + res);
                countStruct++;
                buff = "";
            }
            else if(letter == '['){ // Abrir conjunto nuevo
                buff += "(";
            }
            else if(letter == ']'){ // Cerrar conjunto que fue nuevo
                buff += ")";
            }
            else if(letter == '\'' && isOpen == false){ // Abrir para agregar char
                isOpen = true;
            }
            else if(letter == '\'' && isOpen == true){ // Para cerrar secuencia char
                isOpen = false;
                countOps++;
            }
            else{
                if(isOpen == true){ // Esto quiere decir que se esta ingreando elementos a un conjunto
                    if(letter == '\\'){
                        char nextLetter = string.charAt(i+1);
                        char letterJ = ' ';
                        if(nextLetter == 't'){
                            i++;
                            letterJ = '\t';
                        }
                        if(nextLetter == 'n'){
                            i++;
                            letterJ = '\n';
                        }
                        if(countOps > 0) buff += '|';
                        buff += (int)letterJ;
                    }
                    else{
                        if(countOps > 0) buff += '|';                 
                        buff  += (int)letter;
                    }
                }
                else{ // Ingresar elemento al buffer de forma normal
                    buff += letter;
                }
            }
            // ========================================================
        }
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
