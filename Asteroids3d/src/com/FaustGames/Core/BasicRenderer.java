package com.FaustGames.Core;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import com.FaustGames.Core.Controllers.IPointerController;
import com.FaustGames.Core.Controllers.TouchController;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.GL10;

public class BasicRenderer implements GLSurfaceView.Renderer, IUpdatable {
    EGLSurface EGLSurface;
    EGLContext RenderContext;
    Context mContext;
    Scene mScene;

    //Mesh mMesh;
    boolean mCreated = false;


    public BasicRenderer(Context context, Scene scene) {
        mContext = context;
        mScene = scene;
        //mMesh = new Mesh();
        PointerController.setScene(mScene);
    }

    public void create(){
        if (mCreated) return;
        mCreated = true;
        mScene.create(mContext);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        EGL10 egl = (EGL10)EGLContext.getEGL();
        EGLContext context = egl.eglGetCurrentContext();
        if (context.equals(egl.EGL_NO_CONTEXT))
        {
            Log.d("","EGL_NO_CONTEXT");
        }
        mScene.RenderContext = context;
        mScene.RenderConfig = config;
        mScene.RenderSurface = EGLSurface;

        mDestroyed = false;

        //mScene.unload();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glDepthFunc(GLES20.GL_LESS);
        GLES20.glDepthRangef(0.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_CULL_FACE);

        Shader.Create(mContext);
        create();

        loaded = false;

        lastTime = 0;
        createdOnce = true;
    }

    boolean createdOnce = false;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (DeviceConfiguration.isTablet)
            mScene.getCamera().setViewport(MathF.PI / 1.5f, width, height);
        else
            mScene.getCamera().setViewport(MathF.PI / 1.8f, width, height);
        mScene.getCamera().apply();
        mScene.viewPort(width, height);
        mScene.resize(width, height);
        if (!loaded) {
            mScene.load(mContext);
            loaded = true;
        }
    }

    boolean loaded = false;
    long lastTime = 0;
    Statistics Statistics = new Statistics();

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this)
        {
            long time = System.currentTimeMillis();
            if (lastTime > 0)
            {
                float delta = (float)(time - lastTime) * 0.001f;
                if (delta > 0.03f)
                    delta = 0.03f;
                update(delta);
            }
            /*
            GLES20.glClearColor(
                    ColorTheme.Default.LightAmbient.getR(),
                    ColorTheme.Default.LightAmbient.getG(),
                    ColorTheme.Default.LightAmbient.getB(),
                    ColorTheme.Default.LightAmbient.getA());
            */
            GLES20.glClearColor(
                    0,
                    0,
                    0,
                    0);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            mScene.render();

            Statistics.AddFps(1000 / (int)(time - lastTime));
            if (Configuration.Default.Auto)
            {
                if (Statistics.Fps > 0)
                {
                    int delta = 1000 / Statistics.Fps;
                    delta -= Interval;
                    if (delta > 50)
                    {
                        Statistics.clear();
                        Configuration.Default.dec();
                    }
                    else if (delta < 25)
                    {
                        Statistics.clear();
                        Configuration.Default.inc();
                    }
                }
            }
            lastTime = time;
        }

        //Log.d("asteroids3d fps", "asteroids3d fps : " + Statistics.Fps);
        sleep();
    }

    public static int MaxInterval = 45;
    public static int Interval = 0;

    private void sleep() {
        if (Interval == 0) return;
        try
        {
            Thread.sleep(Interval);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public IPointerController PointerController = new TouchController();

    public boolean onTouchEvent(MotionEvent event) {
        Vertex position = new Vertex(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PointerController.down(position);
                break;
            case MotionEvent.ACTION_UP:
                PointerController.up(position);
                break;
            case MotionEvent.ACTION_CANCEL:
                PointerController.cancel(position);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                PointerController.leave(position);
                break;
            case MotionEvent.ACTION_MOVE:
                PointerController.move(position);
                break;
        }
        return true;
    }

    public void update(float timeDelta) {
        if (timeDelta > 0.03)
            timeDelta = 0.03f;
        mScene.update(timeDelta);
        //mMesh.update(timeDelta);
    }

    boolean mDestroyed;

    public void destroy() {
        mDestroyed = true;
    }

    public void destroyContext() {
        Shader.unload();
        //mScene.unload();
    }
}
