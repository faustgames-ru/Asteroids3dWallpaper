package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

import com.FaustGames.Core.Mathematics.Vertex;

public class ParticleVertex implements IParticlesVertex {
    Vertex mPosition;
    PositionTexture mScreenOffset;

    public ParticleVertex(Vertex position, PositionTexture screenOffset){
        mPosition = position;
        mScreenOffset = screenOffset;
    }

    @Override
    public float getX() {
        return mPosition.getX();
    }

    @Override
    public float getY() {
        return mPosition.getY();
    }

    @Override
    public float getZ() {
        return mPosition.getZ();
    }

    @Override
    public float getScreenOffsetX() {
        return mScreenOffset.getX();
    }

    @Override
    public float getScreenOffsetY() {
        return mScreenOffset.getY();
    }

    @Override
    public float getScreenOffsetZ() {
        return mScreenOffset.getZ();
    }

    @Override
    public float getU() {
        return mScreenOffset.getU();
    }

    @Override
    public float getV() {
        return mScreenOffset.getV();
    }
}
