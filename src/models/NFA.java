package models;

public class NFA extends Automata {
    // Atributos
    private State stateFinal;

    // Constructor
    public NFA(Symbol symbol, State stateInitial, State stateFinal) {
        // Inicializar componentes
        super(stateInitial);
        this.stateFinal = stateFinal;        
        // Agregar nueva transicion de una vez
        addFirstTransition(symbol, stateInitial, stateFinal);
    }

    // Getters
    public State getStateFinal() {
        return stateFinal;
    }

    // Setters
    public void setStateFinal(State stateFinal) {
        this.stateFinal = stateFinal;
    }

    // Mas Metodos exclusivamente para NFA
    public void convertAllStatesToTransitions(){
        for (State state : states) {
            state.setType(Types.Transition);
        }
    }

    @Override
    public String toString() {
        String information = "";
        information += "\n-----------------------------------------------\n";
        information += "AFN (NFA) Construido:\n";
        information += "Estado Inicial: " + this.stateInitial + "\n";
        information += "Estado Final: " + this.stateFinal + "\n";
        information += "Estados: \n";
        for (State state : states) {
            information += state.toString() + "\n";
        }
        information += "Simbolos (Alfabeto): \n";
        for (Symbol symbol : symbols) {
            information += symbol.toString() + "\n";
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
