package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.Element;
import org.antman.binaryconverter.application.converter.structure.PrimitiveElement;

import java.util.ArrayList;
import java.util.List;

public class Decoder {



    public Decoder() {

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

    public List<String> decode(BinaryStructure structure, String binaryString){
        List<String> result = new ArrayList<>();
        int i = 0;
        while(binaryString.length() > 0 && i < structure.size()){
            Element.Type type = structure.get(i).getType();
            if(type.isPrimitive()){
                PrimitiveElement element = (PrimitiveElement)structure.get(i);
                String substring = binaryString.substring(0,element.getSize());
                if(type == Element.Type.INT){
                    result.add(String.valueOf(convertBinaryToInt(substring));
                } else if(type == Element.Type.CHAR){
                    result.add(String.valueOf(convertBinaryToChar(substring));
                } else if(type == Element.Type.FLOAT){
                    result.add(String.valueOf(convertBinaryToFloat(substring)));                }
            }
        }
        return result;
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

    private Float convertBinaryToFloat(String binary) {
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
