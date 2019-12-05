package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class DecoderTest {
    BinaryStructure structure;
    File structureFileOne = new File("test-data\\structure-lvl1.txt");
    File structureFileTwo = new File("test-data\\structure-lvl2.txt");
    File structureFileThree = new File("test-data\\structure-lvl3.txt");
    File decodedFileOne = new File("test-data\\decoded-lvl1.txt");
    File decodedFileTwo = new File("test-data\\decoded-lvl2.txt");
    File encodedFileTwo = new File("test-data\\encoded-lvl2.dat");
    FileHandler handler = new FileHandler();
    @Test
    void decode() {
        Decoder decoder = new Decoder();
        try {
            structure = BinaryStructure.getInstance(structureFileTwo);
            ByteBuffer buffer = handler.readBytesToBuffer(encodedFileTwo);
            System.out.println(decoder.decode(structure, buffer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidBinaryStructureException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}