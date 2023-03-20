package algorithms;

import models.State;
import models.Types;
import models.DFA;

public class DirectDFA {
    public static DFA regularExpressionToDFA(String r){
        DFA dfa = new DFA(new State(1, Types.Initial));
        return dfa;
    }
}
