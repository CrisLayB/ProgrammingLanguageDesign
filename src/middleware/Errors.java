package middleware;

public enum Errors {
    InvalidExpression("\u001B[30m" + "ERROR: Esta expresion es invalida");

    public final String error;

    private Errors(String error){
        this.error = error;
    }    
}
