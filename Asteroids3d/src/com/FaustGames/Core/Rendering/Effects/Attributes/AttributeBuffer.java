package com.FaustGames.Core.Rendering.Effects.Attributes;

import java.nio.ByteBuffer;

public class AttributeBuffer {
    public ByteBuffer Data;

    EffectAttribute mAttribute;
    public AttributeType Type;

    public AttributeBuffer(EffectAttribute attribute) {
        mAttribute = attribute;
    }

    public void apply(int program) {
    }
}
