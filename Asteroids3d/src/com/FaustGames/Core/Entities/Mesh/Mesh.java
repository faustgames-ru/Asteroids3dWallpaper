package com.FaustGames.Core.Entities.Mesh;

import android.content.Context;
import com.FaustGames.Core.*;
import com.FaustGames.Core.Content.MeshGeometryResource;
import com.FaustGames.Core.Content.MeshMapsResource;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.Entities.Transforms.Rotation;
import com.FaustGames.Core.Entities.Transforms.Translation;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferFloat;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferTexturePosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.Position;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Mesh implements IRenderable, ILoadable, IUpdatable {

    ArrayList<MeshVertex> mVertexes = new ArrayList<MeshVertex>();
    ArrayList<MeshTriangle> mTriangles = new ArrayList<MeshTriangle>();
    MeshMapsResource mResources;
    MeshGeometryResource mGeometryResource;

    public Mesh clone(){
        Mesh result = new Mesh();
        result.mVertexes = mVertexes;
        result.mTriangles = mTriangles;
        result.mResources = mResources;
        result.mGeometryResource = mGeometryResource;
        return result;
    }

    private Mesh(){

    }

    public Mesh(MeshMapsResource resources, MeshGeometryResource geometryResource, float scale, float textureScale) {
        mResources = resources;
        mGeometryResource = geometryResource;

        geometryResource.Buffer.position(0);
        geometryResource.Buffer.order(ByteOrder.LITTLE_ENDIAN);
        loadData(geometryResource.Buffer, scale, textureScale);
    }

    public void loadData(ByteBuffer stream, float scale, float textureScale){
        int verticesCount = stream.getInt();
        MeshVertex[] vertices = new MeshVertex[verticesCount];
        Vertex center = Vertex.Empty.clone();
        for (int i = 0; i < verticesCount; i++){
            float[] data = new float[14];
            int index = stream.getInt();

            float x = data[0] = stream.getFloat() * scale;
            float y = data[1] = stream.getFloat() * scale;
            float z = data[2] = stream.getFloat() * scale;

            center.setX(center.getX() + x / verticesCount);
            center.setY(center.getY() + y / verticesCount);
            center.setZ(center.getZ() + z / verticesCount);

            float tx = data[12] = stream.getFloat() * textureScale;
            float ty = data[13] = stream.getFloat() * textureScale;
            //float tz = stream.getFloat();

            float nx = data[3] = stream.getFloat();
            float ny = data[4] = stream.getFloat();
            float nz = data[5] = stream.getFloat();

            float bnx = data[9] = stream.getFloat();
            float bny = data[10] = stream.getFloat();
            float bnz = data[11] = stream.getFloat();

            float tnx = data[6] = stream.getFloat();
            float tny = data[7] = stream.getFloat();
            float tnz = data[8] = stream.getFloat();

            vertices[index] = new MeshVertex(data);
            /*
            vertices[index] = new MeshVertex(x, y, z, tx, ty);
            vertices[index].mNormal = new Vertex(nx, ny, nz);
            vertices[index].mTangent = new Vertex(bnx, bny, bnz);
            vertices[index].mBiNormal = new Vertex(tnx, tny, tnz);
            */
        }
        int trianglesCount = stream.getInt();

        for (int i = 0; i < verticesCount; i++){
            vertices[i].index = mVertexes.size();
            vertices[i].mPosition.setX(vertices[i].mPosition.getX() - center.getX());
            vertices[i].mPosition.setY(vertices[i].mPosition.getY() - center.getY());
            vertices[i].mPosition.setZ(vertices[i].mPosition.getZ() - center.getZ());
            mVertexes.add(vertices[i]);
        }

        for (int i = 0; i < trianglesCount; i++){
            int a = stream.getInt();
            int b = stream.getInt();
            int c = stream.getInt();
            addTriangle(vertices[a], vertices[c], vertices[b]);
        }
        //build(false);
    }

    public void build(boolean generateNormals){
        for (MeshVertex v: mVertexes)
            v.clearTriangles();
        for (MeshTriangle t: mTriangles)
            t.build();
        for (MeshVertex v: mVertexes)
            v.build(generateNormals);
    }

    public void clear(){
        mVertexes.clear();
        mTriangles.clear();
    }

    public MeshTriangle addTriangle(MeshVertex a, MeshVertex b, MeshVertex c){
        MeshTriangle result = new MeshTriangle(a, b, c);
        mTriangles.add(result);
        return result;
    }

    public MeshVertex addVertex(float x, float y, float z, float tx, float ty){
        MeshVertex result = new MeshVertex(x, y, z, tx, ty);
        result.index = mVertexes.size();
        mVertexes.add(result);
        return result;
    }

    public MeshVertex addVertex(Vertex position, float tx, float ty){
        return addVertex(position.getX(), position.getY(), position.getZ(), tx, ty);
    }

    public MeshVertex addVertex(Vertex position, Vertex texturePosition){
        return addVertex(position, texturePosition.getX(), texturePosition.getY());
    }

    @Override
    public void render(Camera camera) {
        Shader.SpecularBump.setGlowLevel(0.5f + MathF.abs(mTime) * 0.5f);
        Shader.SpecularBump.setGlowColor(ColorTheme.Default.Glow);
        Shader.SpecularBump.setSpecularMap(mSpecular);
        Shader.SpecularBump.setDiffuseMap(mDiffuse);
        Shader.SpecularBump.setNormalMap(mNormal);
        Shader.SpecularBump.setGlowMap(mGlow);

        Shader.SpecularBump.setProjectionTransform(camera.getProjectionTransform());
        Shader.SpecularBump.setViewTransform(camera.getViewTransform());
        Shader.SpecularBump.setModelTransforms(new Matrix[]{mTransform});
        Shader.SpecularBump.setNormalModelTransforms(new Matrix3[]{mNormalTransform});
        Shader.SpecularBump.setNormalTransform(camera.getNormal());

        Shader.SpecularBump.setLight(mLight, Color.White, Color.White, Color.Black);
        Shader.SpecularBump.apply();
        Shader.SpecularBump.applyAttribute(mMeshPositions);
        Shader.SpecularBump.applyAttribute(mMeshPositionsNormal);
        Shader.SpecularBump.applyAttribute(mMeshPositionsTangent);
        Shader.SpecularBump.applyAttribute(mMeshPositionsBiNormal);
        Shader.SpecularBump.applyAttribute(mMeshPositionsTexture);
        Shader.SpecularBump.applyAttribute(mMeshTransformIndex);
        Shader.SpecularBump.draw(mMeshIndexBuffer);

        //renderDebug(camera);
    }

    public void create(Context context) {
        mMeshPositions = Shader.SpecularBump.createPositionBuffer();
        mMeshPositionsNormal = Shader.SpecularBump.createNormalBuffer();
        mMeshPositionsTangent = Shader.SpecularBump.createTangentBuffer();
        mMeshPositionsBiNormal = Shader.SpecularBump.createBiNormalBuffer();
        mMeshPositionsTexture = Shader.SpecularBump.createTexturePositionBuffer();
        mMeshTransformIndex = Shader.SpecularBump.createTransformIndexBuffer();

        PositionTexture[] positions = new PositionTexture[mVertexes.size()];
        IPosition[] normals = new IPosition[mVertexes.size()];
        IPosition[] tangents = new IPosition[mVertexes.size()];
        IPosition[] biNormals = new IPosition[mVertexes.size()];
        float[] transforms = new float[mVertexes.size()];

        for (int i = 0; i < mVertexes.size(); i++){
            positions[i] = new PositionTexture(mVertexes.get(i).getPosition(), mVertexes.get(i).getTexturePosition());
            normals[i] = new Position(mVertexes.get(i).getNormal());
            tangents[i] = new Position(mVertexes.get(i).getTangent());
            biNormals[i] = new Position(mVertexes.get(i).getBiNormal());
            transforms[i] = 0;
        }

        mMeshPositions.setValues(positions);
        mMeshPositionsTexture.setValues(positions);

        mMeshPositionsNormal.setValues(normals);
        mMeshPositionsTangent.setValues(tangents);
        mMeshPositionsBiNormal.setValues(biNormals);
        mMeshTransformIndex.setValues(transforms);

        short[] indices = new short[mTriangles.size() * 3];
        for (int i = 0; i < mTriangles.size(); i++){
            indices[i * 3 + 0] = (short)mTriangles.get(i).getA().index;
            indices[i * 3 + 1] = (short)mTriangles.get(i).getB().index;
            indices[i * 3 + 2] = (short)mTriangles.get(i).getC().index;
        }
        mMeshIndexBuffer = new IndexBuffer(indices);
    }

    @Override
    public void load(Context context) {
        mDiffuse = TextureFactory.CreateTexture(context, mResources.Diffuse, false);
        mSpecular = TextureFactory.CreateTexture(context, mResources.Specular, false);
        mNormal = TextureFactory.CreateTexture(context, mResources.Bump, false);
        mGlow =  TextureFactory.CreateTexture(context, mResources.Glow, false);
        //loadDebug(context);
    }

    IndexBuffer mIndexBuffer;
    AttributeBufferPosition mPositionsNormal;
    AttributeBufferPosition mPositionsTangent;
    AttributeBufferPosition mPositionsBiNormal;

    IndexBuffer mMeshIndexBuffer;
    AttributeBufferPosition mMeshPositions;
    AttributeBufferPosition mMeshPositionsNormal;
    AttributeBufferPosition mMeshPositionsTangent;
    AttributeBufferPosition mMeshPositionsBiNormal;
    AttributeBufferTexturePosition mMeshPositionsTexture;
    AttributeBufferFloat mMeshTransformIndex;

    Texture mDiffuse;
    Texture mSpecular;
    Texture mGlow;
    Texture mNormal;
    Translation mTranslation= new Translation();
    Rotation mRotation = new Rotation();
    Matrix3 mNormalTransform = Matrix3.Identity;

    Matrix mTransform = Matrix.Identity;
    Vertex mLightSource = new Vertex(-1, 1, 0);
    Vertex mLight = new Vertex(-1, 1, -0.25f);
    float mTime = 0;

    public Vertex Axis = new Vertex(1, 1, 0);
    public float velocity = 0.2f;
    public float rotationVelocity = 0.2f;

    //public boolean rotate = false;
    public int rotateAxis = -1;

    @Override
    public void update(float timeDelta) {

        mTime += timeDelta;
        while (mTime > 1)
            mTime -= 2;

        Vertex pos = mTranslation.getPosition();
        pos.setX(pos.getX() + velocity * timeDelta);
        while (pos.getX() > 8)
            pos.setX(pos.getX() - 16);
        mTranslation.setPosition(pos);

        if (rotateAxis == 3) {
            mRotation.rotate(Axis, rotationVelocity * timeDelta);
            mTransform = Matrix.Multiply(mRotation.getMatrix(), mTranslation.getMatrix());
        }
        else if (rotateAxis == 0){
            mRotation.rotateX(rotationVelocity * timeDelta);
            mTransform = Matrix.Multiply(mRotation.getMatrix(), mTranslation.getMatrix());
        }
        else if (rotateAxis == 1){
            mRotation.rotateY(rotationVelocity * timeDelta);
            mTransform = Matrix.Multiply(mRotation.getMatrix(), mTranslation.getMatrix());
        }
        else if (rotateAxis == 2){
            mRotation.rotateZ(rotationVelocity * timeDelta);
            mTransform = Matrix.Multiply(mRotation.getMatrix(), mTranslation.getMatrix());
        }
        else{
            mTransform = mTranslation.getMatrix(); //Matrix.Multiply(mRotation.getMatrix(), mTranslation.getMatrix());
        }
        //mLight = mRotation.getMatrix().transform(mLightSource);
        mNormalTransform = new Matrix3(
                mTransform.getXX(), mTransform.getXY(), mTransform.getXZ(),
                mTransform.getYX(), mTransform.getYY(), mTransform.getYZ(),
                mTransform.getZX(), mTransform.getZY(), mTransform.getZZ());

    }

    public Translation getTranslation(){return mTranslation;}

    public Matrix getTransform() {
        return mTransform;
    }

    public Matrix3 getNormalTransform() { return mNormalTransform; }

    public MeshMapsResource getResources() {
        return mResources;
    }
}
