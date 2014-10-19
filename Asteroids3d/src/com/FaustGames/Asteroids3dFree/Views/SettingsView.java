package com.FaustGames.Asteroids3dFree.Views;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.FaustGames.Asteroids3dFree.Preferences;
import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.CommonController;
import com.FaustGames.Core.BasicRenderer;
import com.FaustGames.Core.ColorTheme;
//import com.FaustGames.Core.Configuration;
import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Settings;
import com.FaustGames.PreferencesBinding.PreferencesBinding;
import com.FaustGames.UIBindings.UIBinding;

public class SettingsView extends BasicView {
    TextView LabelColorTheme;
    TextView LabelQuality;
    TextView LabelEnergySave;
    ScrollView ScrollView;
/*
    public SettingsView() {
        super(R.layout.settings);
    }
*/
    public SettingsView() {
        super(R.layout.all_settings);
    }
    @Override
    protected void OnAdd() {
        ScrollView = (ScrollView)getView(R.id.all_settings_scrollView);
        if (DeviceConfiguration.isTablet){
            ScrollView.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
        }

        setClick(R.id.all_settings_our_products, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                CommonController.ButtonCrossPromoClick(Activity);
            }
        });

        setClick(R.id.all_settings_donate, new ButtonTouchListener() {
            @Override
            public void OnClick() {
                CommonController.ButtonDonateClick(Activity);
            }
        });


        new UIBinding("", View, Settings.getInstance());
        /*
        LabelColorTheme = getLabel(R.id.settings_screen_label_color_theme);
        LabelQuality = getLabel(R.id.settings_screen_label_quality);
        LabelEnergySave = getLabel(R.id.settings_screen_label_energy_saving);

        LabelColorTheme.setText(Activity.Preferences.getColorThemeTitle());
        LabelQuality.setText(Activity.Preferences.getConfigurationTitle());
        LabelEnergySave.setText(Activity.Preferences.getSleepIntervalTitle());

        setClick(R.id.settings_screen_button_our_products_id, new ButtonTouchListener() {
            @Override
            public void OnClick() {
            CommonController.ButtonCrossPromoClick(Activity);
            }
        });

        setClick(R.id.settings_screen_button_donate_id, new ButtonTouchListener() {
            @Override
            public void OnClick() {
            CommonController.ButtonDonateClick(Activity);
            }
        });

        setupSeekBar(R.id.settings_screen_seekbar_color_theme, (ColorTheme.ColorThemes.length - 1) * 100, Activity.Preferences.getColorTheme() * 100, new SeekBarChangeListener() {
            @Override
            public void onChange(SeekBar seekBar, int progress, boolean fromUser) {
            Activity.Preferences.setColorTheme(progress / 100);
            LabelColorTheme.setText(Activity.Preferences.getColorThemeTitle());
            }
        });

        setupSeekBar(R.id.settings_screen_seekbar_quality, (Configuration.Configurations.length - 1) * 100, Activity.Preferences.getConfiguration() * 100, new SeekBarChangeListener() {
            @Override
            public void onChange(SeekBar seekBar, int progress, boolean fromUser) {
            Activity.Preferences.setConfiguration(progress / 100);
            LabelQuality.setText(Activity.Preferences.getConfigurationTitle());
            }
        });
        setupSeekBar(R.id.settings_screen_seekbar_energy_saving, BasicRenderer.MaxInterval, Activity.Preferences.getSleepInterval(), new SeekBarChangeListener() {
            @Override
            public void onChange(SeekBar seekBar, int progress, boolean fromUser) {
            Activity.Preferences.setSleepInterval(progress);
            LabelEnergySave.setText(Activity.Preferences.getSleepIntervalTitle());
            }
        });
        */
    }
}