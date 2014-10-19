package com.FaustGames.Core.Entities;

import android.content.Context;
import com.FaustGames.Core.ICreate;
import com.FaustGames.Core.ILoadable;
import com.FaustGames.Core.IRenderable;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBuffer;
import com.FaustGames.Core.Rendering.Effects.Attributes.VertexBufferAttribute;
import com.FaustGames.Core.Rendering.IndexBuffer;

public class PostProcess implements ICreate, ILoadable {
    VertexBuffer _vertexBuffer;
    IndexBuffer _indexBuffer;

    public VertexBuffer getVertexBuffer(){return _vertexBuffer;}
    public IndexBuffer getIndexBuffer(){return _indexBuffer;}

    @Override
    public void create(Context context) {
        _vertexBuffer = new VertexBuffer(new VertexBufferAttribute[]{
            VertexBufferAttribute.Position,
            VertexBufferAttribute.TexturePosition
        });
        _vertexBuffer.setValues(new float[]{
            -1.0f, -1.0f, 0.9f, 0.0f, 0.0f,
            -1.0f,  1.0f, 0.9f, 0.0f, 1.0f,
             1.0f,  1.0f, 0.9f, 1.0f, 1.0f,
             1.0f, -1.0f, 0.9f, 1.0f, 0.0f,
        });
        _indexBuffer = new IndexBuffer(new short[]{
            0, 1, 2, 0, 2, 3
        });
    }

    @Override
    public void load(Context context) {
        _vertexBuffer.createVBO();
    }

}
