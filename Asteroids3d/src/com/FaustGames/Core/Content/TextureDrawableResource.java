package com.FaustGames.Core.Content;

public class TextureDrawableResource extends TextureResource {
    public int Id2048;
    public int Id1024;
    public int Id;
    public TextureDrawableResource(int id2048, int id1024){
        Id2048= id2048;
        Id1024= id1024;
        if (TextureMapResource.MaxSize < 2048)
            Id = id1024;
        else
            Id = id2048;
    }
    @Override
    public int getContentId() {
        return Id;
    }
}
