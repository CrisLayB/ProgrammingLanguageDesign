package algorithms;

import models.NFA;
import models.DFA;
import models.State;
import models.Types;
import models.Transition;
import models.Symbol;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;

public class DFAConstruction {
    
    public static DFA subsetConstruction(NFA nfa){ // Construccion de AFD desde AFN        
        int stateAsciiId = 65;
        
        // Vamos a obtener nuestros clousures
        Set<State> dStates = eclousure(nfa.getTransitions(), nfa.getStateInitial());
                        
        System.out.println("=======================");
        System.out.println("Seeee clousures:");
        System.out.println("-----------------------");
        for (State state : dStates) {
            System.out.println(state.toString());
        }
        System.out.println("=======================");
        
        return new DFA(new State(Character.toString((char)stateAsciiId), Types.Initial));
    }

    private static Set<State> eclousure(List<Transition> transitions, State initialState){ 
        Set<State> clousure = new HashSet<>();
        Stack<State> stack = new Stack<State>();

        stack.push(initialState);
        clousure.add(initialState);

        while(!stack.empty()){
            stack.pop();
            for (Transition transition : transitions) {
                Symbol symbol = transition.getSymbol();
                State stateOrigin = transition.getStateOrigin();
                State nextState = transition.getStateFinal();                
                if(!clousure.contains(nextState) && symbol.getId() == 949 && clousure.contains(stateOrigin)){
                    clousure.add(nextState);
                    stack.push(nextState);
                }
            }
        }

        return clousure;
    }

    private static Set<State> move(Set<State> states, Symbol symbol, NFA nfa){
        Set<State> nextStates = new HashSet<State>();

        for (State state : states) {
            for (Transition transition : nfa.getTransitions()) {
                Symbol transitionSymbol = transition.getSymbol();
                if(transitionSymbol != null && transitionSymbol.getId() == symbol.getId()){
                    nextStates.add(transition.getStateFinal());
                }
            }
        }
        
        return nextStates;
    }
    

    public static DFA directConstructionAFD(String r){ // Construccion de AFD desde expresion regular
        int stateAsciiId = 65;
        DFA dfa = new DFA(new State(Character.toString((char)stateAsciiId), Types.Initial));
        return dfa;
    }

    public static DFA minimizationAFD(DFA dfa){ // Minimizar AFD
        return dfa;
    }
    
}
