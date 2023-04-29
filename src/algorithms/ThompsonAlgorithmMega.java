package algorithms;

import models.NFA;
import models.State;
import models.Types;
import models.Transition;
import models.Symbol;

import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;

// De antemano pido disculpas ya que esta clase será casi lo mismo que "ShuntingYardAlgorithm"
public class ThompsonAlgorithmMega {
    
    // Atributos
    private NFA megaAutomata;
    private int idControl;
    private Map<String, ArrayList<String>> ids;
    private final static char epsilon = '€';

    // Constructor
    public ThompsonAlgorithmMega(Map<String, ArrayList<String>> ids){
        this.ids = ids;
        idControl = -1;
        // Contruct and prepare everything
        preparingMegaAutomata();
    }

    // Getters
    public NFA getMegaAutomata() {
        return megaAutomata;
    }

    // Methods for Contructor
    private void preparingMegaAutomata(){
        // Crear estado inicial
        State initialStateMegaAutomata = new State(++idControl, Types.Initial);
        megaAutomata = new NFA(initialStateMegaAutomata);
        
        for(Map.Entry<String, ArrayList<String>> id: ids.entrySet()){
            String idName = id.getKey();
            ArrayList<String> idRegularExpression = id.getValue();

            // Obtener postfix
            ArrayList<String> regexPostfix = ShuntingYardAlgorithm.infixToPostfix(idRegularExpression);
            System.out.println("===> " + idName + " - " + regexPostfix.toString());
            
            // Obtener el NFA
            NFA nfa = constructNFA(regexPostfix);
            for (Transition transition : nfa.getTransitions()) {
                megaAutomata.addTransition(transition);
            }
            megaAutomata.addFinalState(nfa.getStateFinal());
            Transition newTransition = new Transition(
                new Symbol(epsilon+""), 
                initialStateMegaAutomata, 
                nfa.getStateInitial()
            );
            megaAutomata.addTransition(newTransition);
        }
    }

    private NFA constructNFA(ArrayList<String> postfix){
        Stack<NFA> stack = new Stack<>();

        for (String stringSymbol : postfix) {
            if(stringSymbol.equals("*")){
                NFA nfaKleen = stack.pop();
                stack.add(kleene(nfaKleen));
            }
            else if(stringSymbol.equals("+")){
                NFA nfaOriginal = stack.pop();             
                NFA nfaToKleene = new NFA(nfaOriginal);
                idControl += nfaToKleene.amountStates(); // Get original number id
                stack.add(concatenate(nfaOriginal, kleene(nfaToKleene)));
            }
            else if(stringSymbol.equals("·")){
                NFA nfaDot2 = stack.pop();
                NFA nfaDot1 = stack.pop();
                stack.add(concatenate(nfaDot1, nfaDot2));  
            }
            else if(stringSymbol.equals("?")){
                NFA nfaQuestion = stack.pop();
                stack.add(question(nfaQuestion));
            }
            else if(stringSymbol.equals("|")){
                NFA nfaOr2 = stack.pop();
                NFA nfaOr1 = stack.pop();
                stack.add(or(nfaOr1, nfaOr2));
            }
            else{
                stack.add(createNFA(stringSymbol));
            }
        }
        
        return stack.pop();
    }

    private NFA createNFA(String c){
        Symbol symbol = new Symbol(c);
        idControl++;
        State state1 = new State(idControl, Types.Initial);
        idControl++;
        State state2 = new State(idControl, Types.Final);
        // Crear el nuevo NFA
        NFA nfaSymbol = new NFA(symbol, state1, state2);
        nfaSymbol.addSymbol(symbol);
        return nfaSymbol;
    }

