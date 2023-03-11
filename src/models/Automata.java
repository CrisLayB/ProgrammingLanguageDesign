package models;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    // Atributos
    protected State stateInitial;    
    protected List<State> states;
    protected List<Transition> transitions;
    
    // Constructor
    public Automata(State stateInitial){ 
        this.stateInitial = stateInitial;
        states = new ArrayList<State>();
        transitions = new ArrayList<Transition>();
    }

    // Getters
    public State getStateInitial() {
        return stateInitial;
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

    // Metodos
    protected void addFirstTransition(Symbol symbol, State stateInitial, State stateFinal){
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
        if(states.contains(state)){
            states.remove(state);
        }
        states.add(state);
    }

    public Symbol getSymbol(int numTransition){
        Transition transition = transitions.get(numTransition);
        return transition.getSymbol();
    }

    // ToString para ver el contenido del Automata
    @Override
    public String toString() {
        String information = "";
        information += "-----------------------------------------------\n";
        information += "Automata Normal" + "\n";        
        information += "-----------------------------------------------\n";
        return information;
    }
}
