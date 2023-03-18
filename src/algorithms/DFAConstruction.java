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
        Set<State> s0 = eclousure(nfa.getTransitions(), nfa.getStateInitial());
                     
        System.out.println("=======================");
        System.out.println("Seeee clousures:");
        System.out.println("-----------------------");
        for (State state : s0) {
            System.out.println(state.toString());
        }
        System.out.println("=======================");

        Set<Set<State>> dStates = new HashSet<Set<State>>();
        dStates.add(s0);

        Stack<State> unmarked = new Stack<State>();

        while(!unmarked.isEmpty()){
            
        }
                
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

    private static Set<State> eclousure(Set<State> moveStates, List<Transition> transitions){
        Set<State> eclosureStates = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.addAll(moveStates);
        eclosureStates.addAll(moveStates);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (Transition transition : transitions) {
                if (transition.getStateOrigin().equals(state) && transition.getSymbol().getcId() == 'E') {
                    State nextState = transition.getStateFinal();
                    if (!eclosureStates.contains(nextState)) {
                        eclosureStates.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }

        return eclosureStates;
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
