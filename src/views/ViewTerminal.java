package views;

import middleware.Errors;

public class ViewTerminal {
    public static void results(String r, String rPostfix) throws Exception {
        System.out.println("Expresion Regular (r): " + r);

        if (rPostfix.equals(Errors.InvalidExpression.error)) {
            System.out.println(Errors.InvalidExpression.error);
            return;
        }
        System.out.println("Nueva Expresion Regular (r'): " + rPostfix);
    }
}
