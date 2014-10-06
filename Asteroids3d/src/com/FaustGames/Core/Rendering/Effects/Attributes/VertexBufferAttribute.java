package com.FaustGames.Core.Rendering.Effects.Attributes;

import com.FaustGames.Core.Rendering.Effects.AttributeNames;

public class VertexBufferAttribute {
    public String Name;
    public int Size;

    public VertexBufferAttribute(String name, int size) {
        Name = name;
        Size = size;
    }

    public static VertexBufferAttribute Position = new VertexBufferAttribute(AttributeNames.Position, 3);
    public static VertexBufferAttribute Center = new VertexBufferAttribute(AttributeNames.Center, 3);
    public static VertexBufferAttribute TexturePosition = new VertexBufferAttribute(AttributeNames.TexturePosition, 2);
    public static VertexBufferAttribute CloudsColor0 = new VertexBufferAttribute(AttributeNames.CloudsColor0, 4);
    public static VertexBufferAttribute CloudsColor1 = new VertexBufferAttribute(AttributeNames.CloudsColor1, 4);
    public static VertexBufferAttribute StartsColor = new VertexBufferAttribute(AttributeNames.StartsColor, 4);
    public static VertexBufferAttribute Color = new VertexBufferAttribute(AttributeNames.Color, 4);
    public static VertexBufferAttribute Alpha = new VertexBufferAttribute(AttributeNames.Alpha, 1);
    public static VertexBufferAttribute Normal = new VertexBufferAttribute(AttributeNames.Normal, 3);
    public static VertexBufferAttribute Tangent = new VertexBufferAttribute(AttributeNames.Tangent, 3);
    public static VertexBufferAttribute BiTangent = new VertexBufferAttribute(AttributeNames.BiTangent, 3);
}
