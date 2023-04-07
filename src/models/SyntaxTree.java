package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SyntaxTree {
    private Node<Symbol> root;
    private Stack<Symbol> regexSymbols;
    private int count;
    private List<Symbol> symbols;
    
    // * ---> CONSTRUCTOR
    public SyntaxTree(Stack<Symbol> regexSymbols){
        this.regexSymbols = regexSymbols;
        count = 0;
        symbols = new ArrayList<Symbol>();
        createSyntaxTree();
    }

    private void createSyntaxTree(){
        Stack<Node<Symbol>> stack = new Stack<Node<Symbol>>();
        for (Symbol symbol : regexSymbols) {
            // * ---> EPSILON
            if(symbol.getId() == AsciiSymbol.Epsilon.ascii){
                stack.push(new Node<>()); // Ingresar nullable
            }
            // * ---> OR, PUNTO
            else if(symbol.getId() == AsciiSymbol.Dot.ascii || symbol.getId() == AsciiSymbol.Or.ascii){
                Node<Symbol> right = stack.pop();
                Node<Symbol> left = stack.pop();                
                stack.add(new Node<Symbol>(symbol, left, right));
            }
            // * ---> KLEENE, PLUS, INTERROGATION
            else if( symbol.getId() == AsciiSymbol.Kleene.ascii ){
                Node<Symbol> node = new Node<Symbol>(symbol, stack.pop(), null);
                stack.add(node);
            }
            // * ---> NORMAL SYMBOL
            else{ // Almacenar un symbolo normal
                if(symbol.getId() != AsciiSymbol.Numeral.ascii && !symbolExistsInAlphabet(symbol)){
                    symbols.add(symbol);
                }                    
                stack.add(new Node<Symbol>(symbol, ++count));
            }
        }
        root = stack.pop();
    }
    
    // * ---> GETTERS
    public Node<Symbol> getRoot() {
        return root;
    }

    public List<Symbol> getSymbols(){
        return symbols;
    }

    // * ---> METODOS
    private boolean symbolExistsInAlphabet(Symbol symbolCheck){
        for (Symbol symbol : symbols) {
            if(symbolCheck.getId() == symbol.getId()){
                return true;
            }
        }
        return false;
    }
    
    public Set<Integer> getFirstpos(Node<Symbol> root){
        return null;
    }

    public boolean getNullable(Node<Symbol> root){
        if(root == null) return false;
        return false;
    }

    public Set<Integer> getFollowpos(int pos){
        Set<Integer> followpos = new HashSet<>();
        for (Integer integer : followpos) {
            if(integer == pos){
                followpos.add(integer);
            }
        }
        return followpos;
    }

    public char getVal(int position){
        Symbol symb = regexSymbols.get(position);
        return symb.getcId();
    }
}
