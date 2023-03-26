package algorithms;

import models.State;
import models.Types;
import models.BTreePrinter;
import models.DFA;
import models.Symbol;
import models.SyntaxTree;

import java.util.*;

public class DirectDFA {
    public static DFA regularExpressionToDFA(String r){
        Stack<Symbol> symbolsStack = new Stack<Symbol>();
        for (int i = 0; i < r.length(); i++) {
            char c = r.charAt(i);
            symbolsStack.add(new Symbol(c));
        }

        // Se agrega . y # ya que se debe de extender la expresion
        symbolsStack.add(new Symbol('#'));
        symbolsStack.add(new Symbol('.'));

        // Se creara un arbol sintatico
        SyntaxTree syntaxTree = new SyntaxTree(symbolsStack);
        
        // Mostrar como quedo el arbol sintaxico
        BTreePrinter.printNode(syntaxTree.getRoot());
        
        System.out.println("Alfabeto: ");
        for (Symbol symbol : syntaxTree.getSymbols()) {
            System.out.println(symbol.getcId());
        }
        
        // Empezar a implementar el algoritmo
        DFA dfa = new DFA(new State(1, Types.Initial));

        Set<Integer> firstpos = syntaxTree.getFirstpos(syntaxTree.getRoot());
        Set<Integer> initial = syntaxTree.getNullable(syntaxTree.getRoot()) 
            ? firstpos 
            : new HashSet<>();

        Queue<Set<Integer>> unmarked = new LinkedList<>();
        Map<Set<Integer>, Map<Character, Set<Integer>>> dTransitions = new HashMap<>();
        unmarked.offer(initial);

        while (!unmarked.isEmpty()) {
            Set<Integer> state = unmarked.poll();
            // Set<Integer> marked = new HashSet<>(state);
            for (Symbol s : syntaxTree.getSymbols()) {
                char symbol = s.getcId();
                Set<Integer> followpos = new HashSet<>();
                for (int position : state) {
                    if (syntaxTree.getVal(position) == symbol) {
                        followpos.addAll(syntaxTree.getFollowpos(position));
                    }
                }
                if (!followpos.isEmpty()) {
                    if (!dTransitions.containsKey(followpos)) {
                        dTransitions.put(followpos, new HashMap<>());
                        unmarked.offer(followpos);
                    }
                    dTransitions.get(state).put(symbol, followpos);
                }
            }
        }
        
        return dfa;
    }
}
