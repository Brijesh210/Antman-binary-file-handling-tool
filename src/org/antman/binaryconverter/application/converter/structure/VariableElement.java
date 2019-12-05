package org.antman.binaryconverter.application.converter.structure;

public class VariableElement extends Element implements Comparable{

    private String varName;
    private int position;
    private final int size = 4;
    private int value = 0;
    public String getVarName(){
        return varName;
    }
//    public void setValue(int value){
//        this.value = value;
//    }


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

    @Override
    public int compareTo(Object o) {
        return this.equals(o) ? 0 : -1;
    }


    @Override
    public boolean equals(Object obj) {
        return this.varName.equals(
                ((VariableElement)obj).varName);
    }
}
