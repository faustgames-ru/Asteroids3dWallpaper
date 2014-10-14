package com.FaustGames.Core.Content;

public class EntityResourceMesh extends EntityResource {
    MeshBatchResource _geometryResource;
    TextureResource _normalSpecularMap;
    TextureResource _diffuseGlow;
    int _duplicatesCount = 0;

    public MeshBatchResource getGeometryResource(){
        return _geometryResource;
    }

    public TextureResource getNormalSpecularMap(){
        return _normalSpecularMap;
    }
    public TextureResource getDiffuseGlow(){
        return _diffuseGlow;
    }

    public int getDuplicatesCount(){return _duplicatesCount;}

    public EntityResourceMesh(MeshBatchResource geometryResource, TextureResource normalSpecularMap, TextureResource diffuseGlow, int duplicatesCount){
        _geometryResource = geometryResource;
        _normalSpecularMap = normalSpecularMap;
        _diffuseGlow = diffuseGlow;
        _duplicatesCount = duplicatesCount;
    }
}
