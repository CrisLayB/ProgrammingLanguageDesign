package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

abstract public class Automata {
    // Atributos
    protected State stateInitial;    
    protected List<State> states;
    protected List<Symbol> symbols;
    protected List<Transition> transitions;
    
    // Constructor
    public Automata(){
        states = new ArrayList<State>();
        symbols = new ArrayList<>();
        transitions = new ArrayList<Transition>();
    }
    
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
        if(newSymbol.getId() == 949 || newSymbol.getId() == 69){
            return;
        }
        
        if(symbols.size() == 0){
            symbols.add(newSymbol);
            return;
        }

        for (Symbol symbol : symbols) {
            int symbolNum = symbol.getId();

            if(symbolNum == newSymbol.getId()){
                return;
            }
        }

        symbols.add(newSymbol);
    }
    

    public Set<State> eclousure(Set<State> moveStates){
        Set<State> eclosureStates = new HashSet<State>();
        Stack<State> stack = new Stack<>();
        stack.addAll(moveStates);
        eclosureStates.addAll(moveStates);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (Transition transition : transitions) {
                if (transition.getStateOrigin().equals(state) && transition.getSymbol().getcId() == 'E') {
                    // if (transition.getStateOrigin().equals(state)) {
                    State nextState = transition.getStateFinal();
                    if (!eclosureStates.contains(nextState)) {
                        eclosureStates.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }

        return eclosureStates;
    }

    public State move(State e, Symbol s){
        for (Transition transition : transitions) {
            State originState = transition.getStateOrigin();
            Symbol tSymbol = transition.getSymbol();
            if(originState.getId().equals(e.getId()) && tSymbol.getcId() == s.getcId()){
                return transition.getStateFinal();
            }
        }
        return null;
    }
    
    public Set<State> move(Set<State> e, Symbol s){
        Set<State> nextStates = new HashSet<State>();

        for (State state : e) {
            for (Transition transition : transitions) {
                State stateOriginTransition = transition.getStateOrigin();
                // Se revisara si el id del estado origen de la transicion es igual al id de uno de los estados de e
                if(stateOriginTransition.getId().equals(state.getId())){
                    Symbol symbolTransition = transition.getSymbol();
                    State stateFinalTransition = transition.getStateFinal();
                    // Si dado caso los ids coinciden y el estado final de la transicion no esta ingresada
                    // Entonces se agregara el estado final de la transicion a siguientes estados
                    if(symbolTransition.getId() == s.getId()){
                        if(!nextStates.contains(stateFinalTransition)){
                            nextStates.add(stateFinalTransition);
                        }
                    }
                }
            }
        }
        
        return nextStates;
    }

    // TODO: Metodos abstractos

    public abstract boolean simulate(String w);

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
