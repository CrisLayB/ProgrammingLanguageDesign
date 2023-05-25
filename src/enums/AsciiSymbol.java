package enums;

/*
 * ( : 40
 * ) : 41
 * * : 42
 * | : 124
 * + : 43
 * ? : 63
 * # : 35
 * · : 
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
    Dot('·'),
    Epsilon('€'),
    Arrow('→'),
    SingleQuotee('\'');
        
    public final char c;
    public final int ascii;

    private AsciiSymbol(char c){
        this.c = c;
        this.ascii = (int)c;
    }
}
