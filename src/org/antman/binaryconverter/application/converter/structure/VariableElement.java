package org.antman.binaryconverter.application.converter.structure;

/**
 * Element of type var
 * Treated just like an integer, only the value is store
 * For future use as a loop input
 *
 * @author Ismoil Atajanov
 * @version 2.0
 * @see Element
 */
public class VariableElement extends Element implements Comparable{

    private String varName;
    private int position;

    public String getVarName(){
        return varName;
    }

    public VariableElement(String varName, int pos){
        position = pos;
        type = Type.VAR;
        this.varName = varName;
    }

    @Override
    public int getPosition() {
        return position;
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
