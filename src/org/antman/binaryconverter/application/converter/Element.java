package org.antman.binaryconverter.application.converter;

import java.util.Objects;

public class Element {
    private Type type;
    /**
     * Variable name, if type of element is var or loop
     */
    private String varName;

    /**
     * Number of iterations, if type of element is loop
     */
    private int n;

    //private int size;


    /**
     * Get primitive element
     *
     * @param type of element, such as INT, CHAR, FLOAT
     * @return new element of give type
     */
    public static Element getPrimitiveElement(Type type) {
        return new Element(type);
    }

    /**
     * Get loop element with n number iterations
     *
     * @param n number of iterations
     * @return new element instance of type loop
     */
    public static Element getLoopElement(int n) {
        Element el = new Element(Type.LOOP);
        el.n = n;
        return el;
    }

    /**
     * Get loop element with varName number of iterations
     *
     * @param varName variable element that stores number of iterations
     * @return new element instance of type loop
     */
    public static Element getLoopElement(String varName) {
        Element el = new Element(Type.LOOP_WITH_VAR);
        el.varName = varName;
        return el;
    }


    public static Element getVarElement(String name) {
        Element el = new Element(Type.VAR);
        el.varName = name;
        return el;
    }

    private Element(Type type) {
        this.type = type;
    }

    /**
     * Default constructor
     */
    private Element() {

    }


    /**
     * Type of the element
     */
    public enum Type {
        INT("int"),
        CHAR("char"),
        FLOAT("float"),
        LOOP("loop"),
        LOOP_WITH_VAR("loop"),
        ENDLOOP("endloop"),
        VAR("var");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isPrimitive() {
            return this == INT || this == CHAR || this == FLOAT || this == ENDLOOP;
        }
    }

    @Override
    public String toString() {
        if (type.isPrimitive()) {
            return type.getName();
        } else if (type == Type.LOOP) {
            return type.getName() + "(" + n + ")";
        } else if (type == Type.ENDLOOP) {
            return type.getName();
        } else if (type == Type.LOOP_WITH_VAR) {

        } else if (type == Type.VAR) {

        }
        return "null";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        if (element.type == Type.VAR) return this.varName.equals(element.varName);
        else return this.hashCode() == element.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, varName, n);
    }
}