import views.ViewTerminal;
import controllers.ShuntingYardAlgorithm;
import controllers.ThompsonAlgorithm;
import controllers.GenerateSymbols;
import models.Symbol;

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

    private static void makeAFN(String r) throws Exception {
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(r);

        // Mostrar Resultados de r'
        ViewTerminal.results(r, rPostfix);

        // Obtener todos los simbolos de la forma correspondiente
        Symbol[] symbols = GenerateSymbols.getSymbols(rPostfix);

        // Implementar el Algoritmo de Construccion de Thompson
        ThompsonAlgorithm.constructNFA(symbols);

        // Mostrar Resultados
        // ...
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
         * EJEMPLO EXPRESION REGULAR NO VALIDA
         * a|
         * 
         * MAS EXPRESIONES:
         * a?(a+b)*?b
         */

        if (args.length == 0) {
            System.out.println("No ingresaste una expresion a la par de ejecutable");
            System.out.println("Ejemplo de como ejecutar el programa e ingresar una expresion regular:");
            System.out.println("\njava Main '(a*|b*)c'\n");
            return;
        }

        makeAFN(args[0]);
    }
}
