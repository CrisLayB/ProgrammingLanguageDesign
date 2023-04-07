package controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;

import models.RuleContent;

public class LexicalAnalyzer {
    private ArrayList<String> code;
    private Map<String, String> ids = new LinkedHashMap<String, String>();
    private Map<String, RuleContent> rules = new LinkedHashMap<String, RuleContent>();
    private static List<Character> signsOperation = Arrays.asList('(', ')', '+', '.', '*', '?', '|');        
    private String regularExpression = ""; // Esto podria cambiar a ArrayList si se solicitaran mas expressiones y/o reglas    
        
    // --> Constructor
    public LexicalAnalyzer(ArrayList<String> code){
        this.code = code;
        // Llamar a las funciones para realizar el proceso
        process();
        System.out.println("\n=======> let obtenidos:\n");
        seeIds();
        System.out.println("\n=======> Rule obtenidos:\n");
        seeRules();
        // Vamos a obtener los valores reales de la regla definida
        getValuesRegularExpression();
    }

    // --> Getters
    public String getRegularExpression() {
        return regularExpression;
    }

    public Map<String, RuleContent> getRules() {
        return rules;
    }
    
    // Metodos privados para la interpretacion del codigo yal
    private void process(){
        String identifier = "";
        boolean defineRule = false;
        boolean allowEmptyEntry = false;
        int ruleController = 0;
        RuleContent ruleContent = null;

        for (String string : code) { // Obtener linea por linea
            String buff = "";
            boolean isOpen = false;
            boolean ignore = false;
            // Manejadores que nos ayudaran a controlar let
            int letController = 0;

            for (int i = 0; i < string.length(); i++) { // Analizar linea en especifico
                char letter = string.charAt(i);
                // Por si el buffer tiene elementos dentro entonces se analizara si la estructura es valida
                boolean allow = (letter == ' ' && allowEmptyEntry == false && !buff.isBlank());                
                if(allow && isOpen == false && buff.length() != 0){
                    switch (buff) {
                        // nueva variable
                        case "let":
                            // Analizar si coincide con ["id", "=", "contenido"] // Son 3 elementos
                            letController = 3;
                            buff = "";
                            break;
                        
                        // nueva funcion tipo rule
                        case "rule":
                            // se tiene que velar que sea ["id", "args", "=", "Resto de elementos y acciones"] 
                            // o ["id", "=", "resto de elementos y acciones"]
                            ruleController = 4;
                            defineRule = true;
                            buff = "";
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
                                        // Revisar que el id del valor detectado de let no sea un valor numerico
                                        if(!isNumeric(identifier)) {
                                            System.out.println("No se permite un valor numerico como un id de let");
                                            return;
                                        }
                                        addId(identifier, buff);
                                        identifier = ""; // reset name id
                                        letController -= 1;
                                        allowEmptyEntry = false;
                                        break;
                                }
                                buff = "";
                            }

                            // Add instructions to rule
                            else if(defineRule == true){
                                switch (ruleController) {
                                        case 4: // obtener id de la funcion rule
                                            if(SymbolTable.tokens.contains(buff)){ // revisar que el id no sea parte de los tokens
                                                System.out.println("Id invalido porque se ingreso un token");
                                                return;
                                            }                         
                                            identifier = buff;
                                            ruleController -= 1;
                                        break;
                                    case 3: // verificar si recibe parametros (por medio de [) o signo de asignacion
                                        if(buff.equals("[")){ // Si dado caso se encuentran argumentos
                                            // // llevar a cabo operacion de argumentos
                                            // // !...
                                            // // Ingresar argumentos detectados
                                            // ruleContent = new RuleContent(null);
                                            ruleController -= 1;
                                        }
                                        else if(buff.equals("=")){
                                            ruleContent = new RuleContent();
                                            ruleController = 1; // Continuar ya que todo esta en orden
                                            System.err.println(ruleController);
                                        }
                                        else{
                                            System.out.println("INVALIDO >" + buff + "<");                                            
                                            return;
                                        }
                                        break;
                                    case 2: // verficiar si recibe un signo de asignacion
                                        System.out.println("REVISAR =");
                                        if(!buff.equals("=")){
                                            System.out.println("se esperaba un signo de asignacion =");
                                            return;
                                        }                                    
                                        ruleController -= 1; // Continuar ya que todo esta en orden
                                        break;
                                    case 1:
                                        // Asignar y revisar que los elemenots procesados sean validos
                                        addRuleContent(identifier, buff, ruleContent);
                                        break;
                                }
                                buff = "";
                            }

                            // Si nada coincide entonces estamos ante un token invalido
                            else {
                                System.out.println("Token invalido");
                                buff = "";
                            }
                            break;
                    }                    
                }                
                else{
                    if(ignore == false) {
                        if(letter == ' ' && allowEmptyEntry == false){
                            // do nothing... to be honest i hate dirty code
                        }else{
                            buff += letter;
                        }
                    }
                }
            }

