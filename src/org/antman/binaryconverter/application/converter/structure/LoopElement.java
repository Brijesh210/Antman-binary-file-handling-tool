package org.antman.binaryconverter.application.converter.structure;


/**
 * Element of type Loop
 *
 * @author Ismoil Atajanov
 * @version 3.0
 * @see Element
 */
public class LoopElement extends Element {
    /**
     * Number of loop iterations
     */
    private Integer numberOfLoops;
    /**
     * Number of elements inside the loop block
     */
    private int numberOfElements;
    private int position;
    private VariableElement var;

    /**
     * Constructor
     *
     * @param numberOfLoops    Number of loop iterations
     * @param numberOfElements Number of elements inside the loop block
     */
    LoopElement(int numberOfLoops, int numberOfElements, int pos) {
        position = pos;
        this.type = Type.LOOP;
        this.numberOfLoops = numberOfLoops;
        this.numberOfElements = numberOfElements;
    }

    LoopElement(VariableElement var, int numberOfElements, int pos) {
        position = pos;
        type = Type.LOOP;
        this.var = var;
        this.numberOfElements = numberOfElements;
    }

    public void setNumberOfLoops(int numberOfLoops){
        this.numberOfLoops = numberOfLoops;
    }

    public VariableElement getVar() {
        return var;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        return position == ((LoopElement) obj).position;
    }

    @Override
    public String toString() {
        String nOfIterations;
        if (numberOfLoops == null) nOfIterations = var.getVarName();
        else nOfIterations = String.valueOf(numberOfLoops);
        return type + "(" + nOfIterations + "," + numberOfElements + ")";
    }

    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }
}
