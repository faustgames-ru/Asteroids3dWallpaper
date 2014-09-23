package com.FaustGames.Core.Content;

public class SkyBoxResource {
    public TextureResource XP;
    public TextureResource XM;
    public TextureResource YP;
    public TextureResource YM;
    public TextureResource ZP;
    public TextureResource ZM;
    public SkyBoxResource(
            TextureResource xp,
            TextureResource xm,
            TextureResource yp,
            TextureResource ym,
            TextureResource zp,
            TextureResource zm){
        XP = xp;
        XM = xm;
        YP = yp;
        YM = ym;
        ZP = zp;
        ZM = zm;
    }
}
