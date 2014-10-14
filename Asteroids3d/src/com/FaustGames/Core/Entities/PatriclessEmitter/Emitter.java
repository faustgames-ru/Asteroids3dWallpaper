package com.FaustGames.Core.Entities.PatriclessEmitter;

import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ParticlesEmitterVertex;

import java.util.ArrayList;
import java.util.Vector;

public class Emitter {
    public VectorValueRange Position;
    public VectorValueRange Velocity;
    public VectorValueRange Acceleration;
    public VectorValueRange Color;
    public int Count;
    public FloatValueRange Scale;
    public FloatValueRange LifeTime;
    public FloatValueRange RebornTime;
    public FloatValueRange InitPhase;

    public static Emitter createDefault(){
        return new Emitter();
    }

    public static Emitter createFires(){
        Emitter result = new Emitter();
        result.InitFires();
        return result;
    }

    public static Emitter createClouds(){
        Emitter result = new Emitter();
        result.InitClouds();
        return result;
    }

    public Emitter()
    {
        Position = new VectorValueRange(new Vertex(-30f, -30f, -30f), new Vertex(30f, 30f, 30f));
        Velocity = new VectorValueRange(new Vertex(0.25f, -0.5f, -0.5f), new Vertex(0.25f, 0.5f, 0.5f));
        Acceleration = new VectorValueRange(new Vertex(0.0f, 0.0f, 0.0f), new Vertex(0.1f, 0.0f, 0.0f));
        Color = new VectorValueRange(0.65f, 1.0f);
        Count = 400;
        Scale = new FloatValueRange(0.2f, 0.4f);
        LifeTime = new FloatValueRange(2.0f, 3.0f);
        RebornTime = new FloatValueRange(0.0f, 0.5f);
        InitPhase = new FloatValueRange(0.0f, LifeTime.Max - LifeTime.Min);
    }

    public void InitFires()
    {
        if (DeviceConfiguration.isTablet)
            Position = new VectorValueRange(new Vertex(-20f, -20f, -20f), new Vertex(20f, 20f, 20f));
        else
            Position = new VectorValueRange(new Vertex(-20f, -20f, -20f), new Vertex(20f, 20f, 20f));
        Velocity = new VectorValueRange(new Vertex(5f, -5f, -5f), new Vertex(5f, 5f, 5f));
        Acceleration = new VectorValueRange(new Vertex(2.0f, 0.0f, 0.0f), new Vertex(2.5f, 0.0f, 0.0f));
        Color = new VectorValueRange(0.65f, 1.0f);
        Count = 400;
        if (DeviceConfiguration.isTablet)
            Scale = new FloatValueRange(0.1f, 0.3f);
        else
            Scale = new FloatValueRange(0.05f, 0.2f);
        LifeTime = new FloatValueRange(0.5f, 1.0f);
        RebornTime = new FloatValueRange(0.0f, 0.1f);
        InitPhase = new FloatValueRange(0.0f, LifeTime.Max - LifeTime.Min);
    }

    public void InitClouds()
    {
        if (DeviceConfiguration.isTablet)
            Position = new VectorValueRange(new Vertex(-20f, -20f, -20f), new Vertex(20f, 20f, 20f));
        else
            Position = new VectorValueRange(new Vertex(-20f, -20f, -20f), new Vertex(20f, 20f, 20f));
        Velocity = new VectorValueRange(new Vertex(-2f, -2f, -2f), new Vertex(2f, 2f, 2f));
        Acceleration = new VectorValueRange(new Vertex(-1f, 0.0f, 0.0f), new Vertex(1f, 0.0f, 0.0f));
        Color = new VectorValueRange(0.65f, 1.0f);
        Count = 10;
        if (DeviceConfiguration.isTablet)
            Scale = new FloatValueRange(30.0f, 40.0f);
        else
            Scale = new FloatValueRange(5.0f, 15.0f);
        LifeTime = new FloatValueRange(3.0f, 6.0f);
        RebornTime = new FloatValueRange(0.0f, 0.5f);
        InitPhase = new FloatValueRange(0.0f, LifeTime.Max - LifeTime.Min);
    }

    public void CreateVertices(ArrayList<ParticlesEmitterVertex> source) {
        for (int i = 0; i < Count; i++) {
            Particle particle = Gen();
            particle.CreateVertices(source);
        }
    }

    Particle Gen() {
        Particle particle = new Particle();
        particle.Position = Position.Gen();
        particle.StartVelocity = Velocity.Gen();
        particle.Acceleration = Acceleration.Gen();
        Vertex color = Color.Gen();
        particle.Color = new Color(color.getX(), color.getY(), color.getZ(), 1.0f);
        particle.Scale = Scale.Gen();
        particle.LifeTime = LifeTime.Gen();
        particle.RebornTime = RebornTime.Gen();
        particle.InitPhase = InitPhase.Gen();
        particle.makePeriodic();
        return particle;
    }
}

class Particle
{
    public Vertex Position;
    public Vertex StartVelocity;
    public Vertex Acceleration;
    public Color Color;
    public float Scale;
    public float LifeTime;
    public float RebornTime;
    public float InitPhase;

    public static float GlobalPeriod = 16f;

    public void makePeriodic() {
        float period = LifeTime+RebornTime;
        float periodDivision = (int)(GlobalPeriod / period);
        float newPeriod = GlobalPeriod / periodDivision;
        float scale = newPeriod / period;
        LifeTime = LifeTime * scale;
        RebornTime = RebornTime * scale;
    }

    ParticlesEmitterVertex CreateVertex(Vertex offset, Vertex tOffset){
        ParticlesEmitterVertex v = new ParticlesEmitterVertex();
        v.MidPoint = Position;
        v.Position = offset;
        v.TexturePosition = tOffset;
        v.StartVelocity = StartVelocity;
        v.Acceleration = Acceleration;
        v.Color = Color;
        v.Scale = Scale;
        v.LifeTime = LifeTime;
        v.RebornTime = RebornTime;
        v.InitPhase = InitPhase;
        return v;
    }

    public void CreateVertices(ArrayList<ParticlesEmitterVertex> source) {
        Vertex lt = new Vertex(-1, 1);
        Vertex lb = new Vertex(-1, -1);
        Vertex rt = new Vertex(1, 1);
        Vertex rb = new Vertex(1, -1);
        Vertex tlt = new Vertex(0, 1);
        Vertex tlb = new Vertex(0, 0);
        Vertex trt = new Vertex(1, 1);
        Vertex trb = new Vertex(1, 0);
        source.add(CreateVertex(lt, tlt));
        source.add(CreateVertex(rt, trt));
        source.add(CreateVertex(rb, trb));
        source.add(CreateVertex(lb, tlb));

    }
}

