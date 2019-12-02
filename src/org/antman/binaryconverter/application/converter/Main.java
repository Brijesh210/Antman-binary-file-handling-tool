package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;

import java.io.File;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Encoder encoder = new Encoder();


        BinaryStructure structure = new BinaryStructure();
        structure.addIntElement();
        structure.addFloatElement();
        structure.addLoopElement(9, 1);
        structure.addCharElement();

        File file = new File("C:/Users/shaul/desktop/decoded.txt");


        Encoder encoder1 = new Encoder();
        String binaryResult = encoder1.encoded(file, structure);


        System.out.println(encoder1.getStructure());
        System.out.println(encoder1.getBinaryToStore());
        FileHandler fileHandler = new FileHandler("output.dat");
//        String toBeconverted = encoder.getBinaryToStore().substring(0, 32);
//        System.out.println(convertBinaryToInt(toBeconverted));
        System.out.println(binaryResult);
        fileHandler.writeToTheFile(encoder1.getBinaryToStore());
        Decoder decoder = new
                Decoder(encoder.getStructure(), encoder.getBinaryToStore());
    }

//    private static String convertIntToBinary(int value) {
//        char[] string = new char[32];
//        for (int i = 0; i < string.length; i++) {
//            string[i] = '0';
//        }
//        String binary = String.valueOf(string);
//        if (value < 256 && value > 0) {
//
//            String outputString = Integer.toBinaryString(value);
//            String newString = binary.substring(0, binary.length() - outputString.length());
//            newString = newString + outputString;
//            return newString;
//        } else if (value > 255) {
//            String outputString = Integer.toBinaryString(value);
//            String newString = binary.substring(0, binary.length() - outputString.length());
//            newString = newString + outputString;
//            return newString;
//        } else {
//            return Integer.toBinaryString(value);
//        }
//    }
//
//    private static String convertFloatToBinary(float myFloat) {
//        char[] string = new char[32];
//        for (int i = 0; i < string.length; i++) {
//            string[i] = '0';
//        }
//        String binary = String.valueOf(string);
//        int bits = Float.floatToIntBits(myFloat);
//        String myString = Integer.toBinaryString(bits);
//
//
//        String newString = binary.substring(0, binary.length() - myString.length());
//        newString = newString + myString;
//        return newString;
//
//    }
//
//    private static String convertCharToBinary(char myChar) {
//        char[] string = new char[32];
//        for (int i = 0; i < string.length; i++) {
//            string[i] = '0';
//        }
//        String binaryIn = String.valueOf(string);
//        String binary = Integer.toBinaryString(myChar);
//        String newString = binaryIn.substring(0, binaryIn.length() - binary.length());
//        newString = newString + binary;
//        binary = newString;
//        return binary;
//    }
//
//    private static <T> String convertToBinary(T value) {
//        String temp = "";
//        if (value instanceof Integer) {
//            int val = (Integer) value;
//            temp = convertIntToBinary(val);
//        } else if (value instanceof Float) {
//            float val = (Float) value;
//            temp = convertFloatToBinary(val);
//        } else if (value instanceof Character) {
//            char val = (Character) value;
//            temp = convertCharToBinary(val);
//        }
//        return temp;
//    }

    private static int convertBinaryToInt(String binary) {
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

    private static Float convertFromBinaryToFloat(String binary) {
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

    private static char convertBinaryToChar(String binaryString) {
        int parseInt = Integer.parseInt(binaryString, 2);
        char c = (char) parseInt;
        return c;
    }

}
