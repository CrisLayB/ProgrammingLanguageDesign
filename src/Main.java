import algorithms.ShuntingYardAlgorithm;
import algorithms.ThompsonAlgorithm;
import controllers.Graphviz;
import controllers.SyntaxChecker;
import models.NFA;

/**
 * <h1>Diseño De Lenguajes de Programacion - UVG</h1>
 * <p>
 * Main
 * <p>
 * 
 * Creado por:
 * 
 * @author Cristian Laynez
 * @since 2023
 **/

public class Main {

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
        r = ShuntingYardAlgorithm.concatenate(r);
        
        // * ===========================================================================================
        // * INFIX A POSTFIX ===========================================================================
        // * ===========================================================================================
        
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(r);

        // Mostrar Resultados de r'
        System.out.println("Expresion Regular (r): " + r);
        System.out.println("Cadena (w): " + w);
        System.out.println("Nueva Expresion Regular (r'): " + rPostfix);

        // * ===========================================================================================
        // * CONSTRUCCION DE AFN (Automata Finito No Determinista) =====================================
        // * ===========================================================================================

        // Algoritmo de contruccion de Thompson
        NFA nfa = ThompsonAlgorithm.constructNFA(rPostfix);

        if(nfa == null){
            System.out.println("\nEl nfa procesador es invalido por la expresion ingresada\n");
            return;
        }

        // Mostrar Resultados
        System.out.println("\nRESULTADOS:");
        System.out.println(nfa.toString());

        // TODO: Visualización del AFN
        
        // Escribir el codigo dot del automata a refresentar
        String formatedCode = Graphviz.readContentNFA(nfa);        

        // Crear o sobreescribir el archivo
        if(!Graphviz.writeFileCode(formatedCode, "docs/automataAFN.dot")){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }
        
        // Crear la imagen para ver los resultados
        Graphviz.createImgAutomata("docs/automataAFN.dot", "img/resultsAFN.png");

        // * ===========================================================================================
        // * CONSTRUCCION DE AFD (Automata Finito Determinista) ========================================
        // * ===========================================================================================

        // Algoritmo de Construccion de Subconjuntos

        // Algoritmo de Construccion directa de AFD

        // Algoritmo de minimización de AFD
        
        // TODO: Visualización del AFD

        // * ===========================================================================================
        // * SIMULACIONES ==============================================================================
        // * ===========================================================================================

        // Simulacion de un AFN

        // Simulacion de un AFD

    }

    public static void main(String[] args) throws Exception {
        // compile("(a|b)*", "a");
        int amountArgs = args.length;

        // Si no ingreso ningun argumento
        if (amountArgs == 0) {
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
}
