package org.antman.binaryconverter.application.converter;


import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.Element;
import org.antman.binaryconverter.application.converter.structure.LoopElement;
import org.antman.binaryconverter.application.util.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/***
 *
 */
public class Encoder {

    private String binaryToStore;

    private Map<String, Integer> structure = new LinkedHashMap<>();

    private FileHandler fileHandler = new FileHandler();
    private static int counter = 0;

    Encoder() {
        binaryToStore = "";
    }


    public Map<String, Integer> getStructure() {
        return structure;
    }

    public String encoded(File file, BinaryStructure binaryStructure) {

        List<String> stringList;
        try {
            stringList = fileHandler.extractLinesFromFile(file);
            System.out.println(stringList.toString());

            for (int i = 0; i < stringList.size(); i++) {

                Element.Type typeS = binaryStructure.get(i).getType();

                System.out.println(typeS);
                switch (typeS) {
                    case INT:
                        encode(Integer.parseInt(stringList.get(i)));
                        break;
                    case FLOAT:
                        encode(Float.valueOf(stringList.get(i)));
                        break;
                    case CHAR:
                        encode(stringList.get(i).charAt(0));
                        break;
                    case LOOP:
                        LoopElement element = (LoopElement) binaryStructure.get(i);
                        looping(element.getNumberOfLoops(), stringList.get(i),
                                binaryStructure.get(i + 1).getType().getName());
                        break;
                    default:
                        break;
                }

                System.out.println(stringList.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("the exception thrown is " + e.getMessage());
        }
        return "";
    }

    public char[] looping(int num, String nextLine, String type) {

        char[] array = new char[num];
        System.out.println(type);
        for (int i = 0; i < num; i++) {

            array[i] = nextLine.charAt(i);
            encode(array[i]);
        }

        return array;
    }


    public <T> void encode(T value) {
        String temp = "";
        if (value instanceof Integer) {
            int val = (Integer) value;
            temp = convertIntToBinary(val);
            structure.put("integer", ++counter);
        } else if (value instanceof Double)
            System.out.println("double");
            //TODO: fix this for double
        else if (value instanceof Float) {
            float val = (Float) value;
            temp = convertFloatToBinary(val);
            structure.put("float", ++counter);
        } else if (value instanceof Character) {
            System.out.println("was charachter");
            char val;
            val = (Character) value;
            temp = convertCharToBinary(val);
            structure.put("char", ++counter);
        } else if (value instanceof Set)
            System.out.println("Set! ");
        //TODO: fix this also for other data-type
        binaryToStore = binaryToStore + temp;
    }

    public String getBinaryToStore() {
        return binaryToStore;
    }

    private String convertIntToBinary(int value) {
        char[] string = new char[32];
        for (int i = 0; i < string.length; i++) {
            string[i] = '0';
        }
        String binary = String.valueOf(string);
        if (value < 256 && value > 0) {

            String outputString = Integer.toBinaryString(value);
            String newString = binary.substring(0, binary.length() - outputString.length());
            newString = newString + outputString;
            return newString;
        } else if (value > 255) {
            String outputString = Integer.toBinaryString(value);
            String newString = binary.substring(0, binary.length() - outputString.length());
            newString = newString + outputString;
            return newString;
        } else {
            return Integer.toBinaryString(value);
        }
    }

    private String convertFloatToBinary(float myFloat) {
        char[] string = new char[32];
        for (int i = 0; i < string.length; i++) {
            string[i] = '0';
        }
        String binary = String.valueOf(string);
        int bits = Float.floatToIntBits(myFloat);
        String myString = Integer.toBinaryString(bits);


        String newString = binary.substring(0, binary.length() - myString.length());
        newString = newString + myString;
        return newString;

    }


    private String convertCharToBinary(char myChar) {
        char[] string = new char[32];
        for (int i = 0; i < string.length; i++) {
            string[i] = '0';
        }
        String binaryIn = String.valueOf(string);
        String binary = Integer.toBinaryString(myChar);
        String newString = binaryIn.substring(0, binaryIn.length() - binary.length());
        newString = newString + binary;
        binary = newString;
        return binary;
    }
}
