package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.FaustGames.Core.Content.TextureImageResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.GLHelper;

import java.io.IOException;
import java.io.InputStream;
/*
public class TextureImage extends Texture {
    public static boolean isAnisotropicFilterSupported = false;
    public static boolean isAnisotropicFilterChecked = false;
    public int LowQualityHandler;
    public int HighQualityHandler;
    public boolean Wrap;
    public TextureImageResource Resource;

    public TextureImage(final Context context, TextureImageResource resource, boolean wrap, boolean initWithSmall) {
        Resource = resource;
        Wrap = wrap;
        final int[] textureHandle = new int[2];
        GLES20.glGenTextures(2, textureHandle, 0);

        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            Bitmap image;
            try {
                image = BitmapFactory.decodeResource(context.getResources(), initWithSmall?resource.IdSmall : resource.LowQualityImageId);
                //image = BitmapFactory.decodeResource(context.getResources(), resource.Id);
            } finally {
            }
            if (image != null) {
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, image, 0);
                //Utils.generateMipMapsForBoundTexture(image);
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

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLHelper.checkGlError("GLES20.glTexParameteri");
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLHelper.checkGlError("GLES20.glTexParameteri");
        } else {
            throw new RuntimeException("Error loading texture.");
        }
        TextureHandler = LowQualityHandler = textureHandle[0];
        HighQualityHandler = textureHandle[1];
    }

    public void LoadLargeTextureImage(final Context context, boolean apply) {
        final int[] textureHandle = new int[]{HighQualityHandler};
        //GLES20.glGenTextures(1, textureHandle, 0);
        //GLHelper.checkGlError("GLES20.glGenTextures");

        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            Bitmap image;
            try {
                BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                //bmpOptions.inPreferredConfig = Bitmap.Config.RGB_565;
                image = BitmapFactory.decodeResource(context.getResources(), TextureMapResource.MaxSize <= 1024? Resource.IdSmall: Resource.Id, bmpOptions);
            } finally {
            }
            if (image != null) {
                Utils.generateMipMapsForBoundTexture(image);
                GLHelper.checkGlError("GLUtils.texImage2D");
                image.recycle();
            }


            if (Wrap) {
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

            if (!isAnisotropicFilterChecked) {
                String s = GLES20.glGetString(GLES20.GL_EXTENSIONS);
                GLHelper.checkGlError("GLES20.glGetString");
                int index = s == null?-1:s.indexOf("GL_EXT_texture_filter_anisotropic");
                isAnisotropicFilterSupported = (index >= 0);
                isAnisotropicFilterChecked = true;
            }

            //isAnisotropicFilterSupported = false;

            if (isAnisotropicFilterSupported) {
                float[] largest = new float[1];
                GLES20.glGetFloatv(GLES11Ext.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, largest, 0);
                GLHelper.checkGlError("GLES20.glGetFloatv");

                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_MAX_ANISOTROPY_EXT, largest[0]);
                GLHelper.checkGlError("GLES20.glTexParameterf");

                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            } else {
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            }
        } else {
            throw new RuntimeException("Error loading texture.");
        }
        HighQualityHandler = textureHandle[0];
        if (apply)
            ApplyLargeImage();
    }

    @Override
    public void destroy() {
        if(HighQualityHandler != 0)
        {
            GLES20.glDeleteTextures(1, new int[] { HighQualityHandler }, 0);
            GLHelper.checkGlError("GLES20.glDeleteTextures");
        }
        GLES20.glDeleteTextures(1, new int[] { TextureHandler }, 0);
        GLHelper.checkGlError("GLES20.glDeleteTextures");
    }
    public void ApplyLargeImage() {
        TextureHandler = HighQualityHandler;
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
        }
        finally  {
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
            if (currentMipmap != texture)
            {
                currentMipmap.recycle();
            }

            // remember last generated mipmap
            currentMipmap = mipmap;

        } while (!reachedLastLevel);

        // once again, recycle last mipmap (but don't recycle original texture)
        if (currentMipmap != texture)
        {
            currentMipmap.recycle();
        }
    }
}

*/