    private NFA concatenate(NFA nfa1, NFA nfa2){
        // Se copiara toda la data para tener toda la informacion del nfa2
        State tempState = nfa1.getStateFinal();
        State tempFinalState = nfa2.getStateFinal();
        nfa1.getStateFinal().setType(Types.Transition);
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            nfa1.addTransition(transition);
            nfa1.addState(transition.getStateOrigin());
            nfa1.addState(transition.getStateFinal());      
            nfa1.addSymbol(transition.getSymbol());
        }        
        nfa1.setStateFinal(tempFinalState);
        Transition transition = new Transition(new Symbol(epsilon+""), tempState, nfa2.getStateInitial());
        nfa1.addTransition(transition);
        return nfa1;
    }

    private NFA kleene(NFA nfa){
        nfa.getStateInitial().setType(Types.Transition);
        nfa.getStateFinal().setType(Types.Transition);
        // Crear la transicion que conecta con el estado final ε inicial        
        Symbol epsilonSymbol = new Symbol(epsilon+"");
        Transition transitionBetween = new Transition(epsilonSymbol, nfa.getStateFinal(), nfa.getStateInitial());
        nfa.addTransition(transitionBetween);        
        // Crear los nuevos estados origen y finales
        idControl++;
        State newStateOrigin = new State(idControl, Types.Initial);
        idControl++;
        State newStateFinal = new State(idControl, Types.Final);

        // Ahora tocara que crear tres nuevas transiciones
        Transition transitionNewOrigin = new Transition(epsilonSymbol, newStateOrigin, newStateFinal);
        Transition transitionToOldOrigin = new Transition(epsilonSymbol, newStateOrigin, nfa.getStateInitial());
        Transition transitionToOldFinal = new Transition(epsilonSymbol, nfa.getStateFinal(), newStateFinal);

        // Actualizar toda la informacion
        nfa.addTransition(transitionNewOrigin);
        nfa.addTransition(transitionToOldOrigin);
        nfa.addTransition(transitionToOldFinal);
        // Actualizar los nuevos estados origen
        nfa.setStateInitial(newStateOrigin);
        nfa.setStateFinal(newStateFinal);
        // Agregar los estaods en la lista
        nfa.addState(newStateOrigin);
        nfa.addState(newStateFinal);        
        return nfa;
    }

    public NFA or(NFA nfa1, NFA nfa2){
        nfa1.getStateFinal().setType(Types.Transition);
        for (Transition transition : nfa2.getTransitions()) {
            transition.changeTypeStateOrigin(Types.Transition);
            nfa1.addTransition(transition);
            nfa1.addState(transition.getStateOrigin());
            nfa1.addState(transition.getStateFinal());         
            nfa1.addSymbol(transition.getSymbol());   
        }        

        // Crear nuevo estado origen y final
        idControl++;
        State stateOrigin = new State(idControl, Types.Initial);
        idControl++;
        State stateFinal = new State(idControl, Types.Final);
        
        // Crear nuevas transiciones
        Transition transitionOriginUp = new Transition(new Symbol(epsilon+""), stateOrigin, nfa1.getStateInitial());
        Transition transitionOriginDown = new Transition(new Symbol(epsilon+""), stateOrigin, nfa2.getStateInitial());

        Transition transitionFinalUp = new Transition(new Symbol(epsilon+""), nfa1.getStateFinal(), stateFinal);
        Transition transitionFinalDown = new Transition(new Symbol(epsilon+""), nfa2.getStateFinal(), stateFinal);
        
        // Agregar nuevas transiciones
        nfa1.addTransition(transitionOriginUp);
        nfa1.addTransition(transitionOriginDown);
        nfa1.addTransition(transitionFinalUp);
        nfa1.addTransition(transitionFinalDown);
        
        // Actualizar informacion
        nfa1.setStateInitial(stateOrigin);
        nfa1.setStateFinal(stateFinal);

        nfa1.convertAllStatesToTransitions();

        // Agregar estados del or
        nfa1.addState(stateOrigin);
        nfa1.addState(stateFinal);

        return nfa1;
    }

    public NFA question(NFA nfa1){
        NFA nfa2 = createNFA(epsilon+"");
        return or(nfa1, nfa2);
    }
    
}
