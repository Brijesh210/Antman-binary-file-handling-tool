package org.antman.binaryconverter.application.converter.structure;


/**
 * Element of type Loop
 * @see Element
 * @version 1.0
 * @author Ismoil Atajanov
 */
public class LoopElement extends Element {
    /**
     * Number of loop iterations
     */
    private int numberOfLoops;
    /**
     * Number of elements inside the loop block
     */
    private int numberOfElements;

    private String varName;
    /**
     * Constructor
     * @param numberOfLoops Number of loop iterations
     * @param numberOfElements Number of elements inside the loop block
     */
    public LoopElement(int numberOfLoops, int numberOfElements){
        this.type = Type.LOOP;
        this.numberOfLoops = numberOfLoops;
        this.numberOfElements = numberOfElements;
    }

    public LoopElement(String var, int numberOfElements){
        varName = var;
        this.numberOfElements = numberOfElements;
    }
}
