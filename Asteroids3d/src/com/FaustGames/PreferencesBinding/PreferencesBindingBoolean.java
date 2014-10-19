package com.FaustGames.PreferencesBinding;

import android.content.SharedPreferences;
import com.FaustGames.Core.PropertiesBinding.IPropertyChangeListener;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;

public class PreferencesBindingBoolean extends PreferencesBindingBasic implements IPropertyChangeListener {
    ISharedPreferencesSource _preferences;

    public PreferencesBindingBoolean(ISharedPreferencesSource preferences, PropertyBinder binder){
        _preferences = preferences;
        binder.setBoolean(_preferences.getSharedPreferences().getBoolean(binder.getPropertyName(), binder.getBoolean()));
        binder.addListener(this);
    }

    @Override
    public void PropertyChanged(PropertyBinder binder) {
        boolean value = binder.getBoolean();
        String name =  binder.getPropertyName();
        SharedPreferences.Editor editor = _preferences.getSharedPreferences().edit();
        editor.putBoolean(name, value);
        editor.commit();
    }
}
