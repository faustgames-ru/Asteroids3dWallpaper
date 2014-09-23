package com.FaustGames.Core.Content;

import android.content.Context;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferMesh;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MeshGeometryResource {
    public int Id;
    public int VerticesCount;
    public ByteBuffer Buffer;
    public ByteBuffer VBBuffer;
    public MeshGeometryResource(Context context, int id, int vbid){
        Id = id;
        loadBuffer(context, id);
        try {
            InputStream raw = context.getResources().openRawResource(vbid);
            int size = raw.available();
            VBBuffer = ByteBuffer.allocate(size);
            byte[] buff = new byte[4*1024];
            while (true)
            {
                int count = raw.read(buff);
                if (count < 0) break;
                VBBuffer.put(buff, 0, count);
            }
            VBBuffer.position(0);
            VBBuffer.order(ByteOrder.LITTLE_ENDIAN);
            VerticesCount = size / AttributesBufferMesh.Stride;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadBuffer(Context context, int id){
        try {
            InputStream raw = context.getResources().openRawResource(id);
            int size = raw.available();
            Buffer = ByteBuffer.allocate(size);
            byte[] buff = new byte[4*1024];
            while (true)
            {
                int count = raw.read(buff);
                if (count < 0) break;
                Buffer.put(buff, 0, count);
            }
            Buffer.position(0);
            Buffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
