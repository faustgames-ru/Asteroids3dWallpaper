package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

import com.FaustGames.Core.Mathematics.Vertex;

public class Position extends AttributeData implements IPosition {
    public Vertex xyz = new Vertex();

    public Position(Vertex value) {
        xyz.setX(value.getX());
        xyz.setY(value.getY());
        xyz.setZ(value.getZ());
    }

    public Position(float x, float y, float z) {
        xyz.setX(x);
        xyz.setY(y);
        xyz.setZ(z);
    }

    public float getX(){
        return xyz.getX();
    }

    public float getY(){
        return xyz.getY();
    }

    public float getZ(){
        return xyz.getZ();
    }
}
