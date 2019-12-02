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

    public void setNumberOfElements(int numberOfElements){
        this.numberOfElements = numberOfElements;
    }

    public LoopElement(String var, int numberOfElements){
        type = Type.LOOP;
        varName = var;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public String toString() {
        String nOfIterations;
        if(varName==null) nOfIterations = String.valueOf(numberOfLoops);
        else nOfIterations=varName;
        return type + "(" + nOfIterations + "," +  numberOfElements + ")";
    }

    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }
}
