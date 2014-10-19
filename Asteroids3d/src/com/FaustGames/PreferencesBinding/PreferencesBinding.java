package com.FaustGames.PreferencesBinding;

import android.content.Context;
import android.content.SharedPreferences;
import com.FaustGames.Core.PropertiesBinding.PropertiesBindingFactory;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class PreferencesBinding {
    public static final String PREFERENCES_NAME = "MainPreferences";
    static SharedPreferences _preferences;
    static ISharedPreferencesSource _preferencesSource;

    static Hashtable<PropertyBinder, PreferencesBindingBasic> _bindings = new Hashtable<PropertyBinder, PreferencesBindingBasic>();

    static void bindProperty(PropertyBinder propertyBinder, PreferencesBindingBasic binding){
        if (_bindings.containsKey(propertyBinder)) return;
        _bindings.put(propertyBinder, binding);
    }

    public static void bind(Context context, Object instance){
        Field[] fields = instance.getClass().getFields();
        _preferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
        _preferencesSource = new SharedPreferencesSource();
        for (int i = 0; i < fields.length; i++){
            if (fields[i].getType().isAssignableFrom(float.class)){
                PropertyBinder propertyBinder = PropertiesBindingFactory.getBinder(instance, fields[i]);
                bindProperty(propertyBinder, new PreferencesBindingFloat(_preferencesSource, propertyBinder));
            } else if (fields[i].getType().isAssignableFrom(boolean.class)){
                PropertyBinder propertyBinder = PropertiesBindingFactory.getBinder(instance, fields[i]);
                bindProperty(propertyBinder, new PreferencesBindingBoolean(_preferencesSource, propertyBinder));
            }

        }
    }
}

class SharedPreferencesSource implements ISharedPreferencesSource{
    @Override
    public SharedPreferences getSharedPreferences() {
        return PreferencesBinding._preferences;
    }
}

