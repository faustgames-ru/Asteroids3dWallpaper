package com.FaustGames.Core.Content;

public class EntityResourceLensRings extends EntityResource {
    TextureResource _texture;
    public TextureResource getTexture(){
        return _texture;
    }
    public EntityResourceLensRings(TextureResource texture) {
        _texture = texture;
    }
}
