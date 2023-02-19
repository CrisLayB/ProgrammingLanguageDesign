package test;

import junit.framework.TestCase;

import models.Type;

public class TypesTest extends TestCase {
    
    public void testTypes(){
        Type e = Type.Final;

        assertEquals(e.toString(), "Final");
        assertEquals(e.num, 2);
    }
}
