package com.FaustGames.Core;

import com.FaustGames.Core.Rendering.Color;

public class Settings {
    static Settings _instance = new Settings();
    public static Settings getInstance(){ return _instance; }

    public boolean DisplayLensFlare = true;
    public boolean DisplayClouds = false;
    public boolean HighQualitySkybox = true;
    public float AsteroidsCount = 0.5f;
    public float ParticlesCount = 0.5f;
    public float EnergySaving = 0.0f;
    public float SpecularColor = 0.2f;
    public float GlowColor = 0.2f;
    public float NebulaColor0 = 0.2f;
    public float NebulaColor1 = 0.0f;
    public float ParticlesColor = 0.2f;
}
