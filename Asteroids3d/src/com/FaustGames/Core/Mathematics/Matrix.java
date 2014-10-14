package com.FaustGames.Core.Mathematics;

public class Matrix {
    public int o = 0;
    public float[] values = new float[16];

    public static final Matrix Identity = new Matrix(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public Matrix() {

    }

    public Matrix(float xx, float xy, float xz, float xw,
                  float yx, float yy, float yz, float yw,
                  float zx, float zy, float zz, float zw,
                  float wx, float wy, float wz, float ww) {
        values[o + 0] = xx;
        values[o + 1] = xy;
        values[o + 2] = xz;
        values[o + 3] = xw;

        values[o + 4] = yx;
        values[o + 5] = yy;
        values[o + 6] = yz;
        values[o + 7] = yw;

        values[o + 8] = zx;
        values[o + 9] = zy;
        values[o + 10] = zz;
        values[o + 11] = zw;

        values[o + 12] = wx;
        values[o + 13] = wy;
        values[o + 14] = wz;
        values[o + 15] = ww;
    }

    public Matrix(int ncols, int nrows) {

    }

    public float get(int row, int column) {
        return values[o + row * 4 + column];
    }

    public void set(int row, int column, float value) {
        values[o + row * 4 + column] = value;
    }

    public static Matrix createOrtho(float width, float height, float depth){
        Matrix result = new Matrix();
        result.set(0, 0, 1 / width);
        result.set(1, 1, 1 / height);
        result.set(2, 2, -2 / depth);
        result.set(3, 3, 1);
        return result;
    }
    public static Matrix createProjection(float fov, float aspect, float nearDist, float farDist){
        /*
        if ( fov <= 0 || aspect == 0 )
        {
            Assert( fov > 0 && aspect != 0 );
            return;
        }
        */
        float frustumDepth = farDist - nearDist;
        float oneOverDepth = 1 / frustumDepth;

        Matrix result = new Matrix();
        result.set(1, 1, 1 / MathF.tan(0.5f * fov));
        result.set(0, 0, result.get(1, 1) / aspect);
        result.set(2, 2, farDist * oneOverDepth);
        result.set(3, 2, (-farDist * nearDist) * oneOverDepth);
        result.set(2, 3, 1);
        result.set(3, 3, 0);

        return result;
    }

    public static Matrix createProjectionH(float fov, float aspect, float nearDist, float farDist){
        /*
        if ( fov <= 0 || aspect == 0 )
        {
            Assert( fov > 0 && aspect != 0 );
            return;
        }
        */
        float frustumDepth = farDist - nearDist;
        float oneOverDepth = 1 / frustumDepth;

        Matrix result = new Matrix();
        result.set(0, 0, 1 / MathF.tan(0.5f * fov));
        result.set(1, 1, result.get(0, 0) / aspect);
        result.set(2, 2, farDist * oneOverDepth);
        result.set(3, 2, (-farDist * nearDist) * oneOverDepth);
        result.set(2, 3, 1);
        result.set(3, 3, 0);

        return result;
    }

    public Matrix createTranspose() {
        return new Matrix(
                values[o + 0], values[o + 4], values[o + 8], values[o + 12],
                values[o + 1], values[o + 5], values[o + 9], values[o + 13],
                values[o + 2], values[o + 6], values[o + 10], values[o + 14],
                values[o + 3], values[o + 7], values[o + 11], values[o + 15]);
    }

    public Matrix createNormal(){
        return new Matrix(
                values[o + 0], values[o + 1], values[o + 2], values[o + 3],
                values[o + 4], values[o + 5], values[o + 6], values[o + 7],
                values[o + 8], values[o + 9], values[o + 10], values[o + 11],
                0, 0, 0, 1);
    }

    public static Matrix createRotationZ(float angle){
        return createRotationZ(MathF.sin(angle), MathF.cos(angle));
    }

    public static Matrix createRotationX(float angle){
        return createRotationX(MathF.sin(angle), MathF.cos(angle));
    }

    public static Matrix createRotationY(float angle){
        return createRotationY(MathF.sin(angle), MathF.cos(angle));
    }

    public static Matrix createRotationZ(float sine, float cosine){
        return new Matrix(
                cosine, sine, 0, 0,
                -sine, cosine,0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public static Matrix createRotationX(float sine, float cosine){
        return new Matrix(
                1, 0, 0, 0,
                0, cosine, sine, 0,
                0, -sine, cosine,0,
                0, 0, 0, 1);
    }

    public static  Matrix createRotationY(float sine, float cosine){
        return new Matrix(
                cosine, 0, -sine, 0,
                0, 1, 0, 0,
                sine, 0, cosine,0,
                0, 0, 0, 1);
    }

    public static  Matrix createRotation(Vertex axis, float angle){
        if (MathF.equals(angle, 0))
            return Identity;
        return createRotation(axis, MathF.sin(angle), MathF.cos(angle));
    }

    public static  Matrix createZDirectionMatrix(Vertex axis){
        Vertex normal = axis.normalize();
        float a = normal.getX();
        float b = normal.getY();
        float c = normal.getZ();
        float d = MathF.sqrt(b * b + c * c);
        Matrix rxI = Matrix.Identity;
        if (!MathF.equals(d, 0.0f))
        {
            rxI = createRotationX(b / d, c / d);
        }
        Matrix ryI = createRotationY(-a, d);
        return Multiply(rxI, ryI);
    }

    public static  Matrix createRotation(Vertex axis, float sine, float cosine){
        Vertex normal = axis.normalize();
        float a = normal.getX();
        float b = normal.getY();
        float c = normal.getZ();
        float d = MathF.sqrt(b * b + c * c);
        Matrix rxI = Matrix.Identity;
        Matrix rx = Matrix.Identity;
        if (!MathF.equals(d, 0.0f))
        {
            rxI = createRotationX(b / d, c / d);
            rx = createRotationX(-b / d, c / d);
        }
        Matrix ryI = createRotationY(-a, d);
        Matrix ry = createRotationY(a, d);
        Matrix rz = createRotationZ(sine, cosine);
        return Multiply(rxI, ryI, rz, ry, rx);
    }


    public Matrix inverse()
    {
        float xx = +determinant3X3(getYY(), getYZ(), getYW(), getZY(), getZZ(), getZW(), getWY(), getWZ(), getWW());
        float xy = -determinant3X3(getYX(), getYZ(), getYW(), getZX(), getZZ(), getZW(), getWX(), getWZ(), getWW());
        float xz = +determinant3X3(getYX(), getYY(), getYW(), getZX(), getZY(), getZW(), getWX(), getWY(), getWW());
        float xw = -determinant3X3(getYX(), getYY(), getYZ(), getZX(), getZY(), getZZ(), getWX(), getWY(), getWZ());

        float yx = -determinant3X3(getXY(), getXZ(), getXW(), getZY(), getZZ(), getZW(), getWY(), getWZ(), getWW());
        float yy = +determinant3X3(getXX(), getXZ(), getXW(), getZX(), getZZ(), getZW(), getWX(), getWZ(), getWW());
        float yz = -determinant3X3(getXX(), getXY(), getXW(), getZX(), getZY(), getZW(), getWX(), getWY(), getWW());
        float yw = +determinant3X3(getXX(), getXY(), getXZ(), getZX(), getZY(), getZZ(), getWX(), getWY(), getWZ());

        float zx = +determinant3X3(getXY(), getXZ(), getXW(), getYY(), getYZ(), getYW(), getWY(), getWZ(), getWW());
        float zy = -determinant3X3(getXX(), getXZ(), getXW(), getYX(), getYZ(), getYW(), getWX(), getWZ(), getWW());
        float zz = +determinant3X3(getXX(), getXY(), getXW(), getYX(), getYY(), getYW(), getWX(), getWY(), getWW());
        float zw = -determinant3X3(getXX(), getXY(), getXZ(), getYX(), getYY(), getYZ(), getWX(), getWY(), getWZ());

        float wx = -determinant3X3(getXY(), getXZ(), getXW(), getYY(), getYZ(), getYW(), getZY(), getZZ(), getZW());
        float wy = +determinant3X3(getXX(), getXZ(), getXW(), getYX(), getYZ(), getYW(), getZX(), getZZ(), getZW());
        float wz = -determinant3X3(getXX(), getXY(), getXW(), getYX(), getYY(), getYW(), getZX(), getZY(), getZW());
        float ww = +determinant3X3(getXX(), getXY(), getXZ(), getYX(), getYY(), getYZ(), getZX(), getZY(), getZZ());

        float l = getXX()*xx + getXY()*xy + getXZ()*xz + getXW()*xw;
        if (MathF.equals(l, 0.0f))
        {
            return Matrix.Identity;
        }
        float d = 1 / l;
        return new Matrix(
                xx*d, yx*d, zx*d, wx*d,
                xy*d, yy*d, zy*d, wy*d,
                xz*d, yz*d, zz*d, wz*d,
                xw*d, yw*d, zw*d, ww*d);
    }


    private static float determinant3X3(
            float xx, float xy, float xz,
            float yx, float yy, float yz,
            float zx, float zy, float zz)
    {
        return xx * yy * zz + xy * yz * zx + yx * zy * xz - zx * yy * xz - xy * yx * zz - zy * yz * xx;
    }

    public Matrix invert()
    {
        float[] tmp = new float[12];
        float[] src = new float[16];
        float[] dst = new float[16];

        // Transpose matrix
        for (int i = 0; i < 4; i++) {
            src[i +  0] = values[i*4 + 0];
            src[i +  4] = values[i*4 + 1];
            src[i +  8] = values[i*4 + 2];
            src[i + 12] = values[i*4 + 3];
        }


        // Calculate pairs for first 8 elements (cofactors)
        tmp[0] = src[10] * src[15];
        tmp[1] = src[11] * src[14];
        tmp[2] = src[9]  * src[15];
        tmp[3] = src[11] * src[13];
        tmp[4] = src[9]  * src[14];
        tmp[5] = src[10] * src[13];
        tmp[6] = src[8]  * src[15];
        tmp[7] = src[11] * src[12];
        tmp[8] = src[8]  * src[14];
        tmp[9] = src[10] * src[12];
        tmp[10] = src[8] * src[13];
        tmp[11] = src[9] * src[12];

        // Calculate first 8 elements (cofactors)
        dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
        dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
        dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
        dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
        dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
        dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
        dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
        dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
        dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
        dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
        dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
        dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
        dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
        dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
        dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
        dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];

        // Calculate pairs for second 8 elements (cofactors)
        tmp[0]  = src[2]*src[7];
        tmp[1]  = src[3]*src[6];
        tmp[2]  = src[1]*src[7];
        tmp[3]  = src[3]*src[5];
        tmp[4]  = src[1]*src[6];
        tmp[5]  = src[2]*src[5];
        tmp[6]  = src[0]*src[7];
        tmp[7]  = src[3]*src[4];
        tmp[8]  = src[0]*src[6];
        tmp[9]  = src[2]*src[4];
        tmp[10] = src[0]*src[5];
        tmp[11] = src[1]*src[4];

        // Calculate second 8 elements (cofactors)
        dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
        dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
        dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
        dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
        dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
        dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
        dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
        dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
        dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
        dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
        dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
        dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
        dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
        dst[14] -= tmp[10]* src[11 ] + tmp[2] * src[8]   + tmp[7] * src[9];
        dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
        dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

        // Calculate determinant
        float det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];

        // Calculate matrix inverse
        det = 1.0f / det;
        Matrix result = new Matrix();
        for (int i = 0; i < 16; i++) {
            result.values[i] = dst[i] * det;
        }
        return result;
    }

    public static Matrix createTranslate(Vertex position) {
        return createTranslate(position.getX(), position.getY(), position.getZ());
    }


    public static Matrix createTranslate(float x, float y, float z) {
        return new Matrix(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                x, y, z, 1
        );
    }

    public static Matrix createScaling(float x, float y, float z) {
        return new Matrix(
                x, 0, 0, 0,
                0, y, 0, 0,
                0, 0, z, 0,
                0, 0, 0, 1
        );
    }

    public static Matrix Multiply(Matrix m1, Matrix m2, Matrix m3, Matrix m4, Matrix m5, Matrix m6){
        return  Multiply(Multiply(m1, m2, m3, m4, m5), m6);
    }

    public static Matrix Multiply(Matrix m1, Matrix m2, Matrix m3, Matrix m4, Matrix m5){
        return  Multiply(Multiply(m1, m2, m3, m4), m5);
    }

    public static Matrix Multiply(Matrix m1, Matrix m2, Matrix m3, Matrix m4){
        return  Multiply(Multiply(m1, m2, m3), m4);
    }

    public static Matrix Multiply(Matrix m1, Matrix m2, Matrix m3){
        return  Multiply(Multiply(m1, m2), m3);
    }

    public static Matrix Multiply(Matrix m1, Matrix m2){
        Matrix r = new Matrix(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        int i, j, k;

        for (i=0; i<4; ++i)
            for (j=0; j<4; ++j)  {
                for (k=0; k<4; ++k)
                    r.set(i, j, r.get(i, j) + m1.get(i, k) * m2.get(k, j));
            }
        return r;
    }

    public float getXX() { return values[o + 0]; }
    public float getXY() {
        return values[o + 1];
    }
    public float getXZ() {
        return values[o + 2];
    }
    public float getXW() {
        return values[o + 3];
    }

    public float getYX() {
        return values[o + 4];
    }
    public float getYY() {
        return values[o + 5];
    }
    public float getYZ() {
        return values[o + 6];
    }
    public float getYW() {
        return values[o + 7];
    }

    public float getZX() {
        return values[o + 8];
    }
    public float getZY() {
        return values[o + 9];
    }
    public float getZZ() {
        return values[o + 10];
    }
    public float getZW() {
        return values[o + 11];
    }

    public float getWX() {
        return values[o + 12];
    }
    public float getWY() {
        return values[o + 13];
    }
    public float getWZ() {
        return values[o + 14];
    }
    public float getWW() {
        return values[o + 15];
    }

    public Vertex transform(Vertex v) {
        return new Vertex(
                values[o + 0] * v.get(o + 0) + values[o + 1] * v.get(o + 1) + values[o + 2] * v.get(o + 2) + values[o + 3],
                values[o + 4] * v.get(o + 0) + values[o + 5] * v.get(o + 1) + values[o + 6] * v.get(o + 2) + values[o + 7],
                values[o + 8] * v.get(o + 0) + values[o + 9] * v.get(o + 1) + values[o + 10] * v.get(o + 2) + values[o + 11]);
    }

    public Vertex transformProjection(Vertex v) {
        float w = 1.0f / (values[o + 12] * v.get(o + 0) + values[o + 13] * v.get(o + 1) + values[o + 14] * v.get(o + 2) + values[o + 15]);
        return new Vertex(
                (values[o + 0] * v.get(o + 0) + values[o + 1] * v.get(o + 1) + values[o + 2] * v.get(o + 2) + values[o + 3]) * w,
                (values[o + 4] * v.get(o + 0) + values[o + 5] * v.get(o + 1) + values[o + 6] * v.get(o + 2) + values[o + 7]) * w,
                (values[o + 8] * v.get(o + 0) + values[o + 9] * v.get(o + 1) + values[o + 10] * v.get(o + 2) + values[o + 11]) * w);
    }

    public static Matrix createIdentity() {
        return new Matrix(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public static void applyRotationZ(float sine, float cosine, Matrix matrix){
        matrix.values[matrix.o + 0] = cosine;
        matrix.values[matrix.o + 1] = sine;
        matrix.values[matrix.o + 2] = 0;
        matrix.values[matrix.o + 3] = 0;

        matrix.values[matrix.o + 4] = -sine;
        matrix.values[matrix.o + 5] = cosine;
        matrix.values[matrix.o + 6] = 0;
        matrix.values[matrix.o + 7] = 0;
    }

    public static void applyRotationX(float sine, float cosine, Matrix matrix){
        matrix.values[matrix.o + 4] = 0;
        matrix.values[matrix.o + 5] = cosine;
        matrix.values[matrix.o + 6] = sine;
        matrix.values[matrix.o + 7] = 0;

        matrix.values[matrix.o + 8] = 0;
        matrix.values[matrix.o + 9] = -sine;
        matrix.values[matrix.o + 10] = cosine;
        matrix.values[matrix.o + 11] = 0;
    }

    public static  void applyRotationY(float sine, float cosine, Matrix matrix){
        matrix.values[matrix.o + 0] = cosine;
        matrix.values[matrix.o + 1] = 0;
        matrix.values[matrix.o + 2] = -sine;
        matrix.values[matrix.o + 3] = 0;

        matrix.values[matrix.o + 8] = sine;
        matrix.values[matrix.o + 9] = 0;
        matrix.values[matrix.o + 10] = cosine;
        matrix.values[matrix.o + 11] = 0;
    }

    public static void applyTranslate(Vertex position, Matrix matrix) {
        matrix.values[matrix.o + 12] = position.getX();
        matrix.values[matrix.o + 13] = position.getY();
        matrix.values[matrix.o + 14] = position.getZ();
        matrix.values[matrix.o + 15] = 1;
    }
}
