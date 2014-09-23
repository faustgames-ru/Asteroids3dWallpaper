package com.FaustGames.Core.Entities.PatriclessEmitter;

import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;

public class VectorValueRange
{
    public Vertex Min;
    public Vertex Max;

    public VectorValueRange(Color min, Color max) {
        Min = new Vertex(min.getR(), min.getG(), min.getB());
        Max = new Vertex(max.getR(), max.getG(), max.getB());
    }

    public VectorValueRange(float min, float max) {
        Min = new Vertex(min, min, min);
        Max = new Vertex(max, max, max);
    }

    public VectorValueRange(Vertex min, Vertex max) {
        Min = min;
        Max = max;
    }

    public Vertex Gen()
    {
        return new Vertex(
                MathF.rand(Min.getX(), Max.getX()),
                MathF.rand(Min.getY(), Max.getY()),
                MathF.rand(Min.getZ(), Max.getZ()));
    }
}

