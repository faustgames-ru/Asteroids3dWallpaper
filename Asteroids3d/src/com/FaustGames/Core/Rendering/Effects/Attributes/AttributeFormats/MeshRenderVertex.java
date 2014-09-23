package com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats;

import com.FaustGames.Core.Mathematics.Vertex;

public class MeshRenderVertex implements IMeshVertex {
    Vertex mPosition;
    float u;
    float v;
    Vertex mNormal;
    Vertex mTangent;
    Vertex mBiNormal;
    public float index;

    @Override
    public float getBiNormalX() {
        return mBiNormal.getX();
    }

    @Override
    public float getBiNormalY() {
        return mBiNormal.getY();
    }

    @Override
    public float getBiNormalZ() {
        return mBiNormal.getZ();
    }

    @Override
    public float getNormalX() {
        return mNormal.getX();
    }

    @Override
    public float getNormalY() {
        return mNormal.getY();
    }

    @Override
    public float getNormalZ() {
        return mNormal.getZ();
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
    public float getTangentX() {
        return mTangent.getX();
    }

    @Override
    public float getTangentY() {
        return mTangent.getY();
    }

    @Override
    public float getTangentZ() {
        return mTangent.getZ();
    }

    @Override
    public float getU() {
        return u;
    }

    @Override
    public float getV() {
        return v;
    }
}
