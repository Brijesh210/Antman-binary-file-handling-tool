package org.antman.binaryconverter.application.converter;

import org.antman.binaryconverter.application.util.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Encoder {

    FileHandler handler = new FileHandler();

    @Test
    void testEncodeOne() {
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

    @Test
    void testEncodeTwo(){
        try {
            byte[] actualBytes = getEncodedLvlFive();
            byte[] expectedBytes = handler.readBytesToBuffer(
                    new File("test-data\\encoded\\expected\\encoded-lvl-5.bin")).array();
            handler.writeBytes(actualBytes,new File("test-data\\encoded\\generated\\encoded-lvl-5.bin"));
            assertArrayEquals(expectedBytes,actualBytes,"Encoded bytes are not what expected!");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testEncodedThree(){
        try {
            byte[] actualBytes = getEncodedLvlSix();
//            byte[] expectedBytes = handler.readBytesToBuffer(
//                    new File("test-data\\encoded\\expected\\encoded-lvl-6.bin")).array();
            handler.writeBytes(actualBytes,new File("test-data\\encoded\\generated\\encoded-lvl-6.bin"));
//            assertArrayEquals(expectedBytes,actualBytes,"Encoded bytes are not what expected!");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testEncodeThree(){
        //test with line breaks
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

    private byte[] getEncodedLvlFive(){
        ByteBuffer buffer = ByteBuffer.allocate(178);
        //var(i) loop(i) int float char
        buffer.putInt(5);
        for(int i = 0; i < 5; i++){
            putString("start", buffer);
            for(int j = 0; j < 2; j++) {
                buffer.put((byte)('a' + i+j));
                for(int k = 0; k < 3; k++){
                    buffer.putInt(i+j+k);
                }
            }
            putString("end",buffer);
        }
        buffer.putFloat(35353.5353f);
        return buffer.array();
    }

    //loop(12) char endloop var(x) loop(x) int float var(i) loop(i) int loop(3) char endloop endloop endloop
    private byte[] getEncodedLvlSix(){
        ByteBuffer buffer = ByteBuffer.allocate(5797);
        putString("Hello world ",buffer);
        buffer.putInt(123);  //var(x)
        for(int i = 0; i < 123; i++){
            buffer.putInt(i);
            buffer.putFloat(i/2);
            buffer.putInt(5);
            for(int j = 0; j < 5; j++){
                buffer.putInt(j);
                putString("abc",buffer);
            }
        }
        return buffer.array();
    }
    private void putString(String s, ByteBuffer buffer){
        char[] sss = s.toCharArray();
        for(char ss : sss){
            buffer.put((byte)ss);
        }
    }
}
