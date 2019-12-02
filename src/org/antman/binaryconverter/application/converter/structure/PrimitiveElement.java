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

    /**
     * Constructor for elements of types int,char,float
     * @param type type of the element
     */
    PrimitiveElement(Type type){
        this.type = type;
        size = 32;
    }


    PrimitiveElement(Type type,int size){
        this.type = type;
        this.size = size;
    }


    public int getSize(){
        return size;
    }

}
