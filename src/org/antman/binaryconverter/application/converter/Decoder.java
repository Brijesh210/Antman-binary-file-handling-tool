package org.antman.binaryconverter.application.converter;

import java.util.LinkedHashMap;
import java.util.Map;

public class Decoder {

    private Map<String, Integer> structure = new LinkedHashMap<>();

    private String inputString;

    public Decoder(Map<String, Integer> structure, String inputString) {
        this.structure = structure;
        inputString = inputString;
    }

//    public String decode(File file,BinaryStructure structure) {
//        //read file
////        //loop through the structure
////        for(Element e : structure){
////            //if char float int
////            //read 32 bits
////            //if its loop
////            for(int i = 0; i < ((LoopElement)e).numberOfIterations; i++){
////                //repeat next
////            }
////        }
//        return "";
//    }


    public String getInputString() {
        return inputString;
    }

    private int convertBinaryToInt(String binary) {
        char[] string = new char[32];
        for (int i = 0; i < string.length; i++) {
            string[i] = '0';
        }
        String binaryIn = String.valueOf(string);

        String newString = binaryIn.substring(0, binaryIn.length() - binary.length());
        newString = newString + binary;
        binary = newString;
        System.out.println(binary);
        long l = Long.parseLong(binary, 2);
        return (int) l;
    }

    private Float convertFromBinaryToFloat(String binary) {
        char[] string = new char[32];
        for (int i = 0; i < string.length; i++) {
            string[i] = '0';
        }
        String binaryIn = String.valueOf(string);
        String newString = binaryIn.substring(0, binaryIn.length() - binary.length());
        newString = newString + binary;
        binary = newString;
        if (binary.charAt(0) == '1') {
            String toConvert = binary.substring(1, binary.length());
            Integer intBits = Integer.valueOf(toConvert, 2);
            return -1 * Float.intBitsToFloat(intBits);

        } else {
            Integer intBits = Integer.valueOf(binary, 2);
            return Float.intBitsToFloat(intBits);

        }
    }

    private char convertBinaryToChar(String binaryString) {
        int parseInt = Integer.parseInt(binaryString, 2);
        char c = (char) parseInt;
        return c;
    }

}
