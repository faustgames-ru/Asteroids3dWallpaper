package com.FaustGames.Asteroids3dFree;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.FaustGames.Core.BasicRenderView;
import com.FaustGames.Core.Content.EntityResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Entities.SkyBox;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Settings;
import com.FaustGames.PreferencesBinding.PreferencesBinding;

public class ServiceWallpaper extends WallpaperService implements Thread.UncaughtExceptionHandler
{
    @Override
    public Engine onCreateEngine() {

        Thread.setDefaultUncaughtExceptionHandler(this);

        return new WallpaperServiceEngine(this.getApplicationContext());
    }
    public int getPOT(int v)
    {
        int r = 2;
        while(r < v)
            r*=2;
        return r;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d("Asteroids3d", "uncaughtException: ");
        ex.printStackTrace();
    }

    private class WallpaperServiceEngine extends Engine
    {
        private final Context context;
        public boolean Visible = false;
        SkyBox mSkyBox;
        Scene mScene;
        BasicRenderView mView;
        Content mContent;
        SurfaceHolder mHolder;


        GyroscopeController GyroscopeController;

        public Preferences Preferences;

        public WallpaperServiceEngine(Context context)  {
            DeviceConfiguration.load(context);

            PreferencesBinding.bind(context, Settings.getInstance());
            Preferences = new Preferences(context);

            this.context = context;
            DisplayMetrics dm = new DisplayMetrics();

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
/*
            if (width > height)
                TextureMapResource.MaxSize = width;
            else
                TextureMapResource.MaxSize = height;

            TextureMapResource.WidthPOT = getPOT(width);
            TextureMapResource.HeightPOT = getPOT(height);
*/
            Camera.resistanceK = Configuration.CameraRotationResistance;

            //if (Content == null)

            Content.init(context.getApplicationContext());
            mContent = Content.instance;
            //if (mScene == null)
            {
                /*
                mSkyBox = new SkyBox(mContent.SkyBox);
                mScene = new Scene(mSkyBox, mContent.LensFlareMaps, mContent.Nebula,
                        mContent.MeshDefaultMaps,
                        mContent.MeshBatch,
                        mContent.SmallMeshDefaultMaps,
                        mContent.SmallMeshBatch,
                        mContent.SmallMeshBatch1,
                        mContent.Particle, mContent.Cloud);
                */
                mScene = new Scene();
                mScene.setEntities(mContent.getResources());
                GyroscopeController = new GyroscopeController(context, mScene);
            }
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            mView.onTouchEvent(event);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            Visible = visible;

            if (Visible)
            {
                mView.onResume();
                GyroscopeController.onResume();
            }
            else
            {
                mView.onPause();
                GyroscopeController.onPause();
            }

        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mHolder = holder;
            //if (mView == null)
            {
                mView = new BasicRenderView(context, mScene) {
                    @Override
                    public SurfaceHolder getHolder() {
                        return mHolder;
                    }
                };
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            mHolder = holder;
            mView.surfaceChanged(holder, format, width, height);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            //mView.surfaceDestroyed(holder);
        }
    }
}


