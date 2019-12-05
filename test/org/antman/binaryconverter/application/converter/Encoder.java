package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.converter.structure.BinaryStructure;
import org.antman.binaryconverter.application.converter.structure.InvalidBinaryStructureException;
import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Encoder {

    FileHandler handler = new FileHandler();

    @Test
    void encode() {
        try {
            byte[] actualBytes = getEncodedLvlFour();
            byte[] expectedBytes = handler.readBytesToBuffer(
                    new File("test-data\\encoded\\expected\\encoded-lvl-4.bin")).array();
            handler.writeBytes(actualBytes,new File("test-data\\encoded\\generated\\encoded-lvl-4.bin"));
            assertArrayEquals(expectedBytes,actualBytes,"Encoded bytes are not what expected!");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private byte[] getEncodedLvlFour(){
        Random random = new Random();
        ByteBuffer buffer = ByteBuffer.allocate(82);
        // 10 1 a a a 2 b b b 3 c c c 4 d d d 5 e e e 6 f f f 7 g g g 8 h h h 9 i i i 10 j j j
        buffer.putInt(10);
        for(int i = 0; i < 10; i++){
            buffer.putInt(i);
            for(int j = 0; j < 3; j++) {
                buffer.put((byte) ('a'+i));
            }
        }
        buffer.putFloat(6.6f).putFloat(7.7f);
        return buffer.array();
    }

    private byte[] getEncodedLvlTwo(){
        ByteBuffer buffer = ByteBuffer.allocate(30);
        //int int int char char char float float var(x) loop(x) char endloop
        buffer.putInt(5).putInt(6).putInt(7).put((byte)'c').put((byte)'r').put((byte)'y');
        buffer.putFloat(6.6f).putFloat(7.7f);
        buffer.putInt(3);
        buffer.put((byte)'x').put((byte)'y').put((byte)'z');
        return buffer.array();
    }
}