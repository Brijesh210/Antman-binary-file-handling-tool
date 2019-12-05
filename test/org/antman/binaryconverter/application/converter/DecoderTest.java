package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DecoderTest {
    private FileHandler handler;
    private Decoder decoder;

    private File expectedLvl4;
    private File generatedLvl4;
    private File structureFile;
    private File binaryFile;
    private BinaryStructure structure;

    @BeforeAll
    void init(){
        handler = new FileHandler();
        decoder = new Decoder();
        expectedLvl4 = new File(
                "test-data\\decoded\\expected\\decoded-lvl4.txt");
        generatedLvl4 = new File(
                "test-data\\decoded\\generated\\decoded-lvl4.txt");
        structureFile = new File(
                "test-data\\structure\\structure-lvl-4.struc");
        binaryFile = new File(
                "test-data\\encoded\\expected\\encoded-lvl-4.bin");
    }
    @Test
    void decodeLvlTwo() {

    }

    @Test
    void decodeLvlFour() {
        try {
            structure = BinaryStructure.getInstance(structureFile);
            ByteBuffer buffer = handler.readBytesToBuffer(binaryFile);
            String expected = handler.readAll(expectedLvl4);
            String generated = decoder.decode(structure,buffer);
            System.out.println(generated);
            System.out.println(expected);
            System.out.println("Generated saved");
            handler.write(generated,generatedLvl4);
            assertEquals(expected,generated,"Decoded string is not as expected!");
        } catch (InvalidBinaryStructureException | IOException e) {
            fail(e.getMessage());
        }
    }
}