package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Mathematics.Vertex;

public class Particle {
    public Vertex Position;
    public float Size;
    public int ImageIndex;
    public Particle(Vertex position, float size, int imageIndex){
        Position = position;
        Size = size;
        ImageIndex = imageIndex;
    }
}
