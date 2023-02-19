package middleware;

public enum Errors {
    InvalidExpression("\u001B[31m" + "ERROR: Esta expresion regular insertada es invalida" + "\u001B[0m");

    public final String error;

    private Errors(String error){
        this.error = error;
    }    
}
