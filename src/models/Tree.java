package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tree {
    private Node<PairData<String, String>> root;
    private ArrayList<String> transitions;  
    private int count;
;    private List<String> signsAvoid = Arrays.asList("+", "*", "?", "|", "·");

    public Tree(){
        transitions = new ArrayList<String>();        
        count = 0;
    }

    public void createSyntaxTree(ArrayList<PairData<String, String>> regexPostfix){
        Stack<Node<PairData<String, String>>> stack = new Stack<Node<PairData<String, String>>>();
        for (PairData<String, String> s : regexPostfix) {
            String symbol = s.second;
            // * ---> Valor normal
            if(!signsAvoid.contains(symbol)){
                Node<PairData<String, String>> node = new Node<PairData<String, String>>(s, ++count);
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
    public void postorderTraversal(Node<PairData<String, String>> node){
        if(node != null){
            postorderTraversal(node.left);
            postorderTraversal(node.right);

            // Vamos a determinar si este es un null o no
            System.out.print(node.value.second + " "); // Visitar el nodo

            if(!signsAvoid.contains(node.value.second)){ // Nodo normal
                node.nullable = 0; // False
            }
            else if(node.value.second.equals("|")){
                int c1 = node.left.nullable;
                int c2 = node.right.nullable;
                node.nullable = (c1 == 1 || c2 == 1) ? 1 : 0;
            }
            else if(node.value.second.equals("·")){
                int c1 = node.left.nullable;
                int c2 = node.right.nullable;
                node.nullable = (c1 == 1 && c2 == 1) ? 1 : 0;
            }
            else if(node.value.second.equals("+")){
                node.nullable = (node.left.value.second.equals("E")) ? 1 : 0;
            }
            else if(node.value.second.equals("?")){
                node.nullable = (node.left.value.second.equals("E")) ? 0 : 1;
            }
        }
    }
    
    public void generateTransitions(Node<PairData<String, String>> node){        
        if(node != null){        
            String information = node.value.second;
            // String information = (node.invalidPos()) ?  node.value.second : node.value.second + "-" + node.pos;
            // String information = (node.invalidPos()) ? node.value.second + "-" + node.nullable: node.value.second + "-" + node.pos + "-" + node.nullable;
            transitions.add(node.value.first + " [label=\"" + information + "\"];");
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
