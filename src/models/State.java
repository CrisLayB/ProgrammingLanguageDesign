package models;

public class State {
    private int id; // id of this State
    private Types type;

    public State(int id, Types type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "-> Estado: " + id + " - " + type;
    }
}
