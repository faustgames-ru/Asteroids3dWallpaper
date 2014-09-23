package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectParticlesEmitter extends Effect {
    public EffectParticlesEmitter() {
        super("", "",
                new String[] {
                        "u_Time",
                        "u_AlphaFading",
                        "u_ScaleFading",
                        "u_ProjectionMatrix",
                        "u_ViewMatrix",
                        "u_ColorMap",
                        "u_Color",
                        "u_TimeStep"
                },
                new String[] {
                        "a_MidPoint",
                        "a_Position",
                        "a_TexturePosition",
                        "a_StartVelocity",
                        "a_Acceleration",
                        "a_Color",
                        "a_Scale",
                        "a_LifeTime",
                        "a_RebornTime",
                        "a_InitPhase",
                });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_particles_emitter_vertex),
                loadFromRaw(context, R.raw.effect_particles_emitter_fragment));
    }

    public void setParameters(
            float time,
            float alphaFading,
            float scaleFading,
            Matrix projectionMatrix,
            Matrix viewMatrix,
            Texture colorMap,
            Color color,
            float timeStep){
        Parameters.get(0).setFloat(time);
        Parameters.get(1).setFloat(alphaFading);
        Parameters.get(2).setFloat(scaleFading);
        Parameters.get(3).setMatrix(projectionMatrix);
        Parameters.get(4).setMatrix(viewMatrix);
        Parameters.get(5).setTexture(0, colorMap);
        Parameters.get(6).setColor(color);
        Parameters.get(7).setFloat(timeStep);
    }
}
