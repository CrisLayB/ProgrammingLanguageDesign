package models;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    public Set entryStates; // Start States
    public Set outStates; // Final states
    public Set symbols;
    public State stateInitial;
    public List<Transition> transitions;

    public Automata(){
        transitions = new ArrayList<Transition>();
    }

    public Set Transition(State e, State s){ // move(e, s)
        return null;
    }
}
