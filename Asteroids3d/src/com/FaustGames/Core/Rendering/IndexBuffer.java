package com.FaustGames.Core.Rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class IndexBuffer {
    public ShortBuffer drawListBuffer;
    public ByteBuffer drawListByteBuffer;
    public int Count;
    public IndexBuffer(ByteBuffer byteBuffer) {
        drawListByteBuffer = byteBuffer;
        Count = byteBuffer.capacity() / 2;
        drawListBuffer = byteBuffer.asShortBuffer();
        drawListBuffer.position(0);
    }
    public IndexBuffer(short[] indices) {
        Count = indices.length;
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                indices.length * 2);
        //dlb.order(ByteOrder.nativeOrder());
        dlb.order(ByteOrder.LITTLE_ENDIAN);
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
        drawListByteBuffer = dlb;
    }

    public void destroy() {
    }
}
