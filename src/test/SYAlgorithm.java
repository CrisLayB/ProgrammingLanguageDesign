package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import algorithms.ShuntingYardAlgorithm;

import java.lang.Character;

public class SYAlgorithm {
    @Test
    public void Concatenate(){
        assertEquals("a·b*·a·b*", ShuntingYardAlgorithm.concatenate("ab*ab*"));
        assertEquals("0?·(1?)?·0*", ShuntingYardAlgorithm.concatenate("0?(1?)?0*"));
        assertEquals("(a*|b*)·c", ShuntingYardAlgorithm.concatenate("(a*|b*)c"));
        assertEquals("(b|b)*·a·b·b·(a|b)*", ShuntingYardAlgorithm.concatenate("(b|b)*abb(a|b)*"));
        assertEquals("(a|E)·b·(a+)·c?", ShuntingYardAlgorithm.concatenate("(a|E)b(a+)c?"));
        assertEquals("(a|b)*·a·(a|b)·(a|b)", ShuntingYardAlgorithm.concatenate("(a|b)*a(a|b)(a|b)"));
        assertEquals("a·(a|b)*·b", ShuntingYardAlgorithm.concatenate("a(a|b)*b"));
    }
    
    @Test
    public void ExpressionsComplete(){
        assertEquals("ab*·a·b*·", ShuntingYardAlgorithm.infixToPostfix("a·b*·a·b*"));
        assertEquals("0?1??·0*·", ShuntingYardAlgorithm.infixToPostfix("0?·(1?)?·0*"));
        assertEquals("a*b*|c·", ShuntingYardAlgorithm.infixToPostfix("(a*|b*)·c"));
        assertEquals("bb|*a·b·b·ab|*·", ShuntingYardAlgorithm.infixToPostfix("(b|b)*·a·b·b·(a|b)*"));
        assertEquals("aE|b·a+·c?·", ShuntingYardAlgorithm.infixToPostfix("(a|E)·b·(a+)·c?"));
        assertEquals("ab|*a·ab|·ab|·", ShuntingYardAlgorithm.infixToPostfix("(a|b)*·a·(a|b)·(a|b)"));
        assertEquals("aab|*·b·", ShuntingYardAlgorithm.infixToPostfix("a·(a|b)*·b"));
    }

    /**
     * Evaluar si los checking de Chars y Difitos son validos
     */
    @Test
    public void CheckingDigits(){
        assertEquals(true, Character.isLetterOrDigit('a'));
        assertEquals(true, Character.isLetterOrDigit('0'));
        assertEquals(false, Character.isLetterOrDigit('*'));
    }
}
