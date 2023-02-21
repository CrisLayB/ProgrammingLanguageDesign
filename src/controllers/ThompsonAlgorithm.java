package controllers;

import java.util.Stack;

import models.Symbol;
import models.NFA;

public class ThompsonAlgorithm {
    public static NFA constructNFA(Symbol[] symbols){
        NFA nfa = new NFA();
        Stack<NFA> stack = new Stack<NFA>();
        // Se agregara un estado vacio en el stack

        for (int i = 0; i < symbols.length; i++) {            
            int idSymbol = symbols[i].getId();
            System.out.println(idSymbol);
            switch (idSymbol) {
                case 43: // '+' o '|' // UNION
                    break;

                case 42: // '*' // CLEAN
                    break;

                case 63: // '?' // CONCATENATION
                    break;

                default: // Sunpogamos que es un caracter normal
                    break;
            }
        }
                
        // return stack.pop();
        return null;
    }
}
