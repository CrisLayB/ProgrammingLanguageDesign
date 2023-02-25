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

    public Automata(State stateInitial, State stateFinal) {
        states = new ArrayList<State>();
        finalStates = new ArrayList<State>();
        symbols = new ArrayList<Symbol>();
        this.stateInitial = stateInitial;
        transitions = new ArrayList<Transition>();
        finalStates.add(stateFinal);
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

    public State getStateFinal(){
        return finalStates.get(0);
    }
    
    public List<Transition> allTransitions(){
        return transitions;
    }

    public Transition getInitialTransition(){ // Inicial = 0
        for (Transition transition : transitions) {
            State sInitial = transition.getStateOrigin();
            Types sType = sInitial.getType();
            if(sType.num == 0){
                return transition;
            }
        }
        return null;
    }

    public Transition getFinalTransition(){ // Final = 2
        for (Transition transition : transitions) {
            State sFinal = transition.getStateFinal();
            Types sType = sFinal.getType();
            if(sType.num == 2){
                return transition;
            }
        }
        return null;
    }

    public List<State> Transition(State e, State s) { // move(e, s)
        return null;
    }
}
