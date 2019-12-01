package org.antman.binaryconverter.application.converter;


import org.antman.binaryconverter.application.util.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * BinaryStructure class representing a structure of a binary file, holding a list of types
 * <p>
 * Adding VAR's to a list of declared variables for future use in loops
 * Saving all the
 *
 * @see Element
 */
public class BinaryStructure extends ArrayList<Element>{

    public BinaryStructure(){

    }

    //todo
    public static BinaryStructure loadFromFile(File file) throws FileNotFoundException {
        FileHandler handler = new FileHandler();
//        List<String> lines;
//        try {
//            lines = handler.extractLinesFromFile(file);
//            return new BinaryStructure(lines);
//        } catch (FileNotFoundException e) {
//            throw new FileNotFoundException("File doesn't exist");
//        }
        return new BinaryStructure();
    }


    public void addIntElement() {
        this.add(PrimitiveElement.getIntElement());
    }

    public void addCharElement() {
        this.add(PrimitiveElement.getCharElement());
    }

    public void addFloatElement() {
        this.add(PrimitiveElement.getFloatElement());
    }

    public void addLoopElement(int numbeOfLoops, int numberOfElements) {
        this.add(new LoopElement(numbeOfLoops,numberOfElements));
    }





    /**
     * @param name
     * @param arg  if type == loop || var then arg is varname
     *             if type==loop && variable with name arg doesn't exist then it is a number of iterations
     */
    public void addElementByName(String name, String arg) {
        Element.Type type = Element.Type.valueOf(name.toUpperCase());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Element e : this){
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}
