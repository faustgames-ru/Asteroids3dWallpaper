package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.NebulaResource;
import com.FaustGames.Core.Entities.Mesh.Mesh;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferFloat;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferNebula;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IMeshVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.INebulaVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferMesh;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

public class NebulaBatch implements IRenderable, ILoadable, IUpdatable {
    Nebula[] mNebula;
    public boolean Created;

    public NebulaBatch(Nebula[] nebula, NebulaResource resource){
        mNebula = nebula;
        mResource = resource;
    }
    Nebula[] nebula;
    IndexBuffer mIndexBuffer;
    public AttributeBufferNebula mNebulaBuffer;
    NebulaResource mResource;

    public float Angle1;
    public float Angle2;
    public float Scale1;
    public float Scale2;

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
        Shader.Nebula.setParameters(
                camera.getViewTransform(),
                camera.getProjectionTransform(),
                mTexture1,
                mTexture2,
                ColorTheme.Default.NebulaBack,
                ColorTheme.Default.NebulaFront,
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
