package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.ColorTheme;
import com.FaustGames.Core.Content.SkyBoxResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IPositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.Position;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTextureIndex;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Shader;

import java.util.Vector;

public class SkyBox implements IRenderable, ILoadable {
    static final float Distance = 2048;
    double[] mVertices = new double[]{
            -1.0000, -1.0000, 0.0000, 0.0000, 1.0000,
            -1.0000, 1.0000, 0.0000, 0.0000, 0.0000,
            1.0000, -1.0000, 0.0000, 1.0000, 1.0000,
            1.0000, 1.0000, 0.0000, 1.0000, 0.0000,
    };
            /*
    double[] mVertices = new double[]{
            -1.0000, -1.0000, 0.0000, 0.0000, 1.0000,
            -1.0820, -0.8115, 0.0820, 0.0000, 0.9097,
            -1.1547, -0.5774, 0.1547, 0.0000, 0.7952,
            -1.2060, -0.3015, 0.2060, 0.0000, 0.6560,
            -1.2247, 0.0000, 0.2247, 0.0000, 0.5000,
            -1.2060, 0.3015, 0.2060, 0.0000, 0.3440,
            -1.1547, 0.5774, 0.1547, 0.0000, 0.2048,
            -1.0820, 0.8115, 0.0820, 0.0000, 0.0903,
            -1.0000, 1.0000, 0.0000, 0.0000, 0.0000,
            -0.8115, -1.0820, 0.0820, 0.0903, 1.0000,
            -0.8911, -0.8911, 0.1882, 0.0903, 0.9097,
            -0.9649, -0.6433, 0.2865, 0.0903, 0.7952,
            -1.0190, -0.3397, 0.3587, 0.0903, 0.6560,
            -1.0392, 0.0000, 0.3856, 0.0903, 0.5000,
            -1.0190, 0.3397, 0.3587, 0.0903, 0.3440,
            -0.9649, 0.6433, 0.2865, 0.0903, 0.2048,
            -0.8911, 0.8911, 0.1882, 0.0903, 0.0903,
            -0.8115, 1.0820, 0.0820, 0.0903, 0.0000,
            -0.5774, -1.1547, 0.1547, 0.2048, 1.0000,
            -0.6433, -0.9649, 0.2865, 0.2048, 0.9097,
            -0.7071, -0.7071, 0.4142, 0.2048, 0.7952,
            -0.7559, -0.3780, 0.5119, 0.2048, 0.6560,
            -0.7746, 0.0000, 0.5492, 0.2048, 0.5000,
            -0.7559, 0.3780, 0.5119, 0.2048, 0.3440,
            -0.7071, 0.7071, 0.4142, 0.2048, 0.2048,
            -0.6433, 0.9649, 0.2865, 0.2048, 0.0903,
            -0.5774, 1.1547, 0.1547, 0.2048, 0.0000,
            -0.3015, -1.2060, 0.2060, 0.3440, 1.0000,
            -0.3397, -1.0190, 0.3587, 0.3440, 0.9097,
            -0.3780, -0.7559, 0.5119, 0.3440, 0.7952,
            -0.4082, -0.4082, 0.6330, 0.3440, 0.6560,
            -0.4201, 0.0000, 0.6803, 0.3440, 0.5000,
            -0.4082, 0.4082, 0.6330, 0.3440, 0.3440,
            -0.3780, 0.7559, 0.5119, 0.3440, 0.2048,
            -0.3397, 1.0190, 0.3587, 0.3440, 0.0903,
            -0.3015, 1.2060, 0.2060, 0.3440, 0.0000,
            0.0000, -1.2247, 0.2247, 0.5000, 1.0000,
            0.0000, -1.0392, 0.3856, 0.5000, 0.9097,
            0.0000, -0.7746, 0.5492, 0.5000, 0.7952,
            0.0000, -0.4201, 0.6803, 0.5000, 0.6560,
            0.0000, 0.0000, 0.7321, 0.5000, 0.5000,
            0.0000, 0.4201, 0.6803, 0.5000, 0.3440,
            0.0000, 0.7746, 0.5492, 0.5000, 0.2048,
            0.0000, 1.0392, 0.3856, 0.5000, 0.0903,
            0.0000, 1.2247, 0.2247, 0.5000, 0.0000,
            0.3015, -1.2060, 0.2060, 0.6560, 1.0000,
            0.3397, -1.0190, 0.3587, 0.6560, 0.9097,
            0.3780, -0.7559, 0.5119, 0.6560, 0.7952,
            0.4082, -0.4082, 0.6330, 0.6560, 0.6560,
            0.4201, 0.0000, 0.6803, 0.6560, 0.5000,
            0.4082, 0.4082, 0.6330, 0.6560, 0.3440,
            0.3780, 0.7559, 0.5119, 0.6560, 0.2048,
            0.3397, 1.0190, 0.3587, 0.6560, 0.0903,
            0.3015, 1.2060, 0.2060, 0.6560, 0.0000,
            0.5774, -1.1547, 0.1547, 0.7952, 1.0000,
            0.6433, -0.9649, 0.2865, 0.7952, 0.9097,
            0.7071, -0.7071, 0.4142, 0.7952, 0.7952,
            0.7559, -0.3780, 0.5119, 0.7952, 0.6560,
            0.7746, 0.0000, 0.5492, 0.7952, 0.5000,
            0.7559, 0.3780, 0.5119, 0.7952, 0.3440,
            0.7071, 0.7071, 0.4142, 0.7952, 0.2048,
            0.6433, 0.9649, 0.2865, 0.7952, 0.0903,
            0.5774, 1.1547, 0.1547, 0.7952, 0.0000,
            0.8115, -1.0820, 0.0820, 0.9097, 1.0000,
            0.8911, -0.8911, 0.1882, 0.9097, 0.9097,
            0.9649, -0.6433, 0.2865, 0.9097, 0.7952,
            1.0190, -0.3397, 0.3587, 0.9097, 0.6560,
            1.0392, 0.0000, 0.3856, 0.9097, 0.5000,
            1.0190, 0.3397, 0.3587, 0.9097, 0.3440,
            0.9649, 0.6433, 0.2865, 0.9097, 0.2048,
            0.8911, 0.8911, 0.1882, 0.9097, 0.0903,
            0.8115, 1.0820, 0.0820, 0.9097, 0.0000,
            1.0000, -1.0000, 0.0000, 1.0000, 1.0000,
            1.0820, -0.8115, 0.0820, 1.0000, 0.9097,
            1.1547, -0.5774, 0.1547, 1.0000, 0.7952,
            1.2060, -0.3015, 0.2060, 1.0000, 0.6560,
            1.2247, 0.0000, 0.2247, 1.0000, 0.5000,
            1.2060, 0.3015, 0.2060, 1.0000, 0.3440,
            1.1547, 0.5774, 0.1547, 1.0000, 0.2048,
            1.0820, 0.8115, 0.0820, 1.0000, 0.0903,
            1.0000, 1.0000, 0.0000, 1.0000, 0.0000
    };
*/
            IndexBuffer mIndexBuffer = new IndexBuffer(new short[]{
                    0, 1, 3, 0, 3, 2,
            });

