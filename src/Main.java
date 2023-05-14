import algorithms.MinimizationDFA;
import algorithms.ShuntingYardAlgorithm;
import algorithms.ThompsonAlgorithm;
import automatas.DFA;
import automatas.NFA;
import controllers.FilesCreator;
import controllers.SyntaxChecker;
import models.PairData;
import models.Tree;

import java.util.ArrayList;

/**
 * <h1>Diseño De Lenguajes de Programacion - UVG</h1>
 * <h2> Main </h2>
 * Programa donde se lleva a cabo la creacion de automatas finitos.
 * 
 * Creado por:
 * @author Cristian Laynez - 201281
 * @since 2023
 **/

public class Main {

    public static void main(String[] args) throws Exception {
        int amountArgs = args.length;

        // Si no ingreso ningun argumento
        if (amountArgs == 0) { // Caracter Epsilon: ε y si ascii es 949
            System.out.println("No ingresaste una expresion regular y tampoco una cadena de aceptacion a la par de ejecutable");
            System.out.println("Ejemplo de como ejecutar el programa e ingresar una expresion regular:");
            System.out.println("\njava Main '(a*|b*)c' 'abc'\n");
            return;
        }

        // Si solo le falto ingresar una cadena de aceptacion
        if (amountArgs == 1){
            System.out.println("¡Hey! Te falto ingresar una cadena de aceptacion");
            System.out.println("\njava Main '(a*|b*)c' 'abc'\n");
            return;
        }

        String r = args[0];
        String w = args[1];
        compile(r, w);
    }
    
    private static void compile(String r, String w) throws Exception {
        // Se evaluara los errores sintaxicos de la expresion antes de continuar
        String checkExpression = SyntaxChecker.checkExpression(r);
        if(checkExpression.length() != 0){
            System.out.println("\u001B[31m" + "------------------------------------------------");
            System.out.println("\nERRORES ENCONTRADOS:\n" + checkExpression);
            System.out.println("------------------------------------------------" + "\u001B[0m");
            return;
        }
        
        // Concatenar la expresion para obtener las concatenaciones correctas
        String rConcat = ShuntingYardAlgorithm.concatenate(r);
        
        // * ===========================================================================================
        // * INFIX A POSTFIX ===========================================================================
        // * ===========================================================================================
        
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(rConcat);

        // Mostrar Resultados de r'
        System.out.println("Expresion Regular (r): " + r);
        System.out.println("Cadena (w): " + w);
        System.out.println("Nueva Expresion Regular (r'): " + rPostfix);

        // * ===========================================================================================
        // * CONSTRUCCION DE AFN (Automata Finito No Determinista) =====================================
        // * ===========================================================================================

        // Algoritmo de contruccion de Thompson
        NFA nfa = ThompsonAlgorithm.constructNFA(rPostfix);
        System.out.println(nfa.toString());
        String formatedCode = FilesCreator.readContentNFA(nfa, r);        
        if(!FilesCreator.writeFileCode(formatedCode, "docs/automataAFN.dot")){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }        
        FilesCreator.createDot("docs/automataAFN.dot", "img/resultsAFN.png", "-Tpng");
        FilesCreator.createDot("docs/automataAFN.dot", "img/resultsAFN.pdf", "-Tpdf");

        // * ===========================================================================================
        // * CONSTRUCCION DE AFD (Automata Finito Determinista) ========================================
        // * ===========================================================================================

        // Algoritmo de Construccion de Subconjuntos
        DFA dfaSubsetConstruction = new DFA(nfa);
        System.out.println(dfaSubsetConstruction.toString());
        String formatedCodeDFA = FilesCreator.readContentDFA(dfaSubsetConstruction, r);
        if(!FilesCreator.writeFileCode(formatedCodeDFA, "docs/automataAFD.dot")){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }
        FilesCreator.createDot("docs/automataAFD.dot", "img/resultsAFD.png", "-Tpng");

        // * ===========================================================================================
        // * CONSTRUCCION AFD DIRECTO (Automata Finito Determinista Directo) ===========================
        // * ===========================================================================================

        System.out.println("\nConstruccion afd direct (y arbol)");

        ArrayList<PairData<String, String>> regexExpression = new ArrayList<PairData<String, String>>();
        int idCounter = 0;        
        for (int i = 0; i < rPostfix.length(); i++) {
            String string = rPostfix.charAt(i) + "";
            regexExpression.add(new PairData<String,String>("n"+idCounter, string));
            idCounter++;
        }
        regexExpression.add(new PairData<String,String>("n"+idCounter, "#"));
        idCounter++;
        regexExpression.add(new PairData<String,String>("n"+idCounter, "·"));
        
        Tree treePostfix = new Tree(regexExpression);

        // Vamos a guardar en una imagen los resultados del arbol
        ArrayList<String> transitionsTree = treePostfix.getTransitions();
        String scriptTree = FilesCreator.readContentTree(transitionsTree);
        String fileSintaxTree = "docs/SintaxTree.dot";
        if(!FilesCreator.writeFileCode(scriptTree, fileSintaxTree)){
            System.out.println("No se pudo guardar ni sobreescribir el archivo .dot");
            return;
        }
        FilesCreator.createDot(fileSintaxTree, "img/resultsSintaxTreeNormal.png", "-Tpng");
        
        DFA dfaDirect = new DFA(treePostfix);
        System.out.println(dfaDirect.toString());
        
        // * ===========================================================================================
        // * ALGORITMO DE MINIMIZACION DE AFD ==========================================================
        // * ===========================================================================================
        
        // Algoritmo de Hopcroft
        System.out.println("\nDFA Minimizado");
        DFA dfaMini = MinimizationDFA.minimizingDFA(dfaSubsetConstruction);
        System.out.println(dfaMini.toString());
        
        // * ===========================================================================================
        // * SIMULACIONES ==============================================================================
        // * ===========================================================================================

        // Simulacion de un AFN
        String simAFN = (nfa.simulate(w))
            ? "La cadena " + w + " *SI* es parte de la lenguaje generado [NFA Simulation]" 
            : "La cadena " + w + " *NO* es parte de la lenguaje generado [NFA Simulation]";
        System.out.println(simAFN);

        // Simulacion de un AFD
        String simDFA1 = (dfaSubsetConstruction.simulate(w))
            ? "La cadena " + w + " *SI* es parte de la lenguaje generado [DFA Simulation]" 
            : "La cadena " + w + " *NO* es parte de la lenguaje generado [DFA Simulation]";
        System.out.println(simDFA1);
    }    
}
