package com.nvidia.triton.contrib;

import java.util.Collection;

public class Util {

    /**
     * Check whether a string object is null or empty.
     *
     * @param s the string object.
     * @return true if it's null or empty
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Check whether a collection object is null or empty.
     *
     * @param c the collection object.
     * @return true if it's null or empty
     */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /**
     * Calculate element number from tensor shape.
     *
     * @param shape tensor shape
     * @return element number
     */
    public static long elemNumFromShape(long[] shape) {
        long ret = 1;
        for (long n : shape) {
            ret *= n;
        }
        return ret;
    }

    /**
     * Convert int to bytes in little endian.
     *
     * @param a
     * @return
     */
    public static byte[] intToBytes(int a) {
        byte[] ret = new byte[4];
        ret[0] = (byte)(a & 0xFF);
        ret[1] = (byte)((a >> 8) & 0xFF);
        ret[2] = (byte)((a >> 16) & 0xFF);
        ret[3] = (byte)((a >> 24) & 0xFF);
        return ret;
    }
}
