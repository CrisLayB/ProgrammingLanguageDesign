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

            // Si un operador fue encontrado entonces el contenido del pop
            // sera tomado para el output
            else{
                while(!stack.isEmpty()  && precedence(c) <= precedence(stack.peek()) && leftAssociativity(c)){
                    output += stack.pop();
                }
                stack.push(c);
            }
        }
        
        // Se sacaran el resto de los operadores pendientes
        while(!stack.isEmpty()){
            if(stack.peek() == '(') return Errors.InvalidExpression.error;
            output += stack.pop();
        }
        
        return output;
    }
    
    private static int precedence(char c){
        if(c == '+' || c == '|' || c == '-' || c == '?') return 1;
        if(c == '*' || c == '/' || c == '÷') return 2;
        if(c == '^') return 3;
        return -1;
    }

    private static boolean leftAssociativity(char c){
        if(c == '+' || c == '|' || c == '-' || c == '/' || c == '*' || c == '?') return true;
        return false;
    }
}
