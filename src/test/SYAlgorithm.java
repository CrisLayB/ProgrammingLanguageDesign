package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controllers.ShuntingYardAlgorithm;
import middleware.Errors;

public class SYAlgorithm {
    
    // assertEquals("", ShuntingYardAlgorithm.infixToPostfix(""));
    
    @Test
    public void NormalExpressions(){
        assertEquals("129*+", ShuntingYardAlgorithm.infixToPostfix("1+2*9"));
        assertEquals("12+9*", ShuntingYardAlgorithm.infixToPostfix("(1+2)*9"));
        assertEquals("5238-52^^/+", ShuntingYardAlgorithm.infixToPostfix("5+2/(3-8)^5^2"));        
    }

    @Test
    public void MoreExamples(){
        assertEquals("ABC*+D-", ShuntingYardAlgorithm.infixToPostfix("A+B*C-D"));
        assertEquals("AB+CD*-EF^G*H/+I+", ShuntingYardAlgorithm.infixToPostfix("A+B-C*D+(E^F)*G/H+I"));
        assertEquals("4298-42^^/+", ShuntingYardAlgorithm.infixToPostfix("4+2/(9-8)^4^2"));
    }
    
    @Test
    public void RegularExpressions(){
        assertEquals("a*b*|c", ShuntingYardAlgorithm.infixToPostfix("(a*|b*)c"));
        assertEquals("a|", ShuntingYardAlgorithm.infixToPostfix("a|"));
        assertEquals("aab+*?b?", ShuntingYardAlgorithm.infixToPostfix("a?(a+b)*?b"));
        assertEquals("aab|*?b?", ShuntingYardAlgorithm.infixToPostfix("a?(a|b)*?b"));
    }

    @Test
    public void InvalidExpressions(){
        assertEquals(Errors.InvalidExpression.error, ShuntingYardAlgorithm.infixToPostfix("(1+2*9"));
    }
}
