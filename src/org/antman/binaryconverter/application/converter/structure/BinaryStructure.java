package org.antman.binaryconverter.application.converter.structure;


import org.antman.binaryconverter.application.util.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * BinaryStructure class representing a structure of a binary file
 * Holding a list of Elements
 * @see Element
 * @author Ismoil Atajanov
 * @version 1.1
 */
public class BinaryStructure extends ArrayList<Element>{

    ArrayList<String> declaredVariables;
    public static void main(String[] args) throws FileNotFoundException, InvalidBinaryStructureException {
//        BinaryStructure.loadFromFile(new File("C:/Users/Ismail/Desktop/Antman/structure.txt"));
        BinaryStructure structure = new BinaryStructure();
        structure.addCharElement();
        structure.addFloatElement();
        structure.addLoopElement(10,5);
        structure.addCharElement();
        structure.addFloatElement();
        structure.addCharElement();
        structure.addFloatElement();
        structure.addIntElement();

        for(Element e : structure){
            if(e.type == Element.Type.INT || e.type == Element.Type.FLOAT || e.type == Element.Type.CHAR){

            }
        }
        System.out.println(structure.toString());
    }
    public BinaryStructure(){
        declaredVariables = new ArrayList<>();
    }

    //todo
    public static BinaryStructure loadFromFile(File file) throws FileNotFoundException, InvalidBinaryStructureException {
        BinaryStructure binaryStructure = new BinaryStructure();
        FileHandler handler = new FileHandler();
        List<String> lines = handler.extractLinesFromFile(file);

        System.out.println(Arrays.toString(lines.toArray()));
        String regex =  "\\(|\\)";
        int line = 0;
        try {
            for (String el : lines) {
                String[] element = el.split(regex);
                if (element.length == 1) {        //meaning element is a char float or int
                    Element.Type type = Element.Type.valueOf(el.toUpperCase());
                    if(type == Element.Type.ENDLOOP)
                        throw new InvalidBinaryStructureException("Structure syntax error\nEndLoop statement without a matching Loop statement on line " + line);
                    binaryStructure.add(new PrimitiveElement(type));
                }
                else if(element.length == 2){
                    Element.Type type = Element.Type.valueOf(element[0]);
                    if(type == Element.Type.LOOP){
//                        binaryStructure.addLoopElement();
                    } else if (type == Element.Type.VAR){
                        int var = Integer.parseInt(element[1]);
//                        binaryStructure.declaredVariables.put()
                    } else throw new InvalidBinaryStructureException("Structure syntax error\nInvalid element on line " + line);
                } else throw new InvalidBinaryStructureException("Structure syntax error\nInvalid element on line " + line);
                line++;
            }
        }catch (IllegalArgumentException e){
            throw new InvalidBinaryStructureException("Structure syntax error\nInvalid element on line " + line);
        }


        return binaryStructure;
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
