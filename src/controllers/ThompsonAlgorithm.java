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
                    stack.add(question(nfaPopForQuestion));                    
                    break;

                case 46: // '.'
                    NFA nfa2Dot = stack.pop();
                    NFA nfa1Dot = stack.pop();
                    stack.add(concatenate(nfa1Dot, nfa2Dot));
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
            State stateO = t.getStateOrigin();
            State stateF = t.getStateFinal();
            System.out.println(t.toString() + " | " + stateO.getType() + " & " + stateF.getType());
        }
        System.out.println("------------------------");
        
        if (stack.size() == 1) // Quiere decir que todo esta en orden
            return stack.pop();

        return null; // Si no hay solo uyn elemento quiere decir que el automata es invalido
    }

    private static NFA plus(NFA nfa) {
        NFA nfaPlus = kleene(nfa);    
        nfaPlus.ChagueFinalStatesTransition();    
        Symbol symbol = nfaPlus.getSymbol(0);
        countStates++;
        State state = new State(countStates, Types.Final);
        Transition transition = new Transition(symbol, nfaPlus.getStateFinal(), state);
        nfaPlus.addTransition(transition);
        return nfaPlus;
    }

    private static NFA or(NFA nfaUp, NFA nfaDown) {
        // Crear dos estados nuevos
        State stateInitial = new State(countStates, Types.Initial);
        countStates++;
        State stateFinal = new State(countStates, Types.Final);
        
        // Transiciones principales para el or
        Transition transitionUp = nfaUp.getInitialTransition();
        Transition transitionDown = nfaDown.getFinalTransition();

        nfaUp.ChangueStatesTransition();
        nfaDown.ChangueStatesTransition();

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
        // Crear dos estados nuevos
        countStates++;
        State stateInitial = new State(countStates, Types.Initial);
        countStates++;
        State stateFinal = new State(countStates, Types.Final);
        // Crear las nuevas transiciones
        nfa.ChangueStatesTransition();
        Transition transitionInverse = new Transition(new Symbol((int) 'E', 'E'), nfa.getStateFinal(), nfa.getStateInitial());
        Transition transitionConnection = new Transition(new Symbol((int) 'E', 'E'), stateInitial, stateFinal);
        Transition transitionConnectNFAOrigin = new Transition(new Symbol((int) 'E', 'E'), stateInitial, nfa.getStateInitial());
        Transition transitionConnectNFAFinal = new Transition(new Symbol((int) 'E', 'E'), nfa.getStateFinal(), stateFinal);
        
        NFA nfaKleene = new NFA(stateInitial, stateFinal);
        for (Transition transition : nfa.allTransitions()) {
            nfaKleene.addTransition(transition);
        }
        nfaKleene.addTransition(transitionInverse);
        nfaKleene.addTransition(transitionConnection);
        nfaKleene.addTransition(transitionConnectNFAOrigin);
        nfaKleene.addTransition(transitionConnectNFAFinal);
        return nfaKleene;
    }

    private static NFA concatenate(NFA nfa1, NFA nfa2) {
        for (Transition transition : nfa1.allTransitions()) {
            transition.getStateFinal().setType(Types.Transition);
        }
        
        for (Transition transition : nfa2.allTransitions()) {
            transition.getStateOrigin().setType(Types.Transition);
            nfa1.addTransition(transition);
        }

        return nfa1;
    }

    private static NFA question(NFA nfa) {
        nfa.ChangueStatesTransition();
        Transition transitionDown = new Transition(new Symbol((int)'E', 'E'));
        transitionDown.changueType(Types.Transition, Types.Transition);
        nfa.addTransition(transitionDown);

        State origin = new State(countStates, Types.Initial);
        countStates++;
        State sFinal = new State(countStates, Types.Final);
        
        Transition transitionOriginUp = new Transition(new Symbol((int)'E', 'E'), origin, nfa.getStateInitial());
        Transition transitionOriginDown = new Transition(new Symbol((int)'E', 'E'), origin, transitionDown.getStateOrigin());

        Transition transitionFinalUp = new Transition(new Symbol((int)'E', 'E'), nfa.getStateFinal(), sFinal);
        Transition transitionFinalDown = new Transition(new Symbol((int)'E', 'E'), transitionDown.getStateFinal(), sFinal);

        nfa.addTransition(transitionOriginUp);
        nfa.addTransition(transitionOriginDown);
        nfa.addTransition(transitionFinalUp);
        nfa.addTransition(transitionFinalDown);
        return nfa;
    }
}
