package com.FaustGames.Core.Entities;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.EntityResourceLensFlare;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBuffer;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

public class LensLightBatch extends Entity implements IRenderable, ILoadable, ICreate {
    public static float Scale = .25f;
    Texture _texture;
    EntityResourceLensFlare _resources;
    Light[] _lights;
    VertexBuffer _vertexBuffer;
    IndexBuffer _indexBuffer;
    Texture _depthMap;

    public LensLightBatch(Scene scene, EntityResourceLensFlare resources){
        _depthMap = scene.getDepthMap();
        _resources = resources;
        _lights = scene.getLensLights();
    }

    @Override
    public void create(Context context) {
        _vertexBuffer = new VertexBuffer(new VertexBufferAttribute[]{
                VertexBufferAttribute.Center,
                VertexBufferAttribute.Position,
                VertexBufferAttribute.TexturePosition,
                VertexBufferAttribute.Color
        });
        float[] vertices = VerticesFactory.CreateLightsVertices(_lights, _vertexBuffer.Stride, Scale);
        _vertexBuffer.setValues(vertices);
        _indexBuffer = new IndexBuffer(IndicesFactory.CreateQuadIndices(_lights.length));
    }

    @Override
    public void load(Context context) {
        _texture = TextureFactory.CreateTexture(context, _resources.getTexture(), false);
        _vertexBuffer.createVBO();
    }

    @Override
    public void render(Camera camera) {
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        GLES20.glDepthMask(false);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        Shader.LensLight.setModel(Matrix.Identity);
        Shader.LensLight.setView(camera.getViewTransform());
        Shader.LensLight.setProjection(camera.getProjectionTransform());
        Shader.LensLight.setTexture(_texture);
        Shader.LensLight.setDepthMap(_depthMap);
        Shader.LensLight.apply();
        _vertexBuffer.apply(Shader.LensLight.Attributes);
        Shader.LensLight.draw(_indexBuffer);
    }
}
