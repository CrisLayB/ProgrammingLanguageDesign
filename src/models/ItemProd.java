package models;

import java.util.ArrayList;

import enums.AsciiSymbol;

public class ItemProd implements Cloneable { // Esta clase representara una lista de varias producciones
    private Symbol idInitial;
    private ArrayList<Symbol> initial;
    private ArrayList<Symbol> elements;

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
    public String getExpression() {
        String expression = "";

        for (Symbol symbol : initial) {
            expression += symbol.getStringId();
        }

        expression += AsciiSymbol.Arrow.c;

        for (Symbol symbol : elements) {
            expression += symbol.getStringId();
        }
        
        return expression;
    }
    
    public boolean isReallyInitial(){
        return (initial.size() > 1);
    }

    public Symbol getFirstElement(){
        if(elements.size() == 0) return null;
        return elements.get(0);
    }

    public Symbol getNextProduction(){
        for (int i = 0; i < elements.size(); i++) {
            Symbol s = elements.get(i);
            if(s.getStringId().equals(AsciiSymbol.Dot.c + "")){
                // Tenemos que verificar que exista un elemento           
                if(i+1 >= elements.size()) continue;   
                return elements.get(i+1);
            }
        }
        
        return null;
    }

    // Métodos
    public void insertDot(){
        if(elements.get(0).getStringId().equals(AsciiSymbol.Dot.c + "")) return;
        elements.add(0, new Symbol(AsciiSymbol.Dot.c + ""));
    }

    public boolean moveDot(Symbol sFinded){        
        int posDot = -1; // Buscar el punto
        for (int i = 0; i < elements.size(); i++) {
            Symbol checkSymbol = elements.get(i);

            if(checkSymbol.getStringId().equals(AsciiSymbol.Dot.c + "")){
                posDot = i;
                i = elements.size();
            }
        }
        
        if(posDot == -1 || posDot >= elements.size() - 1) return false;

        Symbol nextSymbol = elements.get(posDot + 1); // Obtener el siguiente simbolo
        if(!sFinded.getStringId().equals(nextSymbol.getStringId())) return false;
        
        elements.remove(posDot); // Eliminar el punto        
        elements.add(posDot + 1, new Symbol(AsciiSymbol.Dot.c + ""));    
        return true;
    }
}
