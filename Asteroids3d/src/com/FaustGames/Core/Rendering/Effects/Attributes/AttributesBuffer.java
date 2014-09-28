package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public abstract class AttributesBuffer {
    public ByteBuffer Data;

    public void applyAttribute(EffectAttribute attribute, int offset, int size, int stride) {

        GLES20.glEnableVertexAttribArray(attribute.mAttributeHandler);
        GLHelper.checkGlError("GLES20.glEnableVertexAttribArray");

        //Data.position(offset);
        //GLES20.glVertexAttribPointer(attribute.mAttributeHandler, size,
        //      GLES20.GL_FLOAT, false,
        //    Stride, Data);
        GLES20.glVertexAttribPointer(attribute.mAttributeHandler, size,
                GLES20.GL_FLOAT, false,
                stride, offset);

        GLHelper.checkGlError("GLES20.glVertexAttribPointer");
    }

    int buffers[] = new int[1];

    public void createVBO(){
        GLES20.glGenBuffers(1, buffers, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, Data.capacity(),
                Data, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void apply(ArrayList<EffectAttribute> attributes) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        onApply(attributes);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void apply() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        onApply();
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void setData(ByteBuffer data) {
        Data = data;
    }

    public void fillData(float[] bufferData) {
        int size = bufferData.length * 4;

        Data = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                size);
        // use the device hardware's native byte order
        Data.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer
        FloatBuffer floatBuffer = Data.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        floatBuffer.put(bufferData);
        // set the buffer to read the first coordinate
        floatBuffer.position(0);
    }

    public abstract void onApply();
    public void onApply(ArrayList<EffectAttribute> attributes) {

    }

    public void destroy() {
        GLES20.glDeleteBuffers(1, buffers, 0);
        GLHelper.checkGlError("GLES20.glDeleteBuffers");
    }
}
