package com.FaustGames.Core.PropertiesBinding;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PropertyBinder {
    Field _field;
    String _propertyName;
    Class<?> _type;
    Object _instance;
    ArrayList<IPropertyChangeListener> _listeners = new ArrayList<IPropertyChangeListener>();
    public PropertyBinder(Object instance, Field field) {
        _instance = instance;
        _type = _instance.getClass();
        _field = field;
        _propertyName = _field.getName();
    }

    public String getPropertyName(){
        return _propertyName;
    }

    public void addListener(IPropertyChangeListener listener){
        if (!_listeners.contains(listener))
            _listeners.add(listener);
    }

    void notifyListeners(){
        for (int i = 0; i < _listeners.size(); i++)
            _listeners.get(i).PropertyChanged(this);
    }

    public Object get(){
        try {
            return _field.get(_instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getFloat() {
        try {
            return _field.getFloat(_instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public boolean getBoolean() {
        try {
            return _field.getBoolean(_instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void set(Object value) {
        try {
            _field.set(_instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        notifyListeners();
    }
    public void setFloat(float value) {
        try {
            _field.setFloat(_instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        notifyListeners();
    }

    public void setBoolean(boolean value) {
        try {
            _field.setBoolean(_instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        notifyListeners();
    }
}
