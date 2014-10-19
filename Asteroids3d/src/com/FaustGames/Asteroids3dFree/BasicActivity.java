package com.FaustGames.Asteroids3dFree;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.widget.FrameLayout;
import com.FaustGames.Asteroids3dFree.Views.BasicView;
import com.FaustGames.CommonController;
import com.FaustGames.Core.BasicRenderView;
import com.FaustGames.Core.Content.EntityResource;
import com.FaustGames.Core.Content.TextureMapResource;
import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Entities.Camera;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Entities.SkyBox;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Settings;
import com.FaustGames.PreferencesBinding.PreferencesBinding;
import com.android.vending.billing.IInAppBillingService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BasicActivity  extends Activity {

    SkyBox mSkyBox;
    Scene mScene;
    BasicRenderView mView;
    FrameLayout mFrame;
    Content mContent;
    public FrameLayout UIViewContainer;
    public BasicView CurrentView;
    public Preferences Preferences;
    public GyroscopeController GyroscopeController;
    public IInAppBillingService BillingService;
    public android.content.ServiceConnection ServiceConnection;

    public int getPOT(int v)
    {
        int r = 2;
        while(r < v)
            r*=2;
        return r;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceConfiguration.load(this);

        if (DeviceConfiguration.isTablet){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        PreferencesBinding.bind(this.getApplicationContext(), Settings.getInstance());

        Preferences = new Preferences(this.getApplicationContext());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        if (width > height)
            TextureMapResource.MaxSize = width;
        else
            TextureMapResource.MaxSize = height;

        TextureMapResource.WidthPOT = getPOT(width);
        TextureMapResource.HeightPOT = getPOT(height);

        Camera.resistanceK = Configuration.CameraRotationResistance;

        Content.init(this.getApplicationContext());
        mContent = Content.instance;
/*
        mSkyBox = new SkyBox(mContent.SkyBox);
        mScene = new Scene(mSkyBox, mContent.LensFlareMaps, mContent.Nebula,
                mContent.MeshDefaultMaps,
                mContent.MeshBatch,
                mContent.SmallMeshDefaultMaps,
                mContent.SmallMeshBatch,
                mContent.SmallMeshBatch1,
                mContent.Particle, mContent.Cloud);
*/
        mScene = new Scene();
        mScene.setEntities(mContent.getResources());

        mView = new BasicRenderView(this, mScene);

        mFrame = new FrameLayout(this);
        mFrame.addView(mView);

        UIViewContainer = new FrameLayout(this);

        mFrame.addView(UIViewContainer);

        setContentView(mFrame);

        GyroscopeController = new GyroscopeController(this, mScene);
    }

    public void SetView(BasicView view)
    {
        if (CurrentView != null)
            CurrentView.Remove(this);
        UIViewContainer.removeAllViews();
        if (view == null) return;
        CurrentView = view;
        CurrentView.Add(this);
        UIViewContainer.addView(view.View);
    }

    public static int RequestCode = 0;

    public void Purchase() {
        ServiceConnection = new BillingServiceConnection(this);
        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"), ServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
        GyroscopeController.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
        GyroscopeController.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GyroscopeController.onPause();
        if (ServiceConnection != null)
            unbindService(ServiceConnection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCode) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (responseCode == 0) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String token = jo.getString("purchaseToken");
                    if (BillingService != null)
                        BillingService.consumePurchase(3, getPackageName(), token);
                    if (ServiceConnection != null)
                        unbindService(ServiceConnection);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class BillingServiceConnection implements ServiceConnection
{
    BasicActivity Activity;
    public BillingServiceConnection(BasicActivity activity)
    {
        Activity = activity;
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Activity.BillingService = null;
        Activity.ServiceConnection = null;
    }

    @Override
    public void onServiceConnected(ComponentName name,
                                   IBinder service) {
        Activity.BillingService = IInAppBillingService.Stub.asInterface(service);
        try {
            Bundle ownedItems = Activity.BillingService.getPurchases(3, Activity.getPackageName(), "inapp", "") ;

            ArrayList<String>  purchaseDataList =
                    ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            for (int i = 0; i < purchaseDataList.size(); i++) {
                JSONObject jo = new JSONObject(purchaseDataList.get(i));
                String token = jo.getString("purchaseToken");
                if (Activity.BillingService != null)
                {
                    int consumeResult = Activity.BillingService.consumePurchase(3,  Activity.getPackageName(), token);
                    if (consumeResult != 0)
                        return;
                }
            }

            Bundle buyIntentBundle = Activity.BillingService.getBuyIntent(3, Activity.getPackageName(), "com.faustgames.asteroids3d.donate", "inapp", "");
            if (buyIntentBundle.getInt("RESPONSE_CODE") == 0)
            {
                PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                Activity.startIntentSenderForResult(pendingIntent.getIntentSender(), Activity.RequestCode, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
