package com.FaustGames.Core.Entities;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.*;
import com.FaustGames.Core.Entities.Mesh.MeshBatch;
import com.FaustGames.Core.Entities.PatriclessEmitter.EmitterBatch;
import com.FaustGames.Core.Geometry.*;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.*;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.TextureRenderTarget;
import com.FaustGames.Core.Rendering.Textures.TextureRenderTargetDepth;

import javax.microedition.khronos.egl.*;
import java.util.ArrayList;

public class Scene implements IUpdatable, ILoadable, ICreate {
    int mWidth;
    int mHeight;

    SkyBox mSkyBox;
    Camera mCamera = new Camera();

    //ArrayList<Mesh> mMeshes = new ArrayList<Mesh>();

    NebulaBatch mNebulaBatch;
    MeshBatch mMeshBatch;
    MeshBatch mMeshBatchSmall;
    MeshBatch mMeshBatchSmall1;
    EmitterBatch mEmitterBatch;
    EmitterBatch mBlackEmitterBatch;
    EmitterBatch mCloudsEmitterBatch;
    LensFlareBatch mLensFlareBatch;
    Light mLight = new Light();
    TextureRenderTargetDepth mDepthMap;
    TextureRenderTargetDepth getDepthMap(){return mDepthMap;}

    //TextureRenderTarget mPostProcessRoot;
    //TextureRenderTarget mPostProcessBlurFilter;

    //TextureRenderTarget mPostProcessBlurH;
    //TextureRenderTarget mPostProcessBlurV;

    LensFlareMapsResource mLensFlareMapsResource;
    MeshMapsResource mMeshMapsResource;
    MeshBatchResource mMeshBatchResource;
    MeshMapsResource mSmallMeshMapsResource;
    MeshBatchResource mSmallMeshBatchResource;
    MeshBatchResource mSmallMeshBatchResource1;
    //TextureMapResource mGlassTextureResource;
    //TextureMapResource mParticlesTexture;
    public EGLContext RenderContext;
    public EGLConfig RenderConfig;
    public EGLSurface RenderSurface;
    //TextureETC1 mGlassTexture;

    PostProcess _depthMapClearQuad;

    public ArrayList<ILoadable> Loadables = new ArrayList<ILoadable>();
    public ArrayList<IRenderable> Renderables = new ArrayList<IRenderable>();
    public ArrayList<IDepthMapRender> RenderDepth = new ArrayList<IDepthMapRender>();
    public ArrayList<ICreate> Creates = new ArrayList<ICreate>();
    public ArrayList<IUpdatable> Updatables = new ArrayList<IUpdatable>();
    public ArrayList<IGeometryTreeItem> GeometryTreeItems = new ArrayList<IGeometryTreeItem>();
    public GeometryTree GeometryTree;
    public Light[] LensRingsLights;
    public Light[] LensLights;
    public Light[] Lights;

    public Light[] getLights(){
        return Lights;
    }
    public Light[] getLensLights(){
        return LensLights;
    }
    public Light[] getLensRingsLights(){
        return LensRingsLights;
    }

    public Light getLight(){
        return mLight;
    }

