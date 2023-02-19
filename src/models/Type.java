package models;

public enum Type {
    Initial(0),
    Transition(1),
    Final(2);

    public final int num;

    private Type(int num){
        this.num = num;
    }    
}
