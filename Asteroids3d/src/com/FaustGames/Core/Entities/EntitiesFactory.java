package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Content.*;
import com.FaustGames.Core.Entities.Mesh.MeshBatch;

public class EntitiesFactory {
    public static Entity CreateEntity(Scene scene, EntityResource resource)
    {
        if (resource instanceof EntityResourceLights)
            return new LightsBatch(scene, (EntityResourceLights)resource);
        if (resource instanceof EntityResourceProceduralSkybox)
            return new ProceduralSkybox((EntityResourceProceduralSkybox)resource);
        if (resource instanceof EntityResourceLensFlare)
            return new LensLightBatch(scene, (EntityResourceLensFlare)resource);
        if (resource instanceof EntityResourceMesh)
            return new MeshBatch(scene, (EntityResourceMesh)resource);
        return null;
    }
}
