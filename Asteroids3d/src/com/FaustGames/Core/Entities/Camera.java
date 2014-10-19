package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Entities.Transforms.Rotation;
import com.FaustGames.Core.Entities.Transforms.Translation;
import com.FaustGames.Core.Geometry.*;
import com.FaustGames.Core.IUpdatable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Physics.AngularValue;

import java.sql.Statement;
import java.util.Vector;

public class Camera implements IUpdatable, IGeometryTreeItem, IGeometryShapeSphere, IGeometryDynamic {
    Matrix mProjection = Matrix.Identity;
    Matrix mLensProjection = Matrix.Identity;
    Matrix mSkyBoxTransform = Matrix.Identity;
    Matrix mViewTransform = Matrix.Identity;
    Matrix mFullTransform = Matrix.Identity;
    Matrix3 mNormal = Matrix3.Identity;
    float mFov = MathF.PI / 4.0f;
    float mHeight = 100;
    float mDistance = 32.0f;
    public float Aspect;
    public static float resistanceK = 0;

    public AngularValue RotationVelocity = new AngularValue();
    public AngularValue ExtraRotationVelocity = new AngularValue();

    public Rotation Rotation = new Rotation();
    public Rotation ExtraRotation = new Rotation();
    public Translation Translation = new Translation();
    private Vertex position;

    public void setViewport(float fov, float width, float height){
        fov *= 0.8f;
        mFov = fov;
        mHeight = height;
        float aspect = width / height;
        Aspect = aspect;
        if (width > height)
        {
            mProjection = Matrix.createProjectionH(fov, 1.0f / aspect, 1.0f, 4096);
            mLensProjection = Matrix.createProjectionH(fov, 1.0f / aspect, 1.0f, 4096);
        }
        else
        {
            mProjection = Matrix.createProjection(fov, aspect, 1.0f, 4096);
            mLensProjection = Matrix.createProjection(fov, aspect, 1.0f, 4096);
        }
    }

    public void apply() {
       // synchronized (this) {
            mSkyBoxTransform = Matrix.Multiply(Rotation.getMatrix(), ExtraRotation.getMatrix(), mProjection);
            //mFullTransform = Matrix.Multiply(Translation.getMatrix(), Matrix.createTranslate(0, 0, mDistance), Rotation.getMatrix(), mProjection);
            mViewTransform = Matrix.Multiply(Rotation.getMatrix(), ExtraRotation.getMatrix(), Matrix.createTranslate(0, 0, mDistance));
            mFullTransform = Matrix.Multiply(mViewTransform, mProjection);
            Matrix inverse = mViewTransform.inverse().createTranspose();
            position = inverse.transform(Vertex.Empty);
            mNormal = new Matrix3(
                    mFullTransform.getXX(), mFullTransform.getXY(), mFullTransform.getXZ(),
                    mFullTransform.getYX(), mFullTransform.getYY(), mFullTransform.getYZ(),
                    mFullTransform.getZX(), mFullTransform.getZY(), mFullTransform.getZZ());
            _bounds.apply(position, getRadius());
       // }
    }

    public float getFov(){ return mFov; }
    public float getHeight(){ return mHeight; }

    public Matrix getViewTransform(){
        return mViewTransform;
    }
    public Matrix getProjectionTransform(){
        return mProjection;
    }
    public Matrix getLensProjectionTransform(){ return mLensProjection; }
    public Matrix getTransform(){
        return mFullTransform;
    }
    public Matrix getSkyBoxTransform(){ return mSkyBoxTransform; }

    @Override
    public void update(float timeDelta) {
        float v = RotationVelocity.getValue();
        float ev = ExtraRotationVelocity.getValue();
        //synchronized (this) {
            Rotation.rotate(RotationVelocity.getAxis(), v * timeDelta);
            ExtraRotation.rotate(ExtraRotationVelocity.getAxis(), ev * timeDelta);
        //}
        //apply();

        float resistanceA = resistanceK * RotationVelocity.getValue();// * RotationVelocity.getValue();
        if (v > 0) {
            v -= resistanceA * timeDelta;
            if (v < 0)
                v = 0;
        }
        else {
            v += resistanceA * timeDelta;
            if (v > 0)
                v = 0;
        }

        resistanceA = resistanceK * ExtraRotationVelocity.getValue();// * RotationVelocity.getValue();
        if (ev > 0) {
            ev -= resistanceA * timeDelta;
            if (ev < 0)
                ev = 0;
        }
        else {
            ev += resistanceA * timeDelta;
            if (ev > 0)
                ev = 0;
        }

        RotationVelocity.setValue(v);
        ExtraRotationVelocity.setValue(ev);
    }

    public Matrix3 getNormal() {
        return mNormal;
    }

    @Override
    public float getRadius() {
        return 5;
    }

    Bounds _bounds = new Bounds();

    public Vertex getPosition() {
        return position;
    }

    @Override
    public void applyBonce(Vertex direction) {

    }

    @Override
    public float getBonce() {
        return 0;
    }

    @Override
    public Bounds getBounds() {
        return _bounds;
    }

    int _geometryTreeNodeId;

    @Override
    public int getGeometryTreeNodeId() {
        return _geometryTreeNodeId;
    }

    @Override
    public void setGeometryTreeNodeId(int value) {
        _geometryTreeNodeId = value;
    }

    @Override
    public ShapesType getShapeType() {
        return ShapesType.Sphere;
    }

    @Override
    public float getMass() {
        return 1e20f;
    }
}
