package com.FaustGames.Core.Entities.PatriclessEmitter;

import android.content.Context;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.IUpdatable;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ParticlesEmitterVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferParticlesEmitter;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Shader;

import java.util.ArrayList;

public class EmitterBatch  implements IRenderable, ILoadable, IUpdatable {
    public Emitter[] Emitters;

    public TextureMapResource TextureResource;
    public Texture ColorMap;
    AttributesBufferParticlesEmitter mParticlesBuffer;
    IndexBuffer mIndexBuffer;
    IndexBuffer mIndexBufferHalf;

    public float FadingAlphaPercent = 0.25f;
    public float FadingScalePercent = 0.25f;
    public Color Color;
    float mTimeStep;

    public boolean LowLevel = false;

    public EmitterBatch(TextureMapResource texture, Emitter[] emitters, Color color, float timeStep) {
        TextureResource = texture;
        Emitters = emitters;
        Color = color;
        mTimeStep = timeStep;
    }

    @Override
    public void load(Context context) {
        ColorMap = TextureFactory.CreateTexture(context, TextureResource, false);
        mParticlesBuffer.createVBO();
        time = 0;
    }

    @Override
    public void render(Camera camera) {
        Shader.ParticlesEmitter.setParameters(
                time,
                FadingAlphaPercent,
                FadingScalePercent,
                camera.getProjectionTransform(),
                camera.getViewTransform(),
                ColorMap,
                Color,
                mTimeStep);
        Shader.ParticlesEmitter.apply();
        mParticlesBuffer.apply(Shader.ParticlesEmitter.Attributes);
        if (LowLevel)
            Shader.ParticlesEmitter.draw(mIndexBufferHalf);
        else
            Shader.ParticlesEmitter.draw(mIndexBuffer);
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
