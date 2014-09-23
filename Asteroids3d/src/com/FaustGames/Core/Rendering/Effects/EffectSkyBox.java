package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;
import com.FaustGames.Core.Rendering.Textures.Texture;

import java.util.Vector;

public class EffectSkyBox extends Effect {
    int mPositionAttributeIndex;
    int mTexturePositionAttributeIndex;

    public EffectSkyBox() {
        super(
                "", "",
                new String[] { "u_ProjectionMatrix", "u_ModelMatrix", "u_Texture", "u_Color", "u_Eye", "u_ViewMatrix", "u_LightPosition"  },
                new String[] { "a_Position", "a_TexCoordinate" });
        mPositionAttributeIndex = 0;
        mTexturePositionAttributeIndex = 1;
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_skybox_vertex),
                loadFromRaw(context, R.raw.effect_skybox_fragment));
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

    public void setEye(Vertex v) {
        Parameters.get(4).setPosition(v);
    }


    public void setView(Matrix matrix) {
        Parameters.get(5).setMatrix(matrix);
    }

    public void setLight(Vertex value) {
        Parameters.get(6).setPosition(value);
    }
}
