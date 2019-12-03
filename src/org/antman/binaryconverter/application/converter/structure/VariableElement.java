package org.antman.binaryconverter.application.converter.structure;

public class VariableElement extends Element {

    private String varName;
    private int position;
    private final int size = 4;
    public VariableElement(String varName, int pos){
        position = pos;
        type = Type.VAR;
        this.varName = varName;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public int getSize(){
        return size;
    }
    @Override
    public String toString() {
        return "var("+varName+")";
    }

    public String getVarName(){
        return varName;
    }
    @Override
    public boolean equals(Object obj) {
        return this.varName.equals(
                ((VariableElement)obj).varName);
    }
}
