package org.antman.binaryconverter.application.converter;

public class LoopElement extends Element {
    private int numberOfLoops;
    private int numberOfElements;

    public LoopElement(int numberOfLoops, int numberOfElements){
        this.type = Type.LOOP;
        this.numberOfLoops = numberOfLoops;
        this.numberOfElements = numberOfElements;
    }
}
