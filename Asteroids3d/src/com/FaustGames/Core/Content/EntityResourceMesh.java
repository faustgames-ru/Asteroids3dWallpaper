package com.FaustGames.Core.Content;

public class EntityResourceMesh extends EntityResource {
    MeshBatchResource _geometryResource;
    TextureResource _normalSpecularMap;
    TextureResource _diffuseGlow;

    public MeshBatchResource getGeometryResource(){
        return _geometryResource;
    }

    public TextureResource getNormalSpecularMap(){
        return _normalSpecularMap;
    }
    public TextureResource getDiffuseGlow(){
        return _diffuseGlow;
    }

    public EntityResourceMesh(MeshBatchResource geometryResource, TextureResource normalSpecularMap, TextureResource diffuseGlow){
        _geometryResource = geometryResource;
        _normalSpecularMap = normalSpecularMap;
        _diffuseGlow = diffuseGlow;
    }
}
