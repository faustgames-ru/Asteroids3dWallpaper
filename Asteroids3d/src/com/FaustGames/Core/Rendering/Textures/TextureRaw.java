package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.FaustGames.Core.Content.TextureImageResource;
import com.FaustGames.Core.Content.TextureRawResource;
import com.FaustGames.Core.GLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TextureRaw extends Texture {

    public TextureRaw (final Context context, TextureRawResource resource, boolean wrap) {
        final int[] textureHandle = new int[2];
        GLES20.glGenTextures(2, textureHandle, 0);
        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            ByteBuffer[] byteData = new ByteBuffer[0];
            try {
                byteData = resource.getByteData(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(byteData == null) return;

            int width = resource.Width;
            int height = resource.Height;
            int level = 0;
            boolean reachedLastLevel;
            do {
                byteData[level].position(0);
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, level, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, byteData[level]);
                GLHelper.checkGlError("GLES20.glTexImage2D");
                reachedLastLevel = (width == 1 && height == 1);
                if (width > 1) width /= 2;
                if (height > 1) height /= 2;
                level++;
            } while (!reachedLastLevel);


            if (wrap) {
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            } else {
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            }

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            GLHelper.checkGlError("GLES20.glTexParameteri");
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLHelper.checkGlError("GLES20.glTexParameteri");
        } else {
            throw new RuntimeException("Error loading texture.");
        }
        TextureHandler = textureHandle[0];
    }
    @Override
    public void destroy() {
        GLES20.glDeleteTextures(1, new int[]{TextureHandler}, 0);
    }
}
