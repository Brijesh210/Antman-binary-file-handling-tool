package org.antman.binaryconverter.application.converter.structure;


/**
 * Thrown when the syntax of the structure is invalid
 * Usually applies to extracting BinaryStructure from file
 * @version 1.0
 * @author Ismoil Atajanov
 */
public class InvalidBinaryStructureException extends Exception {
    public InvalidBinaryStructureException(String msg){
        super(msg);
    }
    public InvalidBinaryStructureException(){
        this("Invalid structure syntax!");
    }


}
