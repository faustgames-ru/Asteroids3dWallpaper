package com.FaustGames.Core.Rendering.Effects;

import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeType;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 26.12.13
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class EffectPositionColor extends Effect {
    int mColorParameterIndex;
    int mTransformParameterIndex;
    int mPositionAttributeIndex;

    public EffectPositionColor() {
        super(
                "precision mediump float;\n" +
                "attribute vec4 vPosition;" +
                "uniform mat4 u_Transform;" +
                "void main() {" +
                "  gl_Position = u_Transform * vPosition;" +
                "}",
                "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}",
                new String[] { "vColor", "u_Transform" },
                new String[] { "vPosition" });
        mColorParameterIndex = 0;
        mTransformParameterIndex = 1;
        mPositionAttributeIndex = 0;
    }

    public AttributeBufferPosition createPositionBuffer() {
        return (AttributeBufferPosition)Attributes.get(mPositionAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public void setColor(Color color) {
        Parameters.get(mColorParameterIndex).setColor(color);
    }

    public void setTransform(Matrix matrix) {
        Parameters.get(mTransformParameterIndex).setMatrix(matrix);
    }

}
