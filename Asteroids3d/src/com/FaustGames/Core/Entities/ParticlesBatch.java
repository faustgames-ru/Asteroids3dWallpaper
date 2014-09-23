package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.Content.LensFlareMapsResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.IUpdatable;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.*;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferLensFlare;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferParticles;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Rendering.Textures.TextureRenderTarget;
import com.FaustGames.Core.Shader;

public class ParticlesBatch implements IRenderable, ILoadable, IUpdatable {

    TextureMapResource mResources;
    Particles[] mParticles;

    Texture mMap;
    AttributesBufferParticles mParticlesBuffer;
    IndexBuffer mIndexBuffer;
    private float mAspect;

    public ParticlesBatch(TextureMapResource atlas, Particles[] particles){
        mResources = atlas;
        mParticles= particles;
        mTransforms = new Matrix[mParticles.length];
    }

    short[] mIndices = new short[]{
            0, 1, 2, 0, 2, 3,
            //0, 2, 1, 0, 3, 2,
    };

    PositionTexture[] mOffsets = new PositionTexture[] {
            new PositionTexture(-1f, -1f, 0, 0.0f, 0.0f),
            new PositionTexture( 1f, -1f, 0, 0.5f, 0.0f),
            new PositionTexture( 1f,  1f, 0, 0.5f, 0.5f),
            new PositionTexture(-1f,  1f, 0, 0.0f, 0.5f),
    };

    Vertex[] mTextureOffsets = new Vertex[]{
            new Vertex(0.0f, 0.0f),
            new Vertex(0.5f, 0.0f),
            new Vertex(0.5f, 0.5f),
            new Vertex(0.0f, 0.5f),

    };

    ParticleVertex createVertex(Particle particle, PositionTexture screenOffset){
        return new ParticleVertex(particle.Position,
                new PositionTexture(
                        screenOffset.getX() * particle.Size,
                        screenOffset.getY() * particle.Size,
                        0,
                        mTextureOffsets[particle.ImageIndex].getX() + screenOffset.getU(),
                        mTextureOffsets[particle.ImageIndex].getY() + screenOffset.getV()));
    }

    public void create(Context context){
        mTransforms = new Matrix[mParticles.length];
        mParticlesBuffer = Shader.Particles.createParticlesBuffer();
        int count = 0;
        for (int i = 0; i < mParticles.length; i++) {
            count += mParticles[i].mParticles.length;
            mTransforms[i] = mParticles[i].mMatrix;
        }
        short[] indices = new short[count  * mIndices.length];

        IParticlesVertex[] vertices = new IParticlesVertex[count * 4];
        float[] transformIndices = new float[count * 4];
        int k = 0;
        int ik = 0;
        for (int j = 0; j < mParticles.length; j++) {

            for (int i = 0; i < mParticles[j].mParticles.length; i++) {
                for (int n = 0; n < mIndices.length; n++){
                    indices[ik] = (short)(k + mIndices[n]);
                    ik++;
                }

                for (int n = 0; n < mOffsets.length; n++){
                    vertices[k] =  createVertex(mParticles[j].mParticles[i], mOffsets[n]);
                    transformIndices[k] = j;
                    k++;
                }
            }
        }

        mIndexBuffer = new IndexBuffer(indices);
        mParticlesBuffer.setValues(vertices, transformIndices);

    }

    @Override
    public void load(Context context) {
        mMap = TextureFactory.CreateTexture(context, mResources, false);
        mParticlesBuffer.createVBO();
    }

    Matrix[] mTransforms;
    Color mColor;

    public void setColor(Color color){
        mColor = color;
    }

    @Override
    public void render(Camera camera) {
        Shader.Particles.setColorMap(mMap);
        Shader.Particles.setAspect(mAspect);
        Shader.Particles.setColor(mColor);
        Shader.Particles.setProjectionTransform(camera.getProjectionTransform());
        Shader.Particles.setViewTransform(camera.getViewTransform());
        Shader.Particles.setModelTransforms(mTransforms);
        Shader.Particles.apply();
        mParticlesBuffer.apply();
        Shader.Particles.draw(mIndexBuffer);
    }

    public void setAspect(float aspect) {
        this.mAspect = aspect;
    }

    @Override
    public void update(float timeDelta) {
        for (int i = 0; i < mParticles.length; i++) {
            mParticles[i].update(timeDelta);
            mTransforms[i] = mParticles[i].mMatrix;
        }
    }
}
