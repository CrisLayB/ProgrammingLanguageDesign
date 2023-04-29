package models;

public class Symbol implements Comparable<Symbol>{
    private int id; // id = ASCII de C_id;
    private char cId;
    private String stringId;
    private String letValue;

    public Symbol(char cId){
        this.id = (int)cId;
        this.cId = cId;
    }

    public Symbol(int id){
        this.id = id;
        this.cId = (char)id;
    }

    public Symbol(String stringId){
        this.stringId = stringId;
        this.letValue = null;
    }

    public Symbol(String stringId, String letValue){
        this.stringId = stringId;
        this.letValue = letValue;
    }

    public int getId() {
        return id;
    }

    public char getcId() {
        return cId;
    }

    public String getStringId() {
        return stringId;
    }

    public String getLetValue() {
        return letValue;
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
