package models;

import java.util.ArrayList;
import java.util.List;
import middleware.Types;

public class Automata {
    protected List<State> states;
    protected List<State> finalStates;
    protected List<Symbol> symbols;
    protected State stateInitial;
    protected List<Transition> transitions;

    public Automata(State stateInitial) {
        states = new ArrayList<State>();
        finalStates = new ArrayList<State>();
        symbols = new ArrayList<Symbol>();
        this.stateInitial = stateInitial;
        transitions = new ArrayList<Transition>();
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        states.add(transition.getStateOrigin());
        finalStates.add(transition.getStateFinal());
        symbols.add(transition.getSymbol());
    }

    public State getStateInitial() {
        return stateInitial;
    }

    public Transition getTransition(){
        return transitions.get(0);
    }
    
    public List<Transition> allTransitions(){
        return transitions;
    }

    public List<State> Transition(State e, State s) { // move(e, s)
        return null;
    }
}
