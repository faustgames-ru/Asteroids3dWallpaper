package com.FaustGames.Core.Entities.PatriclessEmitter;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.EntityResourceParticles;
import com.FaustGames.Core.Content.IColorSource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.Content.TextureResource;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.Entities.Entity;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ParticlesEmitterVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferParticlesEmitter;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

import java.util.ArrayList;

public class EmitterBatch extends Entity implements IRenderable, ILoadable, IUpdatable, ICreate {
    public Emitter[] Emitters;

    public TextureResource TextureResource;
    public Texture ColorMap;
    AttributesBufferParticlesEmitter mParticlesBuffer;
    IndexBuffer mIndexBuffer;
    IndexBuffer mIndexBufferHalf;

    public float FadingAlphaPercent = 0.25f;
    public float FadingScalePercent = 0.25f;
    float mTimeStep;
    boolean _additive;
    boolean _isOptional;
    IColorSource _colorSource;

    public boolean LowLevel = false;

    public EmitterBatch(EntityResourceParticles resource) {
        TextureResource = resource.getTexture();
        Emitters = resource.getEmitters();
        mTimeStep = resource.getTimeStep();
        _additive = resource.isAdditive();
        _colorSource = resource.getColorSource();
        _isOptional = resource.isOptional();
    }

    @Override
    public void load(Context context) {
        ColorMap = TextureFactory.CreateTexture(context, TextureResource, false);
        mParticlesBuffer.createVBO();
        time = 0;
    }

    @Override
    public void render(Camera camera) {
        if (_isOptional && !Settings.getInstance().DisplayClouds) return;
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        if (_additive) {
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
            GLES20.glDepthMask(false);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }
        else{
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            GLES20.glDepthMask(false);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }
        Shader.ParticlesEmitter.setParameters(
                time,
                FadingAlphaPercent,
                FadingScalePercent,
                camera.getProjectionTransform(),
                camera.getViewTransform(),
                ColorMap,
                _colorSource.getColor(),
                mTimeStep);
        Shader.ParticlesEmitter.apply();
        mParticlesBuffer.apply(Shader.ParticlesEmitter.Attributes);
        if (_isOptional)
            Shader.ParticlesEmitter.draw(mIndexBuffer);
        else
            Shader.ParticlesEmitter.draw(mIndexBuffer, Settings.getInstance().ParticlesCount);
    }

    float time = 0;

    @Override
    public void update(float timeDelta) {
        time += timeDelta;
        time %= Particle.GlobalPeriod;
    }

    public void create(Context context){
        mParticlesBuffer = new AttributesBufferParticlesEmitter();
        ArrayList<ParticlesEmitterVertex> verticesList = new ArrayList<ParticlesEmitterVertex>();
        for (int i = 0; i < Emitters.length; i++)
            Emitters[i].CreateVertices(verticesList);
        mParticlesBuffer.setValues(verticesList);
        int pc = verticesList.size() / 4;
        short[] indices = new short[pc * 6];
        short[] indicesHalf = new short[(pc / 2) * 6];
        for (int i = 0; i < pc; i++)
        {
            indices[i * 6 + 0] = (short)(i * 4 + 0);
            indices[i * 6 + 1] = (short)(i * 4 + 1);
            indices[i * 6 + 2] = (short)(i * 4 + 2);
            indices[i * 6 + 3] = (short)(i * 4 + 0);
            indices[i * 6 + 4] = (short)(i * 4 + 2);
            indices[i * 6 + 5] = (short)(i * 4 + 3);

            if (i < (pc / 2)){
                indicesHalf[i * 6 + 0] = (short)(i * 4 + 0);
                indicesHalf[i * 6 + 1] = (short)(i * 4 + 1);
                indicesHalf[i * 6 + 2] = (short)(i * 4 + 2);
                indicesHalf[i * 6 + 3] = (short)(i * 4 + 0);
                indicesHalf[i * 6 + 4] = (short)(i * 4 + 2);
                indicesHalf[i * 6 + 5] = (short)(i * 4 + 3);
            }
        }
        mIndexBuffer = new IndexBuffer(indices);
        mIndexBufferHalf = new IndexBuffer(indicesHalf);
    }
}
