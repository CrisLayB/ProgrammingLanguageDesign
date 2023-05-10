package models;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

public class NFA extends Automata {
    // Atributos
    private State stateFinal;
    private boolean forMegaAutomata = false;

    // Constructor
    public NFA(Symbol symbol, State stateInitial, State stateFinal) {
        // Inicializar componentes
        super(stateInitial);
        this.stateFinal = stateFinal;        
        // Agregar nueva transicion de una vez
        addFirstTransition(symbol, stateInitial, stateFinal);
    }

    public NFA(State stateInitial){
        super(stateInitial);
        forMegaAutomata = true;
    }

    public NFA(NFA nfaToCopy){        
        super();
        // configurar todos los estados
        for (Transition transition : nfaToCopy.transitions) {
            State stateOrigin = transition.getStateOrigin();
            State stateFinal = transition.getStateFinal();
            Symbol symbolTransition = transition.getSymbol();
            // Vamos a modificar la data por conveniencia
            int newNumberOrigin = Integer.parseInt(stateOrigin.getId()) + nfaToCopy.amountStates();
            int newNumberFinal = Integer.parseInt(stateFinal.getId()) + nfaToCopy.amountStates();
            // Crear los nuevos estados
            State newStateOrigin = new State(newNumberOrigin, stateOrigin.getType());
            State newStateFinal = new State(newNumberFinal, stateFinal.getType());
            transitions.add(new Transition(
                new Symbol(symbolTransition.getStringId()),
                newStateOrigin, 
                newStateFinal
            ));

            if(newStateOrigin.getType().num == 0){
                this.stateInitial = newStateOrigin;
            }
            
            if(newStateFinal.getType().num == 2){
                this.stateFinal = newStateFinal;
            }
        }
        // Agregar los estados y simbolos necesarios
        for (Transition transition : transitions) {
            if(!checkIfRepeat(transition.getStateOrigin())) addState(transition.getStateOrigin());
            if(!checkIfRepeat(transition.getStateOrigin())) addState(transition.getStateFinal());
            addSymbol(transition.getSymbol());
        }        
    }

    public NFA(State stateInitial, List<State> statesFinal, List<State> states, List<Transition> transitions){
        super(stateInitial, statesFinal, states, transitions);
        forMegaAutomata = true;
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
    public boolean simulate(String w) {
        Set<State> initialStateSet = new HashSet<State>();
        initialStateSet.add(getStateInitial());
        Set<State> S = eclousure(initialStateSet);
        
        for (int i = 0; i < w.length(); i++) {            
            Symbol c = new Symbol(w.charAt(i));
            S = eclousure(move(S, c));
        }
        
        if(S.contains(this.stateFinal)){
            return true;
        }

        return false;
    }

    public String[] simulateMega(String w){
        Set<State> initialStateSet = new HashSet<State>();
        initialStateSet.add(getStateInitial());
        Set<State> S = eclousure(initialStateSet);
        
        for (int i = 0; i < w.length(); i++) {            
            Symbol c = new Symbol(w.charAt(i)+"");
            S = eclousure(move(S, c));
        }

        // S = eclousure(S);

        if(Collections.disjoint(S, statesFinal)){
            // String temp = ""
            System.out.println("====== CONJUNTO STATES FINAL");
            for (State state : statesFinal) {
                System.out.println(state);
            }
            System.out.println("====== CONJUNTO S");
            for (State state : S) {
                System.out.println(state);
            }

            return new String[]{w, "JAJA"};
        }

        return new String[]{w, "ERROR LEXICO"};
    }

    public boolean simulateTest(String ascii){
        Set<State> initialStateSet = new HashSet<State>();
        initialStateSet.add(getStateInitial());
        Set<State> S = eclousure(initialStateSet);
        
        for (State s : S) {
            System.out.println(s.toString());
        }
        
        // Move
        Symbol c = new Symbol(ascii);
        Set<State> moved = move(S,c);

        System.out.println("+++++++++++++++++++++++++++");
        for (State s : moved) {
            System.out.println(s.toString());
        }
        
        S = eclousure(moved);

        S = eclousure(move(S, c));

        System.out.println("+++++++++++++++++++++++++++");
        for (State s : S) {
            System.out.println(s.toString());
        }

        if(S.contains(this.stateFinal)){
            return true;
        }
        
        return false;
    }

    public String[] identifyId(String wAscii){
        for (Transition transition : getTransitions()) {
            if(transition.getSymbol().getStringId().equals(wAscii)){
                return new String[]{wAscii, transition.getSymbol().getLetValue()};
            }
        }        
        return new String[]{wAscii, "ERROR LEXICO"};
    }

    @Override
    public String toString() {
        String information = "";
        information += "\n-----------------------------------------------\n";
        information += "AFN (NFA) Construido:\n";
        information += "Estado Inicial: " + this.stateInitial + "\n";
        if(forMegaAutomata == true){
            information += "Estados Finales:\n";
            for (State state : statesFinal) {
                information += state + " ";
            }
        }
        information += "\nEstado Final: " + this.stateFinal + "\n";
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
