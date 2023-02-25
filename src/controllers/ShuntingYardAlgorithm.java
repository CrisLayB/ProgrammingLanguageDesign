package controllers;

import java.util.Stack;
import java.lang.Character;

import middleware.Errors;

public class ShuntingYardAlgorithm {
            
    public static String infixToPostfix(String infixExpression){
        Stack<Character> stack = new Stack<>();
        String output = "";
                        
        for(int i = 0; i < infixExpression.length(); i++){
            char c = infixExpression.charAt(i);

            // Si se escanea una letra onumero entonces se agregara al output
            if(Character.isLetterOrDigit(c)) output += c;

            // Si se escanea el parentesis de inicio entonces se jalara al stack
            else if(c == '(') stack.push(c);

            // Si se escanea el parentesis de cierre entonces se sacaran todo
            // los operadores del stack hasta encontrar el parentesis de inicio
            else if(c == ')'){
                while(!stack.isEmpty() && stack.peek() != '(') output += stack.pop();
                stack.pop();
            }

            // Signos que agregar al stack
            else if(c == '+' || c == '|') stack.push(c);
            
            // Agregar el resto de operadores...
            else output += c;            
        }
        
        // Se sacaran el resto de los operadores pendientes
        while(!stack.isEmpty()){
            if(stack.peek() == '(') return Errors.InvalidExpression.error;
            output += stack.pop();
        }
        
        return output;
    }
}
