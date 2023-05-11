package models;

public class State {
    private String id; // id of this State
    private Types type;
    private String leafId;

    public State(int id, Types type) { // Normalmente usado en NFA
        this.id = id+"";
        this.type = type;
    }

    public State(String id, Types type){ // Normalmente usado en DFA
        this.id = id;
        this.type = type;
    }

    public State(String id, Types type, String leafId){
        this.id = id;
        this.type = type;
        this.leafId = leafId;
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

    public String getLeafId() {        
        return leafId;
    }

    public void defineLeafId(String leafId){
        this.leafId = leafId;
    }

    @Override
    public String toString() {
        return "-> Estado: " + id + " - " + type;
    }
}
