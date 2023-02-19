package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controllers.ShuntingYardAlgorithm;
import middleware.Errors;

public class SYAlgorithm {
    
    @Test
    public void NormalExpressions(){
        assertEquals("129*+", ShuntingYardAlgorithm.infixToPostfix("1+2*9"));
        assertEquals("12+9*", ShuntingYardAlgorithm.infixToPostfix("(1+2)*9"));
        assertEquals("5238-52^^/+", ShuntingYardAlgorithm.infixToPostfix("5+2/(3-8)^5^2"));
    }
    
    @Test
    public void RegularExpressions(){
        assertEquals("ab*|*c", ShuntingYardAlgorithm.infixToPostfix("(a*|b*)c"));
        assertEquals("a|", ShuntingYardAlgorithm.infixToPostfix("a|"));
    }

    @Test
    public void InvalidExpressions(){
        assertEquals(Errors.InvalidExpression.error, ShuntingYardAlgorithm.infixToPostfix("(1+2*9"));
    }
}
