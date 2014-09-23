package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPostProcessBlurH extends EffectPostProcess {
    public EffectPostProcessBlurH() {
        super("", "",
                new String[]{
                        "u_Image",
                        "u_TexelSize"
                },
                new String[] { "a_Position", "a_TexCoordinate" });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_postprocess_blur_h_vertex),
                loadFromRaw(context, R.raw.effect_postprocess_blur_h_fragment));
    }

    @Override
    public void setImage(Texture texture){
        Parameters.get(0).setTexture(0, texture);
    }

    @Override
    public void setTexelSize(float value) {
        Parameters.get(1).setFloat(value);
    }
}


