package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controllers.SyntaxChecker;

public class CheckerTest {
    @Test
    public void ValidExpressions(){
        assertEquals(true, SyntaxChecker.checkExpression("ab*ab*").length() == 0);
        assertEquals(true, SyntaxChecker.checkExpression("0?(1?)?0*").length() == 0);
        assertEquals(true, SyntaxChecker.checkExpression("(a*|b*)c").length() == 0);
        assertEquals(true, SyntaxChecker.checkExpression("(b|b)*abb(a|b)*").length() == 0);
        assertEquals(true, SyntaxChecker.checkExpression("(a|E)b(a+)c?").length() == 0);
        assertEquals(true, SyntaxChecker.checkExpression("(a|b)*a(a|b)(a|b)").length() == 0);
    }

    @Test
    public void InvalidExpressions(){
        assertEquals(true, SyntaxChecker.checkExpression("a|").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("*a").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("|ba").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("(a|b").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("(a|b))").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("a||").length() != 0);
        assertEquals(true, SyntaxChecker.checkExpression("ab|E|").length() != 0);
    }

    @Test
    public void EspecificErrors(){
        assertEquals("No se puede tener dos | juntos\n", SyntaxChecker.checkExpression("a||b"));
    }
}
