package algorithms;

import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.lang.Character;

public class ShuntingYardAlgorithm {

    private static List<Character> operators = Arrays.asList('|', '?', '+', '*');
    private static List<Character> operatorsBin = Arrays.asList('|');
    
    public static String concatenate(String expression){
        String result = "";
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(i+1 < expression.length()){
                char cNext = expression.charAt(i+1);
                result += c;
                if(c != '(' && cNext != ')' && !operators.contains(cNext) && !operatorsBin.contains(c)){
                    result += '.';
                }
            }
        }
        return result + expression.charAt(expression.length() - 1);
    }
            
    public static String infixToPostfix(String infixExpression){
        Stack<Character> stack = new Stack<>();
        String output = "";
                        
        for(int i = 0; i < infixExpression.length(); i++){
            char c = infixExpression.charAt(i);

            // Si se escanea el parentesis de inicio entonces se jalara al stack
            if(c == '(') stack.push(c);

            // Si se escanea el parentesis de cierre entonces se sacaran todo
            // los operadores del stack hasta encontrar el parentesis de inicio
            else if(c == ')'){
                while(!stack.isEmpty() && stack.peek() != '(') output += stack.pop();
                stack.pop();
            }
            
            // Si es un valor u operador se instanceraa en el stack y se ordenara los
            // operadores dependiendo del nivel de precedencia
            else{
                while(!stack.isEmpty()  && precedence(c) <= precedence(stack.peek()) && leftAssociativity(c)){
                    output += stack.pop();
                }
                stack.push(c);
            }
        }
        
        // Se sacaran el resto de los operadores pendientes
        while(!stack.isEmpty()){
            if(stack.peek() == '(') return null;
            output += stack.pop();
        }
        
        return output;
    }

    private static int precedence(char c){
        if(c == '(') return 1;
        if(c == '|') return 2;
        if(c == '.') return 3;
        if(c == '?' || c == '*' || c == '+') return 4;
        if(c == '^') return 5;
        return 6;
    }

    private static boolean leftAssociativity(char c){
        if(c == '+' || c == '|' || c == '^' || c == '.' || c == '*' || c == '?') return true;
        return false;
    }
}
