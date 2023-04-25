package algorithms;

import java.util.Stack;

import models.NFA;
import models.State;
import models.Symbol;
import models.Transition;
import models.Types;
import models.AsciiSymbol;

public class ThompsonAlgorithm {
    private static int num = -1;
    private static char epsilon = 'E';
    
    public static NFA constructNFA(String postfixExpression) {
        Stack<NFA> stack = new Stack<NFA>();
        
        for (int i = 0; i < postfixExpression.length(); i++) {
            char c = postfixExpression.charAt(i);
            int cAscii = (int)c;

            if(cAscii == AsciiSymbol.Kleene.ascii){                
                NFA nfaKleen = stack.pop();
                stack.add(kleene(nfaKleen));
            }
            else if(cAscii == AsciiSymbol.Plus.ascii){
                NFA nfaOriginal = stack.pop();             
                NFA nfaToKleene = new NFA(nfaOriginal);
                num += nfaToKleene.amountStates(); // Get original number id
                stack.add(concatenate(nfaOriginal, kleene(nfaToKleene)));
            }
            else if(cAscii == AsciiSymbol.Dot.ascii){
                NFA nfaDot2 = stack.pop();
                NFA nfaDot1 = stack.pop();
                stack.add(concatenate(nfaDot1, nfaDot2));  
            }
            else if(cAscii == AsciiSymbol.Interrogation.ascii){
                NFA nfaQuestion = stack.pop();
                stack.add(question(nfaQuestion));
            }
            else if(cAscii == AsciiSymbol.Or.ascii){
                NFA nfaOr2 = stack.pop();
                NFA nfaOr1 = stack.pop();
                stack.add(or(nfaOr1, nfaOr2));
            }
            else{
                stack.add(createNFA(c));
            } 
        }
        
        return stack.pop();
    }

    private static NFA createNFA(char c){
        Symbol symbol = new Symbol(c);
        num++;
        State state1 = new State(num, Types.Initial);
        num++;
        State state2 = new State(num, Types.Final);
        // Crear el nuevo NFA
        NFA nfaSymbol = new NFA(symbol, state1, state2);
        nfaSymbol.addSymbol(symbol);
        return nfaSymbol;
    }

    private static NFA concatenate(NFA nfa1, NFA nfa2){
        // Se copiara toda la data para tener toda la informacion del nfa2
        State tempState = nfa1.getStateFinal();
        State tempFinalState = nfa2.getStateFinal();
        nfa1.getStateFinal().setType(Types.Transition);
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            nfa1.addTransition(transition);
            nfa1.addState(transition.getStateOrigin());
            nfa1.addState(transition.getStateFinal());      
            nfa1.addSymbol(transition.getSymbol());
        }        
        nfa1.setStateFinal(tempFinalState);
        Transition transition = new Transition(new Symbol(epsilon), tempState, nfa2.getStateInitial());
        nfa1.addTransition(transition);
        return nfa1;
    }

    private static NFA kleene(NFA nfa){
        nfa.getStateInitial().setType(Types.Transition);
        nfa.getStateFinal().setType(Types.Transition);
        // Crear la transicion que conecta con el estado final ε inicial        
        Symbol epsilonSymbol = new Symbol(epsilon);
        Transition transitionBetween = new Transition(epsilonSymbol, nfa.getStateFinal(), nfa.getStateInitial());
        nfa.addTransition(transitionBetween);        
        // Crear los nuevos estados origen y finales
        num++;
        State newStateOrigin = new State(num, Types.Initial);
        num++;
        State newStateFinal = new State(num, Types.Final);

        // Ahora tocara que crear tres nuevas transiciones
        Transition transitionNewOrigin = new Transition(epsilonSymbol, newStateOrigin, newStateFinal);
        Transition transitionToOldOrigin = new Transition(epsilonSymbol, newStateOrigin, nfa.getStateInitial());
        Transition transitionToOldFinal = new Transition(epsilonSymbol, nfa.getStateFinal(), newStateFinal);

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
            nfa1.addSymbol(transition.getSymbol());   
        }        

        // Crear nuevo estado origen y final
        num++;
        State stateOrigin = new State(num, Types.Initial);
        num++;
        State stateFinal = new State(num, Types.Final);
        
        // Crear nuevas transiciones
        Transition transitionOriginUp = new Transition(new Symbol(epsilon), stateOrigin, nfa1.getStateInitial());
        Transition transitionOriginDown = new Transition(new Symbol(epsilon), stateOrigin, nfa2.getStateInitial());

        Transition transitionFinalUp = new Transition(new Symbol(epsilon), nfa1.getStateFinal(), stateFinal);
        Transition transitionFinalDown = new Transition(new Symbol(epsilon), nfa2.getStateFinal(), stateFinal);
        
        // Agregar nuevas transiciones
        nfa1.addTransition(transitionOriginUp);
        nfa1.addTransition(transitionOriginDown);
        nfa1.addTransition(transitionFinalUp);
        nfa1.addTransition(transitionFinalDown);
        
        // Actualizar informacion
        nfa1.setStateInitial(stateOrigin);
        nfa1.setStateFinal(stateFinal);

        nfa1.convertAllStatesToTransitions();

        // Agregar estados del or
        nfa1.addState(stateOrigin);
        nfa1.addState(stateFinal);

        return nfa1;
    }

    public static NFA question(NFA nfa1){
        NFA nfa2 = createNFA(epsilon);
        return or(nfa1, nfa2);
    }
}
