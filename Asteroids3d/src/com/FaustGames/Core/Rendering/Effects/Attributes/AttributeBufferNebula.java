package com.FaustGames.Core.Rendering.Effects.Attributes;

import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.INebulaVertex;

public class AttributeBufferNebula extends AttributesBuffer {
    public static final int PositionStride = 0;
    public static final int TexturePositionStride = 3 * 4;

    public static final int Stride = 5 * 4;
    public static final int StrideFloat = 5;

    Attribute mPosition;
    Attribute mTexturePosition;

    public AttributeBufferNebula(
            Attribute position,
            Attribute texturePosition) {
        mPosition = position;
        mTexturePosition = texturePosition;
    }

    public void applyAttribute(Attribute attribute, int offset, int size) {
        applyAttribute(attribute, offset, size, Stride);
    }

    @Override
    public void onApply() {
        applyAttribute(mPosition, PositionStride, 3);
        applyAttribute(mTexturePosition, TexturePositionStride, 2);
    }

    public void setValues(INebulaVertex[] values) {
        float[] bufferData = new float[values.length * StrideFloat];
        for (int i = 0; i < values.length; i++) {
            int o = i * StrideFloat;
            bufferData[o + 0] = values[i].getX();
            bufferData[o + 1] = values[i].getY();
            bufferData[o + 2] = values[i].getZ();
            bufferData[o + 3] = values[i].getU();
            bufferData[o + 4] = values[i].getV();
        }
        fillData(bufferData);
    }
}
