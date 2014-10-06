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
    private static final int bufferSize = 1024*1024*4;

    public TextureRaw (final Context context, TextureRawResource resource, boolean wrap) {
        final int[] textureHandle = new int[2];
        GLES20.glGenTextures(2, textureHandle, 0);
        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
            InputStream inStream = context.getResources().openRawResource(resource.getContentId());
            byte[] buffer = new byte[bufferSize];
            //ByteBuffer byteData = ByteBuffer.allocate(resource.Width * resource.Height * 4);
            int width = resource.Width;
            int height = resource.Height;
            ByteBuffer[] byteData = new  ByteBuffer[11];
            for (int i = 0; i <byteData.length; i++) {
                byteData[i] = ByteBuffer.allocate(width * height * 4);
                width /= 2;
                height /= 2;
            }

            try {
                int read;
                for (int i = 0; i <byteData.length; i++) {
                    int bytesLeft = byteData[i].capacity();
                    while (bytesLeft != 0) {
                        read = inStream.read(buffer, 0, bytesLeft);
                        if (read == -1)
                            break;
                        byteData[i].put(buffer, 0, read);
                        bytesLeft -= read;
                    }
                }
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            width = resource.Width;
            height = resource.Height;
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
