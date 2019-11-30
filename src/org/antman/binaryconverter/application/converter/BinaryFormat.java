package org.antman.binaryconverter.application.converter;



import org.antman.binaryconverter.application.util.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * BinaryFormat class representing a structure of a binary file, holding a list of types
 *
 *  Adding VAR's to a list of declared variables for future use in loops
 *  Saving all the
 * @see Element
 */
public class BinaryFormat{

    public ArrayList<Element> binaryFormatList;

    public static ArrayList<String> elementNames = new ArrayList<>(Arrays.asList("Int", "Char", "Float", "Loop", ""));
    public static HashMap<String, Integer> elementsMap;

    static {
        elementsMap = new HashMap<>();
        for (int i = 0; i < elementNames.size(); i++) {
            elementsMap.put(elementNames.get(i).toLowerCase(), i);
        }
    }


    public ArrayList<Element> declaredVariables = null;

    public BinaryFormat() {
        binaryFormatList = new ArrayList<>();
    }

    private BinaryFormat(List<String> elements) {
        binaryFormatList = new ArrayList<>();
        for (String element : elements) {
//            binaryFormatList.add(BinaryFormatElement.getElementFromName("element"));
        }
    }

    public void addPrimitive(Element.Type type) {
        binaryFormatList.add(Element.getPrimitiveElement(type));
    }

    public boolean hasVariable(String varName){
        return declaredVariables.contains(Element.getVarElement((varName)));
    }
    public void addLoop(String arg) {
        Element var = Element.getVarElement(arg);
        if (declaredVariables.contains(var))
            binaryFormatList.add(Element.getLoopElement(arg));
        else binaryFormatList.add(Element.getLoopElement(Integer.parseInt(arg)));
//        System.out.println(binaryFormatList.get(0).toString());
    }

    public void addVar(String name) {
        Element el = Element.getVarElement(name);
        binaryFormatList.add(el);
        if (declaredVariables == null) {
            declaredVariables = new ArrayList<>();
        }
        declaredVariables.add(el);
    }

    public static BinaryFormat loadFromFile(File file) throws FileNotFoundException {
        FileHandler handler = new FileHandler();
        List<String> lines;
        try {
            lines = handler.extractLinesFromFile(file);
            return new BinaryFormat(lines);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File doesn't exist");
        }
    }

    /**
     * @param name
     * @param arg  if type == loop || var then arg is varname
     *             if type==loop && variable with name arg doesn't exist then it is a number of iterations
     */
    public void addElementByName(String name, String arg) {
        Element.Type type = Element.Type.valueOf(name.toUpperCase());
        if (type.isPrimitive()) addPrimitive(type);
        else if (type == Element.Type.LOOP) addLoop(arg);
        else if (type == Element.Type.VAR) addVar(arg);
    }






    @Deprecated
    enum BinaryFormatElement {
        INT("int"),
        CHAR("char"),
        FLOAT("float"),
        LOOP("loop"),
        ENDLOOP("endloop"),
        VAR("var") {
            public String varName;
        };

        private int n;
        private final String name;
        private boolean var = false;

        private String varName;

        public BinaryFormatElement setN(int n) {
            this.n = n;
            return this;
        }

        public boolean isVar() {
            return var;
        }

        public void setVar(boolean isVar) {
            var = isVar;
        }

        public String getName() {
            return name;
        }

        public static BinaryFormatElement getElementFromName(String name) {
            switch (name) {


            }
            return VAR;
        }

        @Override
        public String toString() {
            return name + "(" + n + ")";
        }

        BinaryFormatElement(String name) {
            this.name = name;
        }


    }
}
