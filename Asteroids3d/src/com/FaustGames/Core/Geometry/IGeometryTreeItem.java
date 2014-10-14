package com.FaustGames.Core.Geometry;

public interface IGeometryTreeItem extends IGeometryShape {
    Bounds getBounds();
    int getGeometryTreeNodeId();
    void setGeometryTreeNodeId(int value);
}
