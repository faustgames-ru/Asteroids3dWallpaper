package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.Rendering.Effects.Attributes.*;
import com.FaustGames.Core.Rendering.Textures.Texture;

public class EffectSpecularBump extends Effect {

    int mNormalMatrixParameterIndex;
    int mNormalModelMatrixParameterIndex;
    int mModelMatrixParameterIndex;
    int mViewMatrixParameterIndex;
    int mProjectionMatrixParameterIndex;
    int mLightPositionParameterIndex;
    //int mSpecularParameterIndex;
    int mDiffuseGlowParameterIndex;
    int mNormalSpecularParameterIndex;
    //int mGlowParameterIndex;
    int mSpecularLightParameterIndex;
    int mDiffuseLightParameterIndex;
    int mAmbientLightParameterIndex;
    int mTimeParameterIndex;
    int mPositionAttributeIndex;
    int mNormalAttributeIndex;
    int mTangentAttributeIndex;
    int mBiNormalAttributeIndex;
    int mTexturePositionAttributeIndex;
    int mTransformIndexAttributeIndex;

    public EffectSpecularBump() {
        super("", "",
            new String[]{
                    "u_NormalMatrix",
                    "u_NormalModelMatrix",
                    "u_ModelMatrix",
                    "u_ViewMatrix",
                    "u_ProjectionMatrix",
                    "u_LightPosition",
                    //"u_Specular",
                    "u_DiffuseGlow",
                    "u_NormalSpecular",
                    //"u_Glow",
                    "u_SpecularLight",
                    "u_DiffuseLight",
                    "u_AmbientLight",
                    "u_GlowLevel",
                    "u_GlowColor",
                    "u_Eye",
                    "u_FogColor"
            },
            new String[]{
                    "a_Position",
                    "a_Normal",
                    "a_Tangent",
                    "a_BiNormal",
                    "a_TexturePosition",
                    "a_TransformIndex"
            });
        mNormalMatrixParameterIndex = 0;
        mNormalModelMatrixParameterIndex = 1;
        mModelMatrixParameterIndex = 2;
        mViewMatrixParameterIndex = 3;
        mProjectionMatrixParameterIndex = 4;
        mLightPositionParameterIndex = 5;
        mDiffuseGlowParameterIndex = 6;
        mNormalSpecularParameterIndex = 7;
        mSpecularLightParameterIndex = 8;
        mDiffuseLightParameterIndex = 9;
        mAmbientLightParameterIndex = 10;
        mTimeParameterIndex = 11;

        mPositionAttributeIndex = 0;
        mNormalAttributeIndex = 1;
        mTangentAttributeIndex = 2;
        mBiNormalAttributeIndex = 3;
        mTexturePositionAttributeIndex = 4;
        mTransformIndexAttributeIndex = 5;
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        super.onCreate(context);
        setCode(
                loadFromRaw(context, R.raw.effect_specular_bump_vertex),
                loadFromRaw(context, R.raw.effect_specular_bump_fragment));
    }

    public void setGlowLevel(float value){
        Parameters.get(mTimeParameterIndex).setFloat(value);
    }
    public void setGlowColor(Color value){
        Parameters.get(12).setColor(value);
    }
    public void setNormalTransform(Matrix3 matrix){
        Parameters.get(mNormalMatrixParameterIndex).setMatrix(matrix);
    }

    public void setNormalModelTransforms(Matrix3[] matrix){
        Parameters.get(mNormalModelMatrixParameterIndex).setMatrixArray(matrix);
    }

    public void setViewTransform(Matrix matrix){
        Parameters.get(mViewMatrixParameterIndex).setMatrix(matrix);
    }

    public void setModelTransforms(Matrix[] matrix){
        Parameters.get(mModelMatrixParameterIndex).setMatrixArray(matrix);
    }

    public void setProjectionTransform(Matrix matrix){
        Parameters.get(mProjectionMatrixParameterIndex).setMatrix(matrix);
    }

    public void setLight(Vertex position, Color specular, Color diffuse, Color ambient){
        Parameters.get(mLightPositionParameterIndex).setPosition(position);
        Parameters.get(mSpecularLightParameterIndex).setColor(specular);
        Parameters.get(mDiffuseLightParameterIndex).setColor(diffuse);
        Parameters.get(mAmbientLightParameterIndex).setColor(ambient);
    }

    public void setDiffuseGlowMap(Texture texture){
        Parameters.get(mDiffuseGlowParameterIndex).setTexture(1, texture);
    }

    public void setNormalSpecularMap(Texture texture){
        Parameters.get(mNormalSpecularParameterIndex).setTexture(2, texture);
    }

    public AttributeBufferPosition createPositionBuffer(){
        return (AttributeBufferPosition)Attributes.get(mPositionAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferPosition createNormalBuffer(){
        return (AttributeBufferPosition)Attributes.get(mNormalAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferPosition createTangentBuffer(){
        return (AttributeBufferPosition)Attributes.get(mTangentAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferPosition createBiNormalBuffer(){
        return (AttributeBufferPosition)Attributes.get(mBiNormalAttributeIndex).CreateBuffer(AttributeType.Position);
    }

    public AttributeBufferTexturePosition createTexturePositionBuffer(){
        return (AttributeBufferTexturePosition)Attributes.get(mTexturePositionAttributeIndex).CreateBuffer(AttributeType.TexturePosition);
    }

    public AttributeBufferFloat createTransformIndexBuffer(){
        return (AttributeBufferFloat)Attributes.get(mTransformIndexAttributeIndex).CreateBuffer(AttributeType.Float);
    }

    public void setEye(Vertex v) {
        Parameters.get(13).setPosition(v);
    }
    public void setFogColor(Color value) {
        Parameters.get(14).setColor(value);
    }

    public AttributesBufferMesh createMeshBuffer(){
        return new AttributesBufferMesh(
                Attributes.get(mPositionAttributeIndex),
                Attributes.get(mNormalAttributeIndex),
                Attributes.get(mBiNormalAttributeIndex),
                Attributes.get(mTangentAttributeIndex),
                Attributes.get(mTexturePositionAttributeIndex),
                Attributes.get(mTransformIndexAttributeIndex)
        );
    }
}
