package controllers;

import java.util.Stack;

import models.NFA;
// import models.State;
import models.Symbol;
// import middleware.Types;
import models.Transition;

public class ThompsonAlgorithm {

    public static int countStates = 1;

    public static NFA constructNFA(Symbol[] symbols) {
        Stack<NFA> stack = new Stack<NFA>();

        for (int i = 0; i < symbols.length; i++) {
            Symbol symbol = symbols[i];

            switch (symbol.getId()) {
                case 43: // '+'
                    NFA nfaForPlus = stack.pop();

                    break;

                case 124: // '|'
                    //
                    NFA nfa2 = stack.pop();
                    NFA nfa1 = stack.pop();

                    // Crear un nuevo automata
                    NFA nfaForOr = new NFA();
                    break;

                case 42: // '*'
                    NFA nfaForClean = stack.pop();
                    break;

                case 63: // '?'
                    NFA nfaPopForQuestion = new NFA();
                    break;

                default: // NORMAL CHARACTER
                    Transition transition = new Transition(symbol);
                    NFA nfa = new NFA();
                    nfa.saveSymbols(symbol);
                    nfa.addTransition(transition);
                    stack.add(nfa);
                    break;
            }
        }

        if (stack.size() == 1) // Quiere decir que todo esta en orden
            return stack.pop();

        return null; // Si no hay solo uyn elemento quiere decir que el automata es invalido
    }
}
