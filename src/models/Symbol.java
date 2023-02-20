package models;

public class Symbol {
    private int id; // id = ASCII de C_id;
    private char cId;

    public Symbol(int id, char cId){
        this.id = id;
        this.cId = cId;
    }

    public int getId() {
        return id;
    }

    public char getcId() {
        return cId;
    }
}
