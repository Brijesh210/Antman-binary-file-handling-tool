package org.antman.binaryconverter.application.converter.structure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ElementTest {

    @Test
    public void elementEquals(){
        VariableElement var1 = new VariableElement("x",0);
        VariableElement var2 = new VariableElement("x",1);
        assertEquals(var1, var2, "Supposed to be true");

    }
}
