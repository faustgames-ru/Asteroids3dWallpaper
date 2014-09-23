package com.FaustGames.Core.Content;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.opengl.ETC1Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TextureImageResource extends TextureResource {
    public static int MaxSize = 2048;
    public static int WidthPOT = 2048;
    public static int HeightPOT = 2048;
    int[] mMipMaps;
    Bitmap[] mMipMapsTextures;
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

    public TextureImageResource(Context context, String idName, boolean limitSize){
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
        mMipMapsTextures = new Bitmap[mipMaps.size()];
        for (int i = 0; i < mMipMaps.length; i++)
        {
            mMipMaps[i] = mipMaps.get(i);
            int resourceId = mMipMaps[i];
            InputStream input = context.getResources().openRawResource(resourceId);
            mMipMapsTextures[i] = BitmapFactory.decodeStream(input);
            try {
                input.close();
            } catch (IOException e) {
                // ignore exception thrown from close.
            }
        }
    }

    public Bitmap[] getMipMapsTextures(){
        return mMipMapsTextures;
    }

    @Override
    public int getContentId() {
        return mMipMaps[0];
    }
}
