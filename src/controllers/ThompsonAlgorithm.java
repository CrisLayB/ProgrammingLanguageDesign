package controllers;

import java.util.Stack;

import models.NFA;
import models.State;
import models.Symbol;
import middleware.Types;
import models.Transition;

public class ThompsonAlgorithm {

    public static int countStates = 1;

    public static NFA constructNFA(String postfixExpression) {
        // Generar todos los simbolos desde la expression postfix creada
        Symbol[] symbols = GenerateSymbols.getSymbols(postfixExpression);
        
        // Stack que nos ayudara con el algoritmo de thompson
        Stack<NFA> stack = new Stack<NFA>();
        for (int i = 0; i < symbols.length; i++) {
            Symbol symbol = symbols[i];

            switch (symbol.getId()) {
                case 43: // '+'
                    NFA nfaForPlus = stack.pop();
                    plus(nfaForPlus);
                    break;

                case 124: // '|'
                    NFA nfa2 = stack.pop();
                    NFA nfa1 = stack.pop();
                    stack.add(or(nfa1, nfa2));
                    break;

                case 42: // '*'
                    // NFA nfaForClean = stack.pop();
                    // kleene(nfaForClean);
                    break;

                case 63: // '?'
                    NFA nfaPopForQuestion = stack.pop();
                    question(nfaPopForQuestion);
                    break;

                case 46: // '.'
                    concatenate(null);
                    break;

                default: // NORMAL CHARACTER
                    Transition transition = new Transition(symbol);
                    NFA nfa = new NFA(transition.getStateOrigin());
                    nfa.addTransition(transition);
                    stack.add(nfa);
                    System.out.println("NORMAL: " + nfa.getStateInitial().toString());
                    break;
            }
        }

        NFA check = stack.peek();

        System.out.println("------------------------");
        for (Transition t : check.allTransitions()) {
            System.out.println(t.toString());
        }
        System.out.println("------------------------");
        
        if (stack.size() == 1) // Quiere decir que todo esta en orden
            return stack.pop();

        return null; // Si no hay solo uyn elemento quiere decir que el automata es invalido
    }

    private static NFA plus(NFA nfa) {
        return null;
    }

    private static NFA or(NFA nfaUp, NFA nfaDown) {
        // Crear dos estados nuevos
        State stateInitial = new State(countStates, Types.Initial);
        State stateFinal = new State(countStates, Types.Final);

        // Transiciones principales para el or
        Transition transitionUp = nfaUp.getTransition();
        Transition transitionDown = nfaDown.getTransition();

        // Crear nuevas transiciones
        Transition transitionUpIninitial = new Transition(new Symbol((int) 'E', 'E'), stateInitial,
                transitionUp.getStateOrigin());
        Transition transitionDownIninitial = new Transition(new Symbol((int) 'E', 'E'), stateInitial,
                transitionDown.getStateOrigin());

        Transition transitionUpFinal = new Transition(new Symbol((int) 'E', 'E'), transitionUp.getStateFinal(),
                stateFinal);
        Transition transitionDownFinal = new Transition(new Symbol((int) 'E', 'E'), transitionDown.getStateFinal(),
                stateFinal);

        // Crear un nuevo nfa
        NFA nfaUnion = new NFA(stateInitial);
        // Agregar las transiciones al nfa
        nfaUnion.addTransition(transitionUp);
        nfaUnion.addTransition(transitionDown);
        nfaUnion.addTransition(transitionUpIninitial);
        nfaUnion.addTransition(transitionDownIninitial);
        nfaUnion.addTransition(transitionUpFinal);
        nfaUnion.addTransition(transitionDownFinal);
        return nfaUnion;
    }

    private static NFA kleene(NFA nfa) {
        return null;
    }

    private static NFA concatenate(NFA nfa) {
        return null;
    }

    private static NFA question(NFA nfa) {
        return null;
    }

}