    public Scene()
    {
        GeometryTree = new GeometryTree(new Bounds(Vertex.Empty, SceneConfiguration.Default.GeometryTreeSize), SceneConfiguration.Default.GeometryTreeDepth);
        mCamera.apply();
        GeometryTree.add(mCamera);
        mLight = new Light(0, 0, -2048, 1.0f);

        LensRingsLights = new Light[]{
                //mLight,
                new Light(-200f, 0, 0, 1.5f),
                //new Light(200f, 0, 0, 1.5f),
        };

        LensLights = new Light[]{
                mLight,
                new Light(-200f, 0, 0, 1.5f),
                new Light(200f, 0, 0, 1.5f),

                // corners
                new Light(1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, 1024f, -1024f, 1.5f),
                new Light(1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, 1024f, -1024f, 1.5f),
        };

        Lights = new Light[]{
                mLight,
                new Light(-200f, 0, 0, 0.7f),
                new Light(200f, 0, 0, 0.7f),

                // corners
                new Light(1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, 1024f, -1024f, 1.5f),
                new Light(1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, 1024f, -1024f, 1.5f),


                // xp

                new Light(2048f, 64, 1020, 0.2f),
                new Light(2048f, -256, 214, 0.15f),
                new Light(2048f, -824, -224, 0.1f),
                new Light(2048f, 298, -494, 0.1f),
                new Light(2048f, -8, -974, 0.3f),


                // xm

                new Light(-2048.0f, 874.0f, -1216.0f, 0.200f),
                new Light(-2048.0f, -52.0f, -1018.0f, 0.100f),
                new Light(-2048.0f, -788.0f, -802.0f, 0.100f),
                new Light(-2048.0f, 1498.0f, -790.0f, 0.200f),
                new Light(-2048.0f, 548.0f, -418.0f, 0.100f),
                new Light(-2048.0f, -308.0f, -310.0f, 0.100f),
                new Light(-2048.0f, 322.0f, 308.0f, 0.100f),
                new Light(-2048.0f, 178.0f, 488.0f, 0.100f),
                new Light(-2048.0f, 796.0f, 770.0f, 0.300f),
                new Light(-2048.0f, -1322.0f, 830.0f, 0.200f),

                // ym

                new Light(-1258.0f, -740.0f, 2048.0f, 0.050f),
                new Light(-1036.0f, 784.0f, 2048.0f, 0.100f),
                new Light(-556.0f, 16.0f, 2048.0f, 0.050f),
                new Light(74.0f, -134.0f, 2048.0f, 0.050f),
                new Light(2.0f, -1598.0f, 2048.0f, 0.050f),
                new Light(290.0f, 388.0f, 2048.0f, 0.100f),
                new Light(578.0f, 1342.0f, 2048.0f, 0.200f),
                new Light(974.0f, -14.0f, 2048.0f, 0.200f),
                new Light(1532.0f, -1502.0f, 2048.0f, 0.200f),
                new Light(1592.0f, -344.0f, 2048.0f, 0.200f),

                // yp

                new Light(1540.0f, 1528.0f, -2048.0f, 0.200f),
                new Light(1234.0f, -242.0f, -2048.0f, 0.100f),
                new Light(946.0f, 658.0f, -2048.0f, 0.300f),
                new Light(616.0f, -1592.0f, -2048.0f, 0.200f),
                new Light(592.0f, 1102.0f, -2048.0f, 0.200f),
                new Light(-164.0f, -1172.0f, -2048.0f, 0.300f),
                new Light(-338.0f, 1456.0f, -2048.0f, 0.100f),
                new Light(-1022.0f, 1042.0f, -2048.0f, 0.100f),
                new Light(-1112.0f, -26.0f, -2048.0f, 0.100f),
                new Light(-1490.0f, -458.0f, -2048.0f, 0.200f),
                new Light(-1538.0f, 1576.0f, -2048.0f, 0.300f),

                //zm

                new Light(-1024.0f, -2048.0f, -1004.0f, 0.100f),
                new Light(-472.0f, -2048.0f, 538.0f, 0.200f),
                new Light(146.0f, -2048.0f, -158.0f, 0.300f),
                new Light(1274.0f, -2048.0f, 838.0f, 0.300f),

                //zp
                new Light(-478.0f, 2048.0f, 1416.0f, 0.300f),
                new Light(-406.0f, 2048.0f, 518.0f, 0.300f),
                new Light(938.0f, 2048.0f, 926.0f, 0.300f),
                new Light(1466.0f, 2048.0f, 380.0f, 0.100f),
        };

        mDepthMap = new TextureRenderTargetDepth(512, 512);
        _depthMapClearQuad = new PostProcess();
        addEntity(_depthMapClearQuad);
    }

    void addEntity(Object instance){
        if (instance == null) return;
        if (instance instanceof ICreate)
            Creates.add((ICreate)instance);
        if (instance instanceof ILoadable)
            Loadables.add((ILoadable)instance);
        if (instance instanceof IRenderable)
            Renderables.add((IRenderable)instance);
        if (instance instanceof IUpdatable)
            Updatables.add((IUpdatable)instance);
        if (instance instanceof IDepthMapRender)
            RenderDepth.add((IDepthMapRender)instance);
        if (instance instanceof IGeometryContainer)
        {
            IGeometryContainer container = (IGeometryContainer)instance;
            int count = container.getGeometryItemsCount();
            for (int j = 0; j < count; j++){
                IGeometryTreeItem item = container.getGeometryItem(j);
                GeometryTree.add(item);
                GeometryTreeItems.add(item);
            }
        }
    }

