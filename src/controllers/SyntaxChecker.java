package controllers;

public class SyntaxChecker {
    public static String checkExpression(String expression){
        String errors = "";

        // Verificar todos los posibles casos erroneos de signos y parentesis
        
        if(expression.contains("||")){
            errors += "No se puede tener dos | juntos\n";
        }

        if(expression.contains("(|") || expression.contains("(*") || expression.contains("(+") || expression.contains("(?")){
            errors += "No se puede tener un operador | despues de un parentesis\n";
        }
        
        if(expression.contains("|)")){
            errors += "No se puede tener un operador | antes del cierre de un parentesis\n";
        }

        if(expression.charAt(0) == '|' || expression.charAt(0) == '*' || expression.charAt(0) == '+' || expression.charAt(0) == '?'){
            errors += "No se puede ingresar un operador al principio de la expresion\n";
        }

        if(expression.charAt(expression.length() - 1) == '|'){
            errors += "No se puede ingresar el operador | al final\n";
        }

        // Verificar si tiene la misma cantidad de parentesis (de inicio y fin)

        int openParenthesis = 0;
        int closeParenthesis = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(c == '(') openParenthesis++;
            if(c == ')') closeParenthesis++;
        }

        if(openParenthesis != closeParenthesis){
            errors += "No tienen la misma cantidad de llaves\n";
        }
        
        return errors;
    }
}
