package controllers;

public class SyntaxicAnalyzer {
    
    public static String[] structOfInitialFunction(String func){
        switch (func) {
            case "let":                
                String[] checkLet = new String[3];
                checkLet[1] = "=";
                return checkLet;
            case "rule":                
                String[] checkRule = new String[4];
                checkRule[1] = "=";
                checkRule[2] = "=";
                return checkRule;
            case "(":
                String[] checkLP = new String[4];
                checkLP[0] = "*";
                checkLP[2] = "*";
                checkLP[3] = ")";
                break;
        }
        return null;
    }

    public static int letFunction(String recived, String toCheck){
        // Si es null entonces quiere decir que es un identificador o contenido de let
        if(toCheck == null){
            return 1;
        }
        
        // Si se respeto el parentesis                
        if(toCheck.equals("=")){
            // Esto indicara si en efecto se ingreso la asignacion o es invalido
            if(recived.equals(toCheck)) return 2;
            // return (recived.equals(toCheck)) ? 2 : -1;
        }

        return -1; // Entonces quiere decir que es un id o algo libre de ingresar
    }
}
