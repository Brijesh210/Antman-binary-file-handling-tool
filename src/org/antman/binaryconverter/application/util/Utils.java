package org.antman.binaryconverter.application.util;

/**
 * Class providing utility methods
 */
public class Utils {
    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return new byte[0];
        }
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static byte[] intToBytes(final int value) {
        return new byte[]{
                (byte) ((value >> 24) & 0xff),
                (byte) ((value >> 16) & 0xff),
                (byte) ((value >> 8) & 0xff),
                (byte) ((value >> 0) & 0xff),
        };
    }

    public static byte[] floatToBytes(final float value){
        int bits = Float.floatToIntBits(value);
        return intToBytes(bits);
    }
}
