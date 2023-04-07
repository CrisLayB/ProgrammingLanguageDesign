package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tree {
    private Node<String> root;
    private ArrayList<Transition> transitions;

    public Tree(){
        transitions = new ArrayList<Transition>();
    }

    public void createSyntaxTree(ArrayList<String> regexPostfix){
        Stack<Node<String>> stack = new Stack<Node<String>>();
        List<String> signsAvoid = Arrays.asList("+", "*", "?", "|", "·");
        for (String symbol : regexPostfix) {
            // * ---> Valor normal
            if(!signsAvoid.contains(symbol)){
                Node<String> node = new Node<String>(symbol);
                stack.push(node);
            }
            // * ---> OR, DOT
            else if(symbol.equals("|") || symbol.equals("·")){
                Node<String> right = stack.pop();
                Node<String> left = stack.pop();
                Node<String> node = new Node<String>(symbol, left, right);
                stack.push(node);
            }
            // * ---> KLEENE, PLUS, INTERROGATION
            else if(symbol.equals("*") || symbol.equals("+") || symbol.equals("?")){
                Node<String> node = new Node<>(symbol, stack.pop(), null);
                stack.push(node);
            }
        }
        root = stack.pop();
    }  

    // * ---> GETTERS
    public Node<String> getRoot() {
        return root;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    // * ---> METODOS
    public void generateTransitions(Node<String> node){
        // https://graphviz.org/Gallery/directed/Genetic_Programming.html // Para preparar arbol
        if(node != null){
            if(node.left != null){
                transitions.add(
                    new Transition(new Symbol('L'), 
                    new State(node.value, Types.Transition), 
                    new State(node.left.value, Types.Transition))
                );
                generateTransitions(node.left);
            }
            if(node.right != null){
                transitions.add(
                    new Transition(new Symbol('R'), 
                    new State(node.value, Types.Transition), 
                    new State(node.right.value, Types.Transition))
                );
                generateTransitions(node.right);
            }
        }
    }
}
