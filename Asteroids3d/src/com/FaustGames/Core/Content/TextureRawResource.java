package com.FaustGames.Core.Content;

public class TextureRawResource extends TextureResource {
    public int Width = 1024;
    public int Height = 1024;

    int _contentId;

    public TextureRawResource(int id){
        _contentId = id;
    }
    @Override
    public int getContentId() {
        return _contentId;
    }
}
