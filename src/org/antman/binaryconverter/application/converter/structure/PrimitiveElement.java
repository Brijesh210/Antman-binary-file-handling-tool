package org.antman.binaryconverter.application.converter.structure;


/**
 * Element of primitive types - int float char
 * @version 1.1
 * @author Ismoil Atajanov
 */
public class PrimitiveElement extends Element {

    private final int position;
    /**
     * Constructor for elements of types int,char,float
     * @param type type of the element
     */
    PrimitiveElement(Type type, int pos){
        position = pos;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return type == ((PrimitiveElement)obj).type;
    }

    @Override
    public int getPosition() {
        return position;
    }

}
