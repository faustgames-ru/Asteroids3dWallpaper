package com.FaustGames.Core.Rendering.Effects.Parameters;

import android.opengl.GLES20;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Color;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.Rendering.Textures.Texture;

enum EffectParameterType { Color, Texture, TextureArray, Matrix, Matrix3, Position, Float, MatrixArray, Matrix3Array }

public class EffectParameter {
    public String Name;
    public Object Value;
    public int parameterHandler;
    EffectParameterType Type;
    int mTextureIndex;
    public static int[] TexturesMap = new int[] {
        GLES20.GL_TEXTURE0,
        GLES20.GL_TEXTURE1,
        GLES20.GL_TEXTURE2,
        GLES20.GL_TEXTURE3,
        GLES20.GL_TEXTURE4,
        GLES20.GL_TEXTURE5,
        GLES20.GL_TEXTURE6,
        GLES20.GL_TEXTURE7,
    };
    public EffectParameter(String name) {
        Name = name;
    }

    public void create(int program) {
        parameterHandler = GLES20.glGetUniformLocation(program, Name);
        GLHelper.checkGlError("GLES20.glGetUniformLocation");
    }

    public void apply() {
        if (Type == EffectParameterType.Color)
        {
            GLES20.glUniform4fv(parameterHandler, 1, ((Color)Value).toArray(), 0);
            GLHelper.checkGlError("GLES20.glUniform4fv");
        }
        if (Type == EffectParameterType.Position)
        {
            GLES20.glUniform3fv(parameterHandler, 1, ((Vertex) Value).toArray(), 0);
            GLHelper.checkGlError("GLES20.glUniform3fv");
        }
        if (Type == EffectParameterType.Float)
        {
            GLES20.glUniform1f(parameterHandler, ((Float) Value));
            GLHelper.checkGlError("GLES20.glUniform1f");
        }
        if (Type == EffectParameterType.Texture)
        {
            Texture texture = (Texture)Value;
            GLES20.glActiveTexture(TexturesMap[mTextureIndex]);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.GetTextureHandler());
            GLES20.glUniform1i(parameterHandler, mTextureIndex);
            GLHelper.checkGlError("GLES20.glUniform1i");
        }
        if (Type == EffectParameterType.TextureArray)
        {
            Texture[] textures = (Texture[])Value;
            int[] params = new int[textures.length];
            for (int i = 0; i < textures.length; i++)
            {
                GLES20.glActiveTexture(TexturesMap[mTextureIndex + i]);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[i].GetTextureHandler());
                params[i] = mTextureIndex + i;
            }

            GLES20.glUniform1iv(parameterHandler, params.length, params, 0);
            GLHelper.checkGlError("GLES20.glUniform1i");
        }
        if (Type == EffectParameterType.Matrix)
        {
            Matrix matrix= (Matrix)Value;
            GLES20.glUniformMatrix4fv(parameterHandler, 1, false, matrix.values, 0);
            GLHelper.checkGlError("GLES20.glUniform4fv");
        }
        if (Type == EffectParameterType.Matrix3)
        {
            Matrix3 matrix= (Matrix3)Value;
            GLES20.glUniformMatrix3fv(parameterHandler, 1, false, matrix.values, 0);
            GLHelper.checkGlError("GLES20.glUniform3fv");
        }

        if (Type == EffectParameterType.MatrixArray)
        {
            Matrix[] matrix = (Matrix[])Value;
            float[] values = new float[matrix.length * 16];
            int k = 0;
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < 16; j++){
                    values[k] = matrix[matrix[i].o + i].values[j];
                    k++;
                }

            GLES20.glUniformMatrix4fv(parameterHandler, matrix.length, false, values, 0);
            GLHelper.checkGlError("GLES20.glUniform4fv");
        }
        if (Type == EffectParameterType.Matrix3Array)
        {
            Matrix3[] matrix= (Matrix3[])Value;
            float[] values = new float[matrix.length * 9];
            int k = 0;
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix[i].values.length; j++){
                    values[k] = matrix[i].values[j];
                    k++;
                }
            GLES20.glUniformMatrix3fv(parameterHandler, matrix.length, false, values, 0);
            GLHelper.checkGlError("GLES20.glUniform3fv");
        }

    }

    public void setColor(Color color) {
        Type = EffectParameterType.Color;
        Value = color;
    }

    public void setPosition(Vertex v) {
        Type = EffectParameterType.Position;
        Value = v;
    }

    public void setFloat(float v) {
        Type = EffectParameterType.Float;
        Value = v;
    }

    public int setTextures(int index, Texture[] textures) {
        mTextureIndex = index;
        Type = EffectParameterType.TextureArray;
        Value = textures;
        return index + textures.length;
    }

    public int setTexture(int index, Texture texture) {
        mTextureIndex = index;
        Type = EffectParameterType.Texture;
        Value = texture;
        return index + 1;
    }


    public void setMatrix(Matrix matrix) {
        Type = EffectParameterType.Matrix;
        Value = matrix;
    }

    public void setMatrix(Matrix3 matrix) {
        Type = EffectParameterType.Matrix3;
        Value = matrix;
    }

    public void setMatrixArray(Matrix[] matrix) {
        Type = EffectParameterType.MatrixArray;
        Value = matrix;
    }

    public void setMatrixArray(Matrix3[] matrix) {
        Type = EffectParameterType.Matrix3Array;
        Value = matrix;
    }}
