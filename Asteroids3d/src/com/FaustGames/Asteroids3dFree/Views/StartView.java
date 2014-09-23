package com.FaustGames.Asteroids3dFree.Views;

import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Asteroids3dFree.Screens;
import com.FaustGames.CommonController;

public class StartView extends BasicView {

    public StartView() {
        super(R.layout.start);
    }

    @Override
    protected void OnAdd() {
        setClick(R.id.start_screen_button_set_wallpaper, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                CommonController.ButtonSetWallpaperClick(Activity);
            }
        });

        setClick(R.id.start_screen_button_settings, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                Activity.SetView(Screens.Settings);
            }
        });

        setClick(R.id.start_screen_button_our_products, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                CommonController.ButtonCrossPromoClick(Activity);
            }
        });

        setClick(R.id.start_screen_button_donate, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                CommonController.ButtonDonateClick(Activity);
            }
        });
    }
}
