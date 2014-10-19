package com.FaustGames.PreferencesBinding;

import android.content.SharedPreferences;
import com.FaustGames.Core.PropertiesBinding.IPropertyChangeListener;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;

public class PreferencesBindingFloat extends PreferencesBindingBasic implements IPropertyChangeListener {
    ISharedPreferencesSource _preferences;

    public PreferencesBindingFloat(ISharedPreferencesSource preferences, PropertyBinder binder){
        _preferences = preferences;
        binder.setFloat(_preferences.getSharedPreferences().getFloat(binder.getPropertyName(), binder.getFloat()));
        binder.addListener(this);
    }

    @Override
    public void PropertyChanged(PropertyBinder binder) {
        float value = binder.getFloat();
        String name =  binder.getPropertyName();
        SharedPreferences.Editor editor = _preferences.getSharedPreferences().edit();
        editor.putFloat(name, value);
        editor.commit();
    }
}
