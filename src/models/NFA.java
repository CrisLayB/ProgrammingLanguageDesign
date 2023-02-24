package models;

public class NFA extends Automata {

    public NFA() {
        super();
    }

    public NFA(State state) {
        super(state);
    }

    public Automata subsetConstruction() {
        return this;
    }

}
