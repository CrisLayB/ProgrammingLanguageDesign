package models;

import java.util.ArrayList;

import enums.AsciiSymbol;

public class ItemProd implements Cloneable { // Esta clase representara una lista de varias producciones
    private Symbol idInitial;
    private ArrayList<Symbol> initial;
    private ArrayList<Symbol> elements;
    private final String DOT_SYMBOL = AsciiSymbol.Dot.c + "";

    // Constructor
    public ItemProd(ArrayList<Symbol> initial, ArrayList<Symbol> elements){
        this.initial = initial;
        this.elements = elements;
        idInitial = this.initial.get(0);
    }
    
    // Getters    
    public Symbol getIdInitial() {
        return idInitial;
    }
    
    public ArrayList<Symbol> getInitial() {
        return initial;
    }

    public ArrayList<Symbol> getElements() {
        return elements;
    }

    // Special Methods
    public String getInitialExpression(){
        String expression = "";

        for (Symbol symbol : initial) {
            expression += symbol.getStringId();
        }
        
        return expression;
    }

    public String getElementsExpression(){
        String expression = "";

        for (Symbol symbol : elements) {
            expression += symbol.getStringId();
        }

        return expression;
    }
    
    public String getExpression() {
        String expression = "";

        expression += getInitialExpression();

        expression += AsciiSymbol.Arrow.c;

        expression += getElementsExpression();
        
        return expression;
    }
    
    public boolean isReallyInitial(){
        return (initial.size() > 1);
    }

    public Symbol getFirstElement(){
        if(elements.size() == 0) return null;
        return elements.get(0);
    }

    public boolean isComplete(){
        Symbol lastSymbol = elements.get(elements.size() - 1);
        String isDot = lastSymbol.getStringId();        
        return (isDot.equals(DOT_SYMBOL));
    }

    public Symbol getNextProduction(){
        for (int i = 0; i < elements.size(); i++) {
            Symbol s = elements.get(i);
            if(s.getStringId().equals(DOT_SYMBOL)){
                // Tenemos que verificar que exista un elemento           
                if(i+1 >= elements.size()) continue;   
                return elements.get(i+1);
            }
        }
        
        return null;
    }

    // Métodos
    public void insertDot(){
        if(elements.get(0).getStringId().equals(DOT_SYMBOL)) return;
        elements.add(0, new Symbol(DOT_SYMBOL));
    }

    public boolean moveDot(Symbol sFinded){        
        int posDot = -1; // Buscar el punto
        for (int i = 0; i < elements.size(); i++) {
            Symbol checkSymbol = elements.get(i);

            if(checkSymbol.getStringId().equals(DOT_SYMBOL)){
                posDot = i;
                i = elements.size();
            }
        }
        
        if(posDot == -1 || posDot >= elements.size() - 1) return false;

        Symbol nextSymbol = elements.get(posDot + 1); // Obtener el siguiente simbolo
        if(!sFinded.getStringId().equals(nextSymbol.getStringId())) return false;
        
        elements.remove(posDot); // Eliminar el punto        
        elements.add(posDot + 1, new Symbol(DOT_SYMBOL));    
        return true;
    }
}
