package com.FaustGames.Core.Content;

public class MeshMapsResource {
    public TextureResource Bump;
    public TextureResource Specular;
    public TextureResource Diffuse;
    public TextureResource Glow;
    public MeshMapsResource(
            TextureResource bump,
            TextureResource specular,
            TextureResource diffuse,
            TextureResource glow){
        Bump = bump;
        Specular = specular;
        Diffuse = diffuse;
        Glow = glow;
    }
}
