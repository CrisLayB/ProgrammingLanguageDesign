package models;

public class SimpleTransition {
    // Atributos
    private String stateOrigin, stateFinal, symbol;

    // Constructor
    public SimpleTransition(String stateOrigin, String stateFinal, String symbol){
        this.stateOrigin = stateOrigin;
        this.stateFinal = stateFinal;
        this.symbol = symbol;
    }

    // Metodos
    @Override
    public String toString() {
        return stateOrigin + " -> " + stateFinal + "[label=\""+ symbol + "\"]\n";
    }
}
