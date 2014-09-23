package com.FaustGames;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.FaustGames.Asteroids3dFree.BasicActivity;
import com.android.vending.billing.IInAppBillingService;

public class CommonController {
    public static void ButtonSetWallpaperClick(Activity activity) {
        Intent intent = new Intent("android.intent.action.ACTION_LIVE_WALLPAPER_CHOOSER");
        if(Build.VERSION.SDK_INT >= 16)
        {
            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(activity.getPackageName(), activity.getPackageName() + ".ServiceWallpaper"));
        }
        else
        {
            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }

        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.wtf("Asteroids 3d", "No activity found to handle " + intent.toString());
        }
    }
    public static void ButtonCrossPromoClick(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=pub:FaustGames"));
        activity.startActivity(intent);
    }

    public static void ButtonDonateClick(BasicActivity activity) {
        activity.Purchase();
    }
}
