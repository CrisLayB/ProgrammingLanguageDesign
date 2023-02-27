package models;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.List;
import middleware.Types;

public class Automata {
    protected List<State> states;
    protected List<Symbol> symbols;
    protected State stateInitial;
    protected State stateFinal;
    protected List<Transition> transitions;

    public Automata(State stateInitial) {
        states = new ArrayList<State>();
        symbols = new ArrayList<Symbol>();
        this.stateInitial = stateInitial;
        transitions = new ArrayList<Transition>();
    }

    public Automata(State stateInitial, State stateFinal) {
        states = new ArrayList<State>();
        symbols = new ArrayList<Symbol>();
        this.stateInitial = stateInitial;
        this.stateFinal = stateFinal;
        transitions = new ArrayList<Transition>();
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
        states.add(transition.getStateOrigin());
        symbols.add(transition.getSymbol());
    }

    public <E> void revList(List<E> list){
        if (list.size() <= 1 || list == null)
            return;
 
        E value = list.remove(0);

        revList(list);
 
        list.add(value);
    }
    
    public void changueTransition(State sFinal, State sInitial){
        for (Transition transition : transitions) {
            if(transition.getStateFinal().getId() == sFinal.getId()){
                transition.getStateFinal().setId(sInitial.getId());
                transition.getStateFinal().setType(Types.Transition);                
            }
        }
    }
    
    public State getStateInitial() {
        return stateInitial;
    }

    public State getStateFinal(){
        return stateFinal;
    }

    public void setStateFinal(State stateFinal) {
        this.stateFinal = stateFinal;
    }

    public void ChangueStatesTransition(){
        for (Transition transition : transitions) {
            transition.getStateOrigin().setType(Types.Transition);
            transition.getStateFinal().setType(Types.Transition);
        }
    }

    public void ChagueFinalStatesTransition(){
        for(Transition transition : transitions){
            transition.getStateFinal().setType(Types.Transition);
        }
    }
    
    public List<Transition> allTransitions(){
        return transitions;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol getSymbol(int num){
        return symbols.get(num);
    }

    public Transition getInitialTransition(){ // Inicial = 0
        for (Transition transition : transitions) {
            State sInitial = transition.getStateOrigin();
            Types sType = sInitial.getType();
            if(sType.num == 0){
                return transition;
            }
        }
        return null;
    }

    public Transition getFinalTransition(){ // Final = 2
        for (Transition transition : transitions) {
            State sFinal = transition.getStateFinal();
            Types sType = sFinal.getType();
            if(sType.num == 2){
                return transition;
            }
        }
        return null;
    }
}
