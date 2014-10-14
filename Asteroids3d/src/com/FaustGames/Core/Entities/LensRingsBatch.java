package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.Content.EntityResourceLensFlare;
import com.FaustGames.Core.Content.EntityResourceLensRings;
import com.FaustGames.Core.ICreate;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBuffer;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class LensRingsBatch extends Entity implements IRenderable, ILoadable, ICreate {
    public static float Scale = .25f;
    Texture _texture;
    EntityResourceLensRings _resources;
    Light[] _lights;
    VertexBuffer _vertexBuffer;
    IndexBuffer _indexBuffer;
    Texture _depthMap;

    public LensRingsBatch(Scene scene, EntityResourceLensRings resources){
        _depthMap = scene.getDepthMap();
        _resources = resources;
        _lights = scene.getLensLights();
    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void load(Context context) {

    }

    @Override
    public void render(Camera camera) {

    }
}
