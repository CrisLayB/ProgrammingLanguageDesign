package controllers;

import java.util.Stack;

import models.Symbol;
import models.NFA;

public class ThompsonAlgorithm {
    public static NFA constructNFA(Symbol[] symbols) {
        Stack<NFA> stack = new Stack<NFA>();

        for (int i = 0; i < symbols.length; i++) {
            int idSymbol = symbols[i].getId();

            switch (idSymbol) {
                case 43: // '+' // PLUS OPERATOR
                    break;

                case 124: // '|' // OR OPERATOR
                    break;

                case 42: // '*' // CLEAN
                    break;

                case 63: // '?' // CONCATENATION
                    break;

                default: // Sunpogamos que es un caracter normal
                    break;
            }
        }

        if (stack.size() == 1) // Quiere decir que todo esta en orden
            return stack.pop();

        return null; // Si no hay solo uyn elemento quiere decir que el automata es invalido
    }
}
