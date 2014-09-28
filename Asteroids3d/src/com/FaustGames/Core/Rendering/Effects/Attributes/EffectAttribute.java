package com.FaustGames.Core.Rendering.Effects.Attributes;

import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;

import java.util.HashMap;
import java.util.Map;

public class EffectAttribute {
    int mAttributeHandler;
    public String Name;

    public static Map<String,String> BuffersMap = new HashMap<String,String>();

    public EffectAttribute(VertexBufferAttribute attribute) {
        Name = attribute.Name;
    }
    public EffectAttribute(String name) {
        Name = name;
    }

    public void create(int program) {
        mAttributeHandler = GLES20.glGetAttribLocation(program, Name);
        GLHelper.checkGlError("GLES20.glGetAttribLocation");
    }

    public AttributeBuffer CreateBuffer(AttributeType type) {
        if (type == AttributeType.Position)
            return (AttributeBuffer)new AttributeBufferPosition(this);
        if (type == AttributeType.TexturePosition)
            return (AttributeBuffer)new AttributeBufferTexturePosition(this);
        if (type == AttributeType.Float)
            return (AttributeBuffer)new AttributeBufferFloat(this);
        throw new UnsupportedOperationException();
    }
}