    /*
    IndexBuffer mIndexBuffer = new IndexBuffer(new short[] {
            0, 1, 10, 0, 10, 9,
            1, 2, 11, 1, 11, 10,
            2, 3, 12, 2, 12, 11,
            3, 4, 13, 3, 13, 12,
            4, 5, 14, 4, 14, 13,
            5, 6, 15, 5, 15, 14,
            6, 7, 16, 6, 16, 15,
            7, 8, 17, 7, 17, 16,
            9, 10, 19, 9, 19, 18,
            10, 11, 20, 10, 20, 19,
            11, 12, 21, 11, 21, 20,
            12, 13, 22, 12, 22, 21,
            13, 14, 23, 13, 23, 22,
            14, 15, 24, 14, 24, 23,
            15, 16, 25, 15, 25, 24,
            16, 17, 26, 16, 26, 25,
            18, 19, 28, 18, 28, 27,
            19, 20, 29, 19, 29, 28,
            20, 21, 30, 20, 30, 29,
            21, 22, 31, 21, 31, 30,
            22, 23, 32, 22, 32, 31,
            23, 24, 33, 23, 33, 32,
            24, 25, 34, 24, 34, 33,
            25, 26, 35, 25, 35, 34,
            27, 28, 37, 27, 37, 36,
            28, 29, 38, 28, 38, 37,
            29, 30, 39, 29, 39, 38,
            30, 31, 40, 30, 40, 39,
            31, 32, 41, 31, 41, 40,
            32, 33, 42, 32, 42, 41,
            33, 34, 43, 33, 43, 42,
            34, 35, 44, 34, 44, 43,
            36, 37, 46, 36, 46, 45,
            37, 38, 47, 37, 47, 46,
            38, 39, 48, 38, 48, 47,
            39, 40, 49, 39, 49, 48,
            40, 41, 50, 40, 50, 49,
            41, 42, 51, 41, 51, 50,
            42, 43, 52, 42, 52, 51,
            43, 44, 53, 43, 53, 52,
            45, 46, 55, 45, 55, 54,
            46, 47, 56, 46, 56, 55,
            47, 48, 57, 47, 57, 56,
            48, 49, 58, 48, 58, 57,
            49, 50, 59, 49, 59, 58,
            50, 51, 60, 50, 60, 59,
            51, 52, 61, 51, 61, 60,
            52, 53, 62, 52, 62, 61,
            54, 55, 64, 54, 64, 63,
            55, 56, 65, 55, 65, 64,
            56, 57, 66, 56, 66, 65,
            57, 58, 67, 57, 67, 66,
            58, 59, 68, 58, 68, 67,
            59, 60, 69, 59, 69, 68,
            60, 61, 70, 60, 70, 69,
            61, 62, 71, 61, 71, 70,
            63, 64, 73, 63, 73, 72,
            64, 65, 74, 64, 74, 73,
            65, 66, 75, 65, 75, 74,
            66, 67, 76, 66, 76, 75,
            67, 68, 77, 67, 77, 76,
            68, 69, 78, 68, 78, 77,
            69, 70, 79, 69, 79, 78,
            70, 71, 80, 70, 80, 79
    });
*/
    VertexBuffer mBuffer;
    VertexBuffer mBatchBuffer;
    IndexBuffer mBatchIndexBuffer;
    Texture mXP;
    Texture mXM;
    Texture mYP;
    Texture mYM;
    Texture mZP;
    Texture mZM;

