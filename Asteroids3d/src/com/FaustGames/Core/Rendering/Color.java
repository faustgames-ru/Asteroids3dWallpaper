package com.FaustGames.Core.Rendering;

public class Color {
    public static Color Red = new Color(1, 0, 0, 1);
    public static Color Green = new Color(0, 1, 0, 1);
    public static Color Blue = new Color(0, 0, 1, 1);
    public static Color White = new Color(1, 1, 1, 1);
    public static Color Gray = new Color(0.5f, 0.5f, 0.5f, 0.5f);
    public static Color Black = new Color(0, 0, 0, 0);
    public float[] rgba = new float[4];

    public Color(float r, float g, float b, float a) {
        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;
    }

    public float getR() {
        return rgba[0];
    }
    public float getG() {
        return rgba[1];
    }
    public float getB() {
        return rgba[2];
    }
    public float getA() {
        return rgba[3];
    }

    public Color preMultiply() {
        return new Color(getR() * getA(), getG() * getA(), getB() * getA(), getA());
    }

    public float[] toArray() {
        return rgba;
    }

    public static Color fromHSV(float h, float s, float v, float a) {
        int c = android.graphics.Color.HSVToColor(new float[]{h * 360, s, v});
        return new Color(
                (float)android.graphics.Color.red(c) / 255,
                (float)android.graphics.Color.green(c) / 255,
                (float)android.graphics.Color.blue(c) / 255,
                a);
    }
}
