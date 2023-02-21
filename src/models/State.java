package models;

import java.util.List;
import java.util.ArrayList;

import middleware.Types;

public class State {
    private int id; // char id
    private Types type;
    private List<State> statesAFN;

    public State(int id, Types type) {
        this.id = id;
        this.type = type;
        statesAFN = new ArrayList<State>();
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

    public List<State> getStatesAFN() {
        return statesAFN;
    }

    public void addState(State state) {
        statesAFN.add(state);
    }
}
