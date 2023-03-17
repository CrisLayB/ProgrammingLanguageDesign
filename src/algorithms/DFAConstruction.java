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
                     
        Set<Set<State>> dStates = new HashSet<Set<State>>();
        dStates.add(s0);

        Set<Set<State>>unmarked = new HashSet<Set<State>>();
        unmarked.add(s0);

        // while(!unmarked.isEmpty()){
        //     Set<State> T = unmarked.iterator().next();
        //     unmarked.remove(T);

        //     // marcar T
        //     for(State state: T){
        //         state.markState();
        //     }

        //     // Revisar cada simbolo dentro del alfabeto del automata
        //     for (Symbol symbol : nfa.getSymbols()) {
        //         Set<State> U = eclosure(nfa.move(T, symbol, nfa));
        //         // Set<State> U = ec
        //         if (!dStates.contains(U)) {
        //             // agregar U como un estado no marcado en Dstates
        //             Dstates.add(U);
        //             unmarked.add(U);
        //         }
        //     }
        // }

        System.out.println("=======================");
        System.out.println("Seeee clousures:");
        System.out.println("-----------------------");
        for (State state : s0) {
            System.out.println(state.toString());
        }
        System.out.println("=======================");
        
        return new DFA(new State(Character.toString((char)stateAsciiId), Types.Initial));
    }

    private static Set<State> eclousure(List<State> states, Symbol symbol, NFA nfa){
        Set<State> move = new HashSet<>();
        for (State state : states) {
            for (Transition t : nfa.getTransitions()) {
                if (t.getSymbol() != null && t.getSymbol().equals(symbol)) {
                    move.add(t.getStateFinal());
                }
            }
        }
        return null;
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



    public static DFA directConstructionAFD(String r){ // Construccion de AFD desde expresion regular
        int stateAsciiId = 65;
        DFA dfa = new DFA(new State(Character.toString((char)stateAsciiId), Types.Initial));
        return dfa;
    }

    public static DFA minimizationAFD(DFA dfa){ // Minimizar AFD
        return dfa;
    }
    
}
