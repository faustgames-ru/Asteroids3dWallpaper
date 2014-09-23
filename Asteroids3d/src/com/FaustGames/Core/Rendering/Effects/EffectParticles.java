package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferParticles;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectParticles extends Effect {
    public EffectParticles() {
        super(
                "", "",
                new String[]{
                        "u_Aspect",
                        "u_ViewMatrix",
                        "u_ProjectionMatrix",
                        "u_ModelMatrix",
                        "u_ColorMap",
                        "u_Color"
                },
                new String[]{
                        "a_Position",
                        "a_ScreenOffset",
                        "a_TexturePosition",
                        "a_TransformIndex"
                });
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_particles_vertex),
                loadFromRaw(context, R.raw.effect_particles_fragment));
    }

    public void setAspect(float aspect){
        Parameters.get(0).setFloat(aspect);
    }

    public void setViewTransform(Matrix matrix){
        Parameters.get(1).setMatrix(matrix);
    }

    public void setProjectionTransform(Matrix matrix){
        Parameters.get(2).setMatrix(matrix);
    }

    public void setModelTransforms(Matrix[] matrix){
        Parameters.get(3).setMatrixArray(matrix);
    }

    public void setColorMap(Texture texture){
        Parameters.get(4).setTexture(1, texture);
    }

    public void setColor(Color color){
        Parameters.get(5).setColor(color);
    }

    public AttributesBufferParticles createParticlesBuffer(){
        return new AttributesBufferParticles(
                Attributes.get(0),
                Attributes.get(1),
                Attributes.get(2),
                Attributes.get(3));
    }
}
