package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Mathematics.Vertex;

public class VerticesFactory {
    public static float[] CreateLightsVertices(Light[] lights, int floatStride, float scale) {
        float[] vertices = new float[lights.length * floatStride * 4];
        int i = 0;
        for (int lightIndex = 0; lightIndex < lights.length; lightIndex++){
            Light light = lights[lightIndex];
            Vertex position = light.getPosition();
            float distance = position.length() * scale * light.getLensBrightness();
            Vertex direction = position.normalize();
            Vertex normal =direction.getZ() > direction.getY()
                    ?Vertex.crossProduct(direction, Vertex.AxisY)
                    :Vertex.crossProduct(direction, Vertex.AxisZ);
            Vertex tangent = Vertex.crossProduct(normal, direction).normalize().mul(distance);
            normal = normal.normalize().mul(distance);

            Vertex[] positions = new Vertex[]{
                    Vertex.add(position, tangent.inverse(), normal.inverse()),
                    Vertex.add(position, tangent.inverse(), normal),
                    Vertex.add(position, tangent, normal),
                    Vertex.add(position, tangent, normal.inverse()),
            };

            Vertex[] texturePositions = new Vertex[]{
                    new Vertex(0, 0, 0),
                    new Vertex(0, 1, 0),
                    new Vertex(1, 1, 0),
                    new Vertex(1, 0, 0),
            };

            for (int j = 0; j < positions.length; j++) {
                vertices[i++] = position.getX();
                vertices[i++] = position.getY();
                vertices[i++] = position.getZ();

                vertices[i++] = positions[j].getX();
                vertices[i++] = positions[j].getY();
                vertices[i++] = positions[j].getZ();
                vertices[i++] = texturePositions[j].getX();
                vertices[i++] = texturePositions[j].getY();
                vertices[i++] = light.Color.getR();
                vertices[i++] = light.Color.getG();
                vertices[i++] = light.Color.getB();
                vertices[i++] = light.Color.getA();
            }
        }
        return vertices;
    }
}
