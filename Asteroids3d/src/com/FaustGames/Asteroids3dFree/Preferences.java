package com.FaustGames.Asteroids3dFree;

import android.content.Context;
import android.content.SharedPreferences;
import com.FaustGames.Core.*;

public class Preferences {
    public static final String PREFERENCES_NAME = "MainPreferences";
    Context context;
    SharedPreferences settings;

    public Preferences(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(PREFERENCES_NAME, 0);
        ColorTheme.apply(getColorTheme());
        com.FaustGames.Core.Configuration.apply(getConfiguration());
        BasicRenderer.Interval = getSleepInterval();
    }

    public String getColorThemeTitle() {
        int value = settings.getInt("ColorTheme", 0);
        return context.getString(ColorTheme.getTitleId(value));
    }

    public String getConfigurationTitle() {
        int value = settings.getInt("Configuration", 0);
        return context.getString(com.FaustGames.Core.Configuration.getTitleId(value));
    }

    public String getSleepIntervalTitle() {
        int value = settings.getInt("SleepInterval", 0);
        if (value == 0)
            return context.getString(R.string.energy_saving_none);
        else
        {
            return "(" + (value * 50 / BasicRenderer.MaxInterval) + "%)";
        }
    }

    public int getColorTheme(){
        return settings.getInt("ColorTheme", 0);
    }

    public void setColorTheme(int value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("ColorTheme", value);
        editor.commit();
        ColorTheme.apply(value);
    }

    public int getConfiguration(){
        return settings.getInt("Configuration", 3);
    }

    public void setConfiguration(int value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Configuration", value);
        editor.commit();
        com.FaustGames.Core.Configuration.apply(value);
    }

    public int getSleepInterval(){
        return settings.getInt("SleepInterval", 0);
    }

    public void setSleepInterval(int value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("SleepInterval", value);
        editor.commit();
        BasicRenderer.Interval = value;
    }
}
