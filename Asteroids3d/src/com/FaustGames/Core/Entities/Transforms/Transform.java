package com.FaustGames.Core.Entities.Transforms;

import com.FaustGames.Core.Mathematics.Matrix;

public class Transform {
    protected Matrix TransformMatrix = Matrix.Identity;
    public Matrix getMatrix(){
        return TransformMatrix;
    }
}
