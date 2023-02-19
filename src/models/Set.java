package models;

import java.util.List;

public abstract class Set<E> {
    
    protected List<E> elements;

    abstract Set<E> intersection( Set<E> A);
    abstract Set<E> union( Set<E> A);
    abstract Set<E> difference( Set<E> A);
        
    abstract protected void addIteem(Set<E> object);
}
