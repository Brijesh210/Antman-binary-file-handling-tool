package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.*;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Decoder class, allows conversion from binary file to a text file
 * Using a know list of elements inside the file as a BinaryStructure object
 * @see BinaryStructure
 * @author Ismoil Atajanov
 * @version 2.0
 */
public class Decoder {

    private TreeMap<VariableElement, Integer> variables;
    private final String SEPARATOR = " ";

    private String decodePiece(BinaryStructure structure, ByteBuffer buffer, int start, int end) {
        int pos = start;
        int max = end;
        StringBuilder result = new StringBuilder();
        try {
            while (buffer.hasRemaining() && pos < max) {
                Element element = structure.remove(0);
                Element.Type type = element.getType();
                if (type.isPrimitive()) {
                    result.append(extractPrimitive(element, buffer)).append(SEPARATOR);
                } else if (type == Element.Type.VAR) {
                    int varInt = buffer.getInt();
                    result.append(varInt).append(SEPARATOR);
                    variables.put(
                            (VariableElement) element, varInt);
                } else if (type == Element.Type.LOOP) {
                    LoopElement le = (LoopElement) element;
                    VariableElement var = le.getVar();
                    if (var != null) {
                        le.setNumberOfLoops(variables.get(var));
                    }
                    result.append(loop(le, structure, buffer, 0));
                    pos += le.getNumberOfElements();
                    if (le.getNumberOfElements() > 0) {
                        structure.subList(0, le.getNumberOfElements()).clear();
                    }
                }
                pos++;
            }
        }catch(BufferOverflowException e){
            return result.toString();
        }
        return result.substring(0,result.length()-1);
    }

    private String loop(LoopElement le, BinaryStructure structure, ByteBuffer buffer, int nl){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < le.getNumberOfLoops() && buffer.hasRemaining(); i++){
            for(int j = nl; j < nl + le.getNumberOfElements() && buffer.hasRemaining(); j++){
                Element element = structure.get(j);
                if(element.getType().isPrimitive()){
                    result.append(extractPrimitive(element,buffer)).append(SEPARATOR);
                } else if (element.getType() == Element.Type.VAR) {
                    int varInt = buffer.getInt();
                    variables.put(
                            (VariableElement) element, varInt );
                    result.append(varInt).append(SEPARATOR);
                } else if (element.getType() == Element.Type.LOOP) {
                    LoopElement lee = (LoopElement) element;
                    VariableElement var = lee.getVar();
                    if(var!=null){
                        lee.setNumberOfLoops(variables.get(var));
                    }
                    result.append(loop(lee,structure,buffer,j+1)).append(SEPARATOR);
                    j += lee.getNumberOfElements();
                }
            }
        }
        return result.toString();
    }

    private String extractPrimitive(Element element, ByteBuffer buffer) {
        if (element.getType() == Element.Type.FLOAT) {
            return String.valueOf(buffer.getFloat());
        } else if (element.getType() == Element.Type.INT) {
            return String.valueOf(buffer.getInt());
        } else {
            return String.valueOf((char) buffer.get());
        }
    }

    public String decode(BinaryStructure structure, ByteBuffer buffer) {
        variables = new TreeMap<>();
        return decodePiece(structure, buffer, 0, structure.size());
    }

}
