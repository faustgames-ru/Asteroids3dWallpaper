package com.FaustGames.Core.PropertiesBinding;

import java.lang.reflect.Field;
import java.util.Dictionary;
import java.util.Hashtable;

public class PropertiesBindingFactory {
    static Hashtable<Object, Hashtable<Field, PropertyBinder>> _binders = new Hashtable<Object, Hashtable<Field, PropertyBinder>>();
    public static PropertyBinder getBinder(Object instance, Field field){
        if (!_binders.containsKey(instance)){
            _binders.put(instance, new Hashtable<Field, PropertyBinder>());
        }
        Hashtable<Field, PropertyBinder> instanceCache = _binders.get(instance);
        if (!instanceCache.containsKey(field)){
            instanceCache.put(field, new PropertyBinder(instance, field));
        }
        return instanceCache.get(field);
    }
}
