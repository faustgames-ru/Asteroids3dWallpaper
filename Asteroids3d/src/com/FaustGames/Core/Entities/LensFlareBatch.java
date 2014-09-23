package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.ColorTheme;
import com.FaustGames.Core.Content.LensFlareMapsResource;
import com.FaustGames.Core.Entities.Mesh.Mesh;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ILensFlareVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IMeshVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.LensFlareVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferLensFlare;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Rendering.Textures.TextureRenderTarget;
import com.FaustGames.Core.Shader;

public class LensFlareBatch implements IRenderable, ILoadable {

    LensFlareMapsResource mResources;
    Texture mMap;
    Light[] mLights;
    AttributesBufferLensFlare mLensFlareBuffer;
    TextureRenderTarget mDepthMap;
    IndexBuffer mIndexBuffer;
    IndexBuffer mIndexBufferSimple;
    private float mAspect;

    public LensFlareBatch(LensFlareMapsResource resources, Light[] lights){
        mResources = resources;
        mLights = lights;
    }

    PositionTexture[] mOffsets1 = new PositionTexture[] {
        new PositionTexture(-1f, -1f, 0, 0, 0),
        new PositionTexture( 1f, -1f, 0, 0.5f, 0),
        new PositionTexture( 1f,  1f, 0, 0.5f, 0.5f),
        new PositionTexture(-1f,  1f, 0, 0, 0.5f),
    };
    PositionTexture[] mOffsets2 = new PositionTexture[] {
        new PositionTexture(-1f, -1f, 0f, 0, 0.5f),
        new PositionTexture( 1f, -1f, 0f, 0.5f, 0.5f),
        new PositionTexture( 1f,  1f, 0f, 0.5f, 1),
        new PositionTexture(-1f,  1f, 0f, 0, 1),
    };

    PositionTexture[] mOffsets3 = new PositionTexture[] {

        new PositionTexture(-1f, -1f, 0, 0.5f, 0),
        new PositionTexture( 1f, -1f, 0, 1, 0),
        new PositionTexture( 1f,  1f, 0, 1, 0.5f),
        new PositionTexture(-1f,  1f, 0, 0.5f, 0.5f),
    };

    PositionTexture[] mOffsets4 = new PositionTexture[] {

        new PositionTexture(-1f, -1f, 0, 0.5f, 0.5f),
        new PositionTexture( 1f, -1f, 0, 1, 0.5f),
        new PositionTexture( 1f,  1f, 0, 1, 1),
        new PositionTexture(-1f,  1f, 0, 0.5f, 1),
    };

    short[] mIndices = new short[]{
        0, 1, 2, 0, 2, 3,
    };

    lensRingContainer[] mContainres = new lensRingContainer[]{
            new lensRingContainer(mOffsets1, 1.0f, 0),
            new lensRingContainer(mOffsets1, 0.98f, 0),
            //new lensRingContainer(mOffsets2, 0.25f, 0),
            //new lensRingContainer(mOffsets2, -0.25f, 0),
            //new lensRingContainer(mOffsets4, -0.5f, 0),
    };

    LensFlareVertex createVertex(Light light, PositionTexture screenOffset, float offset, float z){
        return new LensFlareVertex(light.getPosition(),
                new PositionTexture(
                        screenOffset.getX() * light.getLensBrightness(),
                        screenOffset.getY() * light.getLensBrightness(),
                        z,
                        screenOffset.getU(),
                        screenOffset.getV()), offset);
    }

