package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class DFA extends Automata {
    private Map<List<Integer>, Map<Symbol, List<Integer>>> transitionsTree; // Exclusivo para tree
    
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

    public DFA(Tree tree){ // Construir afd desde un arbol
        super();
        setSymbols(tree.getSymbols());
        
        transitionsTree = new HashMap<List<Integer>, Map<Symbol, List<Integer>>>();

        int counter = 0;
        this.stateInitial = new State(counter, Types.Initial);
        
        List<Integer> firstpos = tree.getFirstpos();

        System.out.println("====================================");
        for(int i : firstpos){
            System.out.print(i + " ");
        }
        System.out.println("\n====================================");

        transitionsTree.put(firstpos, new HashMap<>());
        counter++;

        Queue<List<Integer>> unmarked = new LinkedList<>();
        unmarked.add(firstpos);
        
        while(!unmarked.isEmpty()){
            List<Integer> state = unmarked.poll();
            for (Symbol s : symbols) {
                List<Integer> nextState = getNextState(state, s);
                if(!nextState.isEmpty() && !transitionsTree.containsKey(nextState)){
                    transitionsTree.put(nextState, new HashMap<>());
                    unmarked.add(nextState);
                    counter++;
                }
                if(!nextState.isEmpty()){
                    System.out.println("EJEM");
                    transitionsTree.get(state).put(s, nextState);
                }
            }
        }     
        
        // Verificar las transiciones
        for(Map.Entry<List<Integer>, Map<Symbol, List<Integer>>> transitionTree: transitionsTree.entrySet()){
            System.out.println("??????????????????????????????????????????");
            List<Integer> keyList = transitionTree.getKey();
            Map<Symbol, List<Integer>> valueMap = transitionTree.getValue();
            System.out.println("=> " + keyList.toString());
            for(Map.Entry<Symbol, List<Integer>> ja: valueMap.entrySet()){
                Symbol symbolJa = ja.getKey();
                List<Integer> valueList = ja.getValue();
                System.out.println(symbolJa.toString() + " and "  + valueList.toString());
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

    private List<Integer> getNextState(List<Integer> currentState, Symbol symbol){
        List<Integer> nextState = new ArrayList<>();
        Map<Symbol, List<Integer>> stateTransitions = transitionsTree.get(currentState);
        if (stateTransitions != null) {
            List<Integer> transitionsForSymbol = stateTransitions.get(symbol);
            if (transitionsForSymbol != null) {
                nextState.addAll(transitionsForSymbol);
            }
        }
        // for (Integer state : currentState) {
        // }
        return nextState;
    }

    @Override
    public boolean simulate(String w) {
        State s = this.stateInitial;

        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            s = move(s, new Symbol(c));
            if(s == null) return false;
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
