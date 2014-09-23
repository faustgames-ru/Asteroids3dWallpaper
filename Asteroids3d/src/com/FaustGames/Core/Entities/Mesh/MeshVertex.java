package com.FaustGames.Core.Entities.Mesh;

import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IMeshVertex;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class MeshVertex implements IMeshVertex {
    Vertex mPosition;
    Vertex mTexturePosition;

    Vertex mNormal;
    Vertex mTangent;
    Vertex mBiNormal;

    public int index;
    public float[] Data;

    public void putToFloatBuffer(FloatBuffer buffer)
    {
        buffer.put(Data);
    }

    ArrayList<MeshTriangle> mTriangles = new ArrayList<MeshTriangle>();

    public MeshVertex(float[] data){
        mPosition = new Vertex(
                data[0],
                data[1],
                data[2]);
        mNormal = new Vertex(
                data[3],
                data[4],
                data[5]);
        mTangent = new Vertex(
                data[6],
                data[7],
                data[8]);
        mBiNormal = new Vertex(
                data[9],
                data[10],
                data[11]);
        mTexturePosition = new Vertex(
                data[12],
                data[13]);
        Data = data;
    }
    public MeshVertex(float x, float y, float z, float tx, float ty){
        mPosition = new Vertex(x, y, z);
        mTexturePosition = new Vertex(tx, ty);
    }

    public Vertex getPosition(){
        return mPosition;
    }

    public Vertex getTexturePosition(){
        return mTexturePosition;
    }

    public void build(boolean generateNormals){
        if (generateNormals)
            mNormal = Vertex.Empty.clone();
        mTangent = Vertex.Empty.clone();
        mBiNormal = Vertex.Empty.clone();
        for (MeshTriangle t : mTriangles) {
            if (generateNormals)
                mNormal.add(t.getNormal());
            mTangent.add(t.getTangent());
            mBiNormal.add(t.getBiNormal());
        }

        if (generateNormals)
            mNormal.normalizeSelf();
        mTangent.normalizeSelf();
        mBiNormal.normalizeSelf();
    }

    public void clearTriangles(){
        mTriangles.clear();
    }

    public void addTriangle(MeshTriangle meshTriangle) {
        mTriangles.add(meshTriangle);
    }

    public Vertex getNormal() {
        return mNormal;
    }

    public Vertex getTangent() {
        return mTangent;
    }

    public Vertex getBiNormal() {
        return mBiNormal;
    }

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
        return mTexturePosition.getX();
    }

    @Override
    public float getV() {
        return mTexturePosition.getY();
    }
}
