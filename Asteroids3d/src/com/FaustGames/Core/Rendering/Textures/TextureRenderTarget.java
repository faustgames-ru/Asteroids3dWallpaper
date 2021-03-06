package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;
import com.FaustGames.Core.ILoadable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class TextureRenderTarget extends Texture implements ILoadable {
    protected int mFrameBuffer;
    //int mDepthBuffer;
    protected int mWidth;
    protected int mHeight;

    public TextureRenderTarget(int width, int height) {
// create the ints for the framebuffer, depth render buffer and texture
        mWidth = width;
        mHeight = height;
    }
    public void resize(int width, int height) {
        mWidth = width;
        mHeight = height;
    }
    @Override
    public void load(Context context){
        int[] fb = new int[1];
        //int[] depthRb = new int[1];
        int[] renderTex = new int[1];

// generate
        GLES20.glGenFramebuffers(1, fb, 0);
        GLHelper.checkGlError("GLES20.glGenFramebuffers");
        //GLES20.glGenRenderbuffers(1, depthRb, 0); // the depth buffer
        //GLHelper.checkGlError("GLES20.glGenRenderbuffers");
        GLES20.glGenTextures(1, renderTex, 0);
        GLHelper.checkGlError("GLES20.glGenTextures");

// generate texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renderTex[0]);
        GLHelper.checkGlError("GLES20.glBindTexture");

// parameters - we have to make sure we clamp the textures to the edges
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLHelper.checkGlError("GLES20.glTexParameteri");
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GLHelper.checkGlError("GLES20.glTexParameteri");
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLHelper.checkGlError("GLES20.glTexParameteri");
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLHelper.checkGlError("GLES20.glTexParameteri");

// create it
// create an empty intbuffer first
        int[] buf = new int[mWidth * mHeight];
        IntBuffer texBuffer = ByteBuffer.allocateDirect(buf.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();

// generate the textures
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, mWidth, mHeight, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, texBuffer);
        GLHelper.checkGlError("GLES20.glTexImage2D");

// create render buffer and bind 16-bit depth buffer
        //GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, depthRb[0]);
        //GLHelper.checkGlError("GLES20.glBindRenderbuffer");
        //GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, mWidth, mHeight);
        //GLHelper.checkGlError("GLES20.glBindRenderbuffer");

        mFrameBuffer = fb[0];
        //mDepthBuffer = depthRb[0];
        TextureHandler = renderTex[0];

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer);
        GLHelper.checkGlError("GLES20.glBindFramebuffer");
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, TextureHandler, 0);
        GLHelper.checkGlError("GLES20.glFramebufferTexture2D");

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void setAsRenderTarget(){
// Bind the framebuffer
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer);
        GLHelper.checkGlError("GLES20.glBindFramebuffer");

        GLES20.glViewport(0, 0, mWidth, mHeight);
        GLHelper.checkGlError("GLES20.glViewport");
// specify texture as color attachment
        //GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, TextureHandler, 0);
        //GLHelper.checkGlError("GLES20.glFramebufferTexture2D");
// attach render buffer as depth buffer
        //GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, mDepthBuffer);
        //GLHelper.checkGlError("GLES20.glFramebufferRenderbuffer");
// check status
        int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
        GLHelper.checkGlError("GLES20.glCheckFramebufferStatus");
        if (status != GLES20.GL_FRAMEBUFFER_COMPLETE)
            return;
// Clear the texture (buffer) and then render as usual...
        //GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT);
        GLHelper.checkGlError("GLES20.glClear");
    }

    public void setDefaultRenderTarget(int width, int height){
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLHelper.checkGlError("GLES20.glBindFramebuffer");
        GLES20.glViewport(0, 0, width, height);
        //GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLHelper.checkGlError("GLES20.glClear");
    }

    @Override
    public void destroy() {
        /// todo: resources cleanup;
    }
}
