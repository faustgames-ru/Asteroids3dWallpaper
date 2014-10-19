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
import com.FaustGames.Core.Geometry.Bounds;
import com.FaustGames.Core.Geometry.IGeometryContainer;
import com.FaustGames.Core.Geometry.IGeometryTreeItem;
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

public class MeshBatch extends Entity implements IRenderable, ILoadable, IUpdatable, ICreate, IDepthMapRender, IGeometryContainer {
    //public ArrayList<Mesh> mMeshes;
    EntityResourceMesh _resources;
    public boolean Created = false;
    Scene _scene;

    public MeshBatchItem[] _items;

    public MeshBatch(Scene scene, EntityResourceMesh resources){
        _scene = scene;
        _resources = resources;
        mLight = scene.getLight();
        MeshBatchResource batchResource = _resources.getGeometryResource();
        _items = new MeshBatchItem[resources.getDuplicatesCount() + 1];
        _items[0] = new MeshBatchItem(_resources, true, _meshes);
        for (int j = 1; j < _items.length; j++) {
            _items[j] = new MeshBatchItem(_resources, true, _meshes);
        }
/*
        mTransforms = new Matrix[batchResource.Count];
        mNormalTransforms = new Matrix3[batchResource.Count];
        for (int j = 0; j < mTransforms.length; j++) {
            mTransforms[j] = batchResource.Positions[j].Transform;
            mNormalTransforms[j] = new Matrix3();
            mNormalTransforms[j].fromMatrix4(mTransforms[j]);
            _meshes.add(new GeometryItemMesh(batchResource.Positions[j].Position, batchResource.Positions[j].R, 1.0f, 0.1f, mTransforms[j]));
        }
*/
    }

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

        Shader.SpecularBump.setGlowLevel(0.5f + MathF.abs(mTime) * 0.5f);
        Shader.SpecularBump.setGlowColor(ColorTheme.Default.getGlow());
        Shader.SpecularBump.setNormalSpecularMap(mNormalSpecular);
        Shader.SpecularBump.setDiffuseGlowMap(mDiffuseGlow);

        Shader.SpecularBump.setProjectionTransform(camera.getProjectionTransform());
        Shader.SpecularBump.setViewTransform(camera.getViewTransform());
        Shader.SpecularBump.setNormalTransform(camera.getNormal());

        //Shader.SpecularBump.setLight(mLight.getPosition(), ColorTheme.Default.getSpecularColor(), ColorTheme.Default.getDiffuseColor(), ColorTheme.Default.getAmbientColor());
        Shader.SpecularBump.setLight(mLight.getPosition(), Color.White, ColorTheme.Default.getDiffuseColor(), ColorTheme.Default.getAmbientColor());
        Shader.SpecularBump.setFogColor(ColorTheme.Default.getFogColor());

        int count = getCount();

        for (int i = 0; i < count; i++) {
        //for (int i = 0; i < 1; i++) {
            Shader.SpecularBump.setModelTransforms(_items[i].getTransforms());
            Shader.SpecularBump.setNormalModelTransforms(_items[i].getNormalsTransforms());
            Shader.SpecularBump.apply();
            mMeshBuffer.apply();
            Shader.SpecularBump.draw(mMeshIndexBuffer);
        }
    }

    int _lastCount = 0;
    int getCount(){
        return 1 + Math.round((_items.length - 1) * Settings.getInstance().AsteroidsCount);
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

    IndexBuffer mMeshIndexBuffer;
    public AttributesBufferMesh mMeshBuffer;

    //Matrix[] mTransforms;
    //Matrix3[] mNormalTransforms;

    public Texture mDiffuseGlow;
    public Texture mNormalSpecular;

    Light mLight;
    float mTime = 0;

    public void setLight(Light light){
        mLight = light;
    }

    @Override
    public void update(float timeDelta) {
        int count = getCount();
        if (count != _lastCount){
            for (int i = count; i < _items.length; i++) {
                for (int j = 0; j < _items[i].getMeshes().size(); j++) {
                    _scene.GeometryTree.remove(_items[i].getMeshes().get(j));
                }
            }
            _lastCount = count;
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < _items[i].getMeshes().size(); j++) {
                _items[i].getMeshes().get(j).update(timeDelta);
                _scene.GeometryTree.update(_items[i].getMeshes().get(j));
            }
        }
/*
        for (int i = 0; i < _meshes.size(); i++)
        {
            _meshes.get(i).update(timeDelta);
            _scene.GeometryTree.update(_meshes.get(i));
        }
*/
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
        int count = getCount();
        for (int i = 0; i < count; i++) {
            Shader.RenderDepth.setModelTransforms(_items[i].getTransforms());
            Shader.RenderDepth.apply();
            mMeshBuffer.applyForDepth(Shader.RenderDepth.Position, Shader.RenderDepth.TransformIndex);
            Shader.RenderDepth.draw(getIndexBuffer());
        }
    }
    ArrayList<GeometryItemMesh> _meshes = new ArrayList<GeometryItemMesh>();

    @Override
    public IGeometryTreeItem getGeometryItem(int i) {
        return _meshes.get(i);
    }

    @Override
    public int getGeometryItemsCount() {
        return _meshes.size();
    }
}