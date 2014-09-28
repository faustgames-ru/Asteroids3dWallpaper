package com.FaustGames.Core.Rendering.Effects;

import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;

public class EffectRenderDepth extends Effect {
    public EffectAttribute Position;
    public EffectAttribute TransformIndex;

    public EffectRenderDepth() {
        super(
                "precision mediump float;\n" +
                "attribute vec3 a_Position;" +
                "attribute float a_TransformIndex;" +

                "uniform mat4 u_ModelMatrix[32];" +
                "uniform mat4 u_ViewMatrix;" +
                "uniform mat4 u_ProjectionMatrix;" +
                "varying float v_Z;"+
                "void main()" +
                "{" +
                "int transformIndex = int(a_TransformIndex);"+
                //"vec4 pos = vec4(a_Position, 1.0);" +
                "vec4 pos = u_ModelMatrix[transformIndex] * vec4(a_Position, 1.0);" +
                "pos = u_ViewMatrix * pos;" +
                "v_Z = 1.0 - clamp(pos.z * 0.01, 0.0, 1.0);"+
                "gl_Position = u_ProjectionMatrix * pos;" +
                "}",

                "precision mediump float;\n" +
                "varying float v_Z;"+
                "void main()" +
                "{" +
                "float z = v_Z;"+
                //"float z = 0.6;"+
                //"if(z > 0.1) z = 1.0;"+
                "gl_FragColor[0] = z;" +
                "gl_FragColor[1] = z;" +
                "gl_FragColor[2] = z;" +
                "gl_FragColor[3] = 1.0;" +
                "}",

                new String[]{
                        "u_ModelMatrix",
                        "u_ViewMatrix",
                        "u_ProjectionMatrix",
                },
                new String[]{
                        "a_Position",
                        "a_TransformIndex"
                });
        Position = Attributes.get(0);
        TransformIndex = Attributes.get(1);
    }

    public void apply(AttributesBufferMesh meshBuffer){
        meshBuffer.applyForDepth(Position, TransformIndex);
    }

    public void setModelTransforms(Matrix[] matrix){
        Parameters.get(0).setMatrixArray(matrix);
    }

    public void setViewTransform(Matrix matrix){
        Parameters.get(1).setMatrix(matrix);
    }

    public void setProjectionTransform(Matrix matrix){
        Parameters.get(2).setMatrix(matrix);
    }

    public AttributeBufferPosition createPositionBuffer() {
        return (AttributeBufferPosition)Position.CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferFloat createTransformIndexBuffer() {
        return (AttributeBufferFloat)TransformIndex.CreateBuffer(AttributeType.Float);
    }
}
