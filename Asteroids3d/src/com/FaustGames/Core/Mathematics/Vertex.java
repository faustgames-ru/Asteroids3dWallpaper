package com.FaustGames.Core.Mathematics;

import com.FaustGames.Core.Entities.PatriclessEmitter.VectorValueRange;

import java.util.Vector;

public class Vertex {
    public float[] xyz = new float[3];

    public final static Vertex AxisX = new Vertex(1, 0, 0);
    public final static Vertex AxisY = new Vertex(0, 1, 0);
    public final static Vertex AxisZ = new Vertex(0, 0, 1);
    public final static Vertex Empty = new Vertex(0, 0, 0);

    public Vertex() {

    }

    public Vertex(float x, float y) {
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = 0;
    }

    public Vertex(float x, float y, float z) {
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
    }

    public Vertex inverse() {
        return new Vertex(-getX(), -getY(), -getZ());
    }
    public Vertex clone() {
        return new Vertex(getX(), getY(), getZ());
    }

    public float lengthSqr() {
        return getX() * getX() + getY() * getY() + getZ() * getZ();
    }

    public float length() {
        return MathF.sqrt(lengthSqr());
    }

    public Vertex normalize() {
        float l = length();
        if (MathF.equals(l, 0.0f)) return Empty;
        return new Vertex(getX() / l, getY() / l, getZ() / l);
    }

    public void normalizeSelf() {
        float l = 1.0f / length();
        mul(l);
    }

    public static Vertex crossProduct(Vertex u, Vertex v){
        Vertex result = new Vertex();
        result.setX(u.get(1) * v.get(2) - u.get(2) * v.get(1));
        result.setY(u.get(2) * v.get(0) - u.get(0) * v.get(2));
        result.setZ(u.get(0) * v.get(1) - u.get(1) * v.get(0));
        return result;
    }

    public static float dotProduct(Vertex u, Vertex v){
        return u.get(0) * v.get(0) + u.get(1) * v.get(1) + u.get(2) * v.get(2);
    }

    public static Vertex sub(Vertex v1, Vertex v2){
        return new Vertex(
                v1.getX() - v2.getX(),
                v1.getY() - v2.getY(),
                v1.getZ() - v2.getZ());
    }

    public static Vertex add(Vertex v1, Vertex v2){
        return new Vertex(
                v1.getX() + v2.getX(),
                v1.getY() + v2.getY(),
                v1.getZ() + v2.getZ());
    }

    public static Vertex add(Vertex v1, Vertex v2, Vertex v3){
        return new Vertex(
                v1.getX() + v2.getX() + v3.getX(),
                v1.getY() + v2.getY() + v3.getY(),
                v1.getZ() + v2.getZ() + v3.getZ());
    }

    public static Vertex mul(Vertex v1, Vertex v2){
        return new Vertex(
                v1.getX() * v2.getX(),
                v1.getY() * v2.getY(),
                v1.getZ() * v2.getZ());
    }

    public static Vertex mul(Vertex v1, float v2){
        return new Vertex(
                v1.getX() * v2,
                v1.getY() * v2,
                v1.getZ() * v2);
    }

    public Vertex add(Vertex v){
        setX(getX() + v.getX());
        setY(getY() + v.getY());
        setZ(getZ() + v.getZ());
        return this;
    }

    public Vertex sub(Vertex v){
        setX(getX() - v.getX());
        setY(getY() - v.getY());
        setZ(getZ() - v.getZ());
        return this;
    }

    public Vertex mul(Vertex v){
        setX(getX() * v.getX());
        setY(getY() * v.getY());
        setZ(getZ() * v.getZ());
        return this;
    }

    public Vertex div(Vertex v){
        setX(getX() / v.getX());
        setY(getY() / v.getY());
        setZ(getZ() / v.getZ());
        return this;
    }

    public Vertex mul(float v){
        setX(getX() * v);
        setY(getY() * v);
        setZ(getZ() * v);
        return this;
    }

    public Vertex div(float v){
        setX(getX() / v);
        setY(getY() / v);
        setZ(getZ() / v);
        return this;
    }

    public float get(int i){
        return xyz[i];
    }

    public float getX(){
        return xyz[0];
    }

    public float getY(){
        return xyz[1];
    }

    public float getZ(){
        return xyz[2];
    }
    public void setX(float value){
        xyz[0] = value;
    }

    public void setY(float value){
        xyz[1] = value;
    }

    public void setZ(float value){
        xyz[2] = value;
    }

    public boolean isEmpty() {
        return MathF.equals(getX(), 0) && MathF.equals(getY(), 0) && MathF.equals(getZ(), 0);
    }

    public float[] toArray() {
        return xyz;
    }
}
