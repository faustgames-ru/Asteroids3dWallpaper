package com.FaustGames.Core.Entities.Mesh;

import com.FaustGames.Core.Entities.SceneConfiguration;
import com.FaustGames.Core.Geometry.*;
import com.FaustGames.Core.IUpdatable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Physics.PhysicsHelper;

import java.util.ArrayList;
import java.util.Vector;

public class GeometryItemMesh implements IGeometryTreeItem, IGeometryShapeSphere, IUpdatable, IGeometryDynamic {
    public Bounds Bounds;
    public int GeometryTreeNodeId;
    Vertex _position;
    Vertex _velocity = new Vertex();
    Matrix _transform;
    Matrix3 _transformNormals;
    float _mass;
    float _volume;
    float _density;
    float _radius;
    float _bonce;
    float _angle;
    float _angularVelocity;
    int _axis;

    Matrix getTransform(){
        return _transform;
    }

    public GeometryItemMesh(Vertex position, float radius, float density, float bonce, Matrix transform, Matrix3 transformNormals) {
        Bounds = new Bounds(position, radius);
        _bonce = bonce;
        _position = position;
        _radius = radius;
        _transform = transform;
        _transformNormals = transformNormals;
        _density = density;
        _volume = MathF.sphereVolume(_radius);
        _mass = _volume * _density;

        _velocity.setX(MathF.rand(-2f, 2f));
        _velocity.setY(MathF.rand(-2f, 2f));
        _velocity.setZ(MathF.rand(-2f, 2f));

        _axis = MathF.rand(2);
        _angularVelocity = MathF.rand(-MathF.PI * 0.1f, MathF.PI * 0.1f);
    }

    @Override
    public Bounds getBounds() {
        return Bounds;
    }

    @Override
    public int getGeometryTreeNodeId() {
        return GeometryTreeNodeId;
    }

    @Override
    public void setGeometryTreeNodeId(int value) {
        GeometryTreeNodeId = value;
    }

    @Override
    public void update(float timeDelta) {
        _velocity.add(_bonceDirection.div(timeDelta));
        _velocity.limit(-5, 5);
        _bonceDirection.clear();

        PhysicsHelper.applyVelocity(_position, _velocity, timeDelta);

        Bounds b = SceneConfiguration.Default.Dimensions;

        if (_position.getX() > b.Max.getX())
            //_position.setX(_position.getX()-200);
            _velocity.setX(-MathF.abs(_velocity.getX()));
        if (_position.getX() < b.Min.getX())
            //_position.setX(_position.getX()+200);
            _velocity.setX(MathF.abs(_velocity.getX()));
        if (_position.getY() > b.Max.getY())
            //_velocity.setY(-2);
            _velocity.setY(-MathF.abs(_velocity.getY()));
        if (_position.getY() < b.Min.getY())
            //_velocity.setY(2);
            _velocity.setY(MathF.abs(_velocity.getY()));
        if (_position.getZ() > b.Max.getZ())
            //_velocity.setZ(-2);
            _velocity.setZ(-MathF.abs(_velocity.getZ()));
        if (_position.getZ() < -b.Max.getZ())
            //_velocity.setZ(2);
            _velocity.setZ(MathF.abs(_velocity.getZ()));

        _angle += _angularVelocity * timeDelta;

        Matrix.applyTranslate(_position, _transform);
        float s = MathF.sin(_angle);
        float c = MathF.cos(_angle);
        switch (_axis){
            case 0:
                Matrix.applyRotationX(s, c, _transform);
                break;
            case 1:
                Matrix.applyRotationY(s, c, _transform);
                break;
            case 2:
                Matrix.applyRotationZ(s, c, _transform);
                break;
        }

        _transformNormals.fromMatrix4(_transform);
        Bounds.apply(_position, _radius);
    }

    @Override
    public ShapesType getShapeType() {
        return ShapesType.Sphere;
    }

    @Override
    public float getRadius() {
        return _radius;
    }

    @Override
    public Vertex getPosition() {
        return _position;
    }


    @Override
    public float getBonce() {
        return _bonce;
    }

    @Override
    public float getMass() {
        return _mass;
    }

    Vertex _bonceDirection = new Vertex();

    @Override
    public void applyBonce(Vertex direction) {
        _bonceDirection.add(direction.mul(_bonce));
    }
}
