package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IFloat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class AttributeBufferFloat extends AttributeBuffer {
    public AttributeBufferFloat(EffectAttribute attribute) {
        super(attribute);
    }

    @Override
    public void apply(int program) {
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mAttribute.mAttributeHandler);
        GLHelper.checkGlError("GLES20.glEnableVertexAttribArray");

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mAttribute.mAttributeHandler, 1,
                GLES20.GL_FLOAT, false,
                4, Data);
        GLHelper.checkGlError("GLES20.glVertexAttribPointer");
    }

    public void setValues(IFloat[] values) {
        Type = AttributeType.Float;
        float[] bufferData = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            bufferData[i] = values[i].getValue();
        }


        Data = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                bufferData.length * 4);
        // use the device hardware's native byte order
        Data.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer
        FloatBuffer floatBuffer = Data.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        floatBuffer.put(bufferData);
        // set the buffer to read the first coordinate
        floatBuffer.position(0);
    }

    public void setValues(float[] bufferData) {
        Type = AttributeType.Float;

        Data = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                bufferData.length * 4);
        // use the device hardware's native byte order
        Data.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer
        FloatBuffer floatBuffer = Data.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        floatBuffer.put(bufferData);
        // set the buffer to read the first coordinate
        floatBuffer.position(0);
    }
}
