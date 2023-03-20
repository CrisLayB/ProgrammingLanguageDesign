package algorithms;

import models.State;
import models.Types;
import models.DFA;
import models.Symbol;

import java.util.Stack;

public class DirectDFA {
    public static DFA regularExpressionToDFA(String r){
        DFA dfa = new DFA(new State(1, Types.Initial));

        Stack<Symbol> symbolsStack = new Stack<Symbol>();
        for (int i = 0; i < r.length(); i++) {
            char c = r.charAt(i);
            symbolsStack.add(new Symbol(c));
        }
        // Se agrega . y # ya que se debe de extender la expresion
        symbolsStack.add(new Symbol('.'));
        symbolsStack.add(new Symbol('#'));

        // Se creara un arbol sintatico

        
        return dfa;
    }
}