    SkyBoxResource mSkyBoxResource;
    private Light light;

    public SkyBox(SkyBoxResource skyBoxResource) {
        mSkyBoxResource = skyBoxResource;
    }

    public VertexColor EmptyVertexColor;

    public class VertexColor
    {
        public Color Stars;
        public Color Clouds0;
        public Color Clouds1;


        public VertexColor() {
            Stars = new Color(
                    MathF.rand(0.9f, 1.0f),
                    MathF.rand(0.9f, 1.0f),
                    MathF.rand(0.9f, 1.0f),
                    1.0f);

            float c0 = MathF.rand(0.0f, 1.0f);
            if (c0 < 0.5)
                c0 = MathF.rand(0.0f, 0.3f);
            else
                c0 = MathF.rand(0.0f, 1.0f);

            float c1 = MathF.rand(0.0f, 1.0f);
            if (c1 < 0.5)
                c1 = MathF.rand(0.0f, 0.3f);
            else
                c1 = MathF.rand(0.0f, 1.0f);

            Clouds0 = new Color(
                    MathF.rand(0.7f, 1.0f),
                    MathF.rand(0.7f, 1.0f),
                    MathF.rand(0.7f, 1.0f),
                    c0);
            Clouds1 = new Color(
                    MathF.rand(0.7f, 1.0f),
                    MathF.rand(0.7f, 1.0f),
                    MathF.rand(0.7f, 1.0f),
                    c1);
        }
    }

    public VertexColor[][][] ColorSpace;

    int GetAddress(float v, int detail) {
        int i = Math.round((v + 1.0f) * 0.5f * (detail - 1));
        if (i < 0)
            i = 0;
        if (i > (detail - 1))
            i = detail - 1;
        return i;
    }

