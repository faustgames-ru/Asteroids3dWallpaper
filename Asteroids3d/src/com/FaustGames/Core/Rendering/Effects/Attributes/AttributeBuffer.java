package com.FaustGames.Core.Rendering.Effects.Attributes;

import java.nio.ByteBuffer;

public class AttributeBuffer {
    public ByteBuffer Data;

    Attribute mAttribute;
    public AttributeType Type;

    public AttributeBuffer(Attribute attribute) {
        mAttribute = attribute;
    }

    public void apply(int program) {
    }
}
