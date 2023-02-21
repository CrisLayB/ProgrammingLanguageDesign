package models;

public class NFA extends Automata {

    public NFA(){
        super();
        entryStates = new Set() {
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

        outStates = new Set() {
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

        symbols = new Set() {
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
        stateInitial = null;
    }
    
    public Automata subsetConstruction() {
        return this;
    }
    
}
