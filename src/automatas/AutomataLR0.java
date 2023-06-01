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
            forQueue.add(new ItemProd(copyElements(item.getInitial()), copyElements(item.getElements())));
        }
        queue.add(forQueue);

        while(!queue.isEmpty()){
            ArrayList<ItemProd> I = queue.poll();
            for (Symbol X : symbols) {
                ArrayList<ItemProd> G = goTo(I, X);
                if(G.size() != 0 && !sameContentSet(G)){
                    C.add((++count), G); // Agregar a C                    
                    queue.offer(copyItems(G)); // Agregar nuevos items al queue
                }
            }
        }    
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
                        ItemProd newProd = new ItemProd(copyElements(production.getInitial()), copyElements(production.getElements()));
                        newProd.insertDot();
                        J.add(newProd);
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
        ArrayList<ItemProd> goToSet = new ArrayList<ItemProd>();

        for (ItemProd itemProd : setI) {
            ItemProd newItem = new ItemProd(copyElements(itemProd.getInitial()), copyElements(itemProd.getElements()));
            if(newItem.moveDot(symbol)){
                goToSet.add(newItem);
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

    private ArrayList<Symbol> copyElements(ArrayList<Symbol> elements){
        ArrayList<Symbol> listNew = new ArrayList<Symbol>();

        for (Symbol s : elements) {
            int option = (s.getIsTerminal()) ? 1 : 0;
            Symbol newSymbol = new Symbol(s.getStringId(), option);
            listNew.add(newSymbol);
        }
        
        return listNew;
    }

    private ArrayList<ItemProd> copyItems(ArrayList<ItemProd> itemsToCopy){
        ArrayList<ItemProd> listNew = new ArrayList<ItemProd>();

        for (ItemProd item : itemsToCopy) {
            ItemProd newItemProd = new ItemProd(copyElements(item.getInitial()), copyElements(item.getElements()));
            listNew.add(newItemProd);
        }
        
        return listNew;
    }

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
