package com.FaustGames.Core.Mathematics;

import java.util.Random;

public class MathF {
    public static final float PI = 3.14f;
    public static final float Eps = 0.001f;

    static Random mRandom = new Random();

    public static boolean equals(float v1, float v2){
        return abs(v1 - v2) < Eps;
    }

    public static float abs(float v){
        return Math.abs(v);
    }

    public static float cos(float angle){
        return (float)Math.cos(angle);
    }
    public static float sin(float angle){
        return (float)Math.sin(angle);
    }

    public static float sqrt(float v) {
        return (float)Math.sqrt(v);
    }

    public static float tan(float v) {
        return (float)Math.tan(v);
    }

    public static float atan2(float y, float x) {
        return (float)Math.atan2(y, x);
    }

    public static float asin(float v) {
        return (float)Math.asin(v);
    }

    public static float rand() {
        return mRandom.nextFloat();
    }

    public static float rand(float max) {
        return mRandom.nextFloat() * max;
    }

    public static float rand(float from, float to) {
        return from + mRandom.nextFloat() * (to - from);
    }

    public static int rand(int max) {
        int result = (int)rand((float)max);
        if (result > max)
            result = max;
        return result;
    }
}
