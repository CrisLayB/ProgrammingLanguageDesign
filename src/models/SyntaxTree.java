package models;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class SyntaxTree {
    private TreeNode<Symbol> root;
    private Stack<Symbol> regexSymbols;
    private int count;
    
    public SyntaxTree(Stack<Symbol> symbols){
        regexSymbols = symbols;
        count = 0;
        createSyntaxTree();
    }

    private void createSyntaxTree(){
        Stack<TreeNode<Symbol>> stackTreeNodes = new Stack<TreeNode<Symbol>>();
        for (Symbol symbol : regexSymbols) {
            if(symbol.getId() == AsciiSymbol.Epsilon.ascii){
                stackTreeNodes.push(null); // Ingresar nullable
            }
            else if(symbol.getId() == AsciiSymbol.ParenthesisLeft.ascii){
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol);
                stackTreeNodes.push(node);
            }
            else if(
                symbol.getId() == AsciiSymbol.ParenthesisRight.ascii ||
                symbol.getId() == AsciiSymbol.Or.ascii
            ){
                TreeNode<Symbol> right = stackTreeNodes.pop();
                TreeNode<Symbol> left = stackTreeNodes.pop();
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, left, right);
                stackTreeNodes.push(node);
            }
            else if(
                symbol.getId() == AsciiSymbol.Kleene.ascii ||
                symbol.getId() == AsciiSymbol.Plus.ascii || 
                symbol.getId() == AsciiSymbol.Interrogation.ascii
            ){
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, stackTreeNodes.pop(), null);
                stackTreeNodes.push(node);
            }
            else{ // Almacenar un symbolo normal
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, ++count);
                stackTreeNodes.add(node);
            }
        }
        root = stackTreeNodes.pop();
    }

    public Set<Integer> getFirstPos(TreeNode<Symbol> pRoot){

        return null;
    }

    public TreeNode<Symbol> getRoot() {
        return root;
    }

    // ! PROBABLEMENTE EN VEZ DE INT/INTEGER ESTO TERMINE SIENDO STATE

    public Symbol getSymbol(int p){
        return null;
    }

    public Set<Integer> getFollowpos(int p){
        return null;
    }
}
