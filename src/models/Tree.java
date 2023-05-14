package models;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Stack;

import enums.AsciiSymbol;

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

                // Agregar numeros para el followPOs
                tableFollowPos.put(count, new ArrayList<Integer>());
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
                node.setNullable(false);
            }
            else if(node.value.second.equals(AsciiSymbol.Epsilon.c + "")){ // True
                node.setNullable(true);
            }
            else if(node.value.second.equals("|")){ // nullable(c1) | nullable(c2)
                Boolean c1 = node.left.getNullable();
                Boolean c2 = node.right.getNullable();
                node.setNullable((c1 || c2));
            }
            else if(node.value.second.equals("·")){ // nullable(c1) & nullable(c2)
                Boolean c1 = node.left.getNullable();
                Boolean c2 = node.right.getNullable();
                node.setNullable((c1 && c2));
            }
            else if(node.value.second.equals("*")){ // True
                node.setNullable(true);
            }
            // // ! ¿Se debe de hacer otra cosa con question mark y plus symbol? //  || node.value.second.equals("?")
            // else if(node.value.second.equals("+")){
            //     node.nullable = (node.left.nullable) ? true : false;
            // }
        }
    }

    private void firstposAndLastPosTraversal(Node<PairData<String, String>> node){
        if(node != null){
            firstposAndLastPosTraversal(node.left);
            firstposAndLastPosTraversal(node.right);

            // Empezar a obtener las primeras posiciones
            if(!signsAvoid.contains(node.value.second)){ // i                
                node.addFirstPos(node.getPos());
                node.addLastPos(node.getPos());
            }
            else if(node.value.second.equals("|")){ // firstPos(c1) U firstPos(c2)                
                for (int i : node.left.getFirstPoses()) {
                    node.addFirstPos(i);
                }
                for (int i : node.right.getFirstPoses()) {
                    node.addFirstPos(i);
                }
                // ? =======================================
                for (int i : node.left.getLastPoses()) {
                    node.addLastPos(i);
                }
                for (int i : node.right.getLastPoses()) {
                    node.addLastPos(i);
                }
            }
            else if(node.value.second.equals("·")){ // si nullable c1 == true: Union, sino firstPOs(c1)
                if(node.left.getNullable() == true){
                    for (int i : node.left.getFirstPoses()) {
                        node.addFirstPos(i);
                    }
                    for (int i : node.right.getFirstPoses()) {
                        node.addFirstPos(i);
                    }
                }
                else{
                    for (int i : node.left.getFirstPoses()) {
                        node.addFirstPos(i);
                    }
                }
                // ? =======================================
                if(node.right.getNullable() == true){
                    for (int i : node.left.getLastPoses()) {
                        node.addLastPos(i);
                    }
                    for (int i : node.right.getLastPoses()) {
                        node.addLastPos(i);
                    }
                }
                else{
                    for (int i : node.right.getLastPoses()) {
                        node.addLastPos(i);
                    }
                }
            }
            else if(node.value.second.equals("*")){ // firstPos(c1)
                for (int i : node.left.getFirstPoses()) {
                    node.addFirstPos(i);
                }
                // ? =======================================
                for (int i : node.left.getLastPoses()) {
                    node.addLastPos(i);
                }
            }
            // ! Depende de lo que me digan con || node.value.second.equals("+") || node.value.second.equals("?")
            // ! Vamos a ver que se hace
        }
    }

    private void generateFollowPos(Node<PairData<String, String>> node){
        if(node != null){
            generateFollowPos(node.left);
            generateFollowPos(node.right);           

            if(node.value.second.equals("·")){ // Si dado caso es concatenacion
                for (int i : node.left.getLastPoses()) { // Con el hijo izquierdo en firstPos
                    for (int j : node.right.getFirstPoses()) { // Con el hijo derecho en lastPos
                        ArrayList<Integer> listNums = tableFollowPos.get(i);
                        if(!listNums.contains(j)) listNums.add(j); // Son las que se encuentran en followPos
                    }
                }
            }
            else if(node.value.second.equals("*")){
                for (int i : node.getLastPoses()) {
                    for (int j : node.getFirstPoses()) {
                        ArrayList<Integer> listNums = tableFollowPos.get(i);
                        if(!listNums.contains(j)) listNums.add(j);
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
