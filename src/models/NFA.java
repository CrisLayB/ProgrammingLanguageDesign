package models;

public class NFA extends Automata {
    public NFA(State state) {
        super(state);
    }

    public NFA(State stateInitial, State stateFinal){
        super(stateInitial, stateFinal);
    }

    public Automata subsetConstruction() {
        return this;
    }

}
