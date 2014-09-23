package com.FaustGames.Core.Entities.Transforms;

import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;

public class Translation extends Transform {
    Vertex mPosition;
    public void setPosition(Vertex v){
        mPosition = v;
        apply();
    }
    public Vertex getPosition(){
        return mPosition;
    }

    void apply(){
        TransformMatrix = Matrix.createTranslate(mPosition);
    }
}
