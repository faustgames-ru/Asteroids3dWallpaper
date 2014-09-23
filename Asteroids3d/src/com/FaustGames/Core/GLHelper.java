package com.FaustGames.Core;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

public class GLHelper {
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            String errorText = GLU.gluErrorString(error);
            Log.e("GLHelper", glOperation + ": glError " + errorText);
            //throw new RuntimeException(glOperation + ": glError " + errorText);
        }
    }
}
