package org.antman.binaryconverter.application.converter.structure;


/**
 * Element of type Loop
 *
 * @author Ismoil Atajanov
 * @version 1.0
 * @see Element
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

    private BinaryStructure structure;

    private int size = 0;

    private int position;

    void setSubStructure(BinaryStructure structure) {
        this.structure = (BinaryStructure) structure.subList(position + 1, position + numberOfElements);
    }

    public int getSize() {
        BinaryStructure subStructure = (BinaryStructure) structure.subList(position + 1, position + numberOfElements);
        return subStructure.size();
    }

    public void setNumberOfLoops(int numberOfLoops){
        this.numberOfLoops = numberOfLoops;
    }
    private VariableElement var;

    public VariableElement getVar() {
        return var;
    }

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


    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    LoopElement(VariableElement var, int numberOfElements, int pos) {
        position = pos;
        type = Type.LOOP;
        this.var = var;
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
        if (var == null) nOfIterations = String.valueOf(numberOfLoops);
        else nOfIterations = var.getVarName();
        return type + "(" + nOfIterations + "," + numberOfElements + ")";
    }

    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }
}
