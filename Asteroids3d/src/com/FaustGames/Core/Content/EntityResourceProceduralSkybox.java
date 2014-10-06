package com.FaustGames.Core.Content;

public class EntityResourceProceduralSkybox extends EntityResource{
    TextureResource _texture;
    public TextureResource getTexture(){
        return _texture;
    }
    public EntityResourceProceduralSkybox(TextureResource texture) {
        _texture = texture;
    }
}