    VertexColor GetColor(Vertex position, int detail) {

        float dot0 = Vertex.dotProduct(position, new Vertex(1, 1, 0).normalize());
        if (MathF.abs(dot0) < 0.001)
            return EmptyVertexColor;
        dot0 = Vertex.dotProduct(position, new Vertex(-1, 1, 0).normalize());
        if (MathF.abs(dot0) < 0.001)
            return EmptyVertexColor;
        dot0 = Vertex.dotProduct(position, new Vertex(0, 1, 1).normalize());
        if (MathF.abs(dot0) < 0.001)
            return EmptyVertexColor;
        dot0 = Vertex.dotProduct(position, new Vertex(0, 1, -1).normalize());
        if (MathF.abs(dot0) < 0.001)
            return EmptyVertexColor;
        /*
        float dot1 = Vertex.dotProduct(position, new Vertex(-1, 1, 1).normalize());
        if (MathF.abs(dot1) > 0.95)
            return EmptyVertexColor;
        float dot2 = Vertex.dotProduct(position, new Vertex(1, -1, 1).normalize());
        if (MathF.abs(dot2) > 0.95)
            return EmptyVertexColor;
        float dot3 = Vertex.dotProduct(position, new Vertex(1,  1, -1).normalize());
        if (MathF.abs(dot3) > 0.95)
            return EmptyVertexColor;
        */
        return ColorSpace
                [GetAddress(position.getX(), detail)]
                [GetAddress(position.getY(), detail)]
                [GetAddress(position.getZ(), detail)];
    }

    public int AddBoxPlane(int detail, float[] vertices, int offset, Matrix transform)    {
        return AddBoxPlane(detail, vertices, offset, transform, detail, -1, detail, -1);
    }

    public int AddBoxPlane(int detail, float[] vertices, int offset, Matrix transform, int exceptXFrom, int exceptXTo, int exceptYFrom, int exceptYTo)
    {
        float stepX = 2.0f / detail;
        float stepY = 2.0f / detail;
        int i = offset;
        float x = -1.0f;
        for (int xi = 0; xi <= detail; xi++) {
            float y = -1.0f;
            for (int yi = 0; yi <= detail; yi++) {

                float l = MathF.sqrt(1.0f + x * x + y * y);
                float z = 1.0f;
                float d = 1.0f;
                float a = 1.0f;
                if (exceptXFrom < exceptXTo) {
                    if ((exceptXFrom < xi)&&(xi<exceptXTo)) {
                        a = 0.0f;
                    }
                }
                if (exceptYFrom < exceptYTo) {
                    if ((exceptYFrom < yi)&&(yi<exceptYTo)) {
                        a = 0.0f;
                    }
                }
                float xx = x * d / l;
                float yy = y * d / l;
                z = z * d / l;


                Vertex vt =  new Vertex(x, y, 1.0f);

                float tx = (MathF.atan2(vt.getX(), d) * 4.0f / MathF.PI + 1.0f) * 0.5f;
                float ty = 1.0f - (MathF.atan2(vt.getY(), d) * 4.0f / MathF.PI + 1.0f) * 0.5f;

                Vertex n = new Vertex(xx, yy, z) .normalize();//??;
                Vertex v =  transform.transform(n);
                VertexColor color = GetColor(v.normalize(), detail);

                vertices[i++] = v.getX();
                vertices[i++] = v.getY();
                vertices[i++] = v.getZ();
                vertices[i++] = tx;
                vertices[i++] = ty;


                vertices[i++] = color.Clouds0.getR();
                vertices[i++] = color.Clouds0.getG();
                vertices[i++] = color.Clouds0.getB();
                vertices[i++] = color.Clouds0.getA();

                vertices[i++] = color.Clouds1.getR();
                vertices[i++] = color.Clouds1.getG();
                vertices[i++] = color.Clouds1.getB();
                vertices[i++] = color.Clouds1.getA();

                vertices[i++] = color.Stars.getR();
                vertices[i++] = color.Stars.getG();
                vertices[i++] = color.Stars.getB();
                vertices[i++] = color.Stars.getA();

                vertices[i++] = a;
                y += stepY;
            }
            x += stepX;
        }
        return i;
    }

    public int AddBoxPlaneIndices(int detail, short[] indices, int offset, int vertexOffset, int stride)
    {
        return AddBoxPlaneIndices(detail, indices, offset, vertexOffset, stride, detail, -1, detail, -1, false);
    }

