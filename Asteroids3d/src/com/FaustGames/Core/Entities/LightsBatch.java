package com.FaustGames.Core.Entities;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.EntityResourceLights;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBuffer;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

public class LightsBatch extends Entity implements IRenderable, ILoadable, ICreate {
    public static float Scale = .25f;

    Light[] _lights;
    EntityResourceLights _resources;
    VertexBuffer _vertexBuffer;
    IndexBuffer _indexBuffer;
    Texture _texture;

    public LightsBatch(Scene scene, EntityResourceLights resources) {
        _resources = resources;
        _lights = scene.getLensLights();
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
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        Shader.PositionColorTexture.setModel(Matrix.Identity);
        Shader.PositionColorTexture.setView(camera.getViewTransform());
        Shader.PositionColorTexture.setProjection(camera.getProjectionTransform());
        Shader.PositionColorTexture.setTexture(_texture);
        Shader.PositionColorTexture.apply();
        _vertexBuffer.apply(Shader.PositionColorTexture.Attributes);
        Shader.PositionColorTexture.draw(_indexBuffer);
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
}
