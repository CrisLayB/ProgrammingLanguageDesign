package test;

import junit.framework.TestCase;
import models.Types;

public class TypesTest extends TestCase {
    
    public void testTypes(){
        Types e = Types.Final;

        assertEquals(e.toString(), "Final");
        assertEquals(e.num, 2);
    }
}
