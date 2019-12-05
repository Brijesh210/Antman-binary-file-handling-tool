package org.antman.binaryconverter.application.converter.structure;


/**
 * Base class representing elements of the binary file
 * Integer, Float, Character, Loop, Variable
 *
 * @author Ismoil Atajanov
 * @version 3.0
 * @see LoopElement
 * @see PrimitiveElement
 * @see VariableElement
 * @see Element.Type
 */
public abstract  class Element{

    Type type;
    public Type getType(){
        return type;
    }
    public static PrimitiveElement getIntElement(){
        PrimitiveElement element = new PrimitiveElement(Type.INT,32);
        return element;
    }

    public static PrimitiveElement getCharElement(){
        PrimitiveElement element = new PrimitiveElement(Type.CHAR,32);
        return element;
    }

    public static PrimitiveElement getFloatElement(){
        PrimitiveElement element = new PrimitiveElement(Type.FLOAT,32);
        return element;
    }
    public abstract int getPosition();

    @Override
    public String toString() {
        return type.name;
    }

    /**
     * Type of the element
     */
    public enum Type {
        INT("int"),
        CHAR("char"),
        FLOAT("float"),
        LOOP("loop"),
        VAR("var"),
        ENDLOOP("endloop");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isPrimitive(){
            return this == Type.INT || this == Type.CHAR || this == Type.FLOAT;
        }
    }


}