package com.FaustGames.Core.Rendering.Effects.Attributes;

import java.util.ArrayList;
import java.util.List;

public class VertexBuffer extends AttributesBuffer {

    public void setValues(double[] values) {
        float[] bufferData = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            bufferData[i] = (float)values[i];
        }
        fillData(bufferData);
    }

    public void setValues(float[] values) {
        float[] bufferData = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            bufferData[i] = values[i];
        }
        fillData(bufferData);
    }

    class AttributeInfo
    {
        public int Offset;
        public VertexBufferAttribute Attribute;
        public AttributeInfo(VertexBufferAttribute attribute, int offset)
        {
            Offset = offset;
            Attribute = attribute;
        }
    }

    public int FloatStride;
    public int Stride;
    List<AttributeInfo> mAttributes = new ArrayList<AttributeInfo>();

    public void applyAttribute(EffectAttribute attribute, int offset, int size) {
        applyAttribute(attribute, offset, size, Stride);
    }

    public VertexBuffer(VertexBufferAttribute[] attributes)
    {
        Stride = 0;
        FloatStride = 0;
        for (int j = 0; j < attributes.length; j++) {
            VertexBufferAttribute attribute = attributes[j];
            AttributeInfo info = new AttributeInfo(attribute, Stride);
            mAttributes.add(info);
            Stride += attribute.Size * 4;
            FloatStride += attribute.Size;
        }
    }

    @Override
    public void onApply(ArrayList<EffectAttribute> attributes) {
        for (int i = 0; i < attributes.size(); i++) {
            EffectAttribute attribute = attributes.get(i);
            for (int j = 0; j < mAttributes.size(); j++) {
                AttributeInfo info = mAttributes.get(j);
                if (info.Attribute.Name == attribute.Name)
                    applyAttribute(attribute, info.Offset, info.Attribute.Size);
            }
        }
    }

    @Override
    public void onApply() {

    }
}
