package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectDiffuse extends Effect {

    public Attribute Position;
    public Attribute Normal;
    public Attribute TexturePosition;
    public Attribute TransformIndex;

    public EffectDiffuse() {
        super("", "",
                new String[]{
                        "u_NormalMatrix",
                        "u_NormalModelMatrix",
                        "u_ModelMatrix",
                        "u_ViewMatrix",
                        "u_ProjectionMatrix",
                        "u_Diffuse",
                        "u_LightPosition",
                        "u_SpecularLight",
                        "u_DiffuseLight",
                        "u_AmbientLight",
                        "u_Glow",
                },
                new String[]{
                        "a_Position",
                        "a_Normal",
                        "a_TexturePosition",
                        "a_TransformIndex"
                });
        Position = Attributes.get(0);
        Normal = Attributes.get(1);
        TexturePosition = Attributes.get(2);
        TransformIndex = Attributes.get(3);
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_specular_vertex),
                loadFromRaw(context, R.raw.effect_specular_fragment));
    }

    public void set(
            Matrix3 normalMatrix,
            Matrix3[] normalModelMatrix,
            Matrix[] modelMatrix,
            Matrix viewMatrix,
            Matrix projectionMatrix,
            Texture diffuse,
            Texture glow,
            Vertex lightPosition,
            Color specularLight,
            Color diffuseLight,
            Color ambientLight) {
        Parameters.get(0).setMatrix(normalMatrix);
        Parameters.get(1).setMatrixArray(normalModelMatrix);
        Parameters.get(2).setMatrixArray(modelMatrix);
        Parameters.get(3).setMatrix(viewMatrix);
        Parameters.get(4).setMatrix(projectionMatrix);
        Parameters.get(5).setTexture(1, diffuse);
        Parameters.get(6).setPosition(lightPosition);
        Parameters.get(7).setColor(specularLight);
        Parameters.get(8).setColor(diffuseLight);
        Parameters.get(9).setColor(ambientLight);
        Parameters.get(10).setTexture(2, glow);
    }
}