    public int AddBoxPlaneIndices(int detail, short[] indices, int offset, int vertexOffset, int stride, int exceptXFrom, int exceptXTo, int exceptYFrom, int exceptYTo, boolean capX)
    {
        int i = offset;
        int vi = vertexOffset / stride;
        for (int xi = 0; xi < detail; xi++) {
            for (int yi = 0; yi < detail; yi++) {
                if ((exceptXFrom < xi) && (xi < exceptXTo)) {
                    vi++;
                    continue;
                }
                if ((exceptYFrom < yi) && (yi < exceptYTo)) {
                    vi++;
                    continue;
                }
                /*
                if ((capX) && (xi == 0)) {
                    if ((yi == 0)) {
                        indices[i++] = (short) (vi + 0);
                        indices[i++] = (short) (vi + detail + 2);
                        indices[i++] = (short) (vi + detail + 1);
                    }
                    else
                    {

                        indices[i++] = (short) (vi + 1);
                        indices[i++] = (short) (vi + detail + 2);
                        indices[i++] = (short) (vi + detail + 1);

                    }
                }
                else  if ((capX) && (xi == (detail-1))) {
                    if ((yi == 0)) {
                        indices[i++] = (short) (vi + 0);
                        indices[i++] = (short) (vi + 1);
                        indices[i++] = (short) (vi + detail + 1);
                    }
                    else
                    {
                        indices[i++] = (short) (vi + 0);
                        indices[i++] = (short) (vi + 1);
                        indices[i++] = (short) (vi + detail + 2);
                    }
                }
                else */
                {
                    indices[i++] = (short) (vi + 0);
                    indices[i++] = (short) (vi + 1);
                    indices[i++] = (short) (vi + detail + 2);
                    indices[i++] = (short) (vi + 0);
                    indices[i++] = (short) (vi + detail + 2);
                    indices[i++] = (short) (vi + detail + 1);
                }
                vi++;
            }
            vi++;
        }
        return i;
    }

    public void create(Context context)
    {
        mBuffer = new VertexBuffer(new VertexBufferAttribute[]{VertexBufferAttribute.Position, VertexBufferAttribute.TexturePosition});
        mBatchBuffer = new VertexBuffer(new VertexBufferAttribute[]{
                VertexBufferAttribute.Position,
                VertexBufferAttribute.TexturePosition,
                VertexBufferAttribute.CloudsColor0,
                VertexBufferAttribute.CloudsColor1,
                VertexBufferAttribute.StartsColor,
                VertexBufferAttribute.Alpha
        });
        mBuffer.setValues(mVertices);

        EmptyVertexColor = new VertexColor();
        EmptyVertexColor.Clouds0.rgba[3] = 0.0f;
        EmptyVertexColor.Clouds1.rgba[3] = 0.0f;
        int stride = mBatchBuffer.FloatStride;
        int detail = 6;

        ColorSpace = new VertexColor[detail][][];
        for (int i = 0; i < ColorSpace.length; i++) {
            ColorSpace[i] = new VertexColor[detail][];
            for (int j = 0; j < ColorSpace.length; j++) {
                ColorSpace[i][j] = new VertexColor[detail];
                for (int k = 0; k < ColorSpace.length; k++) {
                    ColorSpace[i][j][k] = new VertexColor();
                }
            }
        }

        int jointsVerticesOffset = 0;// ((detail + 1)*(detail + 1)*5)*stride;
        int jointsIndicesOffset = 0;//detail*6 * 6;

        int planesCount = 6;

        float[] vertices = new float[((detail + 1)*(detail + 1)*planesCount)*stride + jointsVerticesOffset];
        short[] indices = new short[detail*detail*planesCount*6 + jointsIndicesOffset];

        int offset = 0;
        int indexOffset = 0;

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mZPTransform.createTranspose());

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mZMTransform.createTranspose());

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mXPTransform.createTranspose());

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mXMTransform.createTranspose());

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mYPTransform.createTranspose());

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride);
        offset = AddBoxPlane(detail, vertices, offset, mYMTransform.createTranspose());

