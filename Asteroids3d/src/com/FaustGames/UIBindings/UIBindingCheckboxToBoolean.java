package com.FaustGames.UIBindings;

import android.view.SoundEffectConstants;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import com.FaustGames.Asteroids3dFree.Views.SeekBarChangeListener;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;

public class UIBindingCheckboxToBoolean extends UiBindingBasic implements CheckBox.OnCheckedChangeListener {
    static int Size = 255;
    PropertyBinder _binder;

    public UIBindingCheckboxToBoolean(CheckBox checkBox, PropertyBinder binder) {
        _binder = binder;
        checkBox.setChecked(_binder.getBoolean());
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
        _binder.set(value);
    }
}
