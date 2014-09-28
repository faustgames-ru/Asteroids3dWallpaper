package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ITexturePosition;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class AttributeBufferTexturePosition extends AttributeBuffer {
    public AttributeBufferTexturePosition(EffectAttribute attribute) {
        super(attribute);
    }

    @Override
    public void apply(int program) {
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mAttribute.mAttributeHandler);
        GLHelper.checkGlError("GLES20.glEnableVertexAttribArray");

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mAttribute.mAttributeHandler, 2,
                GLES20.GL_FLOAT, false,
                8, Data);
        GLHelper.checkGlError("GLES20.glVertexAttribPointer");
    }

    public void setValues(ITexturePosition[] values) {
        Type = AttributeType.Position;


        float[] textureData = new float[values.length * 2];
        for (int i = 0; i < values.length; i++) {
            textureData[i * 2 + 0] = values[i].getU();
            textureData[i * 2 + 1] = values[i].getV();
        }
        Data= ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                textureData.length * 4);
        // use the device hardware's native byte order
        Data.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer
        FloatBuffer textureFloatBuffer = Data.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        textureFloatBuffer.put(textureData);
        // set the buffer to read the first coordinate
        textureFloatBuffer.position(0);

    }
}
