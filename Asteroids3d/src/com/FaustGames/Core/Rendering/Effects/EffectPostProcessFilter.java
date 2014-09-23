package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPostProcessFilter extends EffectPostProcess {
    public EffectPostProcessFilter() {
        super("", "",
                new String[]{ "u_Image" },
                new String[] { "a_Position", "a_TexCoordinate" });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_postprocess_filter_vertex),
                loadFromRaw(context, R.raw.effect_postprocess_filter_fragment));
    }

    @Override
    public void setImage(Texture texture){
        Parameters.get(0).setTexture(0, texture);
    }
}

