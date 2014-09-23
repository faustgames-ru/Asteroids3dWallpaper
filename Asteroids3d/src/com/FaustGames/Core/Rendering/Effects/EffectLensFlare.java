package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferLensFlare;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectLensFlare extends Effect {
    public EffectLensFlare() {
            super(
                    "", "",
                    new String[]{
                            "u_ViewMatrix",
                            "u_ProjectionMatrix",
                            "u_Aspect",
                            "u_DepthMap",
                            "u_ColorMap",
                            "u_Color",
                    },
                    new String[]{
                            "a_Position",
                            "a_ScreenOffset",
                            "a_TexturePosition",
                            "a_Offset",
                    });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_lens_flare_vertex),
                loadFromRaw(context, R.raw.effect_lens_flare_fragment));
    }

    public void setViewTransform(Matrix matrix){
        Parameters.get(0).setMatrix(matrix);
    }

    public void setProjectionTransform(Matrix matrix){
        Parameters.get(1).setMatrix(matrix);
    }

    public void setAspect(float aspect){
        Parameters.get(2).setFloat(aspect);
    }

    public void setDepthMap(Texture texture){
        Parameters.get(3).setTexture(0, texture);
    }

    public void setColorMap(Texture texture){
        Parameters.get(4).setTexture(1, texture);
    }

    public void setColor(Color color){
        Parameters.get(5).setColor(color);
    }
    public AttributesBufferLensFlare createLensFlareBuffer(){
        return new AttributesBufferLensFlare(
                Attributes.get(0),
                Attributes.get(1),
                Attributes.get(2),
                Attributes.get(3));
    }
}