    public void create(Context context){

        mLensFlareBuffer = Shader.LensFlare.createLensFlareBuffer();
        int size = mLights.length * mContainres.length * 4;
        short[] indices = new short[mLights.length * mContainres.length * mIndices.length];
        short[] simpleIndices = new short[mLights.length * mIndices.length];

        ILensFlareVertex[] vertices = new ILensFlareVertex[size];
        int k = 0;
        int ik = 0;
        int iks = 0;
        for (int j = 0; j < mLights.length; j++) {

            for (int i = 0; i < mContainres.length; i++) {
                if (i == 0)
                {
                    for (int n = 0; n < mIndices.length; n++){
                        simpleIndices[iks] = (short)(k + mIndices[n]);
                        iks++;
                    }
                }

                for (int n = 0; n < mIndices.length; n++){
                    indices[ik] = (short)(k + mIndices[n]);
                    ik++;
                }
                for (int n = 0; n < mContainres[i].Offsets.length; n++){
                    vertices[k] = createVertex(mLights[j],  mContainres[i].Offsets[n],  mContainres[i].Offset,  mContainres[i].Z);
                    k++;
                }
            }
            /*
            for (int n = 0; n < mIndices.length; n++){
                indices[ik] = (short)(k + mIndices[n]);
                ik++;
            }
            for (int n = 0; n < mOffsets1.length; n++){
                vertices[k] = createVertex(mLights[j], mOffsets1[n], 1);
                k++;
            }

            for (int n = 0; n < mIndices.length; n++){
                indices[ik] = (short)(k + mIndices[n]);
                ik++;
            }
            for (int n = 0; n < mOffsets2.length; n++){
                vertices[k] = createVertex(mLights[j], mOffsets2[n], 0.25f);
                k++;
            }

            for (int n = 0; n < mIndices.length; n++){
                indices[ik] = (short)(k + mIndices[n]);
                ik++;
            }
            for (int n = 0; n < mOffsets3.length; n++){
                vertices[k] = createVertex(mLights[j], mOffsets3[n], -0.25f);
                k++;
            }

            for (int n = 0; n < mIndices.length; n++){
                indices[ik] = (short)(k + mIndices[n]);
                ik++;
            }
            for (int n = 0; n < mOffsets4.length; n++){
                vertices[k] = createVertex(mLights[j], mOffsets4[n], -0.5f);
                k++;
            }
            */
        }

        mIndexBuffer = new IndexBuffer(indices);
        mIndexBufferSimple = new IndexBuffer(simpleIndices);
        mLensFlareBuffer.setValues(vertices);

    }

    @Override
    public void load(Context context) {
        mMap = TextureFactory.CreateTexture(context, mResources.Texture, false);

        mLensFlareBuffer.createVBO();

    }

    public void setDepthMap(TextureRenderTarget depthMap) {
        mDepthMap = depthMap;
    }

    @Override
    public void render(Camera camera) {
        Shader.LensFlare.setDepthMap(mDepthMap);
        Shader.LensFlare.setColorMap(mMap);
        Shader.LensFlare.setColor(ColorTheme.Default.Lens);
        Shader.LensFlare.setAspect(mAspect);
        Shader.LensFlare.setProjectionTransform(camera.getLensProjectionTransform());
        Shader.LensFlare.setViewTransform(camera.getViewTransform());
        //Shader.LensFlare.setProjectionTransform(Matrix.Identity);
        //Shader.LensFlare.setViewTransform(Matrix.Identity);
        Shader.LensFlare.apply();
        mLensFlareBuffer.apply();
        Shader.LensFlare.draw(mIndexBuffer);
    }

    public void renderSimple(Camera camera) {
        Shader.LensFlare.setDepthMap(mDepthMap);
        Shader.LensFlare.setColorMap(mMap);
        Shader.LensFlare.setColor(ColorTheme.Default.Lens);
        Shader.LensFlare.setAspect(mAspect);
        Shader.LensFlare.setProjectionTransform(camera.getLensProjectionTransform());
        Shader.LensFlare.setViewTransform(camera.getViewTransform());
        //Shader.LensFlare.setProjectionTransform(Matrix.Identity);
        //Shader.LensFlare.setViewTransform(Matrix.Identity);
        Shader.LensFlare.apply();
        mLensFlareBuffer.apply();
        Shader.LensFlare.draw(mIndexBufferSimple);
    }

    public void setAspect(float aspect) {
        this.mAspect = aspect;
    }
}

class lensRingContainer {
    public lensRingContainer(PositionTexture[] offsets, float offset, float z){
        Offsets = offsets;
        Z = z;
        Offset = offset;
    }
    public PositionTexture[] Offsets;
    public float Z;
    public float Offset;
}
