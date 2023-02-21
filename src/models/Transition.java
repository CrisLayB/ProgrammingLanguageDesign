package models;

public class Transition {
    public State stateOrigin;
    public State stateFinal;
    public Symbol symbol;

    public Transition(State sOrigin, State sFinal, Symbol symbol){
        stateOrigin = sOrigin;
        stateFinal = sFinal;
        this.symbol = symbol;
    }
}
