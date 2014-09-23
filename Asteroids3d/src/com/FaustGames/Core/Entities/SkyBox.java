package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.ColorTheme;
import com.FaustGames.Core.Content.SkyBoxResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferFloat;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferPosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeBufferTexturePosition;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IPositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.Position;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTexture;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.PositionTextureIndex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferSkybox;
import com.FaustGames.Core.Rendering.IndexBuffer;
import com.FaustGames.Core.Rendering.Textures.Texture;
import com.FaustGames.Core.Rendering.Textures.TextureETC1;
import com.FaustGames.Core.Rendering.Textures.TextureFactory;
import com.FaustGames.Core.Shader;

public class SkyBox implements IRenderable, ILoadable {
    static final float Distance = 2048;
    double[] mVertices = new double[]{
            -1.0000, -1.0000, 0.0000, 0.0000, 1.0000,
            -1.0000, 1.0000, 0.0000, 0.0000, 0.0000,
            1.0000, -1.0000, 0.0000, 1.0000, 1.0000,
            1.0000, 1.0000, 0.0000, 1.0000, 0.0000,
    };
            /*
    double[] mVertices = new double[]{
            -1.0000, -1.0000, 0.0000, 0.0000, 1.0000,
            -1.0820, -0.8115, 0.0820, 0.0000, 0.9097,
            -1.1547, -0.5774, 0.1547, 0.0000, 0.7952,
            -1.2060, -0.3015, 0.2060, 0.0000, 0.6560,
            -1.2247, 0.0000, 0.2247, 0.0000, 0.5000,
            -1.2060, 0.3015, 0.2060, 0.0000, 0.3440,
            -1.1547, 0.5774, 0.1547, 0.0000, 0.2048,
            -1.0820, 0.8115, 0.0820, 0.0000, 0.0903,
            -1.0000, 1.0000, 0.0000, 0.0000, 0.0000,
            -0.8115, -1.0820, 0.0820, 0.0903, 1.0000,
            -0.8911, -0.8911, 0.1882, 0.0903, 0.9097,
            -0.9649, -0.6433, 0.2865, 0.0903, 0.7952,
            -1.0190, -0.3397, 0.3587, 0.0903, 0.6560,
            -1.0392, 0.0000, 0.3856, 0.0903, 0.5000,
            -1.0190, 0.3397, 0.3587, 0.0903, 0.3440,
            -0.9649, 0.6433, 0.2865, 0.0903, 0.2048,
            -0.8911, 0.8911, 0.1882, 0.0903, 0.0903,
            -0.8115, 1.0820, 0.0820, 0.0903, 0.0000,
            -0.5774, -1.1547, 0.1547, 0.2048, 1.0000,
            -0.6433, -0.9649, 0.2865, 0.2048, 0.9097,
            -0.7071, -0.7071, 0.4142, 0.2048, 0.7952,
            -0.7559, -0.3780, 0.5119, 0.2048, 0.6560,
            -0.7746, 0.0000, 0.5492, 0.2048, 0.5000,
            -0.7559, 0.3780, 0.5119, 0.2048, 0.3440,
            -0.7071, 0.7071, 0.4142, 0.2048, 0.2048,
            -0.6433, 0.9649, 0.2865, 0.2048, 0.0903,
            -0.5774, 1.1547, 0.1547, 0.2048, 0.0000,
            -0.3015, -1.2060, 0.2060, 0.3440, 1.0000,
            -0.3397, -1.0190, 0.3587, 0.3440, 0.9097,
            -0.3780, -0.7559, 0.5119, 0.3440, 0.7952,
            -0.4082, -0.4082, 0.6330, 0.3440, 0.6560,
            -0.4201, 0.0000, 0.6803, 0.3440, 0.5000,
            -0.4082, 0.4082, 0.6330, 0.3440, 0.3440,
            -0.3780, 0.7559, 0.5119, 0.3440, 0.2048,
            -0.3397, 1.0190, 0.3587, 0.3440, 0.0903,
            -0.3015, 1.2060, 0.2060, 0.3440, 0.0000,
            0.0000, -1.2247, 0.2247, 0.5000, 1.0000,
            0.0000, -1.0392, 0.3856, 0.5000, 0.9097,
            0.0000, -0.7746, 0.5492, 0.5000, 0.7952,
            0.0000, -0.4201, 0.6803, 0.5000, 0.6560,
            0.0000, 0.0000, 0.7321, 0.5000, 0.5000,
            0.0000, 0.4201, 0.6803, 0.5000, 0.3440,
            0.0000, 0.7746, 0.5492, 0.5000, 0.2048,
            0.0000, 1.0392, 0.3856, 0.5000, 0.0903,
            0.0000, 1.2247, 0.2247, 0.5000, 0.0000,
            0.3015, -1.2060, 0.2060, 0.6560, 1.0000,
            0.3397, -1.0190, 0.3587, 0.6560, 0.9097,
            0.3780, -0.7559, 0.5119, 0.6560, 0.7952,
            0.4082, -0.4082, 0.6330, 0.6560, 0.6560,
            0.4201, 0.0000, 0.6803, 0.6560, 0.5000,
            0.4082, 0.4082, 0.6330, 0.6560, 0.3440,
            0.3780, 0.7559, 0.5119, 0.6560, 0.2048,
            0.3397, 1.0190, 0.3587, 0.6560, 0.0903,
            0.3015, 1.2060, 0.2060, 0.6560, 0.0000,
            0.5774, -1.1547, 0.1547, 0.7952, 1.0000,
            0.6433, -0.9649, 0.2865, 0.7952, 0.9097,
            0.7071, -0.7071, 0.4142, 0.7952, 0.7952,
            0.7559, -0.3780, 0.5119, 0.7952, 0.6560,
            0.7746, 0.0000, 0.5492, 0.7952, 0.5000,
            0.7559, 0.3780, 0.5119, 0.7952, 0.3440,
            0.7071, 0.7071, 0.4142, 0.7952, 0.2048,
            0.6433, 0.9649, 0.2865, 0.7952, 0.0903,
            0.5774, 1.1547, 0.1547, 0.7952, 0.0000,
            0.8115, -1.0820, 0.0820, 0.9097, 1.0000,
            0.8911, -0.8911, 0.1882, 0.9097, 0.9097,
            0.9649, -0.6433, 0.2865, 0.9097, 0.7952,
            1.0190, -0.3397, 0.3587, 0.9097, 0.6560,
            1.0392, 0.0000, 0.3856, 0.9097, 0.5000,
            1.0190, 0.3397, 0.3587, 0.9097, 0.3440,
            0.9649, 0.6433, 0.2865, 0.9097, 0.2048,
            0.8911, 0.8911, 0.1882, 0.9097, 0.0903,
            0.8115, 1.0820, 0.0820, 0.9097, 0.0000,
            1.0000, -1.0000, 0.0000, 1.0000, 1.0000,
            1.0820, -0.8115, 0.0820, 1.0000, 0.9097,
            1.1547, -0.5774, 0.1547, 1.0000, 0.7952,
            1.2060, -0.3015, 0.2060, 1.0000, 0.6560,
            1.2247, 0.0000, 0.2247, 1.0000, 0.5000,
            1.2060, 0.3015, 0.2060, 1.0000, 0.3440,
            1.1547, 0.5774, 0.1547, 1.0000, 0.2048,
            1.0820, 0.8115, 0.0820, 1.0000, 0.0903,
            1.0000, 1.0000, 0.0000, 1.0000, 0.0000
    };
*/
            IndexBuffer mIndexBuffer = new IndexBuffer(new short[]{
                    0, 1, 3, 0, 3, 2,
            });
    /*
    IndexBuffer mIndexBuffer = new IndexBuffer(new short[] {
            0, 1, 10, 0, 10, 9,
            1, 2, 11, 1, 11, 10,
            2, 3, 12, 2, 12, 11,
            3, 4, 13, 3, 13, 12,
            4, 5, 14, 4, 14, 13,
            5, 6, 15, 5, 15, 14,
            6, 7, 16, 6, 16, 15,
            7, 8, 17, 7, 17, 16,
            9, 10, 19, 9, 19, 18,
            10, 11, 20, 10, 20, 19,
            11, 12, 21, 11, 21, 20,
            12, 13, 22, 12, 22, 21,
            13, 14, 23, 13, 23, 22,
            14, 15, 24, 14, 24, 23,
            15, 16, 25, 15, 25, 24,
            16, 17, 26, 16, 26, 25,
            18, 19, 28, 18, 28, 27,
            19, 20, 29, 19, 29, 28,
            20, 21, 30, 20, 30, 29,
            21, 22, 31, 21, 31, 30,
            22, 23, 32, 22, 32, 31,
            23, 24, 33, 23, 33, 32,
            24, 25, 34, 24, 34, 33,
            25, 26, 35, 25, 35, 34,
            27, 28, 37, 27, 37, 36,
            28, 29, 38, 28, 38, 37,
            29, 30, 39, 29, 39, 38,
            30, 31, 40, 30, 40, 39,
            31, 32, 41, 31, 41, 40,
            32, 33, 42, 32, 42, 41,
            33, 34, 43, 33, 43, 42,
            34, 35, 44, 34, 44, 43,
            36, 37, 46, 36, 46, 45,
            37, 38, 47, 37, 47, 46,
            38, 39, 48, 38, 48, 47,
            39, 40, 49, 39, 49, 48,
            40, 41, 50, 40, 50, 49,
            41, 42, 51, 41, 51, 50,
            42, 43, 52, 42, 52, 51,
            43, 44, 53, 43, 53, 52,
            45, 46, 55, 45, 55, 54,
            46, 47, 56, 46, 56, 55,
            47, 48, 57, 47, 57, 56,
            48, 49, 58, 48, 58, 57,
            49, 50, 59, 49, 59, 58,
            50, 51, 60, 50, 60, 59,
            51, 52, 61, 51, 61, 60,
            52, 53, 62, 52, 62, 61,
            54, 55, 64, 54, 64, 63,
            55, 56, 65, 55, 65, 64,
            56, 57, 66, 56, 66, 65,
            57, 58, 67, 57, 67, 66,
            58, 59, 68, 58, 68, 67,
            59, 60, 69, 59, 69, 68,
            60, 61, 70, 60, 70, 69,
            61, 62, 71, 61, 71, 70,
            63, 64, 73, 63, 73, 72,
            64, 65, 74, 64, 74, 73,
            65, 66, 75, 65, 75, 74,
            66, 67, 76, 66, 76, 75,
            67, 68, 77, 67, 77, 76,
            68, 69, 78, 68, 78, 77,
            69, 70, 79, 69, 79, 78,
            70, 71, 80, 70, 80, 79
    });
*/
    AttributesBufferSkybox mBuffer;
    Texture mXP;
    Texture mXM;
    Texture mYP;
    Texture mYM;
    Texture mZP;
    Texture mZM;

