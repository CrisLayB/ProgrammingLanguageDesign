package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SyntaxTree {
    private TreeNode<Symbol> root;
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
        Stack<TreeNode<Symbol>> stackTreeNodes = new Stack<TreeNode<Symbol>>();
        for (Symbol symbol : regexSymbols) {
            // * ---> EPSILON
            if(symbol.getId() == AsciiSymbol.Epsilon.ascii){
                stackTreeNodes.push(new TreeNode<>()); // Ingresar nullable
            }

            // * ---> PUNTO
            else if(symbol.getId() == AsciiSymbol.Dot.ascii){
                TreeNode<Symbol> right = stackTreeNodes.pop();
                TreeNode<Symbol> left = stackTreeNodes.pop();
                
                if(right.isNull() != true && left.isNull() != true){
                    stackTreeNodes.add(new TreeNode<Symbol>(symbol, left, right));
                }
            }

            // * ---> OR 
            else if( symbol.getId() == AsciiSymbol.Or.ascii){
                TreeNode<Symbol> right = stackTreeNodes.pop();
                TreeNode<Symbol> left = stackTreeNodes.pop();
                TreeNode<Symbol> node = (right.isNull() && left.isNull()) 
                    ? new TreeNode<Symbol>() 
                    : new TreeNode<Symbol>(symbol, left, right);
                stackTreeNodes.push(node);
            }

            // * ---> KLEENE
            else if( symbol.getId() == AsciiSymbol.Kleene.ascii ){
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, stackTreeNodes.pop(), null);

                if(!node.left.isNull()){
                    stackTreeNodes.push(node);
                }        
            }

            // * ---> PLUS

            // * ---> INTERROGATION

            // * ---> NORMAL SYMBOL
            else{ // Almacenar un symbolo normal
                if(symbol.getId() != AsciiSymbol.Numeral.ascii && !symbolExistsInAlphabet(symbol)){
                    symbols.add(symbol);
                }                    
                stackTreeNodes.add(new TreeNode<Symbol>(symbol, ++count));
            }
        }
        root = stackTreeNodes.pop();
    }
    
    // * ---> GETTERS
    public TreeNode<Symbol> getRoot() {
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

    // firstpos(node)

    // lastpos(node)

    // nullable(node)

    // followpos(position)

    // ! ================================================
    
    public Set<Integer> getFirstpos(TreeNode<Symbol> root){
        return null;
    }

    public boolean getNullable(TreeNode<Symbol> root){
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
