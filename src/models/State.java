package models;

import java.util.List;
import java.util.ArrayList;

import middleware.Types;

public class State {
    private int id; // char id
    private Types type;
    private List<State> prevStatesAFN;
    private List<State> nextStatesAFN;

    public State(int id, Types type) {
        this.id = id;
        this.type = type;
        prevStatesAFN = new ArrayList<State>();
        nextStatesAFN = new ArrayList<State>();
    }

    public int getId() {
        return id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public List<State> getPrevStatesAFN() {
        return prevStatesAFN;
    }

    public List<State> getNextStatesAFN() {
        return nextStatesAFN;
    }

    public void addPrevState(State state) {
        prevStatesAFN.add(state);
    }

    public void addNextState(State state) {
        nextStatesAFN.add(state);
    }
}
