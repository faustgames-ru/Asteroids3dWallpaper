package com.FaustGames.Core;

import com.FaustGames.Asteroids3dFree.R;
import com.FaustGames.Core.Entities.LensFlareBatch;
/*
public class Configuration {
    public static Configuration AutoConfiguration = new Configuration();
    public static Configuration Lowest = new LowestConfiguration();
    public static Configuration Low = new LowConfiguration();
    public static Configuration Medium = new MediumConfiguration();
    public static Configuration High = new HighConfiguration();

    public static Configuration Default = High;
    public static Configuration[] Configurations = new Configuration[] {
                AutoConfiguration,
                Lowest,
                Low,
                Medium,
                High,
            };

    public static void apply(int index) {
        if (index < 0) apply(0);
        if (index >= Configurations.length) apply(Configurations.length - 1);
        Default = Configurations[index];
    }

    public static int getTitleId(int index) {
        if (index < 0) return getTitleId(0);
        if (index >= Configurations.length) return getTitleId(Configurations.length - 1);
        return Configurations[index].TitleId;
    }

    public int TitleId = R.string.quality_auto;
    public boolean Auto = false;
    public boolean DisplayLensFlare = true;
    public boolean DisplayClouds = true;
    public boolean LowClouds = false;

    public int proxyIndex = 4;

    public void applyProxy(Configuration proxy) {
        DisplayLensFlare = proxy.DisplayLensFlare;
        DisplayClouds = proxy.DisplayClouds;
        LowClouds = proxy.LowClouds;
    }

    public void inc() {
        if (proxyIndex >= (Configurations.length - 1)) return;
        proxyIndex++;
        applyProxy(Configurations[proxyIndex]);
    }

    public void dec() {
        if (proxyIndex <= 1) return;
        proxyIndex--;
        applyProxy(Configurations[proxyIndex]);
    }
}

class LowestConfiguration extends Configuration {
    public LowestConfiguration() {
        TitleId = R.string.quality_low;
        Auto = false;
        DisplayClouds = false;
        DisplayLensFlare = false;
        LowClouds = true;
    }
}

class LowConfiguration extends Configuration {
    public LowConfiguration() {
        TitleId = R.string.quality_medium;
        Auto = false;
        DisplayClouds = false;
        DisplayLensFlare = true;
        LowClouds = true;
    }
}


class MediumConfiguration extends Configuration {
    public MediumConfiguration() {
        TitleId = R.string.quality_high;
        Auto = false;
        DisplayClouds = true;
        DisplayLensFlare = true;
        LowClouds = true;
    }
}
class HighConfiguration extends Configuration {
    public HighConfiguration() {
        TitleId = R.string.quality_highest;
        Auto = false;
        DisplayClouds = true;
        DisplayLensFlare = true;
        LowClouds = false;
    }
}
*/