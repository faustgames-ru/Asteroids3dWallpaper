package com.FaustGames.Core;

import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Rendering.Color;

public class ColorTheme {
    public static ColorTheme Default = new ColorTheme();
    public static ColorTheme[] ColorThemes = new ColorTheme[]
            {
                new ColorTheme(),
                new ColorThemeFire(),
                new ColorThemeBlood(),
                //new ColorThemeRed(),
                new ColorThemeToxic(),
                //new ColorThemeGreen(),
                new ColorThemeCold(),
                //new ColorThemeElectric(),
                //new ColorThemeBlue(),
            };

    public static void apply(int index) {
        if (index < 0) apply(0);
        if (index >= ColorThemes.length) apply(ColorThemes.length - 1);
        Default = ColorThemes[index];
    }

    public static int getTitleId(int index) {
        if (index < 0) return getTitleId(0);
        if (index >= ColorThemes.length) return getTitleId(ColorThemes.length - 1);
        return ColorThemes[index].TitleId;
    }

    public int TitleId = R.string.color_theme_default;
    public Color Light = new Color(1, 1, 1.0f, 1);
    public Color LightSpecular = new Color(1, 1, 1.0f, 1);
    public Color LightAmbient= new Color(0.1f, 0.1f, 0.1f, 1);
    public Color SkyBoxColor= new Color(1.0f, 1.0f, 1.0f, 1);
    public Color Lens = new Color(1.0f, 1.0f, 1.0f, 1);
    public Color Glow = new Color(1, 0.75f, 0.5f, 1);
    public Color NebulaBack = new Color(1, 0.25f, 0.25f, 1);
    public Color NebulaFront= new Color(1, 0.5f, 0.5f, 1);
    public float FogDensity = 0.1f;

    public Color AdditiveParticles= new Color(1, 0.9f, 0.75f, 1);
    public Color Particles= new Color(0.25f, 0.2f, 0.15f, 1);
    public Color Clouds= new Color(1.0f, 1.0f, 1.0f, 1.0f);
}

class ColorThemeRed extends ColorTheme {
    public ColorThemeRed()
    {
        TitleId = R.string.color_theme_red;
        Light = new Color(1, 0.7f, 0.55f, 1);
        SkyBoxColor= new Color(1.0f, 0.75f, 0.75f, 1);
        Lens = new Color(1, 0.55f, 0.45f, 1);
        Glow = new Color(1, 0.5f, 0.5f, 1);
        NebulaBack = new Color(1, 0.25f, 0.05f, 1);
        NebulaFront= new Color(1, 0.35f, 0.25f, 1);

        AdditiveParticles= new Color(1, 0.25f, 0.15f, 1);
        Particles= new Color(0.35f, 0.1f, 0.05f, 1);
        Clouds= new Color(1.0f, 0.75f, 0.75f, 0.7f);
    }
}

class ColorThemeGreen extends ColorTheme {
    public ColorThemeGreen() {
        TitleId = R.string.color_theme_green;
        Light = new Color(0.75f, 1, 0.55f, 1);
        SkyBoxColor= new Color(0.75f, 1.0f, 0.75f, 1);
        Lens = new Color(0.55f, 1, 0.75f, 1);
        Glow = new Color(0.5f, 1.0f, 0.5f, 1);
        NebulaBack = new Color(0.55f, 1.0f, 0.45f , 1);
        NebulaFront= new Color(0.65f, 1.0f, 0.55f, 1);

        AdditiveParticles= new Color(0.25f, 1.0f, 0.15f, 1);
        Particles= new Color(0.05f, 0.35f, 0.05f, 1);
        Clouds= new Color(0.75f, 1.0f, 0.75f, 0.7f);
    }
}

class ColorThemeBlue extends ColorTheme {
    public ColorThemeBlue() {
        TitleId = R.string.color_theme_blue;
        Light = new Color(0.75f, 0.65f, 1.0f, 1);
        SkyBoxColor= new Color(0.75f, 0.75f, 1.0f, 1);
        Lens = new Color(0.45f, 0.65f, 1.0f, 1);
        Glow = new Color(0.5f, 0.5f, 1.0f, 1);
        NebulaBack = new Color(0.55f, 0.55f, 1.0f, 1);
        NebulaFront= new Color(0.75f, 0.75f, 1.0f, 1);

        AdditiveParticles= new Color(0.2f, 0.4f, 1.0f, 1);
        Particles= new Color(0.15f, 0.05f, 0.45f, 1);
        Clouds= new Color(0.75f, 0.75f, 1.0f, 0.7f);
    }
}

