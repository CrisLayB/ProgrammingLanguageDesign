import views.ViewTerminal;
import controllers.ShuntingYardAlgorithm;

/**
 * <h1> Diseño De Lenguajes de Programacion - UVG </h1>
 * <p> Main <p>
 * 
 * Creado por:
 * @author Cristian Laynez
 * @since 2023
 **/    

public class Main {

    private static void app() throws Exception{
        ViewTerminal.welcome();

        String r = ViewTerminal.readRegularExpresion();
        
        // Implementar el Algoritmo Shunting Yard para obtener R'
        String rPostfix = ShuntingYardAlgorithm.infixToPostfix(r);

        // Mostrar Resultados de r'
        ViewTerminal.results(r, rPostfix);

        // Implementar el Algoritmo de Construccion de Thompson
        // ...

        // Mostrar Resultados
        // ...
    }
    
    public static void main(String[] args) throws Exception{
        /**
         * PRUEBAS Y ENTRADAS *************************************
         * 
         * EXPRESIONES REGULARES DEL PRE LAB 'A' Y 'B'
         *      ab*ab*
         *      0?(1?)?0*
         *      (a*|b*)c
         *      (b|b)*abb(a|b)*
         *      (a|E)b(a+)c?
         *      (a|b)*a(a|b)(a|b)
         * 
         * EJEMPLO EXPRESION REGULAR NO VALIDA
         *      a|
         */

        app();
    }
}
