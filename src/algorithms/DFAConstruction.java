package algorithms;

import models.NFA;
import models.DFA;
import models.State;
import models.Types;
import models.Transition;
import models.Symbol;
import models.PairData;

import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class DFAConstruction {
    
    public static DFA subsetConstruction(NFA nfa){ // Construccion de AFD desde AFN        
        int stateNum = 1;
        DFA dfa = new DFA(new State(stateNum, Types.Initial));

        Map<Set<State>, State> dtran = new HashMap<Set<State>, State>();
        Set<State> dStates = eclousure(nfa.getTransitions(), nfa.getStateInitial());
        System.out.println("DStates =========================");
        for (State state : dStates) {
            System.out.println(state.toString());
        }
        System.out.println("===================================");
        Queue<Set<State>> unmarkedStates = new LinkedList<Set<State>>();
        unmarkedStates.add(dStates);
        stateNum++;
        dtran.put(dStates, new State(stateNum, Types.Initial));

        while(!unmarkedStates.isEmpty()){
            Set<State> T = unmarkedStates.remove();
            // dtran.put(T, new State(stateNum, Types.Initial));
            for (Symbol a : nfa.getSymbols()) {
                Set<State> U = eclousure(nfa.move(T, a), nfa.getTransitions());
                if(!dtran.containsKey(U)){
                    unmarkedStates.add(U);
                    stateNum++;
                    dtran.put(U, new State(stateNum, Types.Initial));
                }
                // Transition transition = new Transition(a, dtran.get(T), dtran.get(U));
                // dfa.addTransition(transition);
            }
        }

        for(Map.Entry<Set<State>, State> result : dtran.entrySet()){
            System.out.println("+++++++++++++++++++++++++++++++++++++");
            Set<State> statesDTran = result.getKey();
            State stateDTran = result.getValue();
            System.out.println("-----> " + stateDTran.toString());
            for (State set : statesDTran) {
                System.out.println(set.toString());
            }
        }
        
        return dfa;
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

    public static DFA directConstructionAFD(String r){ // Construccion de AFD desde expresion regular
        int stateAsciiId = 65;
        DFA dfa = new DFA(new State(Character.toString((char)stateAsciiId), Types.Initial));
        return dfa;
    }

    public static DFA minimizationAFD(DFA dfa){ // Minimizar AFD
        return dfa;
    }
    
}
