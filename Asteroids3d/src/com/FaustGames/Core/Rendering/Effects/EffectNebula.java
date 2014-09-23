package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferNebula;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectNebula extends Effect {
    public EffectNebula() {
        super("", "",
            new String[]{
                "u_ViewMatrix",
                "u_ProjectionMatrix",
                "u_ColorMap1",
                "u_ColorMap2",
                "u_Color1",
                "u_Color2",
                "u_Cos1",
                "u_Cos2",
                "u_Sin1",
                "u_Sin2"
            },
            new String[]{
                "a_Position",
                "a_TexturePosition",
            });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_nebula_vertex),
                loadFromRaw(context, R.raw.effect_nebula_fragment));
    }

    public void setParameters(
            Matrix view,
            Matrix projection,
            Texture colorMap1,
            Texture colorMap2,
            Color color1,
            Color color2,
            float cos1,
            float cos2,
            float sin1,
            float sin2){
        Parameters.get(0).setMatrix(view);
        Parameters.get(1).setMatrix(projection);
        Parameters.get(2).setTexture(0, colorMap1);
        Parameters.get(3).setTexture(1, colorMap2);
        Parameters.get(4).setColor(color1);
        Parameters.get(5).setColor(color2);
        Parameters.get(6).setFloat(cos1);
        Parameters.get(7).setFloat(cos2);
        Parameters.get(8).setFloat(sin1);
        Parameters.get(9).setFloat(sin2);
    }

    public AttributeBufferNebula createNebulaBuffer() {
        return new AttributeBufferNebula(Attributes.get(0), Attributes.get(1));
    }
}
