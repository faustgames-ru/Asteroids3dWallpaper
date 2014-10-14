package com.FaustGames.Core.Entities;

import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;

public class VerticesFactory {
    public static float[] CreateRingsVertices(Light[] lights, int floatStride, float scale) {
        int details = 24;
        int ringVerticesCount = details * 4;
        float r1 = 1.0f;
        float r2 = 1.5f;
        float d = 100.0f;
        float[] vertices = new float[lights.length * floatStride * ringVerticesCount];
        int i = 0;
        for (int lightIndex = 0; lightIndex < lights.length; lightIndex++){
            Light light = lights[lightIndex];
            Vertex position = Vertex.mul(light.getPosition().normalize(), d);
            float distance = position.length() * scale * light.getLensBrightness();
            Vertex direction = position.normalize();
            Vertex normal =direction.getZ() < direction.getY()
                    ?Vertex.crossProduct(direction, Vertex.AxisY)
                    :Vertex.crossProduct(direction, Vertex.AxisZ);
            Vertex tangent = Vertex.crossProduct(normal, direction).normalize().mul(distance);
            normal = normal.normalize().mul(distance);

            Vertex[] positions = new Vertex[ringVerticesCount];
            Vertex[] texturePositions = new Vertex[ringVerticesCount];
            int n = 0;
            int m = 0;
            for (int j = 0; j < details; j++){
                float a0 = j * MathF.PI*2.0f / details;
                float x0 = MathF.cos(a0);
                float y0 = MathF.sin(a0);

                float a1 = (j + 1) * MathF.PI*2.0f / details;
                float x1 = MathF.cos(a1);
                float y1 = MathF.sin(a1);

                positions[n++] = Vertex.add(Vertex.add(position, Vertex.mul(tangent, x0*r1)), Vertex.mul(normal, y0*r1));
                positions[n++] = Vertex.add(Vertex.add(position, Vertex.mul(tangent, x0*r2)), Vertex.mul(normal, y0*r2));
                positions[n++] = Vertex.add(Vertex.add(position, Vertex.mul(tangent, x1*r2)), Vertex.mul(normal, y1*r2));
                positions[n++] = Vertex.add(Vertex.add(position, Vertex.mul(tangent, x1*r1)), Vertex.mul(normal, y1*r1));

                texturePositions[m++] = new Vertex(0, 0);
                texturePositions[m++] = new Vertex(0, 1);
                texturePositions[m++] = new Vertex(1, 1);
                texturePositions[m++] = new Vertex(1, 0);

            }

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
                    Vertex.add(Vertex.add(position, tangent.inverse()), normal.inverse()),
                    Vertex.add(Vertex.add(position, tangent.inverse()), normal),
                    Vertex.add(Vertex.add(position, tangent), normal),
                    Vertex.add(Vertex.add(position, tangent), normal.inverse()),
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
