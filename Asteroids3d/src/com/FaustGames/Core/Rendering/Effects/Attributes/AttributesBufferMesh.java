package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IMeshVertex;

import java.nio.ByteBuffer;

public class AttributesBufferMesh extends AttributesBuffer {
    public static final int PositionStride = 0;
    public static final int NormalStride = 3 * 4;
    public static final int TangentStride = 6 * 4;
    public static final int BiNormalStride = 9 * 4;
    public static final int TexturePositionStride = 12 * 4;
    public static final int TransformIndexStride = 14 * 4;
    public static final int Stride = 15 * 4;
    public static final int StrideFloat = 15;

    //public ByteBuffer Data;
    //public int Size;

    EffectAttribute mPosition;
    EffectAttribute mNormal;
    EffectAttribute mBiNormal;
    EffectAttribute mTangent;
    EffectAttribute mTexturePosition;
    EffectAttribute mTransformIndex;

    public AttributesBufferMesh(
            EffectAttribute position,
            EffectAttribute normal,
            EffectAttribute biNormal,
            EffectAttribute tangent,
            EffectAttribute texturePosition,
            EffectAttribute transformIndex) {
        mPosition = position;
        mNormal = normal;
        mBiNormal = biNormal;
        mTangent = tangent;
        mTexturePosition = texturePosition;
        mTransformIndex = transformIndex;
    }

    public void applyAttribute(EffectAttribute attribute, int offset, int size) {
        applyAttribute(attribute, offset, size, Stride);
    }

    public void applyForDepth(EffectAttribute position, EffectAttribute transformIndex) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        applyAttribute(position, PositionStride, 3);
        applyAttribute(transformIndex, TransformIndexStride, 1);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void onApply() {
        applyAttribute(mPosition, PositionStride, 3);
        applyAttribute(mNormal, NormalStride, 3);
        applyAttribute(mBiNormal, BiNormalStride, 3);
        applyAttribute(mTangent, TangentStride, 3);
        applyAttribute(mTexturePosition, TexturePositionStride, 2);
        applyAttribute(mTransformIndex, TransformIndexStride, 1);
    }

    public void setValues(ByteBuffer data) {
        /*
        FloatBuffer fb = data.asFloatBuffer();
        int s = fb.capacity();
        for (int i = 0; i < s; i++)
        {
            float x = fb.get();
            float y = fb.get();
            float z = fb.get();

            float nx = fb.get();
            float ny = fb.get();
            float nz = fb.get();

            float tanx = fb.get();
            float tany = fb.get();
            float tanz = fb.get();

            float bitanx = fb.get();
            float bitany = fb.get();
            float bitanz = fb.get();

            float tx = fb.get();
            float ty = fb.get();

            float index = fb.get();

            if (index == 0)
                continue;
        }
        */
         setData(data);
    }

    public void setValues(IMeshVertex[] values, float[] transformIndices) {

        float[] bufferData = new float[values.length * StrideFloat];
        for (int i = 0; i < values.length; i++) {
            int o = i * StrideFloat;

            bufferData[o + 0] = values[i].getX();
            bufferData[o + 1] = values[i].getY();
            bufferData[o + 2] = values[i].getZ();

            bufferData[o + 3] = values[i].getNormalX();
            bufferData[o + 4] = values[i].getNormalY();
            bufferData[o + 5] = values[i].getNormalZ();

            bufferData[o + 6] = values[i].getBiNormalX();
            bufferData[o + 7] = values[i].getBiNormalY();
            bufferData[o + 8] = values[i].getBiNormalZ();

            bufferData[o + 9] = values[i].getTangentX();
            bufferData[o + 10] = values[i].getTangentY();
            bufferData[o + 11] = values[i].getTangentZ();

            bufferData[o + 12] = values[i].getU();
            bufferData[o + 13] = values[i].getV();

            bufferData[o + 14] = transformIndices[i];
        }
        fillData(bufferData);
    }
}
