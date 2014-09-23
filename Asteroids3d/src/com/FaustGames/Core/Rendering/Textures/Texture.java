package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import com.FaustGames.Core.Content.TextureMapResource;

public abstract class Texture {
    public int GetTextureHandler(){ return TextureHandler; };

    protected int TextureHandler;
    public static int StartLevel = 1024;

    protected Texture() {

    }

    protected Texture(final Context context, TextureMapResource resource) {
    }

    public abstract void destroy();
}
