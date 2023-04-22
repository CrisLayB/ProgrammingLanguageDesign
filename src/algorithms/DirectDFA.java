package algorithms;

import models.State;
import models.Types;
import models.BTreePrinter;
import models.DFA;
import models.Symbol;
import models.Tree;
import models.SyntaxTree;
import models.PairData;

import java.util.ArrayList;

import controllers.AdminFiles;

public class DirectDFA {
    public static DFA regularExpressionToDFA(String regularExpression){
        // Expression regular de forma extendida
        regularExpression += "#";
        regularExpression += "·";
        
        ArrayList<PairData<String, String>> regexExpression = new ArrayList<PairData<String, String>>();
        int idCounter = 0;        
        for (int i = 0; i < regularExpression.length(); i++) {
            String string = regularExpression.charAt(i) + "";
            regexExpression.add(new PairData<String,String>("n"+idCounter, string));
            idCounter++;
        }
        
        // Vamos a crear el arbol
        Tree regexTree = new Tree();
        regexTree.createSyntaxTree(regexExpression);
        
        // Obtener nullable, firstPos, lastPos
        regexTree.postorderTraversal(regexTree.getRoot());

        regexTree.generateTransitions(regexTree.getRoot());

        // Solo por fines autodidactas personales imprimire el arbol para mi
        ArrayList<String> transitionsTree = regexTree.getTransitions();
        String scriptTree = AdminFiles.readContentTree(transitionsTree);
        String fileSintaxTree = "docs/SintaxTree.dot";
        if(!AdminFiles.writeFileCode(scriptTree, fileSintaxTree)){
            System.out.println("No se pudo guardar ni sobreescribir el archivo .dot");
            return null;
        }
        AdminFiles.createImgDot(fileSintaxTree, "img/resultsSintaxTreeRegex.png");

        // Vamos a implementar los nullabes
        

        // Empezar a implementar el algoritmo
        DFA dfa = new DFA(new State(0, Types.Initial));
        
        return dfa;
    }
}
