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
        // Concatenar la expresion para obtener las concatenaciones correctas
        r = ShuntingYardAlgorithm.concatenate(r);
        
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(r);

        // Mostrar Resultados de r'
        ViewTerminal.results(r, rPostfix);

        // Implementar el Algoritmo de Construccion de Thompson
        NFA nfa = ThompsonAlgorithm.constructNFA(rPostfix);

        if(nfa == null){
            System.out.println("\nEl nfa procesador es invalido por la expresion ingresada\n");
            return;
        }

        // Mostrar Resultados
        System.out.println("RESULTADOS:\n");
        System.out.println(nfa.toString());

        // Escribir el codigo del grafo
        String formatedCode = Graphviz.readContentNFA(nfa);        

        // Crear o sobreescribir el archivo
        if(!Graphviz.writeFileCode(formatedCode, fileDot)){
            System.out.println("No se pudo guardar el archivo.dot");
            return;
        }
        
        // Crear la imagen
        Graphviz.createImgOfAutomata(fileDot, outputImg);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("No ingresaste una expresion a la par de ejecutable");
            System.out.println("Ejemplo de como ejecutar el programa e ingresar una expresion regular:");
            System.out.println("\njava Main '(a*|b*)c'\n");
            return;
        }

        makeAFN(args[0], "docs\\automataAFN.dot", "img\\resultsAFN.jpg");
    }
}
