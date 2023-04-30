package controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;

import models.RuleContent;
import models.AsciiSymbol;
import models.Colors;

public class YalChecker {
    // --> Atributos
    private ArrayList<String> code;
    private Map<String, String> ids;
    private Map<String, ArrayList<String>> idsExtended;
    private Map<String, RuleContent> rules;
    private static List<Character> signsOperation;
    private static List<Character> signsForAddParenthesis;
    private static List<Character> allowedTokens;
    private static List<Integer> notAllowedSymbols;
    private ArrayList<String> regexExpression;

    // --> Constructor
    public YalChecker(ArrayList<String> code){
        this.code = code;
        ids = new LinkedHashMap<String, String>();
        idsExtended = new LinkedHashMap<String, ArrayList<String>>();
        rules = new LinkedHashMap<String, RuleContent>();
        signsOperation = Arrays.asList('(', ')', '+', '.', '*', '?', '|');
        signsForAddParenthesis = Arrays.asList('+', '?', '*');
        allowedTokens = Arrays.asList('_');
        notAllowedSymbols = Arrays.asList(32, 9, 10);
        regexExpression = new ArrayList<String>();
    }
    
    // Metodos privados para la interpretacion del codigo yal
    public ArrayList<String> process(){
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
                boolean allow = (notAllowedSymbols.contains((int)letter) && allowEmptyEntry == false && !buff.isBlank());                
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
                            // Verificar si algo se ira a **LET**
                            if(letController != 0){
                                switch (letController) {
                                    case 3: // se espera un nombre de id para let
                                        if(SymbolTable.tokens.contains(buff)){ // revisar que el id no sea parte de los tokens
                                            System.out.println(Colors.RED + string);
                                            System.out.println(buff);
                                            System.out.println("ERROR: Id invalido porque se ingreso un token" + Colors.RESET);
                                            return null;
                                        }                                        
                                        identifier = buff;
                                        letController -= 1;
                                        break;
                                    case 2: // se espera recibir un signo de asignacion
                                        if(!buff.equals("=")){
                                            System.out.println(Colors.RED + string);
                                            System.out.println(buff);
                                            System.out.println("ERROR: Se esperaba un signo de asignacion =" + Colors.RESET);
                                            return null;
                                        }                                        
                                        letController -= 1; // Continuar ya que todo esta en orden
                                        allowEmptyEntry = true;
                                        break;
                                    case 1: // se espera recibir contenido
                                        // Revisar que el id del valor detectado de let no sea un valor numerico
                                        if(!isNumeric(identifier)) {
                                            System.out.println(Colors.RED + string);
                                            System.out.println(identifier);
                                            System.out.println("ERROR: No se permite un valor numerico como un id de let" + Colors.RESET);
                                            return null;
                                        }
                                        String result = addId(buff);
                                        if(result.equals("-1")) return null;
                                        ids.put(identifier, remplaceIdsForValues(result));
                                        identifier = ""; // reset name id
                                        letController -= 1;
                                        allowEmptyEntry = false;
                                        break;
                                }
                                buff = "";
                            }

                            // Add instructions to **RULE**
                            else if(defineRule == true){
                                switch (ruleController) {
                                        case 4: // obtener id de la funcion rule
                                            if(SymbolTable.tokens.contains(buff)){ // revisar que el id no sea parte de los tokens
                                                System.out.println(Colors.RED + string);
                                                System.out.println(Colors.RED + buff);
                                                System.out.println("ERROR: Id invalido porque se ingreso un nombre de un token");
                                                return null;
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
                                            System.out.println(Colors.RED + string);
                                            System.out.println("ERROR: INVALIDO >" + buff + "<" + Colors.RESET);                                            
                                            return null;
                                        }
                                        break;
                                    case 2: // verficiar si recibe un signo de asignacion
                                        if(!buff.equals("=")){
                                            System.out.println(Colors.RED + string);
                                            System.out.println("ERROR: se esperaba un signo de asignacion =" + Colors.RESET);
                                            return null;
                                        }                                    
                                        ruleController -= 1; // Continuar ya que todo esta en orden
                                        break;
                                    case 1:
                                        // Asignar y revisar que los elemenots procesados sean validos
                                        boolean ruleResult = addRuleContent(identifier, buff, ruleContent);
                                        if(ruleResult == false) return null;
                                        break;
                                }
                                buff = "";
                            }

                            // Si nada coincide entonces estamos ante un token invalido
                            else {
                                System.out.println(Colors.RED + string);
                                System.out.println("ERROR: Token invalido: " + buff + Colors.RESET);
                                return null;                                
                            }
                            break;
                    }                    
                }                
                else{
                    if(ignore == false) {
                        if(notAllowedSymbols.contains((int)letter) && allowEmptyEntry == false){
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
                        String result = addId(buff);
                        if(result.equals("-1")) return null;
                        ids.put(identifier, remplaceIdsForValues(result));
                        identifier = ""; // reset name id
                    }
                }
                letController = 0;
                allowEmptyEntry = false;

                // * ----> si falta agregar algo a la varible "rule"
                if(ruleController != 0){
                    boolean ruleResult = addRuleContent(identifier, buff, ruleContent);
                    if(ruleResult == false) return null;
                }
            }
        }
        
        getValuesRegularExpression(ruleContent.getRegex());
        return regexExpression;
    }

    // -> Getters
    public Map<String, String> getIds() {
        return ids;
    }

    public Map<String, ArrayList<String>> getIdsExtended() {
        return idsExtended; // Para retornar los valores de forma mas completa
    }

    public RuleContent getRuleContent(){        
        return rules.entrySet().iterator().next().getValue();
    }
    
    // -> Metodos
    private void getValuesRegularExpression(String regularExpression){        
        String aux = "", result = "";
        for (int i = 0; i < regularExpression.length(); i++) {
            char letter = regularExpression.charAt(i);       
            if(letter == '|'){
                if(ids.containsKey(aux)){
                    result += ids.get(aux);
                    addValueToRegex(aux, ids.get(aux));
                }
                else{
                    if(aux.length() == 1){ // Si dado caso solo tiene un digito
                        result += (int)aux.charAt(0);
                        addAsciiToRegex(((int)aux.charAt(0))+"");
                    }
                    if(aux.length() > 1){
                        result += aux;
                        addAsciiToRegex(aux);
                    }
                }
                result += "|";              
                regexExpression.add("|");
                aux = "";
            }
            else{
                aux += letter;
            }            
        }
        // Verificar longitud de aux por si se almacenara un ascii o un String en el arbol
        if(aux.length() == 1){
            result += (int)regularExpression.charAt(regularExpression.length() - 1);
            addAsciiToRegex(((int)regularExpression.charAt(regularExpression.length() - 1)+""));
        }
        if(aux.length() > 1){
            result += aux;
            addAsciiToRegex(aux);
        }
        regularExpression = result;
    }

    private void addValueToRegex(String id, String value){ // Para agregar los correspondientes ids y concatenasiones
        idsExtended.put(id, new ArrayList<String>());

        String temp = "";        
        regexExpression.add("(");
        regexExpression.add("(");        
        idsExtended.get(id).add("(");
        idsExtended.get(id).add("(");
        for (int index = 0; index < value.length(); index++) {
            char letter = value.charAt(index);
            if(signsOperation.contains(letter)){
                regexExpression.add(letter+"");
                idsExtended.get(id).add(letter+"");
                // Revisar si la expresion se va a concatenar
                if(letter == ')' || letter == '.' || letter == '?'){
                    // Si dado caso llegamos al limite entonces vamos a saltarnos este paso
                    if(index < value.length() - 1){ 
                        char next = value.charAt(index + 1);
                        if(next == '(') {
                            regexExpression.add("·");
                            idsExtended.get(id).add("·");
                        }
                    }
                }
            }
            else{ // Para ir detectando los numeros ascii
                temp += letter;
                char checkNext = value.charAt(index+1);
                if(signsOperation.contains(checkNext)){
                    regexExpression.add(temp);
                    idsExtended.get(id).add(temp);
                    temp = "";
                    if(checkNext == '(') {
                        regexExpression.add("·");
                        idsExtended.get(id).add(".");
                    }
                }
            }
        }
        regexExpression.add(")");
        regexExpression.add("·");
        regexExpression.add("#" + id);
        regexExpression.add(")");
        // Agregar hoja final
        idsExtended.get(id).add(")");
        idsExtended.get(id).add("·");
        idsExtended.get(id).add("#" + id);
        idsExtended.get(id).add(")");
    }

    private void addAsciiToRegex(String value){ // Agregar ids y concatenacion correspondientes del int
        regexExpression.add("(");
        regexExpression.add("(");
        // if(saveToken.length() > 0 && saveToken.length() != content.length()) saveToken += AsciiSymbol.Dot.c;
        if(!isNumeric(value)){ // Esto quiere decir que estamos ante un token todavia no convertido en ascii
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if(i > 0 && i != value.length()) regexExpression.add(AsciiSymbol.Dot.c+"");
                regexExpression.add((int)c+"");
            }
        }
        else{
            regexExpression.add(value+"");
        }
        regexExpression.add(")");
        regexExpression.add("·");
        if(isNumeric(value)){
            regexExpression.add("#" + (char)Integer.parseInt(value));
        }
        else{
            regexExpression.add("#" + value);
        }
        regexExpression.add(")");

        String charValue = (isNumeric(value)) ? (char)Integer.parseInt(value)+"" : value;
        idsExtended.put(charValue, new ArrayList<String>());
        idsExtended.get(charValue).add("(");
        idsExtended.get(charValue).add("(");
        if(!isNumeric(value)){ // Esto quiere decir que estamos ante un token todavia no convertido en ascii
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if(i > 0 && i != value.length()) idsExtended.get(charValue).add(AsciiSymbol.Dot.c+"");
                idsExtended.get(charValue).add((int)c+"");
            }
        }
        else{
            idsExtended.get(charValue).add(value+"");
        }
        idsExtended.get(charValue).add(")");
        idsExtended.get(charValue).add("·");
        if(isNumeric(value)){
            idsExtended.get(charValue).add("#" + (char)Integer.parseInt(value));
        }
        else{
            idsExtended.get(charValue).add("#" + value);
        }
        idsExtended.get(charValue).add(")");
    }

    private boolean addRuleContent(String id, String content, RuleContent ruleContent){
        if(!rules.containsKey(id)){ 
            rules.put(id, ruleContent);
        }

        RuleContent tempRuleContent = rules.get(id); // Obtener la rule definida

        // ==> Se determinara si se guardara un elemento para action o no
        if(tempRuleContent.getAddAction()){ // Siempre y cuando se haya dado permiso                
            if(content.equals("}")){
                tempRuleContent.stopAddAction(); // Se detiene si este encuentra corchehte de cierre
                tempRuleContent.addAction();
                // Luego se unira todas las palabras encontradas en el action
                return true;
            }
            // Se estara actualizando el buffer para la accion
            ruleContent.updateBufferForAction(content);
            return true;
        }
        
        // ==> Guardar un nuevo token
        // Determinar si se guardara contenido de *LET*s definidos
        if(ids.containsKey(content)){
            tempRuleContent.addNameBuffer(content);
            tempRuleContent.updateRegex(content);
        }
        // Determinar si se abrira una accion
        if(content.equals("{")){
            tempRuleContent.allowAddAction();
        }        
        // Determinar si se guardara un "|"
        if(content.equals("|")){
            tempRuleContent.updateRegex(content);
            // Si dado caso no hay nada en los actions entonces se creara una action vacia
            if(tempRuleContent.emptyAction()){ // Si el id no tiene una accion en concreto
                ruleContent.updateBufferForAction("return NULL");
                tempRuleContent.addAction();
            }
        }
        // Determinar si se guardara un CHAR
        if(content.charAt(0) == '\''){
            if(content.length() != 3) {
                System.out.println("\n" + Colors.RED + content);
                System.out.println("ERROR: Se espera a que el char tenga una letra y cierre con una comilla simple" + Colors.RESET);
                return false;
            }
            
            if(content.charAt(2) != '\''){
                System.out.println("\n" + Colors.RED + content);
                System.out.println("ERROR: Se esperaba a que cerraras tu expresion con comillas simples" + Colors.RESET);    
                return false;
            }

            // Si todo sale en orden entonces se guardara de forma exitosa el char detectado
            String charDetected = content.charAt(1) + "";
            tempRuleContent.addNameBuffer(charDetected);
            tempRuleContent.updateRegex(charDetected);
        }
        // Determinar si se guardara un STRING
        if(content.charAt(0) == '"'){
            if(content.length() >= 3 && content.charAt(content.length() - 1) == '"'){
                String saveToken = "";
                for (int i = 1; i < content.length() - 1; i++) {
                    // if(saveToken.length() > 0 && saveToken.length() != content.length()) saveToken += AsciiSymbol.Dot.c;
                    saveToken += content.charAt(i);
                }
                tempRuleContent.addNameBuffer(saveToken);
                tempRuleContent.updateRegex(saveToken);
            }
        }

        return true;
    }

    private String addId(String content){
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
                if(content.charAt(i+2) == '\'')
                    isOpen = true;
                else if(content.charAt(i+1) == '\\' || content.charAt(i+3) == '\''){
                    isOpen = true;
                }
                else {
                    System.out.println(content);
                    System.out.println("ERROR: No definiste de forma correcta el char");
                    return "-1";
                }
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
                    // System.out.println("CARACTER: " + letterOfContent);
                    if(letterOfContent == '\\'){ // ! Analizar caracteres especiales
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
                    else if(Character.isDigit(letterOfContent)){ // ! Ver si es numero
                        processContent += (int)letterOfContent;
                        char checkClose = content.charAt(i+1); // Revisar si se cerrara parentesis
                        if(checkClose != '"'){
                            processContent += '|';
                        }
                    }
                }
                else{ // Ingresar elemento al buffer de forma normal
                    if(!notAllowedSymbols.contains((int)letterOfContent)) // Vamos a saltar los espacios
                        processContent += letterOfContent;
                }
            }
        }
        
        return processContent; // Vamos a pasar el contenido
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
            else if(allowedTokens.contains(letter)){ 
                buffer += (int)letter;
            }
            else{
                temp += letter;
            }
        }        
        // Revisar si es uno de los signos definidos para ver si necesita parentesis
        if(signsForAddParenthesis.contains(buffer.charAt(buffer.length() - 1))){
            return "(" + buffer + ")";
        }

        return buffer;
    }

    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
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
            System.out.println("Temporal Regex: ");
            System.out.println(ruleContent.getRegex());
            System.out.println("============================");
            System.out.println("---> Rule: " + ruleName);
            System.out.println(ruleContent.toString());
            System.out.println("============================");
        }
    }
}
