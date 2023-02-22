package models;

import java.util.ArrayList;
import java.util.List;
import middleware.Types;

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
        stateInitial = new State(0, Types.Initial);
        transitions = new ArrayList<Transition>();
    }

    public void saveSymbols(Symbol symbol) {
        symbols.add(symbol);
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        states.add(transition.getStateOrigin());
        finalStates.add(transition.getStateFinal());
    }

    public List<State> Transition(State e, State s) { // move(e, s)
        return null;
    }
}
