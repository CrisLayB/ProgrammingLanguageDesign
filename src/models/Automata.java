package models;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    public List<State> states;
    public List<State> finalStates;
    public List<Symbol> symbols;
    public State stateInitial;
    public List<Transition> transitions;

    public Automata() {
        states = new ArrayList<State>();
        finalStates = new ArrayList<State>();
        symbols = new ArrayList<Symbol>();
        stateInitial = null;
        transitions = new ArrayList<Transition>();
    }

    public List<State> Transition(State e, State s) { // move(e, s)
        return null;
    }
}
