package com.FaustGames.Core.Mathematics;

import java.util.Vector;

public class Matrix2 {
    public float[] values = new float[4];

    public static final Matrix2 Identity = new Matrix2(
            1, 0,
            0, 1);

    public Matrix2() {

    }

    public Matrix2(
            float xx, float xy,
            float yx, float yy) {
        values[0] = xx;
        values[1] = xy;
        values[2] = yx;
        values[3] = yy;
    }

    public Vertex transform(Vertex v){
        return new Vertex(
                values[0] * v.get(0) + values[1] * v.get(1),
                values[2] * v.get(0) + values[3] * v.get(1));
    }

    public float get(int row, int column) {
        return values[row * 2 + column];
    }

    public void set(int row, int column, float value) {
        values[row * 2 + column] = value;
    }

    public float determinant(){
        return values[0] * values[3] - values[1] * values[2];
    }

    public Matrix2 createInverse() {
        float dt = 1.0f / determinant();
        return new Matrix2(
                values[3] * dt,  -values[1] * dt,
               -values[2] * dt,   values[0] * dt);
    }

    public Matrix2 createTranspose() {
        return new Matrix2(
                values[0], values[2],
                values[1], values[3]);
    }

    public static Matrix2 Multiply(Matrix2 m1, Matrix2 m2, Matrix2 m3, Matrix2 m4, Matrix2 m5, Matrix2 m6){
        return  Multiply(Multiply(m1, m2, m3, m4, m5), m6);
    }

    public static Matrix2 Multiply(Matrix2 m1, Matrix2 m2, Matrix2 m3, Matrix2 m4, Matrix2 m5){
        return  Multiply(Multiply(m1, m2, m3, m4), m5);
    }

    public static Matrix2 Multiply(Matrix2 m1, Matrix2 m2, Matrix2 m3, Matrix2 m4){
        return  Multiply(Multiply(m1, m2, m3), m4);
    }

    public static Matrix2 Multiply(Matrix2 m1, Matrix2 m2, Matrix2 m3){
        return  Multiply(Multiply(m1, m2), m3);
    }

    public static Matrix2 Multiply(Matrix2 m1, Matrix2 m2){
        Matrix2 r = new Matrix2(0, 0, 0, 0);
        int i, j, k;

        for (i=0; i<2; ++i)
            for (j=0; j<2; ++j)  {
                for (k=0; k<2; ++k)
                    r.set(i, j, r.get(i, j) + m1.get(i, k) * m2.get(k, j));
            }
        return r;
    }

    public float getXX() {
        return values[0];
    }
    public float getXY() {
        return values[1];
    }

    public float getYX() {
        return values[2];
    }
    public float getYY() {
        return values[3];
    }
}
