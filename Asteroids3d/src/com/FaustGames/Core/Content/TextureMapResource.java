package com.FaustGames.Core.Content;

import android.content.Context;
import android.opengl.ETC1Util;
import android.opengl.GLES20;
import com.FaustGames.Core.GLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TextureMapResource extends TextureResource {
    public static int MaxSize = 2048;
    public static int WidthPOT = 2048;
    public static int HeightPOT = 2048;
    int[] mMipMaps;
    ETC1Util.ETC1Texture[] mMipMapsTextures;
    public static String[] sizes = new String[]{
            //"4096x4096",
            "2048x2048",
            "1024x1024",
            "512x512",
            "256x256",
            "128x128",
            "64x64",
            "32x32",
            "16x16",
            "8x8",
            "4x4",
            "2x2",
            "1x1"
    };

    public TextureMapResource(Context context, String idName, boolean limitSize){
        int startSize = 2048;
        int startIndex = 0;

        if  (limitSize)
            while (startSize > MaxSize) {
                startSize /= 2;
                startIndex ++;
            }

        ArrayList<Integer> mipMaps = new ArrayList<Integer>();
        for (int i = startIndex; i < sizes.length; i++)
        {
            int actualId = context.getResources().getIdentifier("raw/"+idName+"_"+sizes[i], null, context.getPackageName());
            if (actualId > 0)
                mipMaps.add(actualId);
        }

        mMipMaps = new int[mipMaps.size()];
        mMipMapsTextures = new ETC1Util.ETC1Texture[mipMaps.size()];
        for (int i = 0; i < mMipMaps.length; i++)
        {
            mMipMaps[i] = mipMaps.get(i);
            int resourceId = mMipMaps[i];
            InputStream input = context.getResources().openRawResource(resourceId);
            try{
                mMipMapsTextures[i] = ETC1Util.createTexture(input);
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
        }
    }

    public int[] getMipMaps(){
        return mMipMaps;
    }

    public ETC1Util.ETC1Texture[] getMipMapsTextures(){
        return mMipMapsTextures;
    }

    @Override
    public int getContentId() {
        return mMipMaps[0];
    }
}
