package models;

import middleware.Types;

public class State<E> {
    public int id;
    public Types type;
    public Set<E> statesAFN;
}
