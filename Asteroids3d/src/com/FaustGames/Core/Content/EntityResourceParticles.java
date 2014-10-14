package com.FaustGames.Core.Content;

import com.FaustGames.Core.Entities.PatriclessEmitter.Emitter;
import com.FaustGames.Core.Rendering.Color;

public class EntityResourceParticles extends EntityResource {
    TextureResource _texture;
    Emitter[] _emitters;
    float _timeStep;
    boolean _additive;
    IColorSource _colorSource;

    public IColorSource getColorSource(){
        return _colorSource;
    }

    public TextureResource getTexture(){
        return _texture;
    }

    public Emitter[] getEmitters() {
        return _emitters;
    }

    public boolean isAdditive() {
        return _additive;
    }

    public float getTimeStep() {
        return _timeStep;
    }

    public EntityResourceParticles(TextureResource texture, Emitter[] emitters, float timeStep, boolean additive, IColorSource colorSource){
        _texture = texture;
        _emitters = emitters;
        _timeStep = timeStep;
        _additive = additive;
        _colorSource = colorSource;
    }
}
