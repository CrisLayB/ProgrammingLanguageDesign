package controllers;

import models.Symbol;

public class GenerateSymbols {
    
    public static Symbol[] getSymbols(String postfixExpression){
        Symbol[] symbols = new Symbol[postfixExpression.length()];

        for (int i = 0; i < postfixExpression.length(); i++) {
            char c = postfixExpression.charAt(i);            
            symbols[i] = new Symbol((int)c, c);
        }
        
        return symbols;
    }
    
}
