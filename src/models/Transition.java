package models;

import enums.Types;

public class Transition {
    // Atributos
    private State stateOrigin;
    private State stateFinal;
    private Symbol symbol;

    // Constructor
    public Transition(Symbol symbol, State sOrigin, State sFinal) {
        stateOrigin = sOrigin;
        stateFinal = sFinal;
        this.symbol = symbol;
    }

    // Getters
    public State getStateOrigin() {
        return stateOrigin;
    }

    public State getStateFinal() {
        return stateFinal;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    // Setters
    public void setStateOrigin(State stateOrigin) {
        this.stateOrigin = stateOrigin;
    }

    public void setStateFinal(State stateFinal) {
        this.stateFinal = stateFinal;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    // Metodos adicionales
    public void changeTypeStateOrigin(Types type){
        this.stateOrigin.setType(type);
    }

    public void changeTypeStateFinal(Types type){
        this.stateFinal.setType(type);
    }
    
    // Metodos de mostrar informacion
    @Override
    public String toString() {
        return stateOrigin.getId() + " -> " + stateFinal.getId() + "[label=\""+ symbol.getStringId() + "\"]\n";
    }

    public String toStringMega() {
        return stateOrigin.getId() + " -> " + stateFinal.getId() + "[label=\""+ symbol.getStringId() + "\"]\n";
    }

    public String detailInformation(){
        String information = "";
        information += stateOrigin.getId() + " | " + stateOrigin.getType() + "\n";
        information += symbol.getcId() + "\n";        
        information += stateFinal.getId() + " | " + stateFinal.getType() + "\n";
        return information;
    }
}
