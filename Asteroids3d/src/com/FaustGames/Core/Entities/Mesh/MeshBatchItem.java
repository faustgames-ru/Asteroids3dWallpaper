package com.FaustGames.Core.Entities.Mesh;

import com.FaustGames.Core.Content.EntityResourceMesh;
import com.FaustGames.Core.Content.MeshBatchResource;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Entities.SceneConfiguration;
import com.FaustGames.Core.Geometry.Bounds;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Matrix;
import com.FaustGames.Core.Mathematics.Matrix3;
import com.FaustGames.Core.Mathematics.Vertex;

import java.util.ArrayList;

public class MeshBatchItem {
    Matrix[] _transforms;
    Matrix3[] _normalTransforms;
    ArrayList<GeometryItemMesh> _meshes = new ArrayList<GeometryItemMesh>();
    public Matrix[] getTransforms() {
        return _transforms;
    }

    public Matrix3[] getNormalsTransforms() {
        return _normalTransforms;
    }

    public MeshBatchItem(EntityResourceMesh resources, boolean randomize, ArrayList<GeometryItemMesh> meshes){
        MeshBatchResource batchResource = resources.getGeometryResource();
        _transforms = new Matrix[batchResource.Count];
        _normalTransforms = new Matrix3[batchResource.Count];
        Vertex position;
        for (int j = 0; j < _transforms.length; j++) {
            if (randomize){
                _transforms[j] = Matrix.createIdentity();
                Bounds b = SceneConfiguration.Default.Dimensions;
                position = new Vertex(
                        MathF.rand(b.Min.getX(), b.Max.getX()),
                        MathF.rand(b.Min.getY(), b.Max.getY()),
                        MathF.rand(b.Min.getZ(), b.Max.getZ()));
                Matrix.applyTranslate(position, _transforms[j]);
            }
            else {
                _transforms[j] = batchResource.Positions[j].Transform;
                position = batchResource.Positions[j].Position;
            }
            _normalTransforms[j] = new Matrix3();
            _normalTransforms[j].fromMatrix4(_transforms[j]);
            GeometryItemMesh mesh = new GeometryItemMesh(position, batchResource.Positions[j].R * 0.8f, 1.0f, 0.5f, _transforms[j],  _normalTransforms[j]);
            meshes.add(mesh);
            _meshes.add(mesh);
        }
    }

    public ArrayList<GeometryItemMesh> getMeshes() {
        return _meshes;
    }
}
