package controllers;

import java.util.Stack;

import models.NFA;
import models.State;
import models.Symbol;
import middleware.Types;
import models.Transition;

public class ThompsonAlgorithm {

    public static int countStates = 0;

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
                    stack.add(plus(nfaForPlus));
                    break;

                case 124: // '|'
                    NFA nfa2 = stack.pop();
                    NFA nfa1 = stack.pop();
                    stack.add(or(nfa1, nfa2));
                    break;

                case 42: // '*'
                    NFA nfaForClean = stack.pop();
                    stack.add(kleene(nfaForClean));
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
                    NFA nfa = new NFA(transition.getStateOrigin(), transition.getStateFinal());
                    nfa.addTransition(transition);
                    stack.add(nfa);
                    System.out.println("NORMAL: " + transition.toString());
                    break;
            }
        }

        NFA check = stack.peek();

        System.out.println("------------------------");
        System.out.println("FINAL TRANSITIONS OF UNIQUE NFA");
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
        NFA nfaPlus = kleene(nfa);
        countStates++;
        State state = new State(countStates, Types.Final);
        // System.out.println("JAJA CHECK:" + state.getId());        
        return nfaPlus;
    }

    private static NFA or(NFA nfaUp, NFA nfaDown) {
        // Crear dos estados nuevos
        State stateInitial = new State(countStates, Types.Transition);
        countStates++;
        State stateFinal = new State(countStates, Types.Transition);

        // Transiciones principales para el or
        Transition transitionUp = nfaUp.getInitialTransition();
        Transition transitionDown = nfaDown.getFinalTransition();

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
        NFA nfaUnion = new NFA(stateInitial, stateFinal);
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
        System.out.println("JAJA");
        // Crear dos estados nuevos
        State stateInitial = new State(countStates, Types.Transition);
        countStates++;
        State stateFinal = new State(countStates, Types.Transition);

        // Crear las nuevas transiciones
        Transition transitionInverse = new Transition(new Symbol((int) 'E', 'E'), nfa.getStateFinal(), nfa.getStateInitial());
        Transition transitionConnection = new Transition(new Symbol((int) 'E', 'E'), stateInitial, stateFinal); // OJO
        Transition transitionConnectNFAOrigin = new Transition(new Symbol((int) 'E', 'E'), stateInitial, nfa.getStateInitial());
        Transition transitionConnectNFAFinal = new Transition(new Symbol((int) 'E', 'E'), nfa.getStateFinal(), stateFinal); // OJO
        
        NFA nfaKleene = new NFA(stateInitial, stateFinal);
        nfaKleene.addTransition(nfa.getInitialTransition()); // si
        nfaKleene.addTransition(transitionInverse); // si
        nfaKleene.addTransition(transitionConnection);
        nfaKleene.addTransition(transitionConnectNFAOrigin); // si
        nfaKleene.addTransition(transitionConnectNFAFinal);

        return nfaKleene;
    }

    private static NFA concatenate(NFA nfa) {
        return null;
    }

    private static NFA question(NFA nfa) {
        return null;
    }

}
