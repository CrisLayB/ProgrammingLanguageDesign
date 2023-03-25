package models;

import java.util.Stack;
import java.util.Set;

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

            // * ---> KLEENE, PLUS, INTERROGATION
            else if(
                symbol.getId() == AsciiSymbol.Kleene.ascii ||
                symbol.getId() == AsciiSymbol.Plus.ascii || 
                symbol.getId() == AsciiSymbol.Interrogation.ascii
            ){
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, stackTreeNodes.pop(), null);

                if(!node.left.isNull()){
                    stackTreeNodes.push(node);
                }        
            }

            // * ---> NORMAL SYMBOL
            else{ // Almacenar un symbolo normal
                TreeNode<Symbol> node = new TreeNode<Symbol>(symbol, ++count);

                // Revisar si es Epsilon
                if(symbol.getId() == AsciiSymbol.Epsilon.ascii){
                    node = new TreeNode<Symbol>(null);
                }
                
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
