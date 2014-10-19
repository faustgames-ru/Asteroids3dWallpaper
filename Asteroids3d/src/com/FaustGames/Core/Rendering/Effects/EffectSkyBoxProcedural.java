package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectSkyBoxProcedural  extends Effect {
    public EffectSkyBoxProcedural() {
        super(
                "", "",
                new String[]{"u_ProjectionMatrix", "u_ModelMatrix", "u_ViewMatrix", "u_Texture", "u_CloudsColor0", "u_CloudsColor1", "u_FogColor"},
                new VertexBufferAttribute[]{
                        VertexBufferAttribute.Position,
                        VertexBufferAttribute.TexturePosition,
                        VertexBufferAttribute.CloudsColor0,
                        VertexBufferAttribute.CloudsColor1,
                        VertexBufferAttribute.StartsColor,
                        VertexBufferAttribute.Alpha
                });
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_skybox_procedural_vertex),
                loadFromRaw(context, R.raw.effect_skybox_procedural_fragment));
    }

    public void setProjection(Matrix matrix) {
        Parameters.get(0).setMatrix(matrix);
    }

    public void setModel(Matrix matrix) {
        Parameters.get(1).setMatrix(matrix);
    }

    public void setView(Matrix matrix) {
        Parameters.get(2).setMatrix(matrix);
    }

    public void setTexture(Texture texture) {
        Parameters.get(3).setTexture(0, texture);
    }

    public void setColor0(Color value) {
        Parameters.get(4).setColor(value);
    }
    public void setColor1(Color value) {
        Parameters.get(5).setColor(value);
    }
    public void setFogColor(Color value) {
        Parameters.get(6).setColor(value);
    }
}
