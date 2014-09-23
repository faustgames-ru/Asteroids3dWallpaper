package com.FaustGames.Core.Entities.PatriclessEmitter;

import com.FaustGames.Core.Mathematics.MathF;

public class FloatValueRange
{
    public float Min;
    public float Max;

    public FloatValueRange(float min, float max) {
        Min = min;
        Max = max;
    }

    public float Gen()
    {
        return MathF.rand(Min, Max);
    }
}

