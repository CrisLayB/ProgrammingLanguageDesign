package models;

import java.util.List;
import java.util.ArrayList;

public abstract class Set {
    
    protected List<State> elements;

    public Set(){
        elements = new ArrayList<State>();
    }

    abstract Set intersection( Set A);
    abstract Set union( Set A);
    abstract Set difference( Set A);
        
    public void addIteem(State element){
        elements.add(element);
    }
}
