package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;

public class Light {
    Color Color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    Vertex mPosition;
    Matrix mTransform;
    float mLensBrightness = 1.0f;
    public Light(){}
    public Light(float x, float y, float z, float lensBrightness)
    {
        setPosition(new Vertex(x, y, z));
        mLensBrightness = lensBrightness;
    }
    public Light(Vertex position){setPosition(position);}
    public void setPosition(Vertex position){
        mPosition = position;
        float l = mPosition.length();
        mTransform = Matrix.Identity;
        mTransform = Matrix.Multiply(mTransform, Matrix.createZDirectionMatrix(mPosition.inverse()));
        mTransform = Matrix.Multiply(mTransform, Matrix.createOrtho(4, 4, 4));
    }
    public Matrix getTransform(){
        return mTransform;
    }

    public Vertex getPosition() {
        return mPosition;
    }
    public float getLensBrightness() {
        return mLensBrightness;
    }
}
