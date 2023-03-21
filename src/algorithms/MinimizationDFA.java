package algorithms;

import models.DFA;
import models.State;
import models.Symbol;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class MinimizationDFA {
    public static DFA minimizingDFA(DFA dfa){
        DFA dfaMin = new DFA(dfa.getStateInitial());

        List<Set<State>> partitions = new ArrayList<Set<State>>();
        Set<State> acceptedStates = new HashSet<State>(dfa.getStatesFinal());
        Set<State> notAceptedStates = new HashSet<State>(dfa.getStates());
        notAceptedStates.removeAll(acceptedStates);
        partitions.add(acceptedStates);
        partitions.add(notAceptedStates);

        Queue<Set<State>> queue = new LinkedList<>(partitions);
        
         while (!queue.isEmpty()) {
            List<List<Set<State>>> L = new ArrayList<>();
            List<Set<State>> Ds = new ArrayList<>();
            Set<State> G = queue.poll();
            for (State s : G) {
                for (Symbol a : dfa.getSymbols()) {
                    State t = dfa.move(s, a);
                    for (Set<State> h : partitions) {
                        if(h.contains(t)){
                            Ds.add(h);
                        }
                    }
                    // Agregar Ds a la lista L
                    L.add(Ds);
                }
            }
        }

        return dfaMin;
    }
}