class ColorThemeFire extends ColorTheme {
    public ColorThemeFire()
    {
        TitleId = R.string.color_theme_fire;
        Light = new Color(1, 0.8f, 0.5f, 1);
        SkyBoxColor= new Color(1.0f, 0.8f, 0.6f, 1);
        Lens = new Color(1, 0.5f, 0.3f, 1);
        Glow = new Color(1, 0.75f, 0.5f, 1);
        NebulaBack = new Color(1, 0.25f, 0.05f, 1);
        NebulaFront= new Color(1, 0.75f, 0.25f, 1);

        AdditiveParticles= new Color(1, 0.75f, 0.5f, 1);
        Particles= new Color(0.1f, 0.1f, 0.05f, 1);
        Clouds= new Color(1.0f, 0.75f, 0.5f, 0.7f);
    }
}

class ColorThemeToxic extends ColorTheme {
    public ColorThemeToxic() {
        TitleId = R.string.color_theme_toxic;
        Light = new Color(1.0f, 1, 1.0f, 1);
        SkyBoxColor= new Color(0.85f, 1.0f, 0.85f, 1);
        Lens = new Color(0.85f, 1, 0.85f, 1);
        Glow = new Color(0.25f, 1.0f, 0.5f, 1);
        NebulaBack = new Color(0.25f, 1.0f, 0.5f , 1);
        NebulaFront= new Color(0.85f, 1.0f, 0.85f, 1);

        AdditiveParticles= new Color(0.25f, 1.0f, 0.5f, 1);
        Particles= new Color(0.05f, 0.1f, 0.05f, 1);
        Clouds= new Color(0.85f, 1.0f, 0.5f, 0.7f);
    }
}

class ColorThemeBlood extends ColorTheme {
    public ColorThemeBlood ()
    {
        TitleId = R.string.color_theme_blood;
        Light = new Color(1, 0.6f, 0.6f, 1);
        SkyBoxColor= new Color(1.0f, 0.8f, 0.8f, 1);
        Lens = new Color(1, 1.0f, 1.0f, 1);
        Glow = new Color(1, 0.0f, 0.0f, 1);
        NebulaBack = new Color(1, 0.0f, 0.0f, 1);
        NebulaFront= new Color(1, 0.8f, 0.8f, 1);

        AdditiveParticles= new Color(1, 0.5f, 0.5f, 1);
        Particles= new Color(0.1f, 0.05f, 0.05f, 1);
        Clouds= new Color(1.0f, 1.0f, 1.0f, 0.7f);
    }
}

class ColorThemeCold extends ColorTheme {
    public ColorThemeCold ()
    {
        TitleId = R.string.color_theme_cold;
        Light = new Color(0.6f, 0.8f, 1.0f, 1);
        SkyBoxColor= new Color(0.8f, 0.8f, 1.0f, 1);
        Lens = new Color(0.5f, 0.7f, 1.0f, 1);
        Glow = new Color(0.8f, 0.8f, 1.0f, 1);
        NebulaBack = new Color(0.5f, 0.5f, 1.0f, 1);
        NebulaFront= new Color(0.8f, 0.8f, 1.0f, 1);

        AdditiveParticles= new Color(0.8f, 0.8f, 1.0f, 1);
        Particles= new Color(0.05f, 0.05f, 0.1f, 1);
        Clouds= new Color(1.0f, 1.0f, 1.0f, 0.7f);
    }
}

class ColorThemeElectric extends ColorTheme {
    public ColorThemeElectric() {
        TitleId = R.string.color_theme_electric;
        Light = new Color(0.75f, 0.65f, 1.0f, 1);
        SkyBoxColor= new Color(0.75f, 0.75f, 1.0f, 1);
        Lens = new Color(0.45f, 0.65f, 1.0f, 1);
        Glow = new Color(0.5f, 0.5f, 1.0f, 1);
        NebulaBack = new Color(0.55f, 0.55f, 1.0f, 1);
        NebulaFront= new Color(0.75f, 0.75f, 1.0f, 1);

        AdditiveParticles= new Color(0.2f, 0.4f, 1.0f, 1);
        Particles= new Color(0.15f, 0.05f, 0.45f, 1);
        Clouds= new Color(0.75f, 0.75f, 1.0f, 0.7f);
    }
}

