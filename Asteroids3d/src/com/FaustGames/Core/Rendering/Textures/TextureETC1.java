package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import android.opengl.ETC1Util;
import android.opengl.GLES10Ext;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.GLHelper;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11Ext;
import java.io.IOException;
import java.io.InputStream;

public class TextureETC1 extends Texture {
    public static boolean isAnisotropicFilterSupported = false;
    public static boolean isAnisotropicFilterChecked = false;

    public TextureETC1(final Context context, TextureMapResource resource, boolean wrap) {
        super(context, resource);

        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);

        int[] max = new int[1];
        GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, max, 0);

        int startLevel = 0;
        //if (max[0] < 4096)
        //    startLevel = 1;
        //else
        if (max[0] < 2048)
            startLevel = 1;

        if (textureHandle[0] != 0){
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            int[] mipMaps = resource.getMipMaps();
            ETC1Util.ETC1Texture[] mipMapsTextures = resource.getMipMapsTextures();

            if (wrap){
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            }
            else{
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            }

            if ((mipMaps.length > 1))
            {
                if (!isAnisotropicFilterChecked){
                    String s = GLES20.glGetString(GLES20.GL_EXTENSIONS);
                    GLHelper.checkGlError("GLES20.glGetString");
                    int index = s.indexOf("GL_EXT_texture_filter_anisotropic");
                    isAnisotropicFilterSupported = (index >= 0);
                    isAnisotropicFilterChecked = true;
                }

                isAnisotropicFilterSupported = false;

                if (isAnisotropicFilterSupported)
                {
                    float[] largest = new float[1];
                    GLES20.glGetFloatv(GLES11Ext.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, largest, 0);
                    GLHelper.checkGlError("GLES20.glGetFloatv");

                    GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_MAX_ANISOTROPY_EXT, largest[0]);
                    GLHelper.checkGlError("GLES20.glTexParameterf");

                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
                    GLHelper.checkGlError("GLES20.glTexParameteri");
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                    GLHelper.checkGlError("GLES20.glTexParameteri");
                }
                else
                {
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
                    GLHelper.checkGlError("GLES20.glTexParameteri");
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                    GLHelper.checkGlError("GLES20.glTexParameteri");
                }
            }
            else
            {
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLHelper.checkGlError("GLES20.glTexParameteri");
            }

            for (int i = startLevel; i < mipMaps.length; i++)
            {
                ETC1Util.loadTexture(GLES20.GL_TEXTURE_2D, i - startLevel, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, mipMapsTextures[i]);
/*
                int resourceId = mipMaps[i];
                InputStream input = context.getResources().openRawResource(resourceId);
                try{
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
*/
                GLHelper.checkGlError("ETC1Util.loadTexture");
            }
        }
        else
            throw new RuntimeException("Error loading texture.");
        TextureHandler = textureHandle[0];
    }

    @Override
    public void destroy() {
        GLES20.glDeleteTextures(1, new int[] { TextureHandler }, 0);
    }
}
