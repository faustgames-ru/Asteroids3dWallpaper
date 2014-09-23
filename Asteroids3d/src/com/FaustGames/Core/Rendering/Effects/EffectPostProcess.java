package com.FaustGames.Core.Rendering.Effects;

import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectPostProcess extends Effect {

    public EffectPostProcess(String vertexShader, String fragmentShader, String[] parameters, String[] attributes) {
        super(vertexShader, fragmentShader, parameters, attributes);
    }

    public void setImage(Texture texture){}
    public void setTexelSize(float value){}
}
