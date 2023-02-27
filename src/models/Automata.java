package models;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    // Atributos
    private State stateInitial;
    private State stateFinal;
    private List<State> states;
    private List<Transition> transitions;
    
    // Constructor
    public Automata(Symbol symbol, State stateInitial, State stateFinal){
        // Inicializar componentes
        this.stateInitial = stateInitial;
        this.stateFinal = stateFinal;
        states = new ArrayList<State>();
        transitions = new ArrayList<Transition>();
        // Agregar nueva data
        addFirstTransition(symbol, stateInitial, stateFinal);
    }

    // Getters
    public State getStateInitial() {
        return stateInitial;
    }

    public State getStateFinal() {
        return stateFinal;
    }

    public List<State> getStates() {
        return states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    // Setters
    public void setStateInitial(State stateInitial) {
        this.stateInitial = stateInitial;
    }

    public void setStateFinal(State stateFinal) {
        this.stateFinal = stateFinal;
    }

    // Metodos
    private void addFirstTransition(Symbol symbol, State stateInitial, State stateFinal){
        // Crear primera transicion
        Transition transition = new Transition(symbol, stateInitial, stateFinal);
        transitions.add(transition);
        // Almacenar todos los estados creados
        states.add(stateInitial);
        states.add(stateFinal);
    }

    public void addTransition(Transition transition){
        transitions.add(transition);
    }

    public void addState(State state){
        states.add(state);
    }

    public void concatenate(State state1, State stateFinal, Symbol symbol){
        this.stateFinal = stateFinal;
        states.remove(states.size() - 1);
        states.add(state1);
        states.add(stateFinal);
        transitions.get(transitions.size() - 1).setStateFinal(state1);
        Transition transition = new Transition(symbol, state1, stateFinal);
        transitions.add(transition);
    }

    public void concate(State stateChangue){
        
    }

    // ToString para ver el contenido del Automata
    @Override
    public String toString() {
        String information = "";
        information += "-----------------------------------------------\n";
        information += "Stado Inicial: " + this.stateInitial + "\n";
        information += "Stado Final: " + this.stateFinal + "\n";
        information += "Estados: \n";
        for (State state : states) {
            information += state.toString() + "\n";
        }
        information += "Transiciones: \n";
        for (Transition transition : transitions) {
            information += transition.toString();
        }
        information += "-----------------------------------------------\n";
        return information;
    }
}
