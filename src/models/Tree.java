package models;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tree {
    private Node<PairData<String, String>> root;
    private ArrayList<String> transitions;  
    private int count;
    private List<String> signsAvoid = Arrays.asList("+", "*", "?", "|", "·");

    public Tree(ArrayList<PairData<String, String>> regexPostfix){
        transitions = new ArrayList<String>();        
        count = 0;        
        createSyntaxTree(regexPostfix); 
        nullableTraversal(root); 
        firstposAndLastPosTraversal(root);
        generateTransitions(root);
    }

    private void createSyntaxTree(ArrayList<PairData<String, String>> regexPostfix){
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
    public ArrayList<String> getTransitions() {
        return transitions;
    }

    // * ---> METODOS
    private void nullableTraversal(Node<PairData<String, String>> node){
        if(node != null){
            nullableTraversal(node.left);
            nullableTraversal(node.right);
            
            // * => Determinar todos los nullables
            
            if(!signsAvoid.contains(node.value.second)){ // False
                node.nullable = 0; 
            }
            else if(node.value.second.equals("E")){ // True
                node.nullable = 1;
            }
            else if(node.value.second.equals("|")){ // nullable(c1) | nullable(c2)
                int c1 = node.left.nullable;
                int c2 = node.right.nullable;
                node.nullable = (c1 == 1 || c2 == 1) ? 1 : 0;
            }
            else if(node.value.second.equals("·")){ // nullable(c1) & nullable(c2)
                int c1 = node.left.nullable;
                int c2 = node.right.nullable;
                node.nullable = (c1 == 1 && c2 == 1) ? 1 : 0;
            }
            else if(node.value.second.equals("*") || node.value.second.equals("?")){ // True
                node.nullable = 1;                
            }
            else if(node.value.second.equals("+")){
                node.nullable = (node.left.nullable == 1) ? 1 : 0;
            }
        }
    }

    private void firstposAndLastPosTraversal(Node<PairData<String, String>> node){
        if(node != null){
            firstposAndLastPosTraversal(node.left);
            firstposAndLastPosTraversal(node.right);

            // Empezar a obtener las primeras posiciones
            if(!signsAvoid.contains(node.value.second)){ // i
                if (node.value.second.equals("#")){ // Hoja

                }
                else if(node.value.second.equals("E")){ // Epsilon

                } 
                else{ // Simbolo normal
                    node.addFirstPos(node.pos);
                    node.addLastPos(node.pos);
                }
            }
            else if(node.value.second.equals("|")){ // firstPos(c1) U firstPos(c2)
                // -> Realizar union de FIRST POSES
                for (int firstPos : node.left.getFirstPoses()) {
                    node.addFirstPos(firstPos);
                }
                for (int firstPos : node.right.getFirstPoses()) {
                    node.addFirstPos(firstPos);
                }
                // -> Realizar union de LAST POSES
                for (int firstPos : node.left.getLastPoses()) {
                    node.addLastPos(firstPos);
                }
                for (int firstPos : node.right.getLastPoses()) {
                    node.addLastPos(firstPos);
                }
            }
            else if(node.value.second.equals("·")){ // si nullable c1 == true: Union, sino firstPOs(c1)
                // -> Realizar operacion de FIRST POSES
                if(node.left.nullable == 1){ // Union
                    for (int firstPos : node.left.getFirstPoses()) { // c1
                        node.addFirstPos(firstPos);
                    }
                    for (int firstPos : node.right.getFirstPoses()) { // c2
                        node.addFirstPos(firstPos);
                    }
                }
                else{ // Copy all c1 (left)
                    node.firstpos = node.left.firstpos;
                }
                // -> Realizar operacion de LAST POSES
                if(node.right.nullable == 1){ // Union
                    for (int firstPos : node.left.getLastPoses()) { // c1
                        node.addLastPos(firstPos);
                    }
                    for (int firstPos : node.right.getLastPoses()) { // c2
                        node.addLastPos(firstPos);
                    }
                }
                else{ // Copy all c2 (right)
                    node.lastpos = node.right.lastpos;
                }
            }
            else if(node.value.second.equals("*") || node.value.second.equals("+") || node.value.second.equals("?")){ // firstPos(c1)
                node.firstpos = node.left.firstpos;
                node.lastpos = node.left.lastpos;
            }
        }
    }
    
    private void generateTransitions(Node<PairData<String, String>> node){        
        if(node != null){        
            String information = node.value.second;
            // System.out.println("=================================");
            // System.out.println("==> " + node.value.second + " | POS: " + node.pos);
            // System.out.println("NULLABLE: " + node.nullable);
            // System.out.println("FIRST_POS:");
            // for (int num : node.getFirstPoses()) {
            //     System.out.print(num + ",");
            // }
            // System.out.println("\nLAST_POS:");
            // for (int num : node.getLastPoses()) {
            //     System.out.print(num + ",");
            // }
            // System.out.println("");

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
