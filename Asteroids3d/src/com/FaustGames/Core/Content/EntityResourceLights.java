package com.FaustGames.Core.Content;

public class EntityResourceLights extends EntityResource {
    TextureResource _texture;
    public TextureResource getTexture(){
        return _texture;
    }
    public EntityResourceLights(TextureResource texture) {
        _texture = texture;
    }
}
