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
    private String[][] parsingTable;
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
        constructAutomata();
        constructParsingTable();
    }

    private void constructAutomata(){
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
                int sameContent = findSet(G);
                if(G.size() != 0 && sameContent == -1){
                    C.add((++count), G); // Agregar a C                    
                    queue.offer(copyItems(G)); // Agregar nuevos items al queue
                    transitions.add(new SimpleTransition("S" + counter, "S" + count, X.getStringId()));
                }
                else if(G.size() != 0 && sameContent != -1){
                    transitions.add(new SimpleTransition("S" + counter, "S" + sameContent, X.getStringId()));
                }
            }
        }

        // * Ingresar transicion para estado de aceptacion
        symbols.add(new Symbol("$", 1));
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

    private int findSet(ArrayList<ItemProd> compareSet){
        for (int i = 0; i < C.size(); i++) {
            ArrayList<ItemProd> itemProd = C.get(i);
            if(itemProd.size() == compareSet.size()){
                boolean next = false;
                for (int j = 0; j < compareSet.size(); j++) {
                    ItemProd itemI = itemProd.get(j);
                    ItemProd itemJ = compareSet.get(j);
                    
                    if(!itemI.getExpression().equals(itemJ.getExpression())) next = true;
                }
                if(next) continue;
                return i;
            }
        }
        
        return -1;
    }

    private ArrayList<Symbol> first(Symbol symbol){
        ArrayList<Symbol> firstSet = new ArrayList<Symbol>();

        return firstSet;
    }

    private ArrayList<Symbol> follow(Symbol symbol){
        ArrayList<Symbol> followSet = new ArrayList<Symbol>();

        return followSet;
    }

    private void constructParsingTable() {
        int numStates = C.size() + 1;
        int numSymbols = symbols.size() + 1;
        parsingTable = new String[numStates][numSymbols];

        // * ==> Vamos a preparar la informacion de las columnas
        parsingTable[0][0] = "STATE";
        ArrayList<Symbol> terminalSymbols = new ArrayList<Symbol>();
        ArrayList<Symbol> noTerminalSymbols = new ArrayList<Symbol>();

        for (Symbol s : symbols) {
            if(s.getIsTerminal()){
                terminalSymbols.add(s);
            }
            else{
                noTerminalSymbols.add(s);
            }
        }
    
        int numControl = 1;
        for (Symbol s : terminalSymbols) {
            parsingTable[0][numControl] = s.getStringId();
            numControl++;
        }

        // Guardar numero del dolar
        int dolarNum = numControl - 1;

        for (Symbol s : noTerminalSymbols) {
            parsingTable[0][numControl] = s.getStringId();
            numControl++;
        }

        for (int i = 1; i < numStates; i++) {
            parsingTable[i][0] = (i - 1) + "";
        }
        
        // * ==> Se empezara a contruir la tabla de parsing
        // ! IMPORTATE:
        // !        SUMAR si se ingresara de forma correcta un dato de una COLUMNA
        // !        RESTAR si se ingresara de forma correcta un dato de una FILA
        for (int state = 1; state < numStates; state++) {
            ArrayList<ItemProd> items = C.get(state - 1);
            for (ItemProd item : items) {
                if (item.isComplete()) {
                    if (item.getInitialExpression().equals(productions.get(0).getInitialExpression())){
                        // Accion de Aceptacion para el item inicial
                        parsingTable[state][dolarNum] = "ACCEPT";
                    }
                    else{
                        // Accion de Reducion para el item completo
                        // int productionIndex = productions.indexOf(item.getInitial());
                        // ArrayList<Symbol> followSet = follow(item.getInitial().get(0));
                        // for (Symbol followSymbol : followSet) {
                        //     int symbolIndex = symbols.indexOf(followSymbol);
                        //     parsingTable[state + 1][symbolIndex - 1] = "r" + productionIndex;
                        // }
                    }
                }
                else{
                    Symbol nextSymbol = item.getNextProduction();
                    // Acción de cambio para el simbolo dado
                    int symbolIndex = getIndexSymbol(nextSymbol);
                    String nextState = getGoTo(state - 1, nextSymbol.getStringId());
                    String test = (nextState == null) ? "S?" : nextState;
                    parsingTable[state][symbolIndex] = test;
                }
            }
        }
    }
    
    private int getIndexSymbol(Symbol symbol){
        String symbolStr = symbol.getStringId();
        for (int i = 0; i < parsingTable[0].length; i++) {
            if(symbolStr.equals(parsingTable[0][i])) return i;
        }
        return -1;
    }

    private String getGoTo(int state, String symbol){
        for (SimpleTransition transition : transitions) {
            if (transition.getStateOrigin().equals("S" + state) && transition.getSymbol().equals(symbol)) {
                String destinationState = transition.getStateFinal();
                return destinationState;
            }
        }
        return null;
    }

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

    public void seeParsingTable(){
        for (String[] row : parsingTable) {
            for (String data : row) {           
                System.out.print(data + "\t\t");
            }
            System.out.println();
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