    SkyBoxResource mSkyBoxResource;
    private Light light;

    public SkyBox(SkyBoxResource skyBoxResource) {
        mSkyBoxResource = skyBoxResource;
    }

    public void create(Context context)
    {
        mBuffer = Shader.SkyBox.creatBuffer();
        mBuffer.setValues(mVertices);
    }

    public void load(Context context){
        mXP = TextureFactory.CreateTexture(context, mSkyBoxResource.XP, false);
        mXM = TextureFactory.CreateTexture(context, mSkyBoxResource.XM, false);
        mYP = TextureFactory.CreateTexture(context, mSkyBoxResource.YP, false);
        mYM = TextureFactory.CreateTexture(context, mSkyBoxResource.YM, false);
        mZP = TextureFactory.CreateTexture(context, mSkyBoxResource.ZP, false);
        mZM = TextureFactory.CreateTexture(context, mSkyBoxResource.ZM, false);
        mBuffer.createVBO();
    }

    public void render(Camera camera, Texture texture, Matrix model){
        Shader.SkyBox.setTexture(texture);
        Shader.SkyBox.setView(camera.getViewTransform());
        Shader.SkyBox.setProjection(camera.getProjectionTransform());
        Shader.SkyBox.setModel(model);
        Shader.SkyBox.setColor(ColorTheme.Default.SkyBoxColor);
        Shader.SkyBox.setEye(camera.getPosition());

        //Shader.SkyBox.setFogDensity(ColorTheme.Default.FogDensity);
        Shader.SkyBox.setLight(light.getPosition());

        Shader.SkyBox.apply();
        Shader.SkyBox.draw(mIndexBuffer);
    }

    Matrix mZPTransform = Matrix.Multiply(Matrix.createTranslate(0, 0, 1), Matrix.createScaling(Distance, Distance, Distance));
    Matrix mZMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(0, -1));
    Matrix mXPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(1, 0));
    Matrix mXMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationY(-1, 0));
    Matrix mYPTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(-1, 0));
    Matrix mYMTransform = Matrix.Multiply(mZPTransform, Matrix.createRotationX(1, 0));

    public void render(Camera camera){
        mBuffer.apply();
        render(camera, mYM, mZPTransform);
        render(camera, mYP, mZMTransform);
        render(camera, mXM, mXPTransform);
        render(camera, mXP, mXMTransform);
        render(camera, mZP, mYPTransform);
        render(camera, mZM, mYMTransform);
    }

    public void unload() {
        if (mYM != null)
            mYM.destroy();

        if (mYP != null)
            mYP.destroy();

        if (mXM != null)
            mXM.destroy();

        if (mXP != null)
            mXP.destroy();

        if (mZP != null)
            mZP.destroy();

        if (mZM != null)
            mZM.destroy();
        if (mBuffer != null)
            mBuffer.destroy();
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Light getLight() {
        return light;
    }
}
