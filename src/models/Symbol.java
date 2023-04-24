package models;

public class Symbol implements Comparable<Symbol>{
    private int id; // id = ASCII de C_id;
    private char cId;

    public Symbol(char cId){
        this.id = (int)cId;
        this.cId = cId;
    }

    public Symbol(int id){
        this.id = id;
        this.cId = (char)id;
    }

    public int getId() {
        return id;
    }

    public char getcId() {
        return cId;
    }

    @Override
    public String toString() {
        return "" + cId;
    }

    @Override
    public int compareTo(Symbol other) {
        return Integer.compare(this.id, other.id);
    }
}
