package com.FaustGames.Asteroids3dFree;

import android.os.Bundle;

public class StartActivity extends BasicActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetView(Screens.Start);

    }
    @Override
    public void onBackPressed() {
        if (CurrentView == Screens.Settings)
            SetView(Screens.Start);
        else
            super.onBackPressed();
    }
}