    public void setEntities(EntityResource[] entitiesResources) {
        for(int i = 0; i < entitiesResources.length; i++) {
            Object instance = EntitiesFactory.CreateEntity(this, entitiesResources[i]);
            addEntity(instance);
        }
    }
/*
    public Scene(SkyBox skyBox,
                 LensFlareMapsResource lensFlareMapsResource,
                 NebulaResource nebulaResource,
                 MeshMapsResource meshMapsResource,
                 MeshBatchResource meshBatchResource,
                 MeshMapsResource smallMeshMapsResource,
                 MeshBatchResource smallMeshBatchResource,
                 MeshBatchResource smallMeshBatchResource1,
                 TextureMapResource particlesTexture,
                 TextureMapResource cloudsTexture){
        mMeshMapsResource = meshMapsResource;
        mMeshBatchResource = meshBatchResource;
        mSmallMeshMapsResource = smallMeshMapsResource;
        mSmallMeshBatchResource = smallMeshBatchResource;
        mSmallMeshBatchResource1 = smallMeshBatchResource1;
        mLensFlareMapsResource = lensFlareMapsResource;
        mSkyBox = skyBox;

        mDepthMap = new TextureRenderTargetDepth(64, 64);
        mLight = new Light(0, 0, -2048, 1.0f);

        mNebulaBatch = new NebulaBatch(new Nebula[] {
                new Nebula(new Vertex(-200, 0, 0), 100, false),
                new Nebula(new Vertex(200, 0, 0), 100, true),
        },nebulaResource);

        //mGlassTextureResource = glassTexture;

        Emitter firesEmitter = new Emitter();
        firesEmitter.InitFires();
        mEmitterBatch = new EmitterBatch(particlesTexture, new Emitter[]
                {
                    new Emitter(),
                    firesEmitter
                }, ColorTheme.Default.AdditiveParticles, 2.0f);
        mBlackEmitterBatch = new EmitterBatch(particlesTexture, new Emitter[]
                {
                        new Emitter()
                }, ColorTheme.Default.Particles, 2.0f);

        Emitter cloudsEmitter = new Emitter();
        cloudsEmitter.InitClouds();
        mCloudsEmitterBatch =new EmitterBatch(cloudsTexture, new Emitter[]
                {
                        cloudsEmitter
                }, ColorTheme.Default.Clouds, 10.0f);
    }
*/
    public void viewPort(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public Camera getCamera(){
        return mCamera;
    }
     /*
    public void addMesh(Mesh mesh){
        mMeshes.add(mesh);
    }
       */
/*
    @Override
    public void update(float timeDelta) {
        mCamera.update(timeDelta);
        //for (Mesh mesh : mMeshes)
          //  mesh.update(timeDelta);
        if (mMeshBatch != null)
            mMeshBatch.update(timeDelta);
        if (mMeshBatchSmall != null)
            mMeshBatchSmall.update(timeDelta);
        if (mMeshBatchSmall1 != null)
            mMeshBatchSmall1.update(timeDelta);

        if (mNebulaBatch != null)
            mNebulaBatch.update(timeDelta);

        mEmitterBatch.update(timeDelta);
        mBlackEmitterBatch.update(timeDelta);
        mCloudsEmitterBatch.update(timeDelta);

        mTime += timeDelta;
    }

    private void renderDepthMap() {

        mDepthMap.setAsRenderTarget();

        GLES20.glDepthMask(true);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_BLEND);

        Shader.RenderDepth.setViewTransform(mCamera.getViewTransform());
        Shader.RenderDepth.setProjectionTransform(mCamera.getProjectionTransform());

        Shader.RenderDepth.setModelTransforms(mMeshBatch.getTransforms());
        Shader.RenderDepth.apply();
        mMeshBatch.mMeshBuffer.applyForDepth(Shader.RenderDepth.Position, Shader.RenderDepth.TransformIndex);
        Shader.RenderDepth.draw(mMeshBatch.getIndexBuffer());

        Shader.RenderDepth.setModelTransforms(mMeshBatchSmall.getTransforms());
        Shader.RenderDepth.apply();
        mMeshBatchSmall.mMeshBuffer.applyForDepth(Shader.RenderDepth.Position, Shader.RenderDepth.TransformIndex);
        Shader.RenderDepth.draw(mMeshBatchSmall.getIndexBuffer());

        Shader.RenderDepth.setModelTransforms(mMeshBatchSmall1.getTransforms());
        Shader.RenderDepth.apply();
        mMeshBatchSmall1.mMeshBuffer.applyForDepth(Shader.RenderDepth.Position, Shader.RenderDepth.TransformIndex);
        Shader.RenderDepth.draw(mMeshBatchSmall1.getIndexBuffer());


        mDepthMap.setDefaultRenderTarget(mWidth, mHeight);

    }

    private void renderSkyBox() {
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        mSkyBox.render(mCamera);
    }

    private void renderNebula() {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mNebulaBatch.render(mCamera);
    }

    private void renderMeshBatch() {
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mMeshBatch.render(mCamera);
        mMeshBatchSmall.render(mCamera);
        mMeshBatchSmall1.render(mCamera);
    }

    private void renderEmitters() {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mEmitterBatch.Color = ColorTheme.Default.AdditiveParticles;
        mEmitterBatch.render(mCamera);
        if (Configuration.Default.DisplayClouds)
        {
            mCloudsEmitterBatch.LowLevel = Configuration.Default.LowClouds;
            mCloudsEmitterBatch.Color = ColorTheme.Default.Clouds;
            mCloudsEmitterBatch.render(mCamera);
        }
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mBlackEmitterBatch.Color = ColorTheme.Default.Particles;
        mBlackEmitterBatch.render(mCamera);
    }

    private void renderLensFlareBatch(boolean simple) {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mLensFlareBatch.setAspect((float)mWidth/ (float)mHeight);
        mLensFlareBatch.setDepthMap(mDepthMap);
        if (simple)
            mLensFlareBatch.renderSimple(mCamera);
        else
            mLensFlareBatch.render(mCamera);
    }

    public boolean PostProcessing = false;

    public void render() {
        mCamera.apply();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        renderDepthMap();


        if (PostProcessing)
        {
            //mPostProcessRoot.setAsRenderTarget();
        }


        GLES20.glCullFace(GLES20.GL_FRONT);
        renderMeshBatch();

        GLES20.glDisable(GLES20.GL_CULL_FACE);
        renderSkyBox();

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        renderNebula();

        GLES20.glDisable(GLES20.GL_CULL_FACE);
        renderEmitters();


        if (Configuration.Default.DisplayLensFlare)
            renderLensFlareBatch(false);
    }
*/

    int mPostIndex = 0;

    float mTime = 0.0f;

    public void resize(int width, int height) {
        mWidth = width;
        mHeight = height;
        //mDepthMap.resize(width / 8, height / 8);
    }
/*
    @Override
    public void load(Context context) {

        mEmitterBatch.load(context);
        mBlackEmitterBatch.load(context);
        mCloudsEmitterBatch.load(context);

        mSkyBox.load(context);
        mMeshBatch.load(context);
        mMeshBatchSmall.load(context);
        mMeshBatchSmall1.loadClone(mMeshBatchSmall);

        mDepthMap.load(context);

        mLensFlareBatch.load(context);

        mNebulaBatch.load(context);

        loadAsync(context);
    }
*/
    public void loadAsync(final Context context) {
/*
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                loadLargeTextures(context);
            }
        };

        thread.start();
*/
    }

    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098; /// ??????

    public void loadLargeTextures(Context context) {
        /*
        EGL10 egl = (EGL10)EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        int[] attrib_list = { EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
        EGLContext localContext = egl.eglCreateContext(display, RenderConfig, RenderContext, attrib_list);
        if (localContext .equals(egl.EGL_NO_CONTEXT))
        {
            Log.d("", "EGL_NO_CONTEXT");
            return;
        }

        int SurfaceAttribs[] = {
                EGL11.EGL_WIDTH, 64, //64, 64 is the minimum texture width/height
                EGL11.EGL_HEIGHT, 64,
                EGL11.EGL_NONE};

        EGLSurface eglNewSurface= egl.eglCreatePbufferSurface(display, RenderConfig, SurfaceAttribs);

        egl.eglMakeCurrent(display, eglNewSurface, eglNewSurface, localContext);

        mMeshBatch.mNormal.LoadLargeTextureImage(context, true);

        mSkyBox.mYM.LoadLargeTextureImage(context, false);
        mSkyBox.mXM.LoadLargeTextureImage(context, false);
        mSkyBox.mXP.LoadLargeTextureImage(context, false);
        mSkyBox.mZM.LoadLargeTextureImage(context, false);
        mSkyBox.mZP.LoadLargeTextureImage(context, false);
        mSkyBox.mYP.LoadLargeTextureImage(context, false);

        mSkyBox.mYM.ApplyLargeImage();
        mSkyBox.mXM.ApplyLargeImage();
        mSkyBox.mXP.ApplyLargeImage();
        mSkyBox.mZM.ApplyLargeImage();
        mSkyBox.mZP.ApplyLargeImage();
        mSkyBox.mYP.ApplyLargeImage();

        mMeshBatchSmall.mNormal.LoadLargeTextureImage(context, true);

        mMeshBatch.mDiffuse.LoadLargeTextureImage(context, true);
        mMeshBatch.mSpecular.LoadLargeTextureImage(context, true);
        mMeshBatch.mGlow.LoadLargeTextureImage(context, true);

        mMeshBatchSmall.mDiffuse.LoadLargeTextureImage(context, true);
        mMeshBatchSmall.mSpecular.LoadLargeTextureImage(context, true);
        mMeshBatchSmall.mGlow.LoadLargeTextureImage(context, true);

        egl.eglDestroySurface(display, eglNewSurface);
        egl.eglDestroyContext(display, localContext);
        */
    }

    PositionTexture[] mQuad =  new PositionTexture[]{
        new PositionTexture(-1f, -1f, 0, 0, 0),
        new PositionTexture( -0.5f, -1f, 0, 1, 0),
        new PositionTexture( -0.5f, -0.5f, 0, 1, 1),
        new PositionTexture(-1f,  -0.5f, 0, 0, 1),
    };

    PositionTexture[] mSQuad =  new PositionTexture[]{
            new PositionTexture(0.5f, 0.5f, 0, 0, 0),
            new PositionTexture(1f, 0.5f, 0, 1, 0),
            new PositionTexture(1f, 1f, 0, 1, 1),
            new PositionTexture(0.5f, 1f, 0, 0, 1),
    };
/*
    public void create(Context context) {

        mEmitterBatch.create(context);
        mBlackEmitterBatch.create(context);
        mCloudsEmitterBatch.create(context);

        //mMeshBatch = new MeshBatch(mMeshes.get(0).getResources(), mMeshes);
        mMeshBatch = new MeshBatch(mMeshMapsResource, mMeshBatchResource);
        mMeshBatch.create(context);
        mMeshBatch.setLight(mLight);

        mMeshBatchSmall = new MeshBatch(mSmallMeshMapsResource, mSmallMeshBatchResource);
        mMeshBatchSmall.create(context);
        mMeshBatchSmall.setLight(mLight);

        mMeshBatchSmall1 = new MeshBatch(mSmallMeshMapsResource, mSmallMeshBatchResource1);
        mMeshBatchSmall1.create(context);
        mMeshBatchSmall1.setLight(mLight);

        Light[] lensLights;
        if (DeviceConfiguration.isTablet)
        {
            lensLights = new Light[]{
                    new Light(0, -200, 0, 0.3f),
                    //new Light(0, 0, 200, 0.3f),
                    //new Light(40, 0, -200, 0.3f),
                    //new Light(200, -20, -20, 0.3f),
                    //new Light(0, 0, 20, 0.7f),
                    //new Light(0, 0, -20, 0.7f),
            };
        }
        else
        {
            lensLights = new Light[]{
               mLight,
                new Light(-200f, 0, 0, 0.7f),
                new Light(200f, 0, 0, 0.7f),

                // corners
                new Light(1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, -1024f, 1024f, 1.5f),
                new Light(-1024f, 1024f, 1024f, 1.5f),
                new Light(1024f, 1024f, -1024f, 1.5f),
                new Light(1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, -1024f, -1024f, 1.5f),
                new Light(-1024f, 1024f, -1024f, 1.5f),


                // xp

                new Light(2048f, 64, 1020, 0.2f),
                new Light(2048f, -256, 214, 0.15f),
                new Light(2048f, -824, -224, 0.1f),
                new Light(2048f, 298, -494, 0.1f),
                new Light(2048f, -8, -974, 0.3f),


                // xm

                new Light(-2048.0f, 874.0f, -1216.0f, 0.200f),
                new Light(-2048.0f, -52.0f, -1018.0f, 0.100f),
                new Light(-2048.0f, -788.0f, -802.0f, 0.100f),
                new Light(-2048.0f, 1498.0f, -790.0f, 0.200f),
                new Light(-2048.0f, 548.0f, -418.0f, 0.100f),
                new Light(-2048.0f, -308.0f, -310.0f, 0.100f),
                new Light(-2048.0f, 322.0f, 308.0f, 0.100f),
                new Light(-2048.0f, 178.0f, 488.0f, 0.100f),
                new Light(-2048.0f, 796.0f, 770.0f, 0.300f),
                new Light(-2048.0f, -1322.0f, 830.0f, 0.200f),

                // ym

                new Light(-1258.0f, -740.0f, 2048.0f, 0.050f),
                new Light(-1036.0f, 784.0f, 2048.0f, 0.100f),
                new Light(-556.0f, 16.0f, 2048.0f, 0.050f),
                new Light(74.0f, -134.0f, 2048.0f, 0.050f),
                new Light(2.0f, -1598.0f, 2048.0f, 0.050f),
                new Light(290.0f, 388.0f, 2048.0f, 0.100f),
                new Light(578.0f, 1342.0f, 2048.0f, 0.200f),
                new Light(974.0f, -14.0f, 2048.0f, 0.200f),
                new Light(1532.0f, -1502.0f, 2048.0f, 0.200f),
                new Light(1592.0f, -344.0f, 2048.0f, 0.200f),

                // yp

                new Light(1540.0f, 1528.0f, -2048.0f, 0.200f),
                new Light(1234.0f, -242.0f, -2048.0f, 0.100f),
                new Light(946.0f, 658.0f, -2048.0f, 0.300f),
                new Light(616.0f, -1592.0f, -2048.0f, 0.200f),
                new Light(592.0f, 1102.0f, -2048.0f, 0.200f),
                new Light(-164.0f, -1172.0f, -2048.0f, 0.300f),
                new Light(-338.0f, 1456.0f, -2048.0f, 0.100f),
                new Light(-1022.0f, 1042.0f, -2048.0f, 0.100f),
                new Light(-1112.0f, -26.0f, -2048.0f, 0.100f),
                new Light(-1490.0f, -458.0f, -2048.0f, 0.200f),
                new Light(-1538.0f, 1576.0f, -2048.0f, 0.300f),

                //zm

                new Light(-1024.0f, -2048.0f, -1004.0f, 0.100f),
                new Light(-472.0f, -2048.0f, 538.0f, 0.200f),
                new Light(146.0f, -2048.0f, -158.0f, 0.300f),
                new Light(1274.0f, -2048.0f, 838.0f, 0.300f),

                //zp
                new Light(-478.0f, 2048.0f, 1416.0f, 0.300f),
                new Light(-406.0f, 2048.0f, 518.0f, 0.300f),
                new Light(938.0f, 2048.0f, 926.0f, 0.300f),
                new Light(1466.0f, 2048.0f, 380.0f, 0.100f),
            };
        }
        mLensFlareBatch = new LensFlareBatch(mLensFlareMapsResource, lensLights);
        mLensFlareBatch.create(context);

        mNebulaBatch.create(context);

        mSkyBox.setLight(mLight);
        mSkyBox.create(context);

    }

*/
    IPositionTexture[] mVertices = new IPositionTexture[]{
            new PositionTexture(-1,  1, 0, 0, 1),
            new PositionTexture(-1, -1, 0, 0, 0),
            new PositionTexture( 1, -1, 0, 1, 0),
            new PositionTexture( 1,  1, 0, 1, 1),
    };

    IndexBuffer mIndexBuffer = new IndexBuffer(new short[]
            {
                    0, 1, 2, 0, 2, 3,
            });

    @Override
    public void create(Context context) {
        for (int i = 0; i < Creates.size(); i++)
            Creates.get(i).create(context);
    }

    @Override
    public void load(Context context) {
        mDepthMap.load(context);

        for (int i = 0; i < Loadables.size(); i++)
            Loadables.get(i).load(context);
    }

    FillContactsArgs _contactsArgs = new FillContactsArgs(SceneConfiguration.Default.GeometryTreeDepth);
    IVisitor<GeometryContact> _contactsVisitor =new IVisitor<GeometryContact>() {
        @Override
        public void visit(GeometryContact contact) {
            ContactsProcessor.process(contact);
        }
    };

    public int fillContacts(FillContactsArgs contactsArgs){
        _contactsArgs.getContacts().clear();
        int result = 0;
        for (int i = 0; i < GeometryTreeItems.size(); i++){
            for (int j = i + 1; j < GeometryTreeItems.size(); j++){
                result++;
                if (Bounds.cross(GeometryTreeItems.get(i).getBounds(), GeometryTreeItems.get(j).getBounds())) {
                    contactsArgs.getContacts().add(GeometryTreeItems.get(i), GeometryTreeItems.get(j));
                }
            }
        }
        return result;
    }

    @Override
    public void update(float timeDelta) {
        mCamera.update(timeDelta);
        mCamera.apply();
        GeometryTree.update(mCamera);

        int contactIterationsCount = GeometryTree.fillContacts(_contactsArgs);
        //Log.d("contactIterationsCount:", "contactIterationsCount:" + contactIterationsCount);
        _contactsArgs.getContacts().visit(_contactsVisitor);

        for (int i = 0; i < Updatables.size(); i++)
            Updatables.get(i).update(timeDelta);
    }

    void renderDepthMap(){
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glDepthMask(true);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        Shader.PositionTexture.setTexture(mDepthMap);
        Shader.PositionTexture.setColor(Color.White);
        Shader.PositionTexture.setProjection(Matrix.Identity);
        Shader.PositionTexture.setModel(Matrix.Identity);
        Shader.PositionTexture.apply();
        _depthMapClearQuad.getVertexBuffer().apply(Shader.PositionTexture.Attributes);
        Shader.PositionTexture.draw(_depthMapClearQuad.getIndexBuffer());
    }

    void renderClearQuad(){
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glDepthMask(true);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_ALWAYS);
        Shader.SolidBlack.apply();
        _depthMapClearQuad.getVertexBuffer().apply(Shader.SolidBlack.Attributes);
        Shader.SolidBlack.draw(_depthMapClearQuad.getIndexBuffer());
        GLES20.glDepthFunc(GLES20.GL_LESS);
    }

    public void render() {
        //mCamera.apply();
        if (RenderDepth.size() > 0) {
            mDepthMap.setAsRenderTarget();
            //renderClearQuad();

            /// depth clear does not work on asus transformer ???
            GLES20.glDepthFunc(GLES20.GL_ALWAYS);
            for (int i = 0; i < RenderDepth.size(); i++)
                RenderDepth.get(i).renderDepth(getCamera());
            GLES20.glDepthFunc(GLES20.GL_LESS);
            for (int i = 0; i < RenderDepth.size(); i++)
                RenderDepth.get(i).renderDepth(getCamera());
            mDepthMap.setDefaultRenderTarget(mWidth, mHeight);
        }
        GLES20.glViewport(0, 0, mWidth, mHeight);

        for (int i = 0; i < Renderables.size(); i++)
            Renderables.get(i).render(getCamera());

        //renderDepthMap();
    }
/*
    public void unload() {
        if (mMeshBatch != null)
            mMeshBatch.unload();
        if (mMeshBatchSmall != null)
            mMeshBatchSmall.unload();
        if (mMeshBatchSmall1 != null)
            mMeshBatchSmall1.unloadClone();
        if (mSkyBox != null)
            mSkyBox.unload();
    }
*/
}
