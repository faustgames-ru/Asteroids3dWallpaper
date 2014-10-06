package com.FaustGames.Core.Rendering.Textures;

import android.content.Context;
import com.FaustGames.Core.Content.*;

public class TextureFactory {
    public static  Texture CreateTexture(Context context, TextureResource textureResource, boolean wrap) {

        if (textureResource instanceof TextureMapResource)
            return new TextureETC1(context, (TextureMapResource)textureResource, wrap);
        if (textureResource instanceof TextureImageResource)
            return new TextureUncompressed(context, (TextureImageResource)textureResource, wrap);
        if (textureResource instanceof TextureDrawableResource)
            return new TextureDrawable(context, (TextureDrawableResource)textureResource, wrap);
        if (textureResource instanceof TextureRawResource)
            return new TextureRaw(context, (TextureRawResource)textureResource, wrap);

        return null;
    }
}
