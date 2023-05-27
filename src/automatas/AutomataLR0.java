package automatas;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import models.Symbol;

public class AutomataLR0 {
    private ArrayList<ArrayList<Symbol>> productions;
    private Map<String, ArrayList<ArrayList<Symbol>>> states;
    private ArrayList<Symbol> symbols;
    private int count;
    
    public AutomataLR0(ArrayList<ArrayList<Symbol>> productions, ArrayList<Symbol> symbols){
        // Inicializar atributos
        this.productions = productions;
        this.symbols = symbols;
        this.states = new LinkedHashMap<String, ArrayList<ArrayList<Symbol>>>();
        this.count = -1;
        // Empezar a ingresar la informacion
        construct();
    }

    private void construct(){
        ArrayList<Symbol> itemsJ = closure(productions.get(0));
    }

    private ArrayList<Symbol> first(Symbol symbol){
        ArrayList<Symbol> firstSet = new ArrayList<Symbol>();

        return firstSet;
    }

    private ArrayList<Symbol> follow(Symbol symbol){
        ArrayList<Symbol> followSet = new ArrayList<Symbol>();

        return followSet;
    }

    private ArrayList<Symbol> closure(ArrayList<Symbol> items){
        ArrayList<Symbol> J = new ArrayList<Symbol>(items);
        Queue<Symbol> queue = new LinkedList<>(items);
        // for (Symbol symbol : J) {
        //     System.out.print(symbol.getStringId());
        // }

        while(!queue.isEmpty()){
            Symbol actualSymbol = queue.poll();

        }

        return J;
    }

    private ArrayList<Symbol> goTo(ArrayList<Symbol> setI, Symbol symbol){
        // En este metodo se definiran las transiciones en el automata LR(0)
        ArrayList<Symbol> goToSet = new ArrayList<Symbol>();
        
        for (int i = 0; i < setI.size(); i++) {
            Symbol symbolI = setI.get(i);            
            String strSymbolI = symbolI.getStringId();
            String strSymbol = symbol.getStringId();

            if(strSymbolI.equals(strSymbol)){
                Symbol newSymbol = new Symbol(strSymbolI);
                goToSet.add(newSymbol);
            }
        }
        
        return goToSet;
    }

    public ArrayList<String> prepareTransitions(){
        ArrayList<String> infoTransitions = new ArrayList<String>();

        return infoTransitions;
    }
    
    public void seeStates(){
        for(Map.Entry<String, ArrayList<ArrayList<Symbol>>> state: states.entrySet()){
            String numState = state.getKey();
            ArrayList<ArrayList<Symbol>> productions = state.getValue();

            System.out.println("\n==> " + numState);
            for (ArrayList<Symbol> listProductions : productions) {
                System.out.print("\t");
                for (Symbol symbol : listProductions) {
                    System.out.print(symbol.getStringId());
                }
                System.out.print("\n");
            }
        }
    }
}
