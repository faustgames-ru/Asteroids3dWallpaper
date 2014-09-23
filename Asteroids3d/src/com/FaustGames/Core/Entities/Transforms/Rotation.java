package com.FaustGames.Core.Entities.Transforms;

import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;

public class Rotation extends Transform {
    public void rotate(Vertex axis, float angle){
        TransformMatrix = Matrix.Multiply(TransformMatrix, Matrix.createRotation(axis, angle));
    }
    public void rotateX(float angle){
        TransformMatrix = Matrix.Multiply(TransformMatrix, Matrix.createRotationX(angle));
    }
    public void rotateY(float angle){
        TransformMatrix = Matrix.Multiply(TransformMatrix, Matrix.createRotationY(angle));
    }
    public void rotateZ(float angle){
        TransformMatrix = Matrix.Multiply(TransformMatrix, Matrix.createRotationZ(angle));
    }
}
