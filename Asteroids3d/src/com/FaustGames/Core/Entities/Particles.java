package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Entities.Transforms.Rotation;
import com.FaustGames.Core.IUpdatable;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;

public class Particles implements IUpdatable {
    Particle[] mParticles;
    Matrix mMatrix;
    Vertex mVelocity = Vertex.Empty.clone();
    Vertex mPosition = Vertex.Empty.clone();
    Rotation mRotation = new Rotation();
    float mRotationSpeed;

    public Particles(Particle[] particles, Vertex position, Vertex velocity, float rotation){
        mParticles = particles;
        mPosition = position;
        mVelocity = velocity;
        mMatrix = Matrix.createTranslate(mPosition);
        mRotationSpeed = rotation;
    }

    @Override
    public void update(float timeDelta) {
        mPosition.add(Vertex.mul(mVelocity, timeDelta));
        if (mPosition.getX() > 4)
            mPosition.setX(mPosition.getX() - 8);
        if (mPosition.getX() < -4)
            mPosition.setX(mPosition.getX() + 8);
        if (mPosition.getY() > 4)
            mPosition.setY(mPosition.getX() - 8);
        if (mPosition.getY() < -4)
            mPosition.setY(mPosition.getX() + 8);
        if (mPosition.getZ() > 4)
            mPosition.setZ(mPosition.getX() - 8);
        if (mPosition.getZ() < -4)
            mPosition.setZ(mPosition.getX() + 8);

        mRotation.rotateX(mRotationSpeed * timeDelta);
        mMatrix = Matrix.Multiply(mRotation.getMatrix(), Matrix.createTranslate(mPosition));
    }
}
