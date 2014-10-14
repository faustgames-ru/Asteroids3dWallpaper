package com.FaustGames.Core.Content;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TextureRawResource extends TextureResource {
    public int Width = 512;
    public int Height = 512;
    public int Levels = 10;
    public boolean AllowCache = true;

    int _contentId;
    String _assetsId;
    ByteBuffer[] _byteData = null;

    public ByteBuffer[] getByteData(Context context) throws IOException {
        if (_byteData != null)
            return _byteData;
        InputStream inStream;
        if (_assetsId != null) {
            inStream = context.getAssets().open(_assetsId);
        }
        else{
            inStream = context.getResources().openRawResource(getContentId());
        }
        byte[] buffer = new byte[bufferSize];
        int width = Width;
        int height = Height;
        ByteBuffer[] byteData = new  ByteBuffer[Levels];
        for (int i = 0; i <byteData.length; i++) {
            byteData[i] = ByteBuffer.allocate(width * height * 4);
            width /= 2;
            height /= 2;
        }

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
        if (AllowCache)
            _byteData = byteData;

        return byteData;
    }
    private static final int bufferSize = 1024*1024*4;

    public TextureRawResource(String assetsId, int levels, int width, int height){
        _assetsId = assetsId;
        Width = width;
        Height = height;
        Levels = levels;
    }
    public TextureRawResource(int id, int levels, int width, int height){
        _contentId = id;
        Width = width;
        Height = height;
        Levels = levels;
    }
    @Override
    public int getContentId() {
        return _contentId;
    }
}
