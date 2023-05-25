package models;

public class Symbol implements Comparable<Symbol>{
    private int id; // id = ASCII de C_id;
    private char cId;
    private String stringId;
    private String letValue;
    private boolean isToken = false;
    private boolean isProduction = false;

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
        if(option == 0){ // Detectar si es produccion
            this.stringId = Character.toUpperCase(stringId.charAt(0)) + "";
            isProduction = true;
        }
        // Detectar si es token
        if(option == 1) isToken = true; 
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

    public boolean getIsProduction(){
        return isProduction;
    }

    public boolean getIsToken(){
        return isToken;
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
