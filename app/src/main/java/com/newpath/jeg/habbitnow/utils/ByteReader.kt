package com.newpath.jeg.habbitnow.utils


/**
 * Bit manipulation
 */
class ByteReader {
    companion object{

        /**
         * Checks whether a bit from a byte is set.
         * @param BYTE - the byte to check
         * @param _pos - the position of the bit to check
         * @return - Boolean whether or not the bit is set
         */
        fun isBitSet(BYTE: Byte, _pos: Int):Boolean
        {
            var pos = _pos
            if (pos>8) pos = 8

            println(Integer.toBinaryString(BYTE.toInt() shr pos))
            return ((BYTE.toInt() shr pos) and 1 == 1)
        }
    }
}