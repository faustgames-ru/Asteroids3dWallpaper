package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPostProcessGlass extends Effect {
    public EffectPostProcessGlass() {
        super("", "",
                new String[]{ "u_Image", "u_Glass", "u_Filter","u_Aspect", "u_Distortion1" },
                new String[] { "a_Position", "a_TexCoordinate" });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_postprocess_glass_vertex),
                loadFromRaw(context, R.raw.effect_postprocess_glass_fragment));
    }

    public void setImage(Texture texture){
        Parameters.get(0).setTexture(0, texture);
    }

    public void setGlass(Texture texture){
        Parameters.get(1).setTexture(1, texture);
    }

    public void setFilter(Texture texture){
        Parameters.get(2).setTexture(2, texture);
    }


    public void setAspect(float aspect){
        Parameters.get(3).setFloat(aspect);
    }

    public void setDistortion1(Vertex v){
        Parameters.get(4).setPosition(v);
    }
}
