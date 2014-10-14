package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.Mathematics.Vertex;

public class GeometryContact {
    IGeometryTreeItem _a;
    IGeometryTreeItem _b;
    public Vertex NormalA = new Vertex();
    public Vertex NormalB = new Vertex();
    public IGeometryTreeItem getA(){
        return _a;
    }

    public void setA(IGeometryTreeItem a){
        _a = a;
    }

    public IGeometryTreeItem getB(){
        return _b;
    }

    public void setB(IGeometryTreeItem b){
        _b = b;
    }
}
