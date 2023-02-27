package controllers;

import java.util.Stack;

import models.NFA;
import models.State;
import models.Symbol;
import middleware.Types;
import models.Transition;

public class ThompsonAlgorithm {
    public static int num = -1;
    
    public static NFA constructNFA(String postfixExpression) {
        Stack<NFA> stack = new Stack<NFA>();
        // int num = -1;
        
        for (int i = 0; i < postfixExpression.length(); i++) {
            char c = postfixExpression.charAt(i);
            int cAscii = (int)c;

            switch (cAscii) {
                case 42: // '*'
                    NFA nfaKleen = stack.pop();
                    stack.add(kleene(nfaKleen));
                    break;

                case 43: // '+'
                    
                    break;

                case 46: // '.'
                    NFA nfaDot2 = stack.pop();
                    NFA nfaDot1 = stack.pop();
                    stack.add(concatenate(nfaDot1, nfaDot2));                    
                    break;

                case 63: // '?'
                    
                    break;

                case 124: // '|'
                    
                    break;
                
                default: // Crear nueva transicion
                    Symbol symbol = new Symbol(cAscii, c);
                    num++;
                    State state1 = new State(num, Types.Initial);
                    num++;
                    State state2 = new State(num, Types.Final);
                    // Crear el nuevo NFA
                    NFA nfaSymbol = new NFA(symbol, state1, state2);
                    stack.add(nfaSymbol);
                    break;
            }
            
        }
        
        return stack.pop();
    }

    private static NFA concatenate(NFA nfa1, NFA nfa2){
        // System.out.println("ANTES:");
        // System.out.println(nfa1.toString());
        // Se copiara toda la data para tener toda la informacion del nfa2
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            // System.out.println(transition.detailInformation());
            nfa1.concatenate(transition.getStateOrigin(), transition.getStateFinal(), transition.getSymbol());
        }        
        // System.out.println("DESPUES:");
        // System.out.println(nfa1.toString());
        return nfa1;
    }

    private static NFA kleene(NFA nfa){
        // Crear nuevas transiciones    
        // System.out.println("K ANTES:");
        // System.out.println(nfa.toString());
        // -------------------------------------
        nfa.getStateInitial().setType(Types.Transition);
        nfa.getStateFinal().setType(Types.Transition);
        // Crear la transicion que conecta con el estado final e inicial        
        Symbol epsilon = new Symbol((int)'E', 'E');
        Transition transitionBetween = new Transition(epsilon, nfa.getStateFinal(), nfa.getStateInitial());
        nfa.addTransition(transitionBetween);        
        // Crear los nuevos estados origen y finales
        num++;
        State newStateOrigin = new State(num, Types.Initial);
        num++;
        State newStateFinal = new State(num, Types.Final);

        // Ahora tocara que crear tres nuevas transiciones
        Transition transitionNewOrigin = new Transition(epsilon, newStateOrigin, newStateFinal);
        Transition transitionToOldOrigin = new Transition(epsilon, newStateOrigin, nfa.getStateInitial());
        Transition transitionToOldFinal = new Transition(epsilon, nfa.getStateFinal(), newStateFinal);

        // Actualizar toda la informacion
        nfa.addTransition(transitionNewOrigin);
        nfa.addTransition(transitionToOldOrigin);
        nfa.addTransition(transitionToOldFinal);
        // Actualizar los nuevos estados origen
        nfa.setStateInitial(newStateOrigin);
        nfa.setStateFinal(newStateFinal);
        // Agregar los estaods en la lista
        nfa.addState(newStateOrigin);
        nfa.addState(newStateFinal);
        // -------------------------------------
        // System.out.println("K DESPUES:");
        // System.out.println(nfa.toString());
        return nfa;
    }
}
