package models;

public class State {
    private String id; // id of this State
    private Types type;
    private boolean marked;

    public State(int id, Types type) { // Normalmente usado en NFA
        this.id = id+"";
        this.type = type;
        marked = false;
    }

    public State(String id, Types type){ // Normalmente usado en DFA
        this.id = id;
        this.type = type;
        marked = false;
    }

    public String getId() {
        return id;
    }    

    public void setId(String id) {
        this.id = id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public boolean getMarked(){
        return marked;
    }

    public void markState(){
        marked = true;
    }

    @Override
    public String toString() {
        return "-> Estado: " + id + " - " + type;
    }
}
