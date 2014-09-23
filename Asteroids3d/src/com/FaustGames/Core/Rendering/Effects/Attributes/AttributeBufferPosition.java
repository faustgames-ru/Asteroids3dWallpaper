package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IPosition;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class AttributeBufferPosition extends AttributeBuffer {

    public AttributeBufferPosition(Attribute attribute) {
        super(attribute);
    }

    @Override
    public void apply(int program) {
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mAttribute.mAttributeHandler);
        GLHelper.checkGlError("GLES20.glEnableVertexAttribArray");

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mAttribute.mAttributeHandler, 3,
                GLES20.GL_FLOAT, false,
                12, Data);
        GLHelper.checkGlError("GLES20.glVertexAttribPointer");
    }

    public void setValues(IPosition[] values) {
        Type = AttributeType.Position;
        float[] bufferData = new float[values.length * 3];
        for (int i = 0; i < values.length; i++) {
            bufferData[i * 3 + 0] = values[i].getX();
            bufferData[i * 3 + 1] = values[i].getY();
            bufferData[i * 3 + 2] = values[i].getZ();
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
}
