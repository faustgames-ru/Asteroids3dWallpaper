package com.FaustGames.Asteroids3dFree.Views;

import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

public abstract class ButtonTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            v.playSoundEffect(SoundEffectConstants.CLICK);
            OnClick();
        }
        return false;
    }

    public abstract void OnClick();
}
