package models;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tree {
    private Node<PairData<String, String>> root;
    private ArrayList<String> treeTransitions;  
    private int count;
    private List<String> signsAvoid = Arrays.asList("+", "*", "?", "|", "·");
    private List<Symbol> symbols;
    private Map<Integer, ArrayList<Integer>> tableFollowPos;

    public Tree(ArrayList<PairData<String, String>> regexPostfix){
        // Inicializando propiedades
        treeTransitions = new ArrayList<String>();        
        count = 0;
        symbols = new ArrayList<Symbol>();
        tableFollowPos = new LinkedHashMap<Integer, ArrayList<Integer>>();
        // Crear arbol y obtener nullable, firstpos, lastpos y followpos
        createSyntaxTree(regexPostfix); 
        nullableTraversal(root); 
        firstposAndLastPosTraversal(root);
        generateFollowPos(root);
        generateTransitions(root); // Generar transiciones del arbol
    }

    private void createSyntaxTree(ArrayList<PairData<String, String>> regexPostfix){
        Stack<Node<PairData<String, String>>> stack = new Stack<Node<PairData<String, String>>>();
        for (PairData<String, String> s : regexPostfix) {
            String symbol = s.second;
            // * ---> Valor normal
            if(!signsAvoid.contains(symbol)){
                if(symbol.equals("E")){ // Por si se detecta un EPSILON
                    stack.push(new Node<>());
                    continue;
                }
                
                Node<PairData<String, String>> node = new Node<PairData<String, String>>(s, ++count);
                stack.push(node);

                // Agregar simbolo
                Symbol newSymbol = (isNumeric(symbol)) ? new Symbol(Integer.parseInt(symbol)) : new Symbol(symbol.charAt(0));
                if(!symbolExistsInAlphabet(newSymbol)) symbols.add(newSymbol);
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
    
    public List<Symbol> getSymbols() {
        return symbols;
    }
    
    public ArrayList<String> getTransitions() {
        return treeTransitions;
    }

    // * ---> METODOS
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    private void nullableTraversal(Node<PairData<String, String>> node){
        if(node != null){
            nullableTraversal(node.left);
            nullableTraversal(node.right);
            
            // * => Determinar todos los nullables
            
            if(!signsAvoid.contains(node.value.second)){ // False
                node.nullable = false; 
            }
            else if(node.value.second.equals("E")){ // True
                node.nullable = true;
            }
            else if(node.value.second.equals("|")){ // nullable(c1) | nullable(c2)
                Boolean c1 = node.left.nullable;
                Boolean c2 = node.right.nullable;
                node.nullable = (c1 || c2) ? true : false;
            }
            else if(node.value.second.equals("·")){ // nullable(c1) & nullable(c2)
                Boolean c1 = node.left.nullable;
                Boolean c2 = node.right.nullable;
                node.nullable = (c1 && c2) ? true : false;
            }
            else if(node.value.second.equals("*") || node.value.second.equals("?")){ // True
                node.nullable = true;
            }
            else if(node.value.second.equals("+")){
                node.nullable = (node.left.nullable) ? true : false;
            }
        }
    }

    private void firstposAndLastPosTraversal(Node<PairData<String, String>> node){
        if(node != null){
            firstposAndLastPosTraversal(node.left);
            firstposAndLastPosTraversal(node.right);

            // Empezar a obtener las primeras posiciones
            if(!signsAvoid.contains(node.value.second)){ // i                

            }
            else if(node.value.second.equals("|")){ // firstPos(c1) U firstPos(c2)
                
            }
            else if(node.value.second.equals("·")){ // si nullable c1 == true: Union, sino firstPOs(c1)

            }
            else if(node.value.second.equals("*") || node.value.second.equals("+") || node.value.second.equals("?")){ // firstPos(c1)
                
            }
        }
    }

    private void generateFollowPos(Node<PairData<String, String>> node){
        if(node != null){
            generateFollowPos(node.left);
            generateFollowPos(node.right);
        }
    }

    public List<Integer> getFirstpos(){
        return root.getFirstPoses();
    }

    public List<Integer> getLastpos(){
        return root.getLastPoses();
    }

    public List<Integer> getFollowpos(int pos){
        List<Integer> followpos = new ArrayList<>();
        for (Integer integer : followpos) {
            if(integer == pos){
                followpos.add(integer);
            }
        }
        return followpos;
    }
    
    private boolean symbolExistsInAlphabet(Symbol symbolCheck){
        for (Symbol symbol : symbols) {
            if(symbolCheck.getId() == symbol.getId()){
                return true;
            }
        }
        return false;
    }
    
    private void generateTransitions(Node<PairData<String, String>> node){        
        if(node != null){        
            String information = node.value.second;
            treeTransitions.add(node.value.first + " [label=\"" + information + "\"];");
            if(node.left != null){
                treeTransitions.add(
                    node.value.first + " -> " + node.left.value.first + ";"
                );
                generateTransitions(node.left);
            }
            if(node.right != null){
                treeTransitions.add(
                    node.value.first + " -> " + node.right.value.first + ";"
                );
                generateTransitions(node.right);
            }
        }
    }
}
