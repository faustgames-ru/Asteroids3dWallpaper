package com.FaustGames.UIBindings;

import android.view.SoundEffectConstants;
import android.widget.SeekBar;
import com.FaustGames.Asteroids3dFree.Views.SeekBarChangeListener;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.PropertiesBinding.PropertyBinder;

public class UIBindingSeekbarToFloat extends UiBindingBasic implements SeekBar.OnSeekBarChangeListener {
    static int Size = 255;
    PropertyBinder _binder;

    public UIBindingSeekbarToFloat(SeekBar seekBar, PropertyBinder binder) {
        _binder = binder;
        seekBar.setMax(Size);
        seekBar.setProgress(Math.round(_binder.getFloat() * Size));
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;
        _binder.setFloat(MathF.saturate((float)progress / Size));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        seekBar.playSoundEffect(SoundEffectConstants.CLICK);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.playSoundEffect(SoundEffectConstants.CLICK);
    }
}
