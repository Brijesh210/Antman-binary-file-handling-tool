package org.antman.binaryconverter.application.converter;


import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.Element;
import org.antman.binaryconverter.application.converter.structure.LoopElement;
import org.antman.binaryconverter.application.converter.structure.VariableElement;
import org.antman.binaryconverter.application.util.FileHandler;
import org.antman.binaryconverter.application.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.*;


/***
 *
 */
public class Encoder {

    private FileHandler fileHandler = new FileHandler();


    public Encoder() {
    }

    /**
     *
     *
     */
    public byte[] getEncodedLvlTwo(){
        ByteBuffer buffer = ByteBuffer.allocate(30);
        //int int int char char char float float var(x) loop(x) char endloop
        buffer.putInt(5).putInt(6).putInt(7).put((byte)'c').put((byte)'r').put((byte)'y');
        buffer.putFloat(6.6f).putFloat(7.7f);
        buffer.putInt(3);
        buffer.put((byte)'x').put((byte)'y').put((byte)'z');
        return buffer.array();
    }

    @Deprecated
    public List<Byte> getEncodedLvlTwo(List<String> toEncode, BinaryStructure binaryStructure) {
//        ArrayList<Byte> bytes = new ArrayList<>();
//        int size = toEncode.size();
//        System.out.println(Arrays.toString(toEncode.toArray()));
//        for (int i = 0; i < size && !binaryStructure.isEmpty(); i++) {
//            Element element = binaryStructure.remove(0);
//            System.out.println(toEncode.get(0));
//            if (element.getType() == Element.Type.CHAR) {
//                char c;
//                if (toEncode.get(0).equals("")) {
//                    c = ' ';
//                } else c = toEncode.get(0).toCharArray()[0];
//                bytes.add((byte) c);
//                toEncode.remove(0);
//            } else if (element.getType() == Element.Type.INT
//                    || element.getType() == Element.Type.VAR) {
//                byte[] intBytes = Utils.intToBytes(Integer.parseInt(toEncode.get(0)));
//                bytes.add(intBytes[0]);
//                bytes.add(intBytes[1]);
//                bytes.add(intBytes[2]);
//                bytes.add(intBytes[3]);
//                toEncode.remove(0);
//                if(element.getType() == Element.Type.VAR) {
//                    VariableElement var = (VariableElement)element;
//                    var.setValue(Integer.parseInt(toEncode.get(0)));
//                }
//            } else if (element.getType() == Element.Type.FLOAT) {
//                byte[] floatBytes = Utils.floatToBytes(Float.parseFloat(toEncode.get(0)));
//                bytes.add(floatBytes[0]);
//                bytes.add(floatBytes[1]);
//                bytes.add(floatBytes[2]);
//                bytes.add(floatBytes[3]);
//                toEncode.remove(0);
//            } else if (element.getType() == Element.Type.LOOP) {
//                LoopElement le = (LoopElement)element;
//                BinaryStructure subStructure = (BinaryStructure) binaryStructure.subList(0, le.getPosition() + le.getNumberOfElements());
//                List<String> subString = toEncode.subList(0,le.getNumberOfElements());
//                for(int l = 0; l < le.getNumberOfLoops(); l++){
//                    bytes.addAll(getEncodedLvlTwo(subString,subStructure));
//                }
//            }
//        }
        return null;
    }
    @Deprecated
    public List<Byte> getEncodedLvlTwo(File file, BinaryStructure binaryStructure) throws FileNotFoundException {
        List<String> toEncode = fileHandler.readLines(file);
        return getEncodedLvlTwo(toEncode,binaryStructure);
    }



}
