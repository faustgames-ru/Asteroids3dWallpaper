package com.FaustGames.Asteroids3dFree.Views;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import com.FaustGames.Asteroids3dFree.BasicActivity;

public class BasicView {
    int mId;
    public BasicView(int id)
    {
        mId = id;
    }

    public View View;
    public BasicActivity Activity;

    public static String FontAsset = "Roboto-Medium.ttf";

    public CheckBox getCheckBox(int id) {
        CheckBox b = (CheckBox) View.findViewById(id);
        Typeface font = Typeface.createFromAsset(Activity.getAssets(), FontAsset);
        b.setTypeface(font);
        b.setSoundEffectsEnabled(true);
        return b;
    }

    public View getView(int id) {
        return View.findViewById(id);
    }

    public Button getButton(int id) {
        Button b = (Button) View.findViewById(id);
        Typeface font = Typeface.createFromAsset(Activity.getAssets(), FontAsset);
        b.setTypeface(font);
        b.setSoundEffectsEnabled(true);
        return b;
    }

    public TextView getLabel(int id) {
        TextView b = (TextView) View.findViewById(id);
        Typeface font = Typeface.createFromAsset(Activity.getAssets(), FontAsset);
        b.setTypeface(font);
        return b;
    }

    public SeekBar getSeekBar(int id) {
        SeekBar b = (SeekBar) View.findViewById(id);
        b.setSoundEffectsEnabled(true);
        return b;
    }
    public void setClick(int id, ButtonTouchListener listener)
    {
        getButton(id).setOnTouchListener(listener);
    }
    public void setupSeekBar(int id, int max, int position, SeekBarChangeListener listener)
    {
        SeekBar b = getSeekBar(id);
        b.setMax(max);
        b.setProgress(position);
        getSeekBar(id).setOnSeekBarChangeListener(listener);
    }

    public void Add(BasicActivity activity)
    {
        Activity = activity;
        LayoutInflater inflater = activity.getLayoutInflater();
        View = inflater.inflate(mId, null, false);
        OnAdd();
    }

    public void Remove(Activity activity)
    {
         OnRemove();
    }

    protected void OnAdd()
    {

    }

    protected void OnRemove()
    {
    }

    protected boolean OnBackPressed()
    {
        return false;
    }

    public boolean Back() {
        return OnBackPressed();
    }
}
