package automatas;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

import models.ItemProd;
import models.SimpleTransition;
import models.Symbol;

public class AutomataLR0 {
    // Atributos para guardar informacion del automata
    private ArrayList<ItemProd> productions;
    private ArrayList<ArrayList<ItemProd>> C;
    private ArrayList<Symbol> symbols;
    private ArrayList<SimpleTransition> transitions;
    // Auxiliares para el 
    private int count;
    
    public AutomataLR0(ArrayList<ItemProd> productions, ArrayList<Symbol> symbols){
        // Inicializar atributos
        this.productions = productions;
        this.symbols = symbols;
        this.C = new ArrayList<ArrayList<ItemProd>>();
        this.transitions = new ArrayList<SimpleTransition>();
        count = -1;
        // Empezar a ingresar la informacion
        construct();
    }

    private void construct(){        
        ArrayList<ItemProd> c = closure(productions.get(0));
        C.add((++count), c);

        Queue<ArrayList<ItemProd>> queue = new LinkedList<ArrayList<ItemProd>>();
        queue.add(copyItems(c));
        
        // * Construir Automata LR(0)
        int counter = -1;
        while(!queue.isEmpty()){ 
            ArrayList<ItemProd> I = queue.poll();
            counter++;
            for (Symbol X : symbols) {
                ArrayList<ItemProd> G = goTo(I, X);
                int sameContent = sameContentSet(G);
                if(G.size() != 0 && sameContent == -1){
                    C.add((++count), G); // Agregar a C                    
                    queue.offer(copyItems(G)); // Agregar nuevos items al queue
                    transitions.add(new SimpleTransition("S" + counter, "S" + count, X.getStringId()));
                }
                // else if(G.size() != 0 && sameContent != -1){
                //     transitions.add(new SimpleTransition("S" + count, "S" + sameContent, X.getStringId()));
                // }
            }
        }

        // * Ingresar transicion para estado de aceptacion
        for (int i = 1; i < C.size(); i++) {
            String state = "S" + i;
            ArrayList<ItemProd> productionsSee = C.get(i);
            for (ItemProd item : productionsSee) {
                if(item.isReallyInitial()){ // Determinar si es la expression expandida
                    transitions.add(new SimpleTransition(state, "SA", "$"));
                    i = C.size();
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

        // Si estamos ante un simbolo terminal entonces se aplicara closure
        if(symbol.getIsTerminal() && goToSet.size() == 1){            
            ArrayList<ItemProd> c = closure(goToSet.get(0));
            goToSet.remove(0); // Para evitar ingresar doble produccion
            for (ItemProd item : c) {
                if(!item.isReallyInitial()) goToSet.add(item);
            }
        }
        
        return goToSet;
    }

    // !!! Creo que voy a cambiar el valor de retorno y la logica para que se acomode mejor
    private int sameContentSet(ArrayList<ItemProd> setCheck){
        int num = 0;
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

            if(counter == amountProductionsState) {
                return num;
            }

            num++;
        }

        return -1; // Indica que no hay ninguna coincidencia
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

    public void seeStates(){
        for (int i = 0; i < C.size(); i++) {
            System.out.println("\n==> S" + i);
            ArrayList<ItemProd> productionsSee = C.get(i);

            for (ItemProd itemProd : productionsSee) {
                System.out.println("\t" + itemProd.getExpression());
            }
        }
    }

    public ArrayList<String> prepareContentDot(String label){
        // Metodo que nos dara una estructura para graficarlo con dot
        ArrayList<String> code = new ArrayList<String>();

        // Insertar informacion inicial
        code.add("digraph \"AUTOMATA LR0\" {\n");
        code.add("\tlabel = \"" + label + " [LR0]\"\n");
        code.add("\tlabelloc  =  t\n");
        code.add("\tfontsize  = 20\n");
        code.add("\trankdir=LR size=\"8,5\"\n");
        
        code.add("\tSA [label=\"Aceptar\", shape=\"none\"]\n");
        
        // Implementar informacion de los states
        for (int i = 0; i < C.size(); i++) {
            String state = "S" + i;
            String information = state + "\\n";
            
            ArrayList<ItemProd> productionsSee = C.get(i);
            for (ItemProd itemProd : productionsSee) {
                information += itemProd.getExpression() + "\\n";
            }
            String allinfo = state + " [label=\"" + information + "\", shape=\"box\"];";
            code.add("\t" + allinfo + "\n");
        }
        
        // Implementar transiciones para el contenido
        for (SimpleTransition transition : transitions) {
            code.add("\t" + transition.toString());
        }

        // Parte Final
        code.add("}");
        
        return code;
    }        
}
