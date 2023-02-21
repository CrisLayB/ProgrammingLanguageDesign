package models;

import middleware.Types;

public class State {
    private int id; // char id
    private Types type;    
    private Set statesAFN;

    public State(int id, Types type){
        this.id = id;
        this.type = type;
        statesAFN = new Set() {
            @Override
            Set intersection(Set A) {
                return null;
            }

            @Override
            Set union(Set A) {
                return null;
            }

            @Override
            Set difference(Set A) {
                return null;
            }
        };
    }

    public int getId() {
        return id;
    }

    public Types getType() {
        return type;
    }

    public Set getStatesAFN() {
        return statesAFN;
    }

    public void setType(Types type) {
        this.type = type;
    }
}
