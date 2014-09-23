package com.FaustGames.Core;

import android.content.Context;
import com.FaustGames.Core.Rendering.Effects.*;

public class Shader {
    public static void Create(Context context) {
        Effect.Create(context);
    }
    public static EffectPositionTexture PositionTexture = new EffectPositionTexture();
    public static EffectSkyBox SkyBox = new EffectSkyBox();
    public static EffectSpecularBump SpecularBump = new EffectSpecularBump();
    public static EffectRenderDepth RenderDepth = new EffectRenderDepth();
    public static EffectLensFlare LensFlare = new EffectLensFlare();
    public static EffectParticles Particles = new EffectParticles();
    public static EffectNebula Nebula = new EffectNebula();
    public static EffectParticlesEmitter ParticlesEmitter = new EffectParticlesEmitter();

    public static void unload() {
        Effect.unload();
    }
}