            // Si dado caso el buffer termino lleno y falto procesar informacion
            if(buff.length() != 0){
                // * ----> si falta agregar algo a la variable "let"
                if(letController != 0){
                    // Para obtener lo que hizo falta del contenido del operador
                    if(letController == 1){
                        addId(identifier, buff);
                        identifier = ""; // reset name id
                    }
                }
                letController = 0;
                allowEmptyEntry = false;

                // * ----> si falta agregar algo a la varible "rule"
                if(ruleController != 0){
                    addRuleContent(identifier, buff, ruleContent);
                }
            }
        }
    }

    private void getValuesRegularExpression(){
        String aux = "", result = "";
        for (int i = 0; i < regularExpression.length(); i++) {
            char letter = regularExpression.charAt(i);
            if(letter == '|'){
                result += (ids.containsKey(aux)) ? ids.get(aux) : aux;
                result += "|";
                aux = "";
            }
            else{
                aux += letter;
            }
            
        }
        regularExpression = result;
    }

    private void addRuleContent(String id, String content, RuleContent ruleContent){
        if(!rules.containsKey(id)){ 
            rules.put(id, ruleContent);
        }

        RuleContent tempRuleContent = rules.get(id);
        // Determinar si se guardara una variable
        if(ids.containsKey(content)){
            tempRuleContent.addNameBuffer(content);
            tempRuleContent.updateRegex(content);
        }
        // Determinar si se abrira una accion
        if(content.equals("{")){

        }
        // Determianr si se terminara de guardar dicha accion
        if(content.equals("}")){
            
        }
        // Determinar si se guardara un "|"
        if(content.equals("|")){
            tempRuleContent.updateRegex(content);
        }
        // Determinar si se guardara un char
        if(content.length() == 3){
            if(content.charAt(0) == '\'' && content.charAt(2) == '\''){
                String charDetected = content.charAt(1) + "";
                tempRuleContent.addNameBuffer(charDetected);
                tempRuleContent.updateRegex(charDetected);
            }
        }
    }

    private void addId(String id, String content){
        String processContent = "";
        boolean isOpen = false;
        boolean isOpenStr = false;
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
            else if(letterOfContent == '"' && isOpenStr == false){
                isOpenStr = true;
            }
            else if(letterOfContent == '"' && isOpenStr == true){
                isOpenStr = false;
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
                else if(isOpenStr == true){
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

                        processContent += (int)letterJ;
                        char checkClose = content.charAt(i+1); // Revisar si se cerrara parentesis
                        if(checkClose != '"'){
                            processContent += '|';
                        }
                    }
                }
                else{ // Ingresar elemento al buffer de forma normal
                    processContent += letterOfContent;
                }
            }
        }
        
        // ! --> Se guardara el nuevo contenido
        ids.put(id, remplaceIdsForValues(processContent));
    }

    private String remplaceIdsForValues(String processContent){
        String buffer = "", temp = ""; // Para apoyarnos a realizar el remplazo de variables
        for (int i = 0; i < processContent.length(); i++) {
            char letter = processContent.charAt(i);
            if(signsOperation.contains(letter)){
                if(ids.containsKey(temp)){ // detectar si esta dentro de las variables
                    buffer += ids.get(temp);
                    temp = "";
                }
                else if(isNumeric(temp)){ // detectar si es un ascii (realmente un numero)
                    buffer += temp;
                    temp = "";
                }
                buffer += letter;
            }
            else{
                temp += letter;
            }
        }
        return buffer;
    }

    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    private void seeIds(){
        for(Map.Entry<String, String> id: ids.entrySet()){
            String nameId = id.getKey();
            String contentId = id.getValue();
            System.out.println(nameId + " = " + contentId);
        }
    }

    private void seeRules(){
        for(Map.Entry<String, RuleContent> rule: rules.entrySet()){
            String ruleName = rule.getKey();
            RuleContent ruleContent = rule.getValue();
            System.out.println("============================");
            System.out.println("Temporal Regex: ");
            System.out.println(ruleContent.getRegex());
            regularExpression = ruleContent.getRegex();
            System.out.println("============================");
            System.out.println("---> Rule: " + ruleName);
            System.out.println(ruleContent.toString());
        }
    }
}