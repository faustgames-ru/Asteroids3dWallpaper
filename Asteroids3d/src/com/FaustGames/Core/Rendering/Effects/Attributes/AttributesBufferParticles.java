package com.FaustGames.Core.Rendering.Effects.Attributes;

import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IParticlesVertex;

public class AttributesBufferParticles extends AttributesBuffer {
    EffectAttribute mPosition;
    EffectAttribute mScreenOffset;
    EffectAttribute mTexturePosition;
    EffectAttribute mTransformIndex;

    public AttributesBufferParticles(
            EffectAttribute position,
            EffectAttribute screenOffset,
            EffectAttribute texturePosition,
            EffectAttribute transformIndex) {
        mPosition = position;
        mScreenOffset = screenOffset;
        mTexturePosition = texturePosition;
        mTransformIndex = transformIndex;
    }

    public void applyAttribute(EffectAttribute attribute, int offset, int size) {
        applyAttribute(attribute, offset, size, 36);
    }

    @Override
    public void onApply() {
        applyAttribute(mPosition, 0, 3);
        applyAttribute(mScreenOffset, 3 * 4, 3);
        applyAttribute(mTexturePosition, 6 * 4, 2);
        applyAttribute(mTransformIndex, 8 * 4, 1);
    }

    public void setValues(IParticlesVertex[] values, float[] transformIndices) {
        float[] bufferData = new float[values.length * 9];
        for (int i = 0; i < values.length; i++) {
            int o = i * 9;

            bufferData[o + 0] = values[i].getX();
            bufferData[o + 1] = values[i].getY();
            bufferData[o + 2] = values[i].getZ();

            bufferData[o + 3] = values[i].getScreenOffsetX();
            bufferData[o + 4] = values[i].getScreenOffsetY();
            bufferData[o + 5] = values[i].getScreenOffsetZ();

            bufferData[o + 6] = values[i].getU();
            bufferData[o + 7] = values[i].getV();

            bufferData[o + 8] = transformIndices[i];
        }
        fillData(bufferData);
    }
}

