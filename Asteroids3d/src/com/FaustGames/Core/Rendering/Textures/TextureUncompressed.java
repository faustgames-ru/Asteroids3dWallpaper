package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.FaustGames.Core.Content.TextureImageResource;
import com.FaustGames.Core.Content.TextureResource;
import com.FaustGames.Core.GLHelper;

public class TextureUncompressed extends Texture {
    public TextureUncompressed (final Context context, TextureImageResource resource, boolean wrap) {
        final int[] textureHandle = new int[2];
        GLES20.glGenTextures(2, textureHandle, 0);

        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            Bitmap image;
            try {
                image = resource.getMipMapsTextures()[0];
            } finally {
            }
            if (image != null) {
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, image, 0);
                GLHelper.checkGlError("GLUtils.texImage2D");
            }
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

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
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
