package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

import com.FaustGames.Core.Mathematics.Vertex;

public class PositionTexture extends Position implements IPositionTexture {
    public float[] uv = new float[2];

    public float getU(){
        return uv[0];
    }

    public float getV(){
        return uv[1];
    }

    public PositionTexture(Vertex position, Vertex texturePosition) {
        super(position.getX(), position.getY(), position.getZ());
        uv[0] = texturePosition.getX();
        uv[1] = texturePosition.getY();
    }

    public PositionTexture(float x, float y, float z, float u, float v) {
        super(x, y, z);
        uv[0] = u;
        uv[1] = v;
    }
}
