package com.FaustGames.Core.Content;

import com.FaustGames.Core.Entities.Nebula;

public class EntityResourceNebula extends EntityResource {
    Nebula[] _nebula;
    NebulaResource _resource;

    public Nebula[] getNebula(){
        return _nebula;
    }

    public NebulaResource getResource(){
        return _resource;
    }

    public EntityResourceNebula(TextureResource texture1, TextureResource texture2, Nebula[] nebula){
        _nebula = nebula;
        _resource = new NebulaResource(texture1, texture2);
    }
}
