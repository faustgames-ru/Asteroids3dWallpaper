package com.FaustGames.Core.Entities.Mesh;

import com.FaustGames.Core.Mathematics.Matrix2;
import com.FaustGames.Core.Mathematics.Vertex;

public class MeshTriangle {
    MeshVertex[] abc = new MeshVertex[3];

    Vertex mNormal;
    Vertex mTangent;
    Vertex mBiNormal;

    public MeshTriangle(MeshVertex a, MeshVertex b, MeshVertex c){
        setA(a);
        setB(b);
        setC(c);
    }

    public void build(){

        Vertex ba = Vertex.sub(getB().getPosition(), getA().getPosition());
        Vertex ca = Vertex.sub(getC().getPosition(), getA().getPosition());

        Vertex tBA = Vertex.sub(getB().getTexturePosition(), getA().getTexturePosition());
        Vertex tCA = Vertex.sub(getC().getTexturePosition(), getA().getTexturePosition());


        Matrix2 tMatrix = new Matrix2(
                tBA.getX(), tBA.getY(),
                tCA.getX(), tCA.getY()).createInverse();

        Vertex ex = tMatrix.transform(Vertex.AxisX);
        Vertex ey = tMatrix.transform(Vertex.AxisY);

        mTangent = Vertex.add(
                Vertex.mul(ba, ex.getX()),
                Vertex.mul(ca, ex.getY()));

        mBiNormal = Vertex.add(
                Vertex.mul(ba, ey.getX()),
                Vertex.mul(ca, ey.getY()));

        mNormal = Vertex.mul(Vertex.crossProduct(ba, ca), -1);

        getA().addTriangle(this);
        getB().addTriangle(this);
        getC().addTriangle(this);
    }

    public MeshVertex getVertex(int i){
        return abc[i];
    }

    public MeshVertex[] toArray(){
        return abc;
    }

    public void fromArray(MeshVertex[] values){
        abc = values;
    }

    public MeshVertex getA(){
        return abc[0];
    }
    public MeshVertex getB(){
        return abc[1];
    }
    public MeshVertex getC(){
        return abc[2];
    }
    public void setA(MeshVertex value){
        abc[0] = value;
    }
    public void setB(MeshVertex value){
        abc[1] = value;
    }
    public void setC(MeshVertex value){
        abc[2] = value;
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
}
