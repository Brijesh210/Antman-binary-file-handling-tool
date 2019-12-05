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
public class BinaryStructure extends ArrayList<Element> implements List<Element>{

    ArrayList<VariableElement> declaredVariables;

    public BinaryStructure() {
        declaredVariables = new ArrayList<>();
    }

    //todo
    public static BinaryStructure getInstance(List<String> listOfElements) throws InvalidBinaryStructureException {
        List<String> lines = listOfElements;

        BinaryStructure binaryStructure = new BinaryStructure();
        String regex = "\\(|\\)";
        int line = 1;
        ArrayList<LoopElement> loops = new ArrayList<>();
        int pos = -1;
        try {
            for (String el : lines) {
                if(el.equals("")) {
                    pos++;
                    line++;
                    continue;
                }
                el = el.replaceAll("  *|\t","");
                pos++;
                String[] element = el.split(regex);
                //store the type
                Element.Type type = Element.Type.valueOf(element[0].toUpperCase());
                //Element is a primitive (char, float, int) or an endloop
                if (element.length == 1) {
                    if (type.isPrimitive()) {
                        binaryStructure.add(new PrimitiveElement(type, pos));
                        incrementLoops(loops);
                    }
                    //if endloop, check if there is a matching loop
                    else if (type == Element.Type.ENDLOOP && !loops.isEmpty()) {
                        LoopElement loopElement = loops.remove(loops.size()-1);//(LoopElement) binaryStructure.get(binaryStructure.size() - 1 - loopCounter);
                        binaryStructure.set(loopElement.getPosition(), loopElement);
                    } else
                        throw new InvalidBinaryStructureException("Structure syntax error! EndLoop statement without a matching Loop statement on line " + line);
                    //Element is a loop or a war
                } else if (element.length == 2) {
                    if (type == Element.Type.LOOP) {
                        incrementLoops(loops);
                        //todo
                        int varIndex = binaryStructure.declaredVariables.indexOf(new VariableElement(element[1],-1));
                        if (varIndex>=0) {     //loop with var
                            LoopElement le = new LoopElement(binaryStructure.declaredVariables.get(varIndex),0,pos);
                            loops.add(le);
                            binaryStructure.add(le);
                        } else {                                                             //loop with number
                            try {
                                LoopElement le = new LoopElement(Integer.parseInt(element[1]),0,pos);
                                loops.add(le);
                                binaryStructure.add(le);
                            } catch (IllegalArgumentException e) {
                                throw new InvalidBinaryStructureException("Structure syntax error! Invalid LOOP parameter! See line " + line);
                            }
                        }
                    } else if (type == Element.Type.VAR) {
                        String varName = element[1];
                        binaryStructure.declaredVariables.add(new VariableElement(varName,pos));
                        binaryStructure.add(new VariableElement(varName,pos));
                        incrementLoops(loops);
                    } else
                        throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
                } else
                    throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
                line++;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidBinaryStructureException("Structure syntax error! Invalid element on line " + line);
        }
        return binaryStructure;
    }

    private static void incrementLoops(ArrayList<LoopElement> loops) {
        if(loops.isEmpty()) return;
        for(LoopElement e : loops){
            e.setNumberOfElements(e.getNumberOfElements() + 1);
        }
    }


    public int getSize() {
        int total = 0;
        for (Element e : this) {
            total+=e.getSize();
        }
        return total;
    }

    public static BinaryStructure getInstance(File file) throws FileNotFoundException, InvalidBinaryStructureException {
        FileHandler handler = new FileHandler();
        List<String> lines = handler.readLines(file);
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

    public void addLoopElementManually(int numbeOfLoops, int numberOfElements, int pos) {
        LoopElement el = new LoopElement(numbeOfLoops, numberOfElements, pos);
        el.setSubStructure(this);
        this.add(el);
    }

    public LoopElement addLoopElementManually(VariableElement var, int numberOfElements, int pos) {
        LoopElement element = new LoopElement(var, numberOfElements, pos);
        this.add(element);
        return element;
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
