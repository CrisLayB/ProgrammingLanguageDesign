package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tree {
    private Node<PairData<String, String>> root;
    private ArrayList<String> transitions;

    public Tree(){
        transitions = new ArrayList<String>();
    }

    public void createSyntaxTree(ArrayList<PairData<String, String>> regexPostfix){
        Stack<Node<PairData<String, String>>> stack = new Stack<Node<PairData<String, String>>>();
        List<String> signsAvoid = Arrays.asList("+", "*", "?", "|", "·");
        for (PairData<String, String> s : regexPostfix) {
            String symbol = s.second;
            // * ---> Valor normal
            if(!signsAvoid.contains(symbol)){
                Node<PairData<String, String>> node = new Node<PairData<String, String>>(s);
                stack.push(node);
            }
            // * ---> OR, DOT
            else if(symbol.equals("|") || symbol.equals("·")){
                Node<PairData<String, String>> right = stack.pop();
                Node<PairData<String, String>> left = stack.pop();
                Node<PairData<String, String>> node = new Node<PairData<String, String>>(s, left, right);
                stack.push(node);
            }
            // * ---> KLEENE, PLUS, INTERROGATION
            else if(symbol.equals("*") || symbol.equals("+") || symbol.equals("?")){
                Node<PairData<String, String>> node = new Node<>(s, stack.pop(), null);
                stack.push(node);
            }
        }
        root = stack.pop();
    }  

    // * ---> GETTERS
    public Node<PairData<String, String>> getRoot() {
        return root;
    }

    public ArrayList<String> getTransitions() {
        return transitions;
    }

    // * ---> METODOS
    public void generateTransitions(Node<PairData<String, String>> node){        
        if(node != null){            
            transitions.add(node.value.first + " [label=\"" + node.value.second + "\"];");
            if(node.left != null){
                transitions.add(
                    node.value.first + " -> " + node.left.value.first + ";"
                );
                generateTransitions(node.left);
            }
            if(node.right != null){
                transitions.add(
                    node.value.first + " -> " + node.right.value.first + ";"
                );
                generateTransitions(node.right);
            }
        }
    }
}
