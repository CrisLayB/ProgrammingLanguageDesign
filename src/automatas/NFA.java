package automatas;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import enums.AsciiSymbol;
import enums.Types;
import models.State;
import models.Symbol;
import models.Transition;

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
    public Set<State> eclousure(Set<State> moveStates){
        Set<State> eclosureStates = new HashSet<State>();
        Stack<State> stack = new Stack<>();
        stack.addAll(moveStates);
        eclosureStates.addAll(moveStates);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (Transition transition : transitions) {                
                if (sameIdState(transition.getStateOrigin(), state) && transition.getSymbol().getStringId().equals(AsciiSymbol.Epsilon.c+"")) {
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

    private boolean sameIdState(State s1, State s2){
        return (s1.getId().equals(s2.getId()));
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
            Symbol c = new Symbol((int)w.charAt(i)+"");
            S = eclousure(move(S, c));
        }

        // Vamos a verificar si uno de los estados de S esta dentro de los estados finales
        for (State stateS : S) {
            for (State stateF : statesFinal) {
                String sId = stateS.getId();
                String fId = stateF.getId();
                if(sId.equals(fId)) 
                    return new String[]{w, stateF.getLeafId()};
            }
        }

        // Esto quiere decir que no se retorno un estado de aceptacion
        return new String[]{w, "ERROR LEXICO"};
    }

    public List<String[]> simulate(List<Integer> asccis){
        List<String[]> tokens = new ArrayList<String[]>();

        Set<State> initialStateSet = new HashSet<State>();
        initialStateSet.add(getStateInitial());
        Set<State> S = eclousure(initialStateSet);
        
        String lexema = "";
        State acceptedState = null;
        
        for (int i = 0; i < asccis.size(); i++) {
            int number = asccis.get(i);
            Symbol c = new Symbol(number+"");
            S = eclousure(move(S, c));
            
            if(!S.isEmpty()){
                for (State state : S) { // Guardar estado final
                    if(state.getType() == Types.Final){
                        acceptedState = state;
                        break;
                    }
                }
            }

            if(S.isEmpty()){                
                // Si state no es null entonces vamos a guardar el estado de aceptacion
                if(acceptedState != null){
                    State tempAcceptedState = getStateWithLeaf(acceptedState); // Detectar token final con su hoja
                    tokens.add(new String[]{lexema, tempAcceptedState.getLeafId()});
                    i--; // Regresar al char anterior para verificarlo
                }
                else{ // Detectar error lexico cuando no hay estado de aceptacion
                    lexema += (char)number;
                    tokens.add(new String[]{lexema, "ERROR LEXICO"});
                }
                // Vamos a reiniciar S
                S = eclousure(initialStateSet);
                lexema = "";
                acceptedState = null;
            }            
            else{
                lexema += (char)number;
            }            
        }
        
        return tokens;
    }

    private State getStateWithLeaf(State acceptedState){
        for (State state : statesFinal) {
            if(state.getId().equals(acceptedState.getId())) return state;
        }
        return acceptedState;
    }

    public void defineLeafIdFinalState(String leafId){
        stateFinal.defineLeafId(leafId);
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