/*
        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail-1, true);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mZPTransform,Matrix.createRotationX(0, -1)).createTranspose(), detail, -1, 0, detail);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXPTransform,Matrix.createRotationZ(1, 0)).createTranspose(), detail, -1, 0, detail+1);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXMTransform,Matrix.createRotationZ(-1, 0)).createTranspose(), detail, -1, 0, detail+1);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, -1, detail-1, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXPTransform,Matrix.createRotationZ(-1, 0)).createTranspose(), detail, -1, -1, detail);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, -1, detail-1, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXMTransform,Matrix.createRotationZ(1, 0)).createTranspose(), detail, -1, -1, detail);
*/
        /*
        offset = AddBoxPlane(detail, vertices, offset,
                mZMTransform.createTranspose(),
                Matrix.createRotationZ(MathF.PI / 2.0f).createTranspose(),
                Matrix.Multiply(Matrix.createRotationZ(MathF.PI / 2.0f), Matrix.createScaling(1.0f, 1.0f, 1.0f)).createTranspose() ,
                1);
        */

        mBatchBuffer.setValues(vertices);
        mBatchIndexBuffer = new IndexBuffer(indices);
    }

    public void load(Context context){
        mXP = TextureFactory.CreateTexture(context, mSkyBoxResource.XP, true);
        mXM = TextureFactory.CreateTexture(context, mSkyBoxResource.XM, true);
        mYP = TextureFactory.CreateTexture(context, mSkyBoxResource.YP, true);
        mYM = TextureFactory.CreateTexture(context, mSkyBoxResource.YM, true);
        mZP = TextureFactory.CreateTexture(context, mSkyBoxResource.ZP, true);
        mZM = TextureFactory.CreateTexture(context, mSkyBoxResource.ZM, true);
        mBuffer.createVBO();
        mBatchBuffer.createVBO();
    }

    public void render(Camera camera, Texture texture, Matrix model){
        Shader.SkyBoxProcedural.setView(camera.getViewTransform());
        Shader.SkyBoxProcedural.setProjection(camera.getProjectionTransform());
        Shader.SkyBoxProcedural.setModel(model);
        Shader.SkyBoxProcedural.setTexture(texture);
        Shader.SkyBoxProcedural.apply();
        mBuffer.apply(Shader.SkyBoxProcedural.Attributes);
        Shader.SkyBoxProcedural.draw(mIndexBuffer);
        /*
        Shader.SkyBox.setTexture(texture);
        Shader.SkyBox.setView(camera.getViewTransform());
        Shader.SkyBox.setProjection(camera.getProjectionTransform());
        Shader.SkyBox.setModel(model);
        Shader.SkyBox.setColor(ColorTheme.Default.SkyBoxColor);
        Shader.SkyBox.setEye(camera.getPosition());

        //Shader.SkyBox.setFogDensity(ColorTheme.Default.FogDensity);
        Shader.SkyBox.setLight(light.getPosition());

        Shader.SkyBox.apply();
        Shader.SkyBox.draw(mIndexBuffer);
        */
    }
    /*
    Matrix mZPTransform = Matrix.Multiply(Matrix.createTranslate(0, 0, 1), Matrix.createScaling(Distance, Distance, Distance));
    Matrix mZMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(0, -1));
    Matrix mXPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(1, 0));
    Matrix mXMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(-1, 0));
    Matrix mYPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(-1, 0));
    Matrix mYMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(1, 0));
    */
    Matrix mZPTransform = Matrix.createScaling(Distance, Distance, Distance);
    Matrix mZMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(0, -1));
    Matrix mXPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(1, 0));
    Matrix mXMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(-1, 0));
    Matrix mYPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(-1, 0));
    Matrix mYMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(1, 0));

    public void render(Camera camera){
        /*
        mBuffer.apply();
        render(camera, mYM, mZPTransform);
        render(camera, mYP, mZMTransform);
        render(camera, mXM, mXPTransform);
        render(camera, mXP, mXMTransform);
        render(camera, mZP, mYPTransform);
        render(camera, mZM, mYMTransform);
        */
        mBatchBuffer.apply(Shader.SkyBoxProcedural.Attributes);
        Shader.SkyBoxProcedural.setView(camera.getViewTransform());
        Shader.SkyBoxProcedural.setProjection(camera.getProjectionTransform());
        Shader.SkyBoxProcedural.setModel(Matrix.Identity);
        Shader.SkyBoxProcedural.setTexture(mYM);
        Shader.SkyBoxProcedural.apply();
        Shader.SkyBoxProcedural.draw(mBatchIndexBuffer );
    }

    public void unload() {
        if (mYM != null)
            mYM.destroy();

        if (mYP != null)
            mYP.destroy();

        if (mXM != null)
            mXM.destroy();

        if (mXP != null)
            mXP.destroy();

        if (mZP != null)
            mZP.destroy();

        if (mZM != null)
            mZM.destroy();
        if (mBuffer != null)
            mBuffer.destroy();
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Light getLight() {
        return light;
    }
}
