package automatas;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.LinkedList;

import models.ItemProd;
import models.PairData;
import models.Symbol;

public class AutomataLR0 {
    private ArrayList<ItemProd> productions;
    private ArrayList<ArrayList<ItemProd>> C;
    private ArrayList<Symbol> symbols;
    private int count;
    
    public AutomataLR0(ArrayList<ItemProd> productions, ArrayList<Symbol> symbols){
        // Inicializar atributos
        this.productions = productions;
        this.symbols = symbols;
        this.C = new ArrayList<ArrayList<ItemProd>>();
        count = -1;
        // Empezar a ingresar la informacion
        construct();
    }

    private void construct(){        
        ArrayList<ItemProd> c = closure(productions.get(0));
        C.add((++count), c);
        
        Queue<ArrayList<ItemProd>> queue = new LinkedList<ArrayList<ItemProd>>();
        ArrayList<ItemProd> forQueue = new ArrayList<ItemProd>();
        for (ItemProd item : c) {
            forQueue.add(new ItemProd(item.getExpression(), item.getInitial(), item.getElements()));
        }
        queue.add(forQueue);
        
        while(!queue.isEmpty()){
            ArrayList<ItemProd> I = queue.poll();
            for (Symbol X : symbols){
                ArrayList<ItemProd> G = goTo(I, X);
                if(G.size() != 0 && !sameContentSet(G)){                        
                    C.add((++count), G); // Agregar G a C
                    // Agregar transicion
                }
            }
        }
        
        // boolean noMoreAddedSets = false;
        // do {
        //     boolean isNewSets = false;
        //     for (int i = 0; i < C.size(); i++) {                
        //         ArrayList<ItemProd> I = C.get(i);

        //         for (Symbol X : symbols){
        //             ArrayList<ItemProd> G = goTo(I, X);
        //             if(G.size() != 0 && !sameContentSet(G)){                        
        //                 C.add((++count), G); // Agregar G a C
        //                 // Agregar transicion
        //                 isNewSets = true;
        //             }
        //         }
        //     }
        //     if(isNewSets == false) noMoreAddedSets = true;
        // } while (!noMoreAddedSets);
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

    private ArrayList<ItemProd> goTo(ArrayList<ItemProd> setI, Symbol symbol){
        // En este metodo se definiran las transiciones en el automata LR(0)        
        ArrayList<ItemProd> copyI = new ArrayList<>();
        for (ItemProd itemProd : setI) {
            copyI.add(new ItemProd(itemProd.getExpression(), itemProd.getInitial(), itemProd.getElements()));
        }
        ArrayList<ItemProd> goToSet = new ArrayList<ItemProd>();
                
        for (ItemProd item : copyI) {
            // ! PROGRAMAR BUENA LOGICA DE GOTO
            if(item.moveDot(symbol)){
                goToSet.add(item);
            }
        }
        
        return goToSet;
    }

    private boolean sameContentSet(ArrayList<ItemProd> setCheck){
        for (ArrayList<ItemProd> stateC : C) {
            int amountProductionsState = stateC.size();
            int amountSetCheck = setCheck.size();

            if(amountProductionsState != amountSetCheck) continue;

            int counter = 0;
            for (int i = 0; i < amountSetCheck; i++) {
                ItemProd itemCheck = setCheck.get(i);
                ItemProd itemState = stateC.get(i);

                String strItemCheck = itemCheck.getExpression();
                String strItemState = itemState.getExpression();

                if(strItemCheck.equals(strItemState)){
                    counter++;
                }
            }

            if(counter == amountProductionsState) return true;
        }

        return false;
    }

    // private ArrayList<Symbol> first(Symbol symbol){
    //     ArrayList<Symbol> firstSet = new ArrayList<Symbol>();

    //     return firstSet;
    // }

    // private ArrayList<Symbol> follow(Symbol symbol){
    //     ArrayList<Symbol> followSet = new ArrayList<Symbol>();

    //     return followSet;
    // }

    public ArrayList<String> prepareTransitions(){
        // Metodo que nos dara una estructura para graficarlo con dot
        ArrayList<String> infoTransitions = new ArrayList<String>();

        return infoTransitions;
    }
    
    public void seeStates(){
        for (int i = 0; i < C.size(); i++) {
            System.out.println("\n==> S" + i);
            ArrayList<ItemProd> productionsSee = C.get(i);

            for (ItemProd itemProd : productionsSee) {
                System.out.println("\t" + itemProd.getExpression());
            }
        }
    }
}
