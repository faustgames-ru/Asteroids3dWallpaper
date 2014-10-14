package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.Entities.PatriclessEmitter.VectorValueRange;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;

public class Bounds {
    public Vertex Min;
    public Vertex Max;

    public Bounds(Vertex center, float radius){
        Min = new Vertex(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
        Max = new Vertex(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
    }

    public Bounds(){
        Min = new Vertex(MathF.MaxFloat, MathF.MaxFloat, MathF.MaxFloat);
        Max = new Vertex(-MathF.MaxFloat, -MathF.MaxFloat, -MathF.MaxFloat);
    }

    public Bounds(Vertex min, Vertex max){
        Min = min;
        Max = max;
    }

    public void apply(Vertex center, float radius){
        Vertex.sub(center, radius, Min);
        Vertex.add(center, radius, Max);
    }

    public Vertex getSize(){
        return Vertex.sub(Max, Min);
    }


    public void test(float x, float y, float z) {
        if (Min.getX() > x)
            Min.setX(x);
        if (Min.getY() > y)
            Min.setY(y);
        if (Min.getZ() > z)
            Min.setZ(z);
        if (Max.getX() < x)
            Max.setX(x);
        if (Max.getY() < y)
            Max.setY(y);
        if (Max.getZ() < z)
            Max.setZ(z);
    }

    public void test(Vertex center, float radius) {
        test(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        test(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
    }

    public void test(Vertex value){
        test(value.getX(), value.getY(), value.getZ());
    }

    public boolean contains(Vertex v)
    {
        return
            (Min.getX() <= v.getX()) && (v.getX() <= Max.getX()) &&
            (Min.getY() <= v.getY()) && (v.getY() <= Max.getY()) &&
            (Min.getZ() <= v.getZ()) && (v.getZ() <= Max.getZ());
    }

    public boolean contains(Bounds bounds)
    {
        return contains(bounds.Min) && contains(bounds.Max);
    }

    public void clear()
    {
        Min.setX(MathF.MaxFloat);
        Min.setY(MathF.MaxFloat);
        Min.setZ(MathF.MaxFloat);

        Max.setX(-MathF.MaxFloat);
        Max.setY(-MathF.MaxFloat);
        Max.setZ(-MathF.MaxFloat);
    }

    public static boolean cross(Bounds bounds1, Bounds bounds2)
    {
        if (bounds1.Max.getX() < bounds2.Min.getX()) return false;
        if (bounds2.Max.getX() < bounds1.Min.getX()) return false;
        if (bounds1.Max.getY() < bounds2.Min.getY()) return false;
        if (bounds2.Max.getY() < bounds1.Min.getY()) return false;
        if (bounds1.Max.getZ() < bounds2.Min.getZ()) return false;
        if (bounds2.Max.getZ() < bounds1.Min.getZ()) return false;
        return true;
    }

    public Bounds[] divideX()
    {
        float midV = (Min.getX() + Max.getX())*0.5f;
        return new Bounds[]
        {
            new Bounds(new Vertex(Min.getX(), Min.getY(), Min.getZ()), new Vertex(midV, Max.getY(), Max.getZ())),
            new Bounds(new Vertex(midV, Min.getY(), Min.getZ()), new Vertex(Max.getX(), Max.getY(), Max.getZ()))
        };
    }

    public Bounds[] divideY()
    {
        float midV = (Min.getY() + Max.getY())*0.5f;
        return new Bounds[]
        {
            new Bounds(new Vertex(Min.getX(), Min.getY(), Min.getZ()), new Vertex(Max.getX(), midV, Max.getZ())),
            new Bounds(new Vertex(Min.getX(), midV, Min.getZ()), new Vertex(Max.getX(), Max.getY(), Max.getZ()))
        };
    }

    public Bounds[] divideZ()
    {
        float midV = (Min.getZ() + Max.getZ())*0.5f;
        return new Bounds[]
        {
            new Bounds(new Vertex(Min.getX(), Min.getY(), Min.getZ()), new Vertex(Max.getX(), Max.getY(), midV)),
            new Bounds(new Vertex(Min.getX(), Min.getY(), midV), new Vertex(Max.getX(), Max.getY(), Max.getZ()))
        };
    }
}
