package com.FaustGames.Core.Geometry;

public enum ShapesType {
    None(0),
    Sphere(1);

    private final int value;
    private ShapesType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
