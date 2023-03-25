package models;

/*
 * ( : 40
 * ) : 41
 * * : 42
 * | : 124
 * + : 43
 * ? : 63
 * # : 35
 * . : 46
 * E : 69
 */
public enum AsciiSymbol {
    ParenthesisLeft('('),
    ParenthesisRight(')'),
    Kleene('*'),
    Or('|'),
    Plus('+'),
    Interrogation('?'),
    Numeral('#'),
    Dot('.'),
    Epsilon('E');
        
    public final char c;
    public final int ascii;

    private AsciiSymbol(char c){
        this.c = c;
        this.ascii = (int)c;
    }
}
