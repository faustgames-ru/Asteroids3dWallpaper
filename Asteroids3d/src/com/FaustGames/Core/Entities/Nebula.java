package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Mathematics.Vertex;

public class Nebula {
    public Vertex Position;
    public float Size;
    public boolean Inverse;

    public Nebula(Vertex position, float size, boolean inverse) {
        Position = position;
        Size = size;
        Inverse = inverse;
    }
}
