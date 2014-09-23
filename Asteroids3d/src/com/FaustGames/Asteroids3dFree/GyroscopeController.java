package com.FaustGames.Asteroids3dFree;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Entities.Transforms.Rotation;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Physics.AngularValue;

public class GyroscopeController {
    Scene mScene;
    Context mContext;
    SensorManager mSensorManager;
    Sensor mGyroscope;
    Sensor mAccelerometer;
    Vertex gyroscope = Vertex.Empty;
    Vertex accelerometer = Vertex.Empty;
    GyroscopeListener mGyroscopeListener = new GyroscopeListener();
    AccelerometerListener mAccelerometerListener = new AccelerometerListener();


    public GyroscopeController(Context context, Scene scene)
    {
        mContext = context;
        mScene = scene;

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mGyroscope != null)
            mSensorManager.registerListener(mGyroscopeListener, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        else if (mAccelerometer != null)
            mSensorManager.registerListener(mAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    class GyroscopeListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event) {
            gyroscope = new Vertex(event.values[0], event.values[1], event.values[2]);
            ProcessSensors();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    class AccelerometerListener implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event) {
            accelerometer = new Vertex(event.values[0], event.values[1], event.values[2]);
            ProcessAccelerometer();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public int getDeviceDefaultOrientation() {

        WindowManager windowManager =  (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        android.content.res.Configuration config = mContext.getResources().getConfiguration();

        int rotation = windowManager.getDefaultDisplay().getRotation();

        if ( ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) &&
                config.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE)
                || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) &&
                config.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT)) {
            return android.content.res.Configuration.ORIENTATION_LANDSCAPE;
        }
        else
        {
            return android.content.res.Configuration.ORIENTATION_PORTRAIT;
        }
    }

    void ProcessSensors() {
        float x = gyroscope.getX();
        float y = gyroscope.getY();
        float z = -gyroscope.getZ();

        int orientation = getScreenOrientation();
        if (getDeviceDefaultOrientation() == android.content.res.Configuration.ORIENTATION_PORTRAIT)
        {
            switch(orientation) {
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                    y = -gyroscope.getY();
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                    x = -gyroscope.getY();
                    y = gyroscope.getX();
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                    x = gyroscope.getY();
                    y = -gyroscope.getX();
                    break;
            }
        }
        else
        {
            switch(orientation) {
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                    y = -gyroscope.getY();
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                    x = -gyroscope.getY();
                    y = gyroscope.getX();
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                    x = gyroscope.getY();
                    y = -gyroscope.getX();
                    break;
            }
        }

        Vertex axisX = Vertex.mul(Vertex.AxisX, x);
        Vertex axisY = Vertex.mul(Vertex.AxisY, y);
        Vertex axisZ = Vertex.mul(Vertex.AxisZ, z);
        Vertex cross = Vertex.add(Vertex.add(axisX, axisY), axisZ);
        if (!cross.isEmpty())
        {
            mScene.getCamera().ExtraRotationVelocity.setAxis(cross.normalize());
            float angle = gyroscope.length() * 0.5f;
            mScene.getCamera().ExtraRotationVelocity.setValue(angle);
        }
    }

    void ProcessAccelerometer() {
        // todo: accelerometer support

        float x = accelerometer.getX();
        float y = accelerometer.getY();
        float z = accelerometer.getZ();

        int orientation = getScreenOrientation();
        switch(orientation) {
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                y = -accelerometer.getY();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                x = -accelerometer.getY();
                y = accelerometer.getX();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                x = accelerometer.getY();
                y = -accelerometer.getX();
                break;
        }

        Vertex accelerometerG = mScene.getCamera().ExtraRotation.getMatrix().transform(new Vertex(0, 0, 1));
        Vertex cross = Vertex.crossProduct(new Vertex(x, y, z).normalize(), accelerometerG);
        if (!cross.isEmpty())
        {
            mScene.getCamera().ExtraRotationVelocity.setAxis(cross.normalize());
            float angle = MathF.asin(cross.length());
            mScene.getCamera().ExtraRotationVelocity.setValue(angle);
        }
    }

    private int getScreenOrientation() {
        int rotation = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    Log.e("Asteroids 3D", "Unknown screen orientation. Defaulting to " +
                            "portrait.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        else {
            switch(rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    Log.e("Asteroids 3D", "Unknown screen orientation. Defaulting to " +
                            "landscape.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    public void onPause() {
        gyroscope = Vertex.Empty;
        if (mGyroscope!= null)
            mSensorManager.unregisterListener(mGyroscopeListener);
        else if (mAccelerometer!= null)
            mSensorManager.unregisterListener(mAccelerometerListener);
    }

    public void onResume() {
        if (mGyroscope!= null)
            mSensorManager.registerListener(mGyroscopeListener, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        else if (mAccelerometer!= null)
            mSensorManager.registerListener(mAccelerometerListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
