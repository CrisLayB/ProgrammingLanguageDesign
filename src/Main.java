import views.ViewTerminal;
import controllers.ShuntingYardAlgorithm;
import controllers.ThompsonAlgorithm;
import models.NFA;
import controllers.Graphviz;;

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

    private static void makeAFN(String r, String fileDot, String outputImg) throws Exception {
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(r);

        // Mostrar Resultados de r'
        ViewTerminal.results(r, rPostfix);

        // Implementar el Algoritmo de Construccion de Thompson
        NFA nfa = ThompsonAlgorithm.constructNFA(rPostfix);

        // Escribir el codigo del grafo
        String formatedCode = Graphviz.readContentNFA(nfa);

        // Crear o sobreescribir el archivo
        if(!Graphviz.writeFileCode(formatedCode, fileDot)){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }

        // Mostrar Resultados
        
        // Crear la imagen
        Graphviz.createImgOfAutomata(fileDot, outputImg);
    }

    public static void main(String[] args) throws Exception {
        /**
         * PRUEBAS Y ENTRADAS *************************************
         * 
         * EXPRESIONES REGULARES DEL PRE LAB 'A' Y 'B'
         * ab*ab*
         * 0?(1?)?0*
         * (a*|b*)c
         * (b|b)*abb(a|b)*
         * (a|E)b(a+)c?
         * (a|b)*a(a|b)(a|b)
         * 
         * MAS EXPRESIONES:
         * a(a|b)*b
         * 
         * ! EJEMPLOS EXPRESIONES REGULAR NO VALIDAS
         * a|
         * *a
         * |ba
         * (a|b
         * (a|b))
         * a||
         * ab|E|
        */

        if (args.length == 0) {
            System.out.println("No ingresaste una expresion a la par de ejecutable");
            System.out.println("Ejemplo de como ejecutar el programa e ingresar una expresion regular:");
            System.out.println("\njava Main '(a*|b*)c'\n");
            return;
        }

        makeAFN(args[0], "src\\docs\\automata.dot", "src\\img\\results.jpg");
    }
}
