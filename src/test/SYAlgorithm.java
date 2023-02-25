package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.lang.Character;

import controllers.ShuntingYardAlgorithm;
import middleware.Errors;

public class SYAlgorithm {

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
    */
    @Test
    public void ValidExpressions(){
        // ab*ab*
        assertEquals("ab*ab*", ShuntingYardAlgorithm.infixToPostfix("ab*ab*"));
        // 0?(1?)?0*
        assertEquals("0?1??0*", ShuntingYardAlgorithm.infixToPostfix("0?(1?)?0*"));
        // (a*|b*)c
        assertEquals("a*b*|c", ShuntingYardAlgorithm.infixToPostfix("(a*|b*)c"));
        // (b|b)*abb(a|b)*
        assertEquals("bb|*abbab|*", ShuntingYardAlgorithm.infixToPostfix("(b|b)*abb(a|b)*"));
        // (a|E)b(a+)c?
        assertEquals("aE|ba+c?", ShuntingYardAlgorithm.infixToPostfix("(a|E)b(a+)c?"));
        // (a|b)*a(a|b)(a|b)
        assertEquals("ab|*aab|ab|", ShuntingYardAlgorithm.infixToPostfix("(a|b)*a(a|b)(a|b)"));
        // a(a|b)*b
        assertEquals("aab|*b", ShuntingYardAlgorithm.infixToPostfix("a(a|b)*b"));
    }

    /**
     * ! EJEMPLOS EXPRESIONES REGULAR NO VALIDAS
     * a|
     * *a
     * |ba
     * (a|b
     * (a|b))
     * a||
     * ab|E|
     */

    /**
     * Evaluar si los checking de Chars y Difitos son validos
     */
    @Test
    public void CheckingDigits(){
        assertEquals(true, Character.isLetterOrDigit('a'));
        assertEquals(true, Character.isLetterOrDigit('0'));
        assertEquals(false, Character.isLetterOrDigit('*'));
    }

    @Test
    public void InvalidExpressions() {
        assertEquals(Errors.InvalidExpression.error, ShuntingYardAlgorithm.infixToPostfix("(1+2*9"));
    }
}
