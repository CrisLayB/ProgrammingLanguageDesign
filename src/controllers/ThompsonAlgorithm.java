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
                    NFA nfaPlus = stack.pop();
                    Transition transitionPlus = new Transition(new Symbol((int)'E', 'E'), nfaPlus.getStateFinal(), nfaPlus.getStateInitial());
                    nfaPlus.addTransition(transitionPlus);
                    stack.add(nfaPlus);
                    break;

                case 46: // '.'
                    NFA nfaDot2 = stack.pop();
                    NFA nfaDot1 = stack.pop();
                    stack.add(concatenate(nfaDot1, nfaDot2));                    
                    break;

                case 63: // '?'
                    NFA nfaQuestion = stack.pop();
                    stack.add(question(nfaQuestion));
                    break;

                case 124: // '|'
                    NFA nfaOr2 = stack.pop();
                    NFA nfaOr1 = stack.pop();
                    stack.add(or(nfaOr1, nfaOr2));
                    break;
                
                default: // Crear nueva transicion
                    stack.add(createNFA(cAscii, c));
                    break;
            }
            
        }
        
        return stack.pop();
    }

    private static NFA createNFA(int cAscii, char c){
        Symbol symbol = new Symbol(cAscii, c);
        num++;
        State state1 = new State(num, Types.Initial);
        num++;
        State state2 = new State(num, Types.Final);
        // Crear el nuevo NFA
        NFA nfaSymbol = new NFA(symbol, state1, state2);
        return nfaSymbol;
    }

    private static NFA concatenate(NFA nfa1, NFA nfa2){
        // Se copiara toda la data para tener toda la informacion del nfa2
        State tempState = nfa1.getStateFinal();
        nfa1.getStateFinal().setType(Types.Transition);
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            nfa1.addTransition(transition);
            nfa1.addState(transition.getStateOrigin());
            nfa1.addState(transition.getStateFinal());            
            nfa1.setStateFinal(transition.getStateFinal());
        }        
        Transition transition = new Transition(new Symbol((int)'E', 'E'), tempState, nfa2.getStateInitial());
        nfa1.addTransition(transition);
        return nfa1;
    }

    private static NFA kleene(NFA nfa){
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
        return nfa;
    }

    public static NFA or(NFA nfa1, NFA nfa2){
        nfa1.getStateFinal().setType(Types.Transition);
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            nfa1.addTransition(transition);
            nfa1.addState(transition.getStateOrigin());
            nfa1.addState(transition.getStateFinal());            
        }        

        // Crear nuevo estado origen y final
        num++;
        State stateOrigin = new State(num, Types.Initial);
        num++;
        State stateFinal = new State(num, Types.Final);
        
        // Crear nuevas transiciones
        Transition transitionOriginUp = new Transition(new Symbol((int)'E', 'E'), stateOrigin, nfa1.getStateInitial());
        Transition transitionOriginDown = new Transition(new Symbol((int)'E', 'E'), stateOrigin, nfa2.getStateInitial());

        Transition transitionFinalUp = new Transition(new Symbol((int)'E', 'E'), nfa1.getStateFinal(), stateFinal);
        Transition transitionFinalDown = new Transition(new Symbol((int)'E', 'E'), nfa2.getStateFinal(), stateFinal);
        
        // Agregar nuevas transiciones
        nfa1.addTransition(transitionOriginUp);
        nfa1.addTransition(transitionOriginDown);
        nfa1.addTransition(transitionFinalUp);
        nfa1.addTransition(transitionFinalDown);
        
        // Actualizar informacion
        nfa1.setStateInitial(stateOrigin);
        nfa1.setStateFinal(stateFinal);
        return nfa1;
    }

    public static NFA question(NFA nfa1){
        NFA nfa2 = createNFA((int)'E', 'E');
        return or(nfa1, nfa2);
    }
}
