package models;

public class Symbol implements Comparable<Symbol>{
    private int id; // id = ASCII de C_id;
    private char cId;
    private String stringId;
    private String letValue;
    private boolean isTerminal = true;

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

    public Symbol(String stringId, int option){
        this.stringId = stringId;
        if(option == 0){ // Si es produccion entonces esta en mayusculas y no es un terminal
            this.stringId = Character.toUpperCase(stringId.charAt(0)) + "";
            isTerminal = false;
        }        
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

    public boolean getIsTerminal(){
        return isTerminal;
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
