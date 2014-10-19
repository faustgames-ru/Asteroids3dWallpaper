package com.FaustGames.Core.Rendering.Effects;

import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPositionTexture extends Effect {
    int mPositionAttributeIndex;
    int mTexturePositionAttributeIndex;

    public EffectPositionTexture() {
        super(
                "uniform mat4 u_ProjectionMatrix;\n" +
                "uniform mat4 u_ModelMatrix;\n"+
                "precision mediump float;\n" +
                "attribute vec3 a_Position;\n" +
                "attribute vec2 a_TexturePosition;\n" +
                "varying vec2 v_TexCoordinate;\n" +
                "\n" +
                "void main() {\n" +
                "  v_TexCoordinate = a_TexturePosition;\n" +
                "  gl_Position = u_ProjectionMatrix *  (u_ModelMatrix * vec4(a_Position, 1.0));\n" +
                "}",
                "precision mediump float;\n" +
                "varying vec2 v_TexCoordinate;\n" +
                "uniform sampler2D u_Texture;\n" +
                "uniform vec4 u_Color;\n"+
                "\n" +
                "void main() {\n" +
                "  gl_FragColor = texture2D(u_Texture, v_TexCoordinate) * u_Color;\n" +
                "}",
                new String[] { "u_ProjectionMatrix", "u_ModelMatrix", "u_Texture", "u_Color" },
                new VertexBufferAttribute[] { VertexBufferAttribute.Position, VertexBufferAttribute.TexturePosition });
        mPositionAttributeIndex = 0;
        mTexturePositionAttributeIndex = 1;
    }

    public AttributesBufferSkybox creatBuffer() {
        return new AttributesBufferSkybox(Attributes.get(0), Attributes.get(1));
    }

    public AttributeBufferPosition createPositionBuffer() {
        return (AttributeBufferPosition)Attributes.get(mPositionAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferTexturePosition createTexturePositionBuffer() {
        return (AttributeBufferTexturePosition)Attributes.get(mTexturePositionAttributeIndex).CreateBuffer(AttributeType.TexturePosition);
    }

    public void setProjection(Matrix matrix) {
        Parameters.get(0).setMatrix(matrix);
    }

    public void setModel(Matrix matrix) {
        Parameters.get(1).setMatrix(matrix);
    }

    public void setTexture(Texture texture) {
        Parameters.get(2).setTexture(0, texture);
    }

    public void setColor(Color color) {
        Parameters.get(3).setColor(color);
    }
}
