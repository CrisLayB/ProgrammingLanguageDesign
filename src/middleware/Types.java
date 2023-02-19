package middleware;

public enum Types {
    Initial(0),
    Transition(1),
    Final(2);

    public final int num;

    private Types(int num){
        this.num = num;
    }    
}
