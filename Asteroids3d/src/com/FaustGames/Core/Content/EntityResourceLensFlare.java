package com.FaustGames.Core.Content;

public class EntityResourceLensFlare  extends EntityResource {
    TextureResource _texture;
    public boolean Ring;
    public TextureResource getTexture(){
        return _texture;
    }
    public EntityResourceLensFlare(TextureResource texture, boolean ring) {
        _texture = texture;
        Ring = ring;
    }
}