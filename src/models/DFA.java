package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

public class DFA extends Automata {
    // Atributos
    private List<State> statesFinal;

    // Constructor
    public DFA(State stateInitial){
        super(stateInitial);
        statesFinal = new ArrayList<>();
        states.add(stateInitial);
    }

    public DFA(NFA nfa){ // Aplicando algoritmo de Subconjuntos
        super();
        setSymbols(nfa.getSymbols());
        statesFinal = new ArrayList<>();
        int counter = 0;
        this.stateInitial = new State(counter, Types.Initial);

        Set<PairData<Set<State>, Integer>> dStates = new HashSet<PairData<Set<State>, Integer>>();
        Set<State> s0 = new HashSet<>();
        s0.add(nfa.getStateInitial());
        PairData<Set<State>,Integer> initalDState = new PairData<Set<State>,Integer>(nfa.eclousure(s0), counter);
        dStates.add(initalDState);

        Stack<PairData<Set<State>, Integer>> unmarked = new Stack<>();
        unmarked.add(initalDState);
        
        while(!unmarked.isEmpty()){
            PairData<Set<State>,Integer> marked = unmarked.pop();
            Set<State> T = marked.first;
            int numT = marked.second;
            for (Symbol a : nfa.getSymbols()) { // Obtener todo el alfabeto
                Set<State> U = nfa.eclousure(nfa.move(T, a));
                PairData<Set<State>,Integer> newState = null;                
                if(U.size() != 0){
                    if(!dStateInDStates(U, dStates)){
                        counter++;
                        newState = new PairData<Set<State>,Integer>(U, counter);
                        dStates.add(newState);
                        unmarked.add(newState);                        
                    }
                    else{
                        if(U.contains(nfa.getStateFinal())){ // Agregar los estados finales
                            AddFinalState(new State(counter, Types.Final));
                        }

                        for (PairData<Set<State>, Integer> stateSet : dStates) {
                            Set<State> stateComp = stateSet.first;
                            if(stateComp.equals(U)){
                                newState = stateSet;                            
                            }
                        }
                    }
                    // Agregar nueva transicion
                    State state1 = new State(numT, Types.Transition);
                    State state2 = new State(newState.second, Types.Transition);
                    addState(state2);
                    addState(state2);
                    addTransition(new Transition(a, state1, state2));
                }
            }
        }
    }

    public List<State> getStatesFinal(){
        return statesFinal;
    }

    // Metodos
    private boolean dStateInDStates(Set<State> U, Set<PairData<Set<State>, Integer>> dStates){
        for (PairData<Set<State>,Integer> pairData : dStates) {
            Set<State> dState = pairData.first;
            if(dState.size() == U.size()){
                if(dState.equals(U)){
                    return true;
                }
            }            
        }
        return false;
    }
    
    public void AddFinalState(State state){
        for (State stateFinal : statesFinal) {
            String idStateFinal = stateFinal.getId();
            String idState = state.getId();
            if(idStateFinal.equals(idState)){
                return;
            }
        }

        statesFinal.add(state);
    }

    @Override
    public void addState(State state){
        for (State stateAFD : states) {
            String idState = state.getId();
            String idStateAFD = stateAFD.getId();
            if(idState.equals(idStateAFD)){
                return;
            }
        }
        states.add(state);
    }

    @Override
    public boolean simulate(String w) {
        State s = this.stateInitial;

        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            s = move(s, new Symbol(c));
        }

        for (State state : statesFinal) { // Revisar si "s" esta dentro de estados finales
            String checkStateFinal = state.getId();
            if(s.getId().equals(checkStateFinal)){
                return true;
            }
        }
        
        return false;
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
        information += "Simbolos (Alfabeto): \n";
        for(Symbol symbol : symbols){
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
