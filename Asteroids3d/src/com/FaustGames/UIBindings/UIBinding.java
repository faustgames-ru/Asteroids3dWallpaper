package com.FaustGames.UIBindings;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import com.FaustGames.Core.PropertiesBinding.PropertiesBindingFactory;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;
import com.FaustGames.PreferencesBinding.PreferencesBindingBasic;
import com.FaustGames.PreferencesBinding.PreferencesBindingFloat;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class UIBinding {

    Hashtable<PropertyBinder, UiBindingBasic> _bindings = new Hashtable<PropertyBinder, UiBindingBasic>();

    void bindProperty(PropertyBinder propertyBinder, UiBindingBasic binding){
        if (_bindings.containsKey(propertyBinder)) return;
        _bindings.put(propertyBinder, binding);
    }

    public UIBinding(String controlsPrefix, View view, Object instance) {
        Field[] fields = instance.getClass().getFields();
        for (int i = 0; i < fields.length; i++){
            String controlName = controlsPrefix+fields[i].getName();
            View control = view.findViewWithTag(controlName);
            if (fields[i].getType().isAssignableFrom(float.class)){
                if (control instanceof SeekBar){
                    PropertyBinder propertyBinder = PropertiesBindingFactory.getBinder(instance, fields[i]);
                    bindProperty(propertyBinder, new UIBindingSeekbarToFloat((SeekBar) control, propertyBinder));
                }
            } else if (fields[i].getType().isAssignableFrom(boolean.class)){
                if (control instanceof CheckBox){
                    PropertyBinder propertyBinder = PropertiesBindingFactory.getBinder(instance, fields[i]);
                    bindProperty(propertyBinder, new UIBindingCheckboxToBoolean((CheckBox) control, propertyBinder));
                }
            }
        }
    }
}
