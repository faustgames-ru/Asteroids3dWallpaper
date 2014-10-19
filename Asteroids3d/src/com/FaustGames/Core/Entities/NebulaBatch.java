package com.FaustGames.Core.Entities;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.EntityResourceNebula;
import com.FaustGames.Core.Content.NebulaResource;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferNebula;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.INebulaVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

public class NebulaBatch extends Entity implements IRenderable, ILoadable, IUpdatable, ICreate {
    Nebula[] mNebula;
    public boolean Created;

    public NebulaBatch(EntityResourceNebula resource){
        mNebula = resource.getNebula();
        mResource = resource.getResource();
    }
    IndexBuffer mIndexBuffer;
    public AttributeBufferNebula mNebulaBuffer;
    NebulaResource mResource;

    public float Angle1;
    public float Angle2;

    class NebulaVertex extends PositionTexture implements INebulaVertex{
        public NebulaVertex(float x, float y, float z, float u, float v) {
            super(x, y, z, u, v);
        }
    }

    public void create(Context context){
        mNebulaBuffer = Shader.Nebula.createNebulaBuffer();

        int size = mNebula.length * 4;
        INebulaVertex[] vertices = new INebulaVertex[size];
        for (int j = 0; j < mNebula.length; j++) {

            int o = j * 4;
            vertices[o + 0] = new NebulaVertex(
                    mNebula[j].Position.getX(),
                    mNebula[j].Position.getY() - mNebula[j].Size,
                    mNebula[j].Position.getZ() - mNebula[j].Size,
                    0, 0);
            vertices[o + 1] = new NebulaVertex(
                    mNebula[j].Position.getX(),
                    mNebula[j].Position.getY() - mNebula[j].Size,
                    mNebula[j].Position.getZ() + mNebula[j].Size,
                    0, 1);
            vertices[o + 2] = new NebulaVertex(
                    mNebula[j].Position.getX(),
                    mNebula[j].Position.getY() + mNebula[j].Size,
                    mNebula[j].Position.getZ() + mNebula[j].Size,
                    1, 1);
            vertices[o + 3] = new NebulaVertex(
                    mNebula[j].Position.getX(),
                    mNebula[j].Position.getY() + mNebula[j].Size,
                    mNebula[j].Position.getZ() - mNebula[j].Size,
                    1, 0);
        }
        mNebulaBuffer.setValues(vertices);
        short[] indices = new short[mNebula.length * 6];
        for (int j = 0; j < mNebula.length; j++) {
            int o = j * 6;
            int k = j * 4;
            if (!mNebula[j].Inverse) {
                indices[o + 0] = (short)(k + 0);
                indices[o + 1] = (short)(k + 1);
                indices[o + 2] = (short)(k + 2);
                indices[o + 3] = (short)(k + 0);
                indices[o + 4] = (short)(k + 2);
                indices[o + 5] = (short)(k + 3);
            }
            else {
                indices[o + 0] = (short)(k + 0);
                indices[o + 1] = (short)(k + 2);
                indices[o + 2] = (short)(k + 1);
                indices[o + 3] = (short)(k + 0);
                indices[o + 4] = (short)(k + 3);
                indices[o + 5] = (short)(k + 2);
            }
        }

        mIndexBuffer = new IndexBuffer(indices);
        Created = true;
    }

    Texture mTexture1;
    Texture mTexture2;
    @Override
    public void load(Context context) {
        mTexture1 = TextureFactory.CreateTexture(context, mResource.Texture1, false);
        mTexture2 = TextureFactory.CreateTexture(context, mResource.Texture2, false);
        mNebulaBuffer.createVBO();
    }

    @Override
    public void render(Camera camera) {
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        Shader.Nebula.setParameters(
                camera.getViewTransform(),
                camera.getProjectionTransform(),
                mTexture1,
                mTexture2,
                ColorTheme.Default.getNebula0(),
                ColorTheme.Default.getNebula1(),
                MathF.cos(Angle1),
                MathF.cos(Angle2),
                MathF.sin(Angle1),
                MathF.sin(Angle2)
        );

        Shader.Nebula.apply();
        mNebulaBuffer.apply();
        Shader.Nebula.draw(mIndexBuffer);

    }

    @Override
    public void update(float timeDelta) {
        Angle1 -= 1.0f * timeDelta;
        Angle2 -= 0.2f * timeDelta;
    }
}
