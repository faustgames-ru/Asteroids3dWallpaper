package com.FaustGames.Core.Entities;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.Content.EntityResource;
import com.FaustGames.Core.Content.EntityResourceProceduralSkybox;
import com.FaustGames.Core.ICreate;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBuffer;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Shader;

public class ProceduralSkybox extends Entity implements IRenderable, ILoadable, ICreate {
    static final float Distance = 2048;
    EntityResourceProceduralSkybox _resources;
    Texture _texture;
    public ProceduralSkybox(EntityResourceProceduralSkybox resources){
        _resources = resources;
    }

    public VertexColor EmptyVertexColor;

    public class VertexColor
    {
        public Color Stars;
        public Color Clouds0;
        public Color Clouds1;


        public VertexColor() {
            Stars = new Color(
                    MathF.rand(0.8f, 1.0f),
                    MathF.rand(0.8f, 1.0f),
                    MathF.rand(0.8f, 1.0f),
                    1.0f);

            float c0 = MathF.saturate(MathF.rand(-0.5f, 0.7f));
            /*
            if (c0 < 0.5)
                c0 = MathF.rand(0.0f, 0.3f);
            else
                c0 = MathF.rand(0.0f, 1.0f);
            */
            float c1 = MathF.saturate(MathF.rand(-0.5f, 0.7f));
            /*
            if (c1 < 0.5)
                c1 = MathF.rand(0.0f, 0.3f);
            else
                c1 = MathF.rand(0.0f, 1.0f);
            */
            Clouds0 = new Color(
                    MathF.rand(0.8f, 1.0f),
                    MathF.rand(0.8f, 1.0f),
                    MathF.rand(0.8f, 1.0f),
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
        /*
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
        */
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

    VertexBuffer _vertexBuffer;
    IndexBuffer _indexBuffer;

    @Override
    public void create(Context context) {
        _vertexBuffer = new VertexBuffer(new VertexBufferAttribute[]{
                VertexBufferAttribute.Position,
                VertexBufferAttribute.TexturePosition,
                VertexBufferAttribute.CloudsColor0,
                VertexBufferAttribute.CloudsColor1,
                VertexBufferAttribute.StartsColor,
                VertexBufferAttribute.Alpha
        });

        EmptyVertexColor = new VertexColor();
        EmptyVertexColor.Clouds0.rgba[3] = 0.0f;
        EmptyVertexColor.Clouds1.rgba[3] = 0.0f;
        int stride = _vertexBuffer.FloatStride;
        int detail = 4;

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

        int jointsVerticesOffset = ((detail + 1)*(detail + 1)*5)*stride;
        int jointsIndicesOffset = detail*6 * 6;

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


        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail-1, true);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mZPTransform, Matrix.createRotationX(0, -1)).createTranspose(), detail, -1, 0, detail);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXPTransform,Matrix.createRotationZ(1, 0)).createTranspose(), detail, -1, 0, detail+1);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, 0, detail, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXMTransform,Matrix.createRotationZ(-1, 0)).createTranspose(), detail, -1, 0, detail+1);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, -1, detail-1, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXPTransform,Matrix.createRotationZ(-1, 0)).createTranspose(), detail, -1, -1, detail);

        indexOffset = AddBoxPlaneIndices(detail, indices, indexOffset, offset, stride, detail, -1, -1, detail-1, false);
        offset = AddBoxPlane(detail, vertices, offset, Matrix.Multiply(mXMTransform,Matrix.createRotationZ(1, 0)).createTranspose(), detail, -1, -1, detail);

        /*
        offset = AddBoxPlane(detail, vertices, offset,
                mZMTransform.createTranspose(),
                Matrix.createRotationZ(MathF.PI / 2.0f).createTranspose(),
                Matrix.Multiply(Matrix.createRotationZ(MathF.PI / 2.0f), Matrix.createScaling(1.0f, 1.0f, 1.0f)).createTranspose() ,
                1);
        */

        _vertexBuffer.setValues(vertices);
        _indexBuffer = new IndexBuffer(indices);
    }

    Matrix mZPTransform = Matrix.createScaling(Distance, Distance, Distance);
    Matrix mZMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(0, -1));
    Matrix mXPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(1, 0));
    Matrix mXMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(-1, 0));
    Matrix mYPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(-1, 0));
    Matrix mYMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(1, 0));

    @Override
    public void load(Context context) {
        _texture = TextureFactory.CreateTexture(context, _resources.getTexture(), true);
        _vertexBuffer.createVBO();
    }

    @Override
    public void render(Camera camera) {
        GLES20.glDepthMask(false);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        _vertexBuffer.apply(Shader.SkyBoxProcedural.Attributes);
        Shader.SkyBoxProcedural.setView(camera.getViewTransform());
        Shader.SkyBoxProcedural.setProjection(camera.getProjectionTransform());
        Shader.SkyBoxProcedural.setModel(Matrix.Identity);
        Shader.SkyBoxProcedural.setTexture(_texture);
        Shader.SkyBoxProcedural.apply();
        Shader.SkyBoxProcedural.draw(_indexBuffer );
    }
}
