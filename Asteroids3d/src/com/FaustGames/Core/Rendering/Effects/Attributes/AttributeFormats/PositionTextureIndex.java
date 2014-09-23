package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

public class PositionTextureIndex extends PositionTexture implements IFloat {
    public float textureIndex;

    public PositionTextureIndex(float x, float y, float z, float u, float v, int index) {
        super(x, y, z, u, v);
        textureIndex = index;
    }

    @Override
    public float getValue() {
        return textureIndex;
    }
}
