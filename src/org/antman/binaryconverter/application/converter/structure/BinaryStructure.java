package org.antman.binaryconverter.application.converter.structure;


import org.antman.binaryconverter.application.util.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * BinaryStructure class representing a structure of a binary file
 * Holding a list of Elements
 *
 * @author Ismoil Atajanov
 * @version 1.1
 * @see Element
 */
public class BinaryStructure extends ArrayList<Element> {

    ArrayList<String> declaredVariables;

    //Test of loadFromFile
//    public static void main(String[] args) throws FileNotFoundException{
//        BinaryStructure structure = null;
//        try {
//            structure = BinaryStructure.getInstance(new File("C:/Users/Ismail/Desktop/Antman/structure1.txt"));
//        } catch (InvalidBinaryStructureException e) {
//            System.out.println(e.getMessage());
//            return;
//        }
//        System.out.println(structure.size());
//        for(Element e : structure){
//            System.out.println(e.toString());
//        }
//    }

    public BinaryStructure() {
        declaredVariables = new ArrayList<>();

    }

    //todo
    public static BinaryStructure getInstance(List<String> listOfElements) throws InvalidBinaryStructureException {
        List<String> lines = listOfElements;
        BinaryStructure binaryStructure = new BinaryStructure();
//        System.out.println(Arrays.toString(lines.toArray()));
        String regex = "\\(|\\)";
        int line = 1;
        int loopCounter = -1;
        boolean loopActive = false;
        try {
            for (String el : lines) {

                String[] element = el.split(regex);
                if (element.length == 1) {        //meaning element is a char float or int
                    Element.Type type = Element.Type.valueOf(el.toUpperCase());
                    if (type == Element.Type.ENDLOOP)
                        if (!loopActive)
                            throw new InvalidBinaryStructureException("Structure syntax error! EndLoop statement without a matching Loop statement on line " + line);
                        else {
                            LoopElement loopElement = (LoopElement)binaryStructure.get(binaryStructure.size() - 1 - loopCounter);
                            loopElement.setNumberOfElements(loopCounter);
                            binaryStructure.set(binaryStructure.size() - 1 - loopCounter,loopElement);
                            loopCounter = -1;
                            loopActive = false;
                        }
                    else if (type.isPrimitive())
                        binaryStructure.add(new PrimitiveElement(type));
                    else
                        throw new InvalidBinaryStructureException("Structure syntax error! Invalid element type on line " + line);
                } else if (element.length == 2) {
                    Element.Type type = Element.Type.valueOf(element[0].toUpperCase());
                    if (type == Element.Type.LOOP) {
                        if(loopActive) throw new InvalidBinaryStructureException("Structure syntax error! Missing ENDLOOP between LOOP elements! See line " + line);
                        if (binaryStructure.declaredVariables.contains(element[1])) {     //loop with var
                            binaryStructure.addLoopElement(element[1], 0);
                        } else {                                                             //loop with number
                            try {
                                binaryStructure.addLoopElement(Integer.parseInt(element[1]), 0);
                            }catch (IllegalArgumentException e){
                                throw new InvalidBinaryStructureException("Structure syntax error! Invalid LOOP parameter! See line " + line);
                            }
                        }
                        loopActive = true;
                    } else if (type == Element.Type.VAR) {
                        String varName = element[1];
                        binaryStructure.declaredVariables.add(varName);
                        binaryStructure.add(new VariableElement(varName));
                    } else
                        throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
                } else
                    throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
                line++;
                if(loopActive) loopCounter++;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
        }

        return binaryStructure;
    }

    public static BinaryStructure getInstance(File file) throws FileNotFoundException, InvalidBinaryStructureException {
        FileHandler handler = new FileHandler();
        List<String> lines = handler.extractLinesFromFile(file);
        return BinaryStructure.getInstance(lines);
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
        this.add(new LoopElement(numbeOfLoops, numberOfElements));
    }

    public void addLoopElement(String varName, int numberOfElements) {
        this.add(new LoopElement(varName, numberOfElements));
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
        for (Element e : this) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
}
