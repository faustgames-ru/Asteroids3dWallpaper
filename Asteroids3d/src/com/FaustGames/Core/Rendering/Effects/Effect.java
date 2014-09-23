package com.FaustGames.Core.Rendering.Effects;

import android.content.Context;
import android.opengl.ETC1Util;
import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.Rendering.Effects.Attributes.Attribute;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBuffer;
import com.FaustGames.Core.Rendering.Effects.Parameters.EffectParameter;
import com.FaustGames.Core.Rendering.IndexBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Effect {
    String mVertexShaderCode;
    String mFragmentShaderCode;
    int mVertexShader;
    int mFragmentShader;
    int mProgram;
    public ArrayList<EffectParameter> Parameters = new ArrayList<EffectParameter>();

    public ArrayList<Attribute> Attributes= new ArrayList<Attribute>();
    public static ArrayList<Effect> Effects = new ArrayList<Effect>();

    public static void Create(Context context) {
        for (int i = 0; i < Effects.size(); i++)
            Effects.get(i).create(context);
    }

    public static void unload() {
        for (int i = 0; i < Effects.size(); i++)
            Effects.get(i).destroy();
    }

    private void destroy() {
        GLES20.glDetachShader(this.mProgram, this.mFragmentShader);
        GLHelper.checkGlError("GLES20.glDetachShader");
        GLES20.glDetachShader(this.mProgram, this.mVertexShader);
        GLHelper.checkGlError("GLES20.glDetachShader");
        GLES20.glDeleteProgram(this.mProgram);
        GLHelper.checkGlError("GLES20.glDeleteProgram");
        GLES20.glDeleteShader(this.mVertexShader);
        GLHelper.checkGlError("GLES20.glDeleteShader");
        GLES20.glDeleteShader(this.mFragmentShader);
        GLHelper.checkGlError("GLES20.glDeleteShader");
    }

    public String loadFromRaw(Context context, int id){
        InputStream input = context.getResources().openRawResource(id);
        try{
            byte[] b = new byte[input.available()];
            input.read(b);
            return new String(b);
        }
        catch(IOException e){
            System.out.println("DEBUG! IOException"+e.getMessage());
        }
        finally{
            try {
                input.close();
            } catch (IOException e) {
                // ignore exception thrown from close.
            }
        }
        return null;
    }

    public Effect(String vertexShader, String fragmentShader, String[] parameters, String[] attributes) {
        Constructor(vertexShader, fragmentShader, parameters, attributes);
    }

    public void onCreate(Context context){

    }
    protected void setCode(String vertexShader, String fragmentShader){
        mVertexShaderCode = vertexShader;
        mFragmentShaderCode = fragmentShader;
    }

    public void create(Context context) {
        onCreate(context);
        mVertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                mVertexShaderCode);
        mFragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                mFragmentShaderCode);

        mProgram = GLES20.glCreateProgram();                // create empty OpenGL Program
        GLHelper.checkGlError("GLES20.glCreateProgram");
        GLES20.glAttachShader(mProgram, mVertexShader);     // add the vertex shader to program
        GLHelper.checkGlError("GLES20.glAttachShader vertexShader");
        GLES20.glAttachShader(mProgram, mFragmentShader);   // add the fragment shader to program
        GLHelper.checkGlError("GLES20.glAttachShader fragmentShader");
        GLES20.glLinkProgram(mProgram);                     // create OpenGL program executables
        GLHelper.checkGlError("GLES20.glLinkProgram");
        GLES20.glUseProgram(mProgram);
        GLHelper.checkGlError("GLES20.glUseProgram");

        for (int i = 0; i < Parameters.size(); i++)
            Parameters.get(i).create(mProgram);
        for (int i = 0; i < Attributes.size(); i++)
            Attributes.get(i).create(mProgram);
    }

    public void apply() {
        GLES20.glUseProgram(mProgram);
        GLHelper.checkGlError("GLES20.glUseProgram");
        for (int i = 0; i < Parameters.size(); i++)
            Parameters.get(i).apply();
    }

    public void applyAttribute(AttributeBuffer attributeBuffer) {
        attributeBuffer.apply(mProgram);
    }

    public void draw(IndexBuffer indexBuffer) {
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.Count,
                GLES20.GL_UNSIGNED_SHORT, indexBuffer.drawListByteBuffer);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.Count,
        //        GLES20.GL_UNSIGNED_SHORT, indexBuffer.drawListBuffer);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, count);
        GLHelper.checkGlError("GLES20.glDrawElements");
    }


    public void drawLines(IndexBuffer indexBuffer) {
        GLES20.glDrawElements(GLES20.GL_LINES, indexBuffer.Count,
                GLES20.GL_UNSIGNED_SHORT, indexBuffer.drawListBuffer);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, count);
        GLHelper.checkGlError("GLES20.glDrawElements");
    }

    private void Constructor(String vertexShader, String fragmentShader, String[] parameters, String[] attributes) {
        mVertexShaderCode = vertexShader;
        mFragmentShaderCode = fragmentShader;
        for (int i = 0; i < parameters.length; i++)
            Parameters.add(new EffectParameter(parameters[i]));
        for (int i = 0; i < attributes.length; i++)
            Attributes.add(new Attribute(attributes[i]));
        Effects.add(this);
    }

    private static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        GLHelper.checkGlError("GLES20.glCreateShader");
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLHelper.checkGlError("GLES20.glShaderSource");
        GLES20.glCompileShader(shader);
        GLHelper.checkGlError("GLES20.glCompileShader");
        return shader;
    }
}
