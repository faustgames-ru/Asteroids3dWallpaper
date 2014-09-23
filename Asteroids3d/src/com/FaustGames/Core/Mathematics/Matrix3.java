package com.FaustGames.Core.Mathematics;

public class Matrix3 {
    public float[] values = new float[9];

    public static final Matrix3 Identity = new Matrix3(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1);

    public Matrix3() {

    }

    public Matrix3(
            float xx, float xy, float xz,
            float yx, float yy, float yz,
            float zx, float zy, float zz) {
        values[0] = xx;
        values[1] = xy;
        values[2] = xz;

        values[3] = yx;
        values[4] = yy;
        values[5] = yz;

        values[6] = zx;
        values[7] = zy;
        values[8] = zz;
    }

    public Vertex transform(Vertex v){
        return new Vertex(
                values[0] * v.get(0) + values[1] * v.get(1) + values[2] * v.get(2),
                values[3] * v.get(0) + values[4] * v.get(1) + values[5] * v.get(2),
                values[6] * v.get(0) + values[7] * v.get(1) + values[8] * v.get(2));
    }

    public float get(int row, int column) {
        return values[row * 2 + column];
    }

    public void set(int row, int column, float value) {
        values[row * 3 + column] = value;
    }

    public float determinant(){
        float a = values[0];
        float b = values[1];
        float c = values[2];

        float d = values[3];
        float e = values[4];
        float f = values[5];

        float g = values[6];
        float h = values[7];
        float i = values[8];

        return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }

    public Matrix3 createInverse() {
        float a = values[0];
        float b = values[1];
        float c = values[2];

        float d = values[3];
        float e = values[4];
        float f = values[5];

        float g = values[6];
        float h = values[7];
        float i = values[8];

        float dt = 1.0f / a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
        float A = e*i - f*h;
        float B = - (d*i - f*g);
        float C = d*h - e*g;

        float D = -(b*i - c*h);
        float E = a*i - c*g;
        float F = -(a*h - b*g);

        float G = b*f - c*e;
        float H = -(a*f - c*d);
        float I = a*e - b*d;

        return new Matrix3(
                A * dt, D * dt, G * dt,
                B * dt, E * dt, H * dt,
                C * dt, F * dt, I * dt);
    }

    public Matrix3 createTranspose() {
        return new Matrix3(
                values[0], values[3], values[6],
                values[1], values[4], values[7],
                values[2], values[5], values[8]);
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
    public float getXZ() {
        return values[2];
    }
    public float getYX() {
        return values[3];
    }
    public float getYY() {
        return values[4];
    }
    public float getYZ() {
        return values[5];
    }
    public float getZX() {
        return values[6];
    }
    public float getZY() {
        return values[7];
    }
    public float getZZ() {
        return values[8];
    }
}
