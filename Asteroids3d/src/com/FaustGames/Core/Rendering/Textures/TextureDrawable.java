package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.FaustGames.Core.Content.TextureDrawableResource;
import com.FaustGames.Core.Content.TextureImageResource;
import com.FaustGames.Core.GLHelper;

public class TextureDrawable extends Texture {
    public TextureDrawable (final Context context, TextureDrawableResource resource, boolean wrap) {
        final int[] textureHandle = new int[2];
        GLES20.glGenTextures(2, textureHandle, 0);

        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            Bitmap image;
            try {
                image = BitmapFactory.decodeResource(context.getResources(), resource.getContentId());
            } finally {
            }
            if (image != null) {
                Utils.generateMipMapsForBoundTexture(image);
                //GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, image, 0);
                GLHelper.checkGlError("GLUtils.texImage2D");
                image.recycle();
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

final class Utils {

    private static Matrix yFlipMatrix;

    static {
        yFlipMatrix = new Matrix();
        yFlipMatrix.postScale(-1, 1); // flip Y axis
    }

    public static Bitmap getTextureFromBitmapResource(Context context, int resourceId) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), yFlipMatrix, false);
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public static void generateMipMapsForBoundTexture(Bitmap texture) {
        // generate the full texture (mipmap level 0)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, texture, 0);
        GLHelper.checkGlError("GLUtils.texImage2D");

        Bitmap currentMipmap = texture;

        int width = texture.getWidth();
        int height = texture.getHeight();
        int level = 0;

        boolean reachedLastLevel;
        do {

            // go to next mipmap level
            if (width > 1) width /= 2;
            if (height > 1) height /= 2;
            level++;
            reachedLastLevel = (width == 1 && height == 1);

            // generate next mipmap
            Bitmap mipmap = Bitmap.createScaledBitmap(currentMipmap, width, height, true);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, level, mipmap, 0);
            GLHelper.checkGlError("GLUtils.texImage2D");

            // recycle last mipmap (but don't recycle original texture)
            if (currentMipmap != texture) {
                currentMipmap.recycle();
            }

            // remember last generated mipmap
            currentMipmap = mipmap;

        } while (!reachedLastLevel);

        // once again, recycle last mipmap (but don't recycle original texture)
        if (currentMipmap != texture) {
            currentMipmap.recycle();
        }
    }
}
