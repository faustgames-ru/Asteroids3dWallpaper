package com.FaustGames.Asteroids3dFree;

import android.content.Context;
import com.FaustGames.Core.Content.*;
import com.FaustGames.Core.DeviceConfiguration;

public class Content {
    public static Content instance;
    public static void init(Context context) {
        if (instance == null)
            instance = new Content(context);
    }
    private Content(Context context){

        boolean limitSize = !DeviceConfiguration.isTablet;
        TextureDrawableResource noise = new TextureDrawableResource(R.drawable.noise, R.drawable.noise);
        SkyBox = new SkyBoxResource(
                //new TextureDrawableResource(R.drawable.skybox_hd_xp, R.drawable.skybox_hd_small_xp),
                //new TextureDrawableResource(R.drawable.skybox_hd_xm, R.drawable.skybox_hd_small_xm),
                //new TextureDrawableResource(R.drawable.skybox_hd_yp, R.drawable.skybox_hd_small_yp),
                //new TextureDrawableResource(R.drawable.skybox_hd_ym, R.drawable.skybox_hd_small_ym),
                //new TextureDrawableResource(R.drawable.skybox_hd_zp, R.drawable.skybox_hd_small_zp),
                //new TextureDrawableResource(R.drawable.skybox_hd_zm, R.drawable.skybox_hd_small_zm));
                noise,
                noise,
                noise,
                noise,
                noise,
                noise
                //new TextureMapResource(context, "skybox_hd_xp", false),
                //new TextureMapResource(context, "skybox_hd_xm", false),
                //new TextureMapResource(context, "skybox_hd_yp", false),
                //new TextureMapResource(context, "skybox_hd_ym", false),
                //new TextureMapResource(context, "skybox_hd_zp", false),
                //new TextureMapResource(context, "skybox_hd_zm", false)
                );

        LensFlareMaps = new LensFlareMapsResource(new TextureMapResource(context, "lens_flare", limitSize));

        MeshDefaultMaps = new MeshMapsResource(
                new TextureDrawableResource(R.drawable.asteroid_180_nrm, R.drawable.asteroid_180_nrm_small),
                //new TextureMapResource(context, "asteroid_180_nrm", false),
                new TextureMapResource(context, "asteroid_180_spc", true),
                new TextureMapResource(context, "asteroid_180", true),
                new TextureMapResource(context, "asteroid_180_glw", true)
        );

        SmallMeshDefaultMaps = new MeshMapsResource(
                new TextureDrawableResource(R.drawable.asteroid_80_nrm, R.drawable.asteroid_80_nrm_small),
                //new TextureMapResource(context, "asteroid_80_nrm", false),
                new TextureMapResource(context, "asteroid_80_spc", true),
                new TextureMapResource(context, "asteroid_80", true),
                new TextureMapResource(context, "asteroid_80_glw", true)
        );

        //Glass = new TextureMapResource(context, "glass");
        Particle = new TextureMapResource(context, "particle", true);
        Cloud = new TextureMapResource(context, "cloud", true);
        //MeshBatch = new MeshBatchResource(context, "batch_vb", "batch_ib", "batch_transforms", true);
        MeshBatch = new MeshBatchResource(context, "asteroids_180_v", "asteroids_180_i", "asteroids_180_t", false);
        SmallMeshBatch = new MeshBatchResource(context, "asteroids_80_v", "asteroids_80_i", "asteroids_80_t", false);
        SmallMeshBatch1 = new MeshBatchResource(context, "asteroids_80_1_v", "asteroids_80_1_i", "asteroids_80_1_t", false);

        Nebula = new NebulaResource(
                new TextureMapResource(context, "nebula1", true),
                new TextureMapResource(context, "nebula2", true));

    }

    public SkyBoxResource SkyBox;
    public LensFlareMapsResource LensFlareMaps;
    public MeshMapsResource MeshDefaultMaps;
    public MeshBatchResource MeshBatch;
    public MeshMapsResource SmallMeshDefaultMaps;
    public MeshBatchResource SmallMeshBatch;
    public MeshBatchResource SmallMeshBatch1;

    public NebulaResource Nebula;
    //public TextureMapResource Glass;
    public TextureMapResource Particle;
    public TextureMapResource Cloud;
}
