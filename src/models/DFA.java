package models;

import java.util.ArrayList;
import java.util.List;

public class DFA extends Automata {
    // Atributos
    private List<State> statesFinal;

    // Constructor
    public DFA(State stateInitial){
        super(stateInitial);
        statesFinal = new ArrayList<>();
        states.add(stateInitial);
    }

    // Metodos
    public void AddFinalState(State state){
        statesFinal.add(state);
    }
    
    @Override
    public String toString() {
        String information = "";
        information += "\n-----------------------------------------------\n";
        information += "AFD (DFA) Construido:\n";
        information += "Estado Inicial: " + this.stateInitial + "\n";
        information += "Estados Finales:\n";
        for(State state : statesFinal){
            information += state.toString() + "\n";
        }
        information += "Estados: \n";
        for (State state : states) {
            information += state.toString() + "\n";
        }
        information += "Cantidad Estados: " + states.size() + "\n";
        information += "Transiciones: \n";
        for (Transition transition : transitions) {
            information += transition.toString();
        }
        information += "-----------------------------------------------\n";
        return information;
    }
}
