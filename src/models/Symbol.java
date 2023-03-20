package models;

public class Symbol {
    private int id; // id = ASCII de C_id;
    private char cId;

    public Symbol(char cId){
        this.id = (int)cId;
        this.cId = cId;
    }

    public int getId() {
        return id;
    }

    public char getcId() {
        return cId;
    }

    @Override
    public String toString() {
        return "Simbolo: " + cId;
    }
}
