package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Physics.IBonce;
import com.FaustGames.Core.Physics.IMass;

public interface IGeometryDynamic extends IMass, IBonce {
    void applyBonce(Vertex direction);
}
