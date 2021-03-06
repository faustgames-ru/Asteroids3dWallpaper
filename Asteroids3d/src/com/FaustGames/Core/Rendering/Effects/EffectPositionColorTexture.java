package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPositionColorTexture  extends Effect {
    public EffectPositionColorTexture() {
        super(
                "", "",
                new String[]{"u_ProjectionMatrix", "u_ModelMatrix", "u_ViewMatrix", "u_Texture"},
                new VertexBufferAttribute[]{
                        VertexBufferAttribute.Position,
                        VertexBufferAttribute.TexturePosition,
                        VertexBufferAttribute.Color,
                });
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_position_color_texture_vertex),
                loadFromRaw(context, R.raw.effect_position_color_texture_fragment));
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
}
