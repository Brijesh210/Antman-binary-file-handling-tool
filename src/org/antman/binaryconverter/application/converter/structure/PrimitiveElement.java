package org.antman.binaryconverter.application.converter.structure;


/**
 * Element of primitive types - int float char
 * @version 1.0
 * @author Ismoil Atajanov
 */
public class PrimitiveElement extends Element {

    /**
     * Binary size of an element
     */
    private final int size;

    private final int position;
    /**
     * Constructor for elements of types int,char,float
     * @param type type of the element
     */
    PrimitiveElement(Type type, int pos){
        position = pos;
        if(type == Type.INT) size = 4;
        else if(type == Type.FLOAT) size = 4;
        else size = 1;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return type == ((PrimitiveElement)obj).type;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
