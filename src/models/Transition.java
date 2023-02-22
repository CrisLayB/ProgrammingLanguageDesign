package models;

import middleware.Types;

import controllers.ThompsonAlgorithm;

public class Transition {
    private State stateOrigin;
    private State stateFinal;
    private Symbol symbol;

    // Create a normal Transition
    public Transition(Symbol symbol) {
        this.symbol = symbol;
        stateOrigin = new State(ThompsonAlgorithm.countStates, Types.Transition);
        ThompsonAlgorithm.countStates++;
        stateFinal = new State(ThompsonAlgorithm.countStates, Types.Transition);
        ThompsonAlgorithm.countStates++;
    }

    // Concatenar
    public Transition(Symbol symbol, State sOrigin, State sFinal) {
        this.symbol = symbol;
        stateOrigin = sOrigin;
        stateFinal = sFinal;
    }

    public State getStateOrigin() {
        return stateOrigin;
    }

    public State getStateFinal() {
        return stateFinal;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return stateOrigin.getId() + " - " + symbol.getcId() + " - " + stateFinal.getId();
    }
}
