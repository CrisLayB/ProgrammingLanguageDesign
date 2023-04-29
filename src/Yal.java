// Importar codigo java propio
import controllers.FilesCreator;
import controllers.YalChecker;
import models.Tree;
import models.PairData;
import models.Transition;
import models.NFA;
import algorithms.ShuntingYardAlgorithm;
import algorithms.ThompsonAlgorithmMega;

import models.*;
import java.util.*;

// Importar librerias de java framework collections
import java.util.ArrayList;

/**
 * <h1>Diseño De Lenguajes de Programacion - UVG</h1>
 * <h2> Yal </h2>
 * Programa donde se lleva a cabo el analisis de los archivos yal increados
 * 
 * Creado por:
 * @author Cristian Laynez - 201281
 * @since 2023
 **/

public class Yal {
    public static void main(String[] args) {
        int amountArgs = args.length;

        // Si no ingreso ningun argumento
        if (amountArgs == 0){
            System.out.println("Ejemplo de como ejecutar el programa e el nombre del archivo YALex:");
            System.out.println("\njava Yal 'src/docs/slr-1.yal'\n");
            return;
        }
        
        // * ====> Vamos a obtener todo el contenido del archivo
        ArrayList<String> yalContent = FilesCreator.readFileContent(args[0]);

        if(yalContent == null){ // Si dado caso no se detecta de fomra correcta el codigo yal
            System.out.println("\n--> No se a detectado un archivo valido\n");
            return;
        }

        System.out.println("========================================================");
        System.out.println("Contenido del archivo yal ==============================");
        System.out.println("========================================================");
        for (String string : yalContent) {
            System.out.println(string);
        }
        System.out.println("========================================================");
                
        // * ====> Obtener Regex del yal
        YalChecker tokenizer = new YalChecker(yalContent); // Procesar la data
        ArrayList<String> regex = tokenizer.process(); 
        if(regex == null){
            return;
        }
        tokenizer.seeIds(); // Ver detalles de los ids
        tokenizer.seeRules(); // Ver detalles de las reglas        
        System.out.println("Regex Expression: ");
        for (String string : regex) { // Vamos a ver toda la expresion regex
            System.out.print(string);
        }
        System.out.println("\n\nPostfix Expression: ");
        ArrayList<String> regexPostfix = ShuntingYardAlgorithm.infixToPostfix(regex);
        if(regexPostfix == null){
            System.out.println("F");
            return;
        }
        // Mostrar todas las expresions de postfix y agregar ids para cada elemento
        ArrayList<PairData<String, String>> regexExpression = new ArrayList<PairData<String, String>>();
        int idCounter = 0;        
        for (String string : regexPostfix) {
            System.out.print(string + ",");
            regexExpression.add(new PairData<String,String>("n"+idCounter, string));
            idCounter++;
        }
        regexExpression.add(new PairData<String,String>("n"+idCounter, "#"));
        idCounter++;
        regexExpression.add(new PairData<String,String>("n"+idCounter, "·"));
        System.out.println("");

        // * ====> Obtener el arbol sintatico
        Tree regexTree = new Tree(regexExpression);
        
        ArrayList<String> transitionsTree = regexTree.getTransitions();
        String scriptTree = FilesCreator.readContentTree(transitionsTree);
        String fileSintaxTree = "docs/SintaxTree.dot";
        if(!FilesCreator.writeFileCode(scriptTree, fileSintaxTree)){
            System.out.println("No se pudo guardar ni sobreescribir el archivo .dot");
            return;
        }
        FilesCreator.createDot(fileSintaxTree, "img/resultsSintaxTree.png", "-Tpng");

        // * ====> Crear un automata
        ThompsonAlgorithmMega thompsonMegaAutomata = new ThompsonAlgorithmMega(tokenizer.getIdsExtended());
        NFA megaAutomata = thompsonMegaAutomata.getMegaAutomata();
        System.out.println(megaAutomata.toString());

        // Guardar resultado de un mega automata en un pdf
        String formatedCode = FilesCreator.readContentMegaNFA(megaAutomata, "MegaAutomata [" + args[0] + "]" );
        if(!FilesCreator.writeFileCode(formatedCode, "docs/megaAutomata.dot")){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }
        FilesCreator.createDot("docs/megaAutomata.dot", "img/megaAutomata__.pdf", "-Tpdf");
                
        // * ====> Crear el scanner con todos los datos generados
        if(!FilesCreator.createScannerJava(megaAutomataCode(megaAutomata))){
            System.out.println("Un error acabo de ocurrir");
            return;
        }
        System.out.println("\nEl Scanner.java a sido creado de forma exitosa.");
    }

    private static ArrayList<String> megaAutomataCode(NFA megaAutomata){
        ArrayList<String> code = new ArrayList<String>();

        // Escribir estado inicial
        code.add(String.format("State initialState = new State(%s, %s);", megaAutomata.getStateInitial().getId(), "Types." + megaAutomata.getStateInitial().getType()));
        // Escribir los estados finales
        code.add("List<State> finalStates = new ArrayList<State>();");
        for (State fS : megaAutomata.getStatesFinal()) {  
            code.add(
                String.format(
                    "finalStates.add(new State(\"%s\", %s));", fS.getId(), "Types." + fS.getType())
            );
        }
        // Escribir todos los estados
        code.add("List<State> states = new ArrayList<State>();");
        for (State a : megaAutomata.getStates()) {
            code.add(
                String.format(
                    "states.add(new State(\"%s\", %s));", a.getId(), "Types." + a.getType())
            );
        }
        // Escribir todo el alfabeto (Symbolos)
        // code.add("List<Symbol> symbols = new ArrayList<Symbol>();");
        // for (Symbol sym : megaAutomata.getSymbols()) {
        //     code.add(
        //         String.format("symbols.add(new Symbol(\"%s\"));", sym.getStringId())
        //     );
        // }
        // Escribir todas las transiciones        
        code.add("List<Transition> transitions = new ArrayList<Transition>();");
        for (Transition t : megaAutomata.getTransitions()) {
            code.add(
                String.format(
                    "transitions.add(new Transition(new Symbol(\"%s\"), new State(\"%s\", %s), new State(\"%s\", %s)));", 
                    t.getSymbol().getStringId(), t.getStateOrigin().getId(), "Types." + t.getStateOrigin().getType(), 
                    t.getStateFinal().getId(), "Types." + t.getStateFinal().getType())
            );            
        }

        code.add("return new NFA(initialState, finalStates, states, transitions);");
        
        return code;
    }
}