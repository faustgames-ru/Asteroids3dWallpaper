package com.FaustGames.Core.Content;

public class EntityResourceLensFlare  extends EntityResource {
    TextureResource _texture;
    public TextureResource getTexture(){
        return _texture;
    }
    public EntityResourceLensFlare(TextureResource texture) {
        _texture = texture;
    }
}