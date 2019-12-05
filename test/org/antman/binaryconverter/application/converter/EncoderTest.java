package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EncoderTest {

    BinaryStructure structure;
    File structureFileOne = new File("test-data\\structure-lvl1.txt");
    File structureFileTwo = new File("test-data\\structure-lvl2.txt");
    File structureFileThree = new File("test-data\\structure-lvl3.txt");
    File decodedFileOne = new File("test-data\\decoded-lvl1.txt");
    File decodedFileTwo = new File("test-data\\decoded-lvl2.txt");
    FileHandler handler = new FileHandler();

    @Test
    void encode() {
        try {
            BinaryStructure structure = BinaryStructure.getInstance(structureFileTwo);
            String toEncode = handler.readAll(decodedFileTwo);
//            System.out.println(toEncode);
            byte[] bytes = new Encoder().getEncodedLvlFour();
            handler.writeBytes(bytes,new File("test-data\\encoded\\encoded-lvl-4.dat"));
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (InvalidBinaryStructureException e) {
            fail(e.getMessage());
        }
    }
}