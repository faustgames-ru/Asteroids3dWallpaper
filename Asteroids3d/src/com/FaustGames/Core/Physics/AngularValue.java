package com.FaustGames.Core.Physics;

import com.FaustGames.Core.Mathematics.Vertex;

public class AngularValue {
    Vertex mAxis = Vertex.AxisZ.clone();
    float mValue = 0;

    public Vertex getAxis(){
        return mAxis;
    }
    public float getValue(){ return mValue; }

    public AngularValue setAxis(Vertex value){
        mAxis = value;
        return this;
    }

    public AngularValue setValue(float value){
        mValue = value;
        return this;
    }
}
