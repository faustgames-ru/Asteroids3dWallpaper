package com.FaustGames.Core.Content;

import android.content.Context;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributesBufferMesh;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class MeshBatchResource {
    public int VerticesCount;
    public ByteBuffer VertexBuffer;
    public ByteBuffer IndexBuffer;
    public ByteBuffer PositionsBuffer;

    public MeshBatchTransformResource[] Positions = new MeshBatchTransformResource[32];
    public int Count;

    public MeshBatchResource(Context context, String vb, String ib, String transform, boolean useOrder){
        String oder = (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)?"_big":"_lit";
        if (!useOrder)
            oder = "";
        int vbId = context.getResources().getIdentifier("raw/"+vb+oder, null, context.getPackageName());
        int ibId = context.getResources().getIdentifier("raw/"+ib+oder, null, context.getPackageName());
        int tId = context.getResources().getIdentifier("raw/"+transform+oder, null, context.getPackageName());

        loadPosition(context, tId);
        loadVertexBuffer(context, vbId);
        loadIndexBuffer(context, ibId);

        for (int i = 0; i < Count; i++)
        {
            Positions[i] = new MeshBatchTransformResource();
            Positions[i].R = PositionsBuffer.getFloat();
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Positions[i].Transform.set(y, x, PositionsBuffer.getFloat());
                }
            }
            Positions[i].Position = Positions[i].Transform.createTranspose().transform(Vertex.Empty);
        }
    }

    public ByteOrder getByteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
        //return ByteOrder.nativeOrder();
    }

    public void loadPosition(Context context, int id) {
        try {
            InputStream raw = context.getResources().openRawResource(id);
            int size = raw.available();
            Count = size / (64 + 4);
            PositionsBuffer = ByteBuffer.allocate(size);
            PositionsBuffer.order(getByteOrder());
            byte[] buff = new byte[4*1024];
            while (true)
            {
                int count = raw.read(buff);
                if (count < 0) break;
                PositionsBuffer.put(buff, 0, count);
            }
            raw.close();
            PositionsBuffer.position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadIndexBuffer(Context context, int id) {
        try {
            InputStream raw = context.getResources().openRawResource(id);
            int size = raw.available();
            IndexBuffer = ByteBuffer.allocate(size);
            IndexBuffer.order(getByteOrder());
            byte[] buff = new byte[4*1024];
            while (true)
            {
                int count = raw.read(buff);
                if (count < 0) break;
                IndexBuffer.put(buff, 0, count);
            }
            raw.close();
            IndexBuffer.position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVertexBuffer(Context context, int id)      {
        try {
            InputStream raw = context.getResources().openRawResource(id);
            int size = raw.available();
            VertexBuffer = ByteBuffer.allocate(size);
            VertexBuffer.order(getByteOrder());
            byte[] buff = new byte[32*1024];
            while (true)
            {
                int count = raw.read(buff);
                if (count < 0) break;
                VertexBuffer.put(buff, 0, count);
            }
            raw.close();
            VertexBuffer.position(0);
            VerticesCount = size / AttributesBufferMesh.Stride;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
