package com.FaustGames.Asteroids3dFree;

import android.content.Context;
import com.FaustGames.Core.ColorTheme;
import com.FaustGames.Core.Content.*;
import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Entities.Nebula;
import com.FaustGames.Core.Entities.PatriclessEmitter.Emitter;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;

public class Content {
    public static Content instance;
    public static void init(Context context) {
        if (instance == null)
            instance = new Content(context);
    }
    private Content(Context context) {

        TextureResource noise = new TextureDrawableResource(R.drawable.noise);

        Lights = new EntityResourceLights(new TextureDrawableResource(R.drawable.star_light));
        LensFlare = new EntityResourceLensFlare(new TextureDrawableResource(R.drawable.lens_light), false);
        //LensRings = new EntityResourceLensFlare(new TextureDrawableResource(R.drawable.rainbow), true);
        ProceduralSkybox = new EntityResourceProceduralSkybox(noise);

        Meshes = new EntityResourceMesh[]{
            new EntityResourceMesh(
                    new MeshBatchResource(context, "asteroids_180_0_v", "asteroids_180_0_i", "asteroids_180_0_t", false),
                    new TextureRawResource(R.raw.asteroid_180_nrm_spc, 11, 1024, 1024),
                    new TextureRawResource(R.raw.asteroid_180_dif_glw, 11, 1024, 1024),
                    1),
            new EntityResourceMesh(
                    new MeshBatchResource(context, "asteroids_80_0_v", "asteroids_80_0_i", "asteroids_80_0_t", false),
                    new TextureRawResource(R.raw.asteroid_80_nrm_spc, 10, 512, 512),
                    new TextureRawResource(R.raw.asteroid_80_dif_glw, 10, 512, 512),
                    2),
        };

        Emitter firesEmitter = Emitter.createFires();
        Emitter defaultEmitter = Emitter.createDefault();
        Emitter cloudsEmitter = Emitter.createClouds();

        Particles = new EntityResourceParticles[]{
            new EntityResourceParticles(new TextureDrawableResource(R.drawable.particle), new Emitter[]{ firesEmitter, defaultEmitter }, 0.5f, true, new IColorSource() {
                @Override
                public Color getColor() {
                    return ColorTheme.Default.AdditiveParticles;
                }
            }),
            new EntityResourceParticles(new TextureDrawableResource(R.drawable.particle), new Emitter[]{ firesEmitter, defaultEmitter }, 0.25f, false, new IColorSource() {
                @Override
                public Color getColor() {
                    return ColorTheme.Default.Particles;
                }
            }),
            new EntityResourceParticles(new TextureDrawableResource(R.drawable.cloud), new Emitter[]{ cloudsEmitter }, 2.0f, true, new IColorSource() {
                @Override
                public Color getColor() {
                    return ColorTheme.Default.Clouds;
                }
            }),
        };

        Nebula = new EntityResourceNebula(new TextureDrawableResource(R.drawable.nebula1), new TextureDrawableResource(R.drawable.nebula2), new Nebula[]{
                    new Nebula(new Vertex(-200, 0, 0), 100, false),
                    new Nebula(new Vertex(200, 0, 0), 100, true),
                });
    }

    public EntityResource[] getResources(){
        return new EntityResource[]{
            ProceduralSkybox,
            Lights,
            Meshes[0],
            Meshes[1],
            Nebula,
            LensFlare,
            Particles[0],
            Particles[1],
            Particles[2],
            //LensRings,
        };
    }

    public EntityResourceLights Lights;
    public EntityResourceProceduralSkybox ProceduralSkybox;
    public EntityResourceLensFlare LensFlare;
    public EntityResourceLensFlare LensRings;
    public EntityResourceMesh[] Meshes;
    public EntityResourceParticles[] Particles;
    public EntityResourceNebula Nebula;
}
