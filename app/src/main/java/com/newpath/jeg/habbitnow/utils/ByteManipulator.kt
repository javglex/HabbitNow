package com.newpath.jeg.habbitnow.utils


/**
 * Bit manipulation
 */
class ByteManipulator {
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
            if (pos<0) pos = 0
            return ((BYTE.toInt() shr pos) and 1 == 1)
        }

        fun setBit(BYTE: Byte, _pos:Int): Byte {
            return (BYTE.toInt() or (1 shl _pos)).toByte()
        }

        fun unsetBit(BYTE: Byte, _pos: Int): Byte {
            return (BYTE.toInt() and (1 shl _pos).inv()).toByte()
        }

    }
}