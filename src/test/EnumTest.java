package test;

import junit.framework.TestCase;
import models.Types;
import models.AsciiSymbol;

public class EnumTest extends TestCase {
    
    public void testTypes(){
        Types e = Types.Final;

        assertEquals(e.toString(), "Final");
        assertEquals(e.num, 2);
    }

    public void testAsciiSymbols(){
       AsciiSymbol epsilon = AsciiSymbol.Epsilon;

       assertEquals(epsilon.c, 'E');
       assertEquals(epsilon.ascii, 69);
    }
}
