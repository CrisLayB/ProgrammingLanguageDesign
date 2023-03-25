package algorithms;

import models.State;
import models.Types;
import models.BTreePrinter;
import models.DFA;
import models.Symbol;
import models.SyntaxTree;

import java.util.Stack;

import java.util.Set;

public class DirectDFA {
    public static DFA regularExpressionToDFA(String r){
        Stack<Symbol> symbolsStack = new Stack<Symbol>();
        for (int i = 0; i < r.length(); i++) {
            char c = r.charAt(i);
            symbolsStack.add(new Symbol(c));
        }
        // Se agrega . y # ya que se debe de extender la expresion
        symbolsStack.add(new Symbol('#'));
        symbolsStack.add(new Symbol('.'));

        // Se creara un arbol sintatico
        DFA dfa = new DFA(new State(1, Types.Initial));
        SyntaxTree syntaxTree = new SyntaxTree(symbolsStack);
        // Mostrar como quedo el arbol sintaxico
        BTreePrinter.printNode(syntaxTree.getRoot());
        
        return dfa;
    }
}
