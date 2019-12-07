package org.antman.binaryconverter.application.converter.structure;

import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryStructureTest {

    BinaryStructure structure;
    File testStructureFileOne = new File("test-data\\structure-lvl1.txt");
    File testStructureFileTwo = new File("test-data\\structure-lvl2.txt");
    File testStructureFileThree = new File("test-data\\structure-lvl3.txt");
    FileHandler handler = new FileHandler();

    @BeforeEach
    void setUp() {
        structure = new BinaryStructure();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstanceFromFile() {
    }

    @Test
    void testStructureWithSpaces(){
        try {
            structure = BinaryStructure.getInstance(new File("test-data\\structure\\structure-space.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (InvalidBinaryStructureException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void testStructure() {
        structure.addIntElement();
        structure.addCharElement();
        structure.addLoopElementManually(2,2,2);
        structure.addCharElement();
        structure.addLoopElementManually(3,1,4);
        structure.addIntElement();


    }

    @Test
    void getInstanceFromList() {
        try {
            BinaryStructure structureOneExcpected = new BinaryStructure();
            structureOneExcpected.addIntElement();
            structureOneExcpected.addFloatElement();
            for(int i = 0; i < 9; i++) structureOneExcpected.addCharElement();

            BinaryStructure structureOne = BinaryStructure.getInstance(testStructureFileOne);
            BinaryStructure structureTwo = BinaryStructure.getInstance(testStructureFileTwo);
            BinaryStructure structureThree = BinaryStructure.getInstance(testStructureFileThree);
            System.out.println(structureOne.toString());
        } catch (FileNotFoundException e) {
            fail("File not found");
        } catch (InvalidBinaryStructureException e) {
            e.printStackTrace();
        }

    }
}