package algorithms;

import models.NFA;
import models.DFA;
import models.State;
import models.Types;
import models.Transition;
import models.Symbol;
import models.PairForDTran;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DFAConstruction {
    
    public static DFA subsetConstruction(NFA nfa){ // Construccion de AFD desde AFN        
        int stateAsciiId = 65;
        
        // Vamos a obtener nuestros clousures iniciales
        Set<State> s0 = eclousure(nfa.getTransitions(), nfa.getStateInitial());
        for (State state : s0) {
            System.out.println("+++++> " + state.toString());
        }
                     
        Set<Set<State>> dStates = new HashSet<>();
        dStates.add(s0);

        Stack<Set<State>> unmarked = new Stack<>();
        unmarked.push(s0);

        // HashMap<Symbol, Set<State>> dTran = new HashMap<>();
        Map<PairForDTran<Set<State>, Symbol>, Set<State>> dTran = new HashMap<PairForDTran<Set<State>, Symbol>, Set<State>>();
        
        while(!unmarked.empty()){
            Set<State> T = unmarked.pop();
            for (Symbol a : nfa.getSymbols()) {
                // Set<State> U = eclousure(T, nfa.getTransitions());
                Set<State> U = eclousure(nfa.move(T, a), nfa.getTransitions());
                if(!dStates.contains(U)){
                    dStates.add(U);
                    // unmarked.push(U);
                }                
                dTran.put(new PairForDTran<Set<State>, Symbol>(T, a), U);
            }
        }

        System.out.println("===///////////////////////////////");
        for (Set<State> set : dStates) {
            System.out.println("===========================");
            for (State s : set) {
                System.out.println(s.toString());
            }
        }
        System.out.println("============================");
        System.out.println("VER RESULTADOS DE DTRAN: ");
        for(Map.Entry<PairForDTran<Set<State>, Symbol>, Set<State>> result : dTran.entrySet()){
            PairForDTran<Set<State>, Symbol> key = result.getKey();
            Set<State> statesDTrain = key.first;
            Symbol symbolDTrain = key.second;
            Set<State> value = result.getValue();
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("--> " + symbolDTrain.toString());
            for (State s1 : statesDTrain) {
                System.out.println(s1.toString());
            }
            System.out.println("------------------------------");
            for (State s1 : value) {
                System.out.println(s1.toString());
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        }
        System.out.println("============================");

                
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
                if(!clousure.contains(nextState) && symbol.getId() == 69 && clousure.contains(stateOrigin)){
                    clousure.add(nextState);
                    stack.push(nextState);
                }
            }
        }

        return clousure;
    }

    private static Set<State> eclousure(Set<State> moveStates, List<Transition> transitionsNFA){
        Set<State> eclosureStates = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.addAll(moveStates);
        eclosureStates.addAll(moveStates);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (Transition transition : transitionsNFA) {
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
