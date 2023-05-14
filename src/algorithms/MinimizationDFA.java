package algorithms;

import models.State;
import models.Symbol;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import automatas.DFA;

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
        List<List<Set<State>>> L = new ArrayList<>();
        
        while(!queue.isEmpty()){
            Set<State> newPartition = queue.poll();
            
            for (Symbol symbol : dfa.getSymbols()) {
                List<Set<State>> Ds = new ArrayList<>();
                Set<State> t = dfa.move(newPartition, symbol);
                for (Set<State> h : queue) {
                    if(t.equals(h)){
                        Ds.add(h);
                    }
                }
                L.add(Ds);
            }                                
        }

        return dfaMin;
    }
}
