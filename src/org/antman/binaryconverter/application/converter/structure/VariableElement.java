package org.antman.binaryconverter.application.converter.structure;

public class VariableElement extends Element {

    private String varName;

    public VariableElement(String varName){
        type = Type.VAR;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "var("+varName+")";
    }
}
