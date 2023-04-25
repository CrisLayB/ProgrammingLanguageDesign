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
                node.firstpos.add(node.pos);
                node.lastpos.add(node.pos);
                tableFollowPos.put(node.pos, new ArrayList<>());
            }
            else if(node.value.second.equals("|")){ // firstPos(c1) U firstPos(c2)
                // -> Realizar union de FIRST POSES
                node.addFirstPos(node.left.firstpos);
                node.addFirstPos(node.right.firstpos);
                // -> Realizar union de LAST POSES
                node.addLastPos(node.left.lastpos);
                node.addLastPos(node.right.lastpos);                
            }
            else if(node.value.second.equals("·")){ // si nullable c1 == true: Union, sino firstPOs(c1)
                if(node.left.nullable){ // Firstpos
                    node.addFirstPos(node.left.firstpos);
                    node.addFirstPos(node.right.firstpos);
                }
                else{
                    node.addFirstPos(node.left.firstpos);
                }
                
                if(node.right.nullable){ // Lastpos
                    node.addLastPos(node.left.lastpos);
                    node.addLastPos(node.right.lastpos);
                }                
                else{
                    node.addLastPos(node.right.lastpos);
                }
            }
            else if(node.value.second.equals("*") || node.value.second.equals("+") || node.value.second.equals("?")){ // firstPos(c1)
                node.addFirstPos(node.left.firstpos);
                node.addLastPos(node.left.lastpos);
            }
        }
    }

    private void generateFollowPos(Node<PairData<String, String>> node){
        if(node != null){
            generateFollowPos(node.left);
            generateFollowPos(node.right);

            // Empezar a generar todos los followPos
            if(node.value.second.equals(".")){
                for (int i : node.left.getLastPoses()) {
                    for (int j : node.right.firstpos) {
                        ArrayList<Integer> list = tableFollowPos.get(i);
                        if(!list.contains(j)) list.add(j);
                    }
                }
            }
            if(node.value.second.equals("*") || node.value.second.equals("+")){
                for (int i : node.lastpos) {
                    for (int j : node.firstpos) {
                        ArrayList<Integer> list = tableFollowPos.get(i);
                        if(!list.contains(j)) list.add(j);
                    }
                }
            }
        }
    }

    public List<Integer> getFirstpos(){
        return root.getFirstPoses();
    }

    public List<Integer> getLastpos(){
        return root.getLastPoses();
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
