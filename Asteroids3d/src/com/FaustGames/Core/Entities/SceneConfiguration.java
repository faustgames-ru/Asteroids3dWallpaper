package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Geometry.Bounds;
import com.FaustGames.Core.Mathematics.Vertex;

public class SceneConfiguration {
    public static SceneConfiguration Default = new SceneConfiguration();

    public Bounds Dimensions = new Bounds(new Vertex(-50, -50, -50), new Vertex(50, 50, 50));
    public int GeometryTreeDepth = 10;
    public float GeometryTreeSize = 200;
}
