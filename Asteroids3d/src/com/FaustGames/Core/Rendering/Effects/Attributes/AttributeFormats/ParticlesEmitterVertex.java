package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;

public class ParticlesEmitterVertex {
    public Vertex MidPoint;
    public Vertex Position;
    public Vertex TexturePosition;
    public Vertex StartVelocity;
    public Vertex Acceleration;
    public Color Color;
    public float Scale;
    public float LifeTime;
    public float RebornTime;
    public float InitPhase;
}
