import models.*;
import algorithms.*;
import controllers.*;
import java.util.*;

public class DirtyTest {

    private static char epsilon = 'E';
    
    public static void main(String[] args) {
        System.out.println("PRUEBAS SHUCAS");
        NFA nfaTesting = new NFA(new Symbol((int)'a', 'a'), new State(6, Types.Initial), new State(9, Types.Final));
        nfaTesting.addSymbol(new Symbol((int)'b', 'b'));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(6, Types.Initial), new State(4, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(4, Types.Initial), new State(0, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(4, Types.Initial), new State(2, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)'a', 'a'), new State(0, Types.Initial), new State(1, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)'b', 'b'), new State(2, Types.Initial), new State(3, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(1, Types.Initial), new State(5, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(3, Types.Initial), new State(5, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(5, Types.Initial), new State(4, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)epsilon, epsilon), new State(5, Types.Initial), new State(7, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)'a', 'a'), new State(7, Types.Initial), new State(8, Types.Transition)));
        nfaTesting.addTransition(new Transition(new Symbol((int)'b', 'b'), new State(8, Types.Initial), new State(9, Types.Transition)));

        System.out.println(nfaTesting.toString());
        DFA dfa = SubsetConstruction.nfaToDfa(nfaTesting);
        System.out.println(dfa.toString());

        String formatedCodeDFA = Graphviz.readContentDFA(dfa, "(a|b)*ab");

        if(!Graphviz.writeFileCode(formatedCodeDFA, "docs/automataAFD.dot")){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }

        Graphviz.createImgAutomata("docs/automataAFD.dot", "img/resultsAFD.png");
    }
}
