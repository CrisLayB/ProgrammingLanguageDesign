package views;

import java.util.Scanner;

import middleware.Errors;

public class ViewTerminal {
    private static Scanner scan = new Scanner(System.in);
    
    public static void welcome(){
        System.out.println("\nDISENIO DE LENGUAJES DE PROGRAMACION\n");
    }
    
    public static String readRegularExpresion(){
        System.out.println("Porfavor ingrese una expresion regular:");
        return scan.nextLine();
    }    

    public static void results(String r, String rPostfix) throws Exception{
        System.out.println("Expresion Regular (r): " + r);

        if(rPostfix.equals(Errors.InvalidExpression.error)){
            System.out.println(rPostfix);
            throw new Exception(Errors.InvalidExpression.error);
        }
        System.out.println("Nueva Expresion Regular (r'): " + rPostfix);
    }
}
