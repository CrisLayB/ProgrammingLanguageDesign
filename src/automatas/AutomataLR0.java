package automatas;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

import enums.AsciiSymbol;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import models.ItemProd;
import models.Symbol;

public class AutomataLR0 {
    private ArrayList<ItemProd> productions;
    private Map<String, ArrayList<ItemProd>> states;
    private ArrayList<Symbol> symbols;
    private int count;
    
    public AutomataLR0(ArrayList<ItemProd> productions, ArrayList<Symbol> symbols){
        // Inicializar atributos
        this.productions = productions;
        this.symbols = symbols;
        this.states = new LinkedHashMap<String, ArrayList<ItemProd>>();
        this.count = -1;
        // Empezar a ingresar la informacion
        construct();
    }

    private void construct(){        
        ArrayList<ItemProd> C = closure(productions.get(0));
        states.put("S" + (++count), C);

        boolean noMoreAddedSets = false;
        do {
            boolean isNewSets = false;
            for (int i = 0; i < C.size(); i++) {
                ItemProd item = C.get(i);
                for (Symbol X : symbols) {
                    ArrayList<ItemProd> G = goTo(item, X);
                    // Verificar que G no este vacio y que no este en Sets
                    if(G.size() != 0){
                        // Agregar G a States
                        isNewSets = true;
                    }
                }
            }

            if(isNewSets == false) noMoreAddedSets = true;
        } while (!noMoreAddedSets);
    }

    private ArrayList<ItemProd> closure(ItemProd items){
        ArrayList<ItemProd> J = new ArrayList<ItemProd>();
        J.add(items);
        ArrayList<String> noTerminalChecked = new ArrayList<String>();
        
        boolean finish = false;
        do {
            boolean isMoreAdded = false;
            for (int i = 0; i < J.size(); i++) {
                ItemProd item = J.get(i);
                Symbol symbolNext = item.getNextProduction(); // Simbolo siguiente a identificar
    
                if(symbolNext == null) continue;
    
                if(noTerminalChecked.contains(symbolNext.getStringId())) continue;

                if(symbolNext.getIsTerminal()) continue;

                for (ItemProd production : productions) {
                    String strItem = item.getExpression();
                    String strProduction = production.getExpression();

                    Symbol symPro = production.getIdInitial();
                    String strSymProduction = symPro.getStringId();

                    if(!strItem.equals(strProduction) && strSymProduction.equals(symbolNext.getStringId())){
                        production.insertDot();
                        J.add(production);
                        isMoreAdded = true;
                    }
                }

                noTerminalChecked.add(symbolNext.getStringId());
            }     
            
            if(isMoreAdded == false) finish = true;
        } while (!finish);

        return J;
    }

    private ArrayList<ItemProd> goTo(ItemProd setI, Symbol symbol){
        // En este metodo se definiran las transiciones en el automata LR(0)
        ArrayList<ItemProd> goToSet = new ArrayList<ItemProd>();
        
        // for (int i = 0; i < setI.size(); i++) {
            // Symbol symbolI = setI.get(i);            
            // String strSymbolI = symbolI.getStringId();
            // String strSymbol = symbol.getStringId();

            // if(strSymbolI.equals(strSymbol)){
            //     Symbol newSymbol = new Symbol(strSymbolI);
            //     goToSet.add(newSymbol);
            // }
        // }
        
        return goToSet;
    }

    private ArrayList<Symbol> first(Symbol symbol){
        ArrayList<Symbol> firstSet = new ArrayList<Symbol>();

        return firstSet;
    }

    private ArrayList<Symbol> follow(Symbol symbol){
        ArrayList<Symbol> followSet = new ArrayList<Symbol>();

        return followSet;
    }

    public ArrayList<String> prepareTransitions(){
        // Metodo que nos dara una estructura para graficarlo con dot
        ArrayList<String> infoTransitions = new ArrayList<String>();

        return infoTransitions;
    }
    
    public void seeStates(){
        for(Map.Entry<String, ArrayList<ItemProd>> state: states.entrySet()){
            String numState = state.getKey();
            ArrayList<ItemProd> productions = state.getValue();

            System.out.println("\n==> " + numState);
            for (ItemProd listProductions : productions) {
                System.out.println("\t" + listProductions.getExpression());
                // System.out.print("\t");
                // for (Symbol symbol : listProductions) {
                //     System.out.print(symbol.getStringId());
                // }
                // System.out.print("\n");
            }
        }
    }
}
