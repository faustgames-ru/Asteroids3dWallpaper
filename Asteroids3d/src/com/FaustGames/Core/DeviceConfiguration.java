package com.FaustGames.Core;

import android.content.Context;
import android.util.DisplayMetrics;
import com.FaustGames.Asteroids3dFree.Preferences;

public class DeviceConfiguration {
    public static boolean isTablet;
    public static boolean isSmall;

    public static void load(Context context) {
        isTablet = (context.getResources().getConfiguration().screenLayout& android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
