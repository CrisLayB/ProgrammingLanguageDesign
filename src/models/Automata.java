package models;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    public List<State> entryStates;
    public List<State> outStates;
    public List<State> symbols;
    public State stateInitial;
    public List<Transition> transitions;

    public Automata() {

        stateInitial = null;
        transitions = new ArrayList<Transition>();
    }

    public Transition Transition(State e, State s) { // move(e, s)
        return null;
    }
}
