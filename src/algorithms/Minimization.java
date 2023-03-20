package algorithms;

import models.DFA;
import models.State;
import models.Types;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;

public class Minimization {
    public static DFA minimizingDFA(DFA dfa){
        DFA dfaMin = new DFA(new State(0, Types.Initial));

        List<Set<State>> partitions = new ArrayList<Set<State>>();
        Set<State> acceptingStates = new HashSet<State>();
        Set<State> nonAcceptingStates = new HashSet<State>();

        for (State state : dfa.getStates()) {
            
        }
        
        return dfa;
    }
}
