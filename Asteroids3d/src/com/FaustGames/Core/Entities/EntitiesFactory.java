package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Content.*;
import com.FaustGames.Core.Entities.Mesh.MeshBatch;
import com.FaustGames.Core.Entities.PatriclessEmitter.EmitterBatch;

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
        if (resource instanceof EntityResourceParticles)
            return new EmitterBatch((EntityResourceParticles)resource);
        if (resource instanceof EntityResourceNebula)
            return new NebulaBatch((EntityResourceNebula)resource);
        if (resource instanceof EntityResourceLensRings)
            return new LensRingsBatch(scene, (EntityResourceLensRings)resource);
        return null;
    }
}
