package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import com.FaustGames.Core.Content.TextureDrawableResource;
import com.FaustGames.Core.Content.TextureImageResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.Content.TextureResource;

public class TextureFactory {
    public static  Texture CreateTexture(Context context, TextureResource textureResource, boolean wrap) {

        if (textureResource instanceof TextureMapResource)
            return new TextureETC1(context, (TextureMapResource)textureResource, wrap);
        if (textureResource instanceof TextureImageResource)
            return new TextureUncompressed(context, (TextureImageResource)textureResource, wrap);
        if (textureResource instanceof TextureDrawableResource)
            return new TextureDrawable(context, (TextureDrawableResource)textureResource, wrap);

        return null;
    }
}
