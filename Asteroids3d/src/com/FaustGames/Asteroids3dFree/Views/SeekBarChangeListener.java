package com.FaustGames.Asteroids3dFree.Views;

import android.view.SoundEffectConstants;
import android.widget.SeekBar;

public abstract class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        onChange(seekBar, progress, fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        seekBar.playSoundEffect(SoundEffectConstants.CLICK);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.playSoundEffect(SoundEffectConstants.CLICK);
    }

    public abstract void onChange(SeekBar seekBar, int progress, boolean fromUser);
}
