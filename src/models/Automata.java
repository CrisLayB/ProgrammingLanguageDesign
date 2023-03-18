package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Automata {
    // Atributos
    protected State stateInitial;    
    protected List<State> states;
    protected List<Symbol> symbols;
    protected List<Transition> transitions;
    
    // Constructor
    public Automata(State stateInitial){ 
        this.stateInitial = stateInitial;
        states = new ArrayList<State>();
        symbols = new ArrayList<>();
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

    public List<Symbol> getSymbols(){
        return symbols;
    }

    // Setters
    public void setStateInitial(State stateInitial) {
        this.stateInitial = stateInitial;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    // Metodos
    protected void addFirstTransition(Symbol symbol, State stateInitial, State stateFinal){
        // Crear primera transicion
        Transition transition = new Transition(symbol, stateInitial, stateFinal);
        transitions.add(transition);
        // Almacenar todos los estados creados
        states.add(stateInitial);
        states.add(stateFinal);
        // Almacenar Simbolo
        addSymbol(symbol);
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

    public void addSymbol(Symbol newSymbol){
        if(!symbols.contains(newSymbol) && newSymbol.getId() != 949){ // 949 es para evitar epsilon
            symbols.add(newSymbol);
        }
    }

    public Set<State> move(State e, Symbol s){
        Set<State> nextStates = new HashSet<State>();

        for (Transition transition : transitions) {
            State stateOriginTransition = transition.getStateOrigin();
            Symbol symbolTransition = transition.getSymbol();
            if(stateOriginTransition.equals(e) && symbolTransition.equals(s)){
                nextStates.add(transition.getStateFinal());
            }
        }
        
        return nextStates;
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
