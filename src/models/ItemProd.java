package models;

import java.util.ArrayList;

import enums.AsciiSymbol;

public class ItemProd { // Esta clase representara una lista de varias producciones
    private String expression;
    private Symbol idInitial;
    private ArrayList<Symbol> initial;
    private ArrayList<Symbol> elements;

    // Constructor
    public ItemProd(String expression, ArrayList<Symbol> initial, ArrayList<Symbol> elements){
        this.expression = expression;
        this.initial = initial;
        this.elements = elements;
        idInitial = this.initial.get(0);
    }

    // Getters
    public String getExpression() {
        return expression;
    }

    public Symbol getIdInitial() {
        return idInitial;
    }
    
    public ArrayList<Symbol> getInitial() {
        return initial;
    }

    public ArrayList<Symbol> getElements() {
        return elements;
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
                return elements.get(i+1);
            }
        }
        
        return null;
    }

    // Métodos
    public void insertDot(){
        if(elements.get(0).getStringId().equals(AsciiSymbol.Dot.c + "")) return;
        
        elements.add(0, new Symbol(AsciiSymbol.Dot.c + ""));
        
        rewriteExpression();
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
        
        if(posDot == -1 || posDot == elements.size() - 1) return false;

        Symbol nextSymbol = elements.get(posDot + 1); // Obtener el siguiente simbolo
        if(!sFinded.getStringId().equals(nextSymbol.getStringId())) return false;
        
        elements.remove(posDot); // Eliminar el punto        
        elements.add(posDot + 1, new Symbol(AsciiSymbol.Dot.c + ""));
        rewriteExpression();
        return true;
    }

    private void rewriteExpression(){
        expression = ""; // Reiniciar expression

        for (Symbol symbol : initial) {
            expression += symbol.getStringId();
        }

        expression += AsciiSymbol.Arrow.c;
        
        for (Symbol symbol : elements) {
            expression += symbol.getStringId();
        }
    }
}
