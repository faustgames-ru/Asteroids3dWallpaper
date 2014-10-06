package com.FaustGames.Core.Entities.Mesh;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.EntityResource;
import com.FaustGames.Core.Content.EntityResourceMesh;
import com.FaustGames.Core.Content.MeshBatchResource;
import com.FaustGames.Core.Content.MeshMapsResource;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.Entities.Entity;
import com.FaustGames.Core.Entities.Light;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferFloat;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferMesh;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferTexturePosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IMeshVertex;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class MeshBatch extends Entity implements IRenderable, ILoadable, IUpdatable, ICreate, IDepthMapRender {
    //public ArrayList<Mesh> mMeshes;
    EntityResourceMesh _resources;
    public boolean Created = false;

    public MeshBatch(Scene scene, EntityResourceMesh resources){
        //mMeshes = meshes;
        _resources = resources;
        mLight = scene.getLight();
        MeshBatchResource batchResource = _resources.getGeometryResource();
        mTransforms = new Matrix[batchResource.Count];
        mNormalTransforms = new Matrix3[batchResource.Count];
        mMeshTransforms = new MeshTransform[batchResource.Count];
        for (int j = 0; j < mTransforms.length; j++) {
            mTransforms[j] = batchResource.Positions[j];
            mNormalTransforms[j] =new Matrix3(
                    mTransforms[j].getXX(), mTransforms[j].getXY(), mTransforms[j].getXZ(),
                    mTransforms[j].getYX(), mTransforms[j].getYY(), mTransforms[j].getYZ(),
                    mTransforms[j].getZX(), mTransforms[j].getZY(), mTransforms[j].getZZ());

            int r = MathF.rand(2);
            if (r == 0)
            {
                mMeshTransforms[j] = new MeshTransform();
                mMeshTransforms[j].rotationVelocity = MathF.rand(-0.5f, 0.5f);
            }
            else if (r == 1)
            {
                mMeshTransforms[j] = new MeshTransformX();
                mMeshTransforms[j].rotationVelocity = MathF.rand(-0.25f, 0.25f);
            }
            else
            {
                mMeshTransforms[j] = new MeshTransformY();
                mMeshTransforms[j].rotationVelocity = MathF.rand(-0.25f, 0.25f);
            }

            mMeshTransforms[j].Origin = mTransforms[j];
        }
    }
        /*
    public MeshBatch(MeshMapsResource resources, ArrayList<Mesh> meshes){
        mMeshes = meshes;
        mResources = resources;
    }
         */
    public void create(Context context){
        mMeshBuffer = Shader.SpecularBump.createMeshBuffer();
        mMeshBuffer.setValues(_resources.getGeometryResource().VertexBuffer);
        mMeshIndexBuffer = new IndexBuffer(_resources.getGeometryResource().IndexBuffer);
        Created = true;
    }

    public void renderBump(Camera camera) {
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_FRONT);
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        Shader.SpecularBump.setEye(camera.getPosition());
        //Shader.SpecularBump.setFogDensity(ColorTheme.Default.FogDensity);

        Shader.SpecularBump.setGlowLevel(0.5f + MathF.abs(mTime) * 0.5f);
        Shader.SpecularBump.setGlowColor(ColorTheme.Default.Glow);
        Shader.SpecularBump.setNormalSpecularMap(mNormalSpecular);
        Shader.SpecularBump.setDiffuseGlowMap(mDiffuseGlow);

        Shader.SpecularBump.setProjectionTransform(camera.getProjectionTransform());
        Shader.SpecularBump.setViewTransform(camera.getViewTransform());
        //synchronized (mMeshes) {
        Shader.SpecularBump.setModelTransforms(mTransforms);
        Shader.SpecularBump.setNormalModelTransforms(mNormalTransforms);
        //}
        Shader.SpecularBump.setNormalTransform(camera.getNormal());

        Shader.SpecularBump.setLight(mLight.getPosition(), ColorTheme.Default.Light, ColorTheme.Default.LightSpecular, ColorTheme.Default.LightAmbient);
        Shader.SpecularBump.apply();
        mMeshBuffer.apply();
        Shader.SpecularBump.draw(mMeshIndexBuffer);
    }

    @Override
    public void render(Camera camera) {
        renderBump(camera);
    }

    @Override
    public void load(Context context) {
        mDiffuseGlow = TextureFactory.CreateTexture(context, _resources.getDiffuseGlow(), false);
        mNormalSpecular = TextureFactory.CreateTexture(context, _resources.getNormalSpecularMap(), false);
        mMeshBuffer.createVBO();
    }

    public void loadClone(MeshBatch meshBatch) {
        mDiffuseGlow = meshBatch.mDiffuseGlow;
        mNormalSpecular = meshBatch.mNormalSpecular;
        mMeshBuffer.createVBO();
    }

    IndexBuffer mMeshIndexBuffer;
    public AttributesBufferMesh mMeshBuffer;
    //public AttributeBufferPosition mPositionsBuffer;
    //public AttributeBufferFloat mTransformIndexBuffer;

    Matrix[] mTransforms;
    Matrix3[] mNormalTransforms;
    MeshTransform[] mMeshTransforms;

    public Texture mDiffuseGlow;
    public Texture mNormalSpecular;

    Light mLight;
    float mTime = 0;

    public Matrix[] getTransforms(){
        return mTransforms;
    }

    public void setLight(Light light){
        mLight = light;
    }

    public AttributesBufferMesh getMeshBuffer(){
        return mMeshBuffer;
    }

    @Override
    public void update(float timeDelta) {

        for (int j = 0; j < mTransforms.length; j++) {
            //mMeshTransforms[j].update(timeDelta);
            mMeshTransforms[j].update(0.0f);
            //mTransforms[j] = mMeshTransforms[j].getMatrix();
            mTransforms[j] = mMeshTransforms[j].Origin;
            mNormalTransforms[j] =new Matrix3(
                    mTransforms[j].getXX(), mTransforms[j].getXY(), mTransforms[j].getXZ(),
                    mTransforms[j].getYX(), mTransforms[j].getYY(), mTransforms[j].getYZ(),
                    mTransforms[j].getZX(), mTransforms[j].getZY(), mTransforms[j].getZZ());
        }

        mTime += timeDelta;
        while (mTime > 1)
            mTime -= 2;
    }

    public IndexBuffer getIndexBuffer() {
        return mMeshIndexBuffer;
    }

    public void unloadClone() {
        if (mMeshBuffer != null)
            mMeshBuffer.destroy();
        if (mMeshIndexBuffer != null)
            mMeshIndexBuffer.destroy();
    }
    public void unload() {
        if (mDiffuseGlow != null)
            mDiffuseGlow.destroy();
        if (mNormalSpecular != null)
            mNormalSpecular.destroy();
        if (mMeshBuffer != null)
            mMeshBuffer.destroy();
        if (mMeshIndexBuffer != null)
            mMeshIndexBuffer.destroy();
    }

    @Override
    public void renderDepth(Camera camera) {
        GLES20.glDepthMask(true);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_FRONT);

        Shader.RenderDepth.setViewTransform(camera.getViewTransform());
        Shader.RenderDepth.setProjectionTransform(camera.getProjectionTransform());

        Shader.RenderDepth.setModelTransforms(mTransforms);
        Shader.RenderDepth.apply();
        mMeshBuffer.applyForDepth(Shader.RenderDepth.Position, Shader.RenderDepth.TransformIndex);
        Shader.RenderDepth.draw(getIndexBuffer());

    }
}

class MeshTransform
{
    public float rotationVelocity = 0.2f;
    public float rotation = 0;
    public Matrix Origin = Matrix.Identity;
    public void update(float delta)
    {
        rotation += delta *  rotationVelocity;
    }
    public Matrix getMatrix(){
        return Matrix.Multiply(Matrix.createRotationZ(rotation), Origin);
    }
}

class MeshTransformX extends MeshTransform
{
    public void update(float delta)
    {
        rotation += delta *  rotationVelocity;
    }

    @Override
    public Matrix getMatrix(){
        return Matrix.Multiply(Origin, Matrix.createRotationX(rotation));
    }
}

class MeshTransformY extends MeshTransform
{
    public void update(float delta)
    {
        rotation += delta *  rotationVelocity;
    }
    @Override
    public Matrix getMatrix(){
        return Matrix.Multiply(Matrix.createRotationY(rotation), Origin);
    }
}
