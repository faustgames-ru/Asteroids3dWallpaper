package com.FaustGames.Core.Rendering.Effects;

import com.FaustGames.Core.Rendering.Effects.Attributes.*;

public class EffectSolidBlack extends Effect {
    int mPositionAttributeIndex;
    int mTexturePositionAttributeIndex;

    public EffectSolidBlack() {
        super(
                "precision mediump float; attribute vec3 a_Position; void main() { gl_Position = vec4(a_Position, 1.0); }",
                "precision mediump float; void main() { gl_FragColor = vec4(0.0,0.0,0.0,1.0); }",
                new String[] { },
                new VertexBufferAttribute[]{ VertexBufferAttribute.Position, });
    }

}
