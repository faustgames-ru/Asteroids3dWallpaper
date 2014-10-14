package com.FaustGames.Core.Physics;

import com.FaustGames.Core.Mathematics.Vertex;

public class PhysicsHelper {
    public static void applyVelocity(Vertex position, Vertex velocity, float time){
        position.setX(position.getX()+velocity.getX() * time);
        position.setY(position.getY()+velocity.getY() * time);
        position.setZ(position.getZ()+velocity.getZ() * time);
    }
    public static void applyAcceleration(Vertex velocity, Vertex acceleration, float time){
        applyVelocity(velocity, acceleration, time);
    }
    public static void applyForce(Vertex velocity, Vertex force, float mass, float time){
        applyVelocity(velocity, force, time / mass);
    }
}
