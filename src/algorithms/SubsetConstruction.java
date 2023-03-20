package algorithms;

import models.NFA;
import models.PairData;
import models.State;
import models.Types;
import models.DFA;
import models.Symbol;
import models.Transition;

import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

public class SubsetConstruction {    
    public static DFA nfaToDfa(NFA nfa){
        DFA dfa = new DFA(new State(0, Types.Initial));
        int counter = 0;

        Set<PairData<Set<State>, Integer>> dStates = new HashSet<PairData<Set<State>, Integer>>();
        Set<State> s0 = new HashSet<>();
        s0.add(nfa.getStateInitial());
        PairData<Set<State>,Integer> initalDState = new PairData<Set<State>,Integer>(nfa.eclousure(s0), counter);
        dStates.add(initalDState);

        System.out.println("0 CHECK DSTATES: ");
        for (PairData<Set<State>,Integer> pairData : dStates) {
            Set<State> one = pairData.first;
            int two = pairData.second;
            System.out.println(one.toString() + " and " + two);
        }

        Stack<PairData<Set<State>, Integer>> unmarked = new Stack<>();
        unmarked.add(initalDState);
        
        while(!unmarked.isEmpty()){
            PairData<Set<State>,Integer> marked = unmarked.pop();
            Set<State> T = marked.first;
            int numT = marked.second;
            for (Symbol a : nfa.getSymbols()) { // Obtener todo el alfabeto
                Set<State> U = nfa.eclousure(nfa.move(T, a));
                PairData<Set<State>,Integer> newState = null;
                boolean result = dStateInDStates(U, dStates);
                if(U.size() != 0){
                    if(!result){
                        counter++;
                        newState = new PairData<Set<State>,Integer>(U, counter);
                        dStates.add(newState);
                        unmarked.add(newState);
                    }
                    else{
                        for (PairData<Set<State>, Integer> stateSet : dStates) {
                            Set<State> stateComp = stateSet.first;
                            if(stateComp.equals(U)){
                                newState = stateSet;                            
                            }
                        }
                    }
                    // Agregar nueva transicion
                    dfa.addTransition(new Transition(a, new State(numT, Types.Transition), new State(newState.second, Types.Transition)));
                }
            }
        }
        
        // System.out.println("CHECK DSTATES: ");
        // for (PairData<Set<State>,Integer> pairData : dStates) {
        //     Set<State> one = pairData.first;
        //     int two = pairData.second;
        //     System.out.println(one.toString() + " and " + two);
        // }
        
        return dfa;
    }
        
    private static boolean dStateInDStates(Set<State> U, Set<PairData<Set<State>, Integer>> dStates){
        for (PairData<Set<State>,Integer> pairData : dStates) {
            Set<State> dState = pairData.first;
            if(dState.size() == U.size()){
                if(dState.equals(U)){
                    return true;
                }
            }            
        }
        return false;
    }
}
