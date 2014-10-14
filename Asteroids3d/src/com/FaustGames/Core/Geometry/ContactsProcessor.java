package com.FaustGames.Core.Geometry;

import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;
import com.FaustGames.Core.Physics.IMass;

import java.util.Vector;

public abstract class ContactsProcessor {

    static ContactsProcessor[][] _collisionMap = new ContactsProcessor[][]{
            null,
            new ContactsProcessor[] { null, new ContactsProcessorSphereSphere() }
    };

    public static void process(GeometryContact contact){
        int i = contact._a.getShapeType().getValue();
        int j = contact._b.getShapeType().getValue();
        _collisionMap[i][j].processContact(contact);
    }

    public abstract void processContact(GeometryContact contact);
}

class ContactsProcessorSphereSphere extends ContactsProcessor {
    @Override
    public void processContact(GeometryContact contact) {
        IGeometryShapeSphere sphere0 = (IGeometryShapeSphere)contact._a;
        IGeometryShapeSphere sphere1 = (IGeometryShapeSphere)contact._b;
        if (!Bounds.cross(contact._a.getBounds(), contact._b.getBounds())) return;
        float distanceSqr = Vertex.lengthSqr(sphere0.getPosition(), sphere1.getPosition());
        float r = sphere0.getRadius() + sphere1.getRadius();
        float rSqr = MathF.sqr(r);
        if (distanceSqr > rSqr) return;
        if (!(contact._a instanceof IGeometryDynamic)) return;
        if (!(contact._b instanceof IGeometryDynamic)) return;

        float distance = MathF.sqrt(distanceSqr);
        float delta = r - distance;

        Vertex.sub(sphere0.getPosition(), sphere1.getPosition(), contact.NormalA);
        if (contact.NormalA.isEmpty()) {
            contact.NormalA.setX(0);
            contact.NormalA.setY(0);
            contact.NormalA.setZ(1);
        }
        else {
            contact.NormalA.normalizeSelf();
        }
        Vertex.inverse(contact.NormalA, contact.NormalB);

        IGeometryDynamic mass0 = (IGeometryDynamic)contact._a;
        IGeometryDynamic mass1 = (IGeometryDynamic)contact._b;
        float m0 = mass0.getMass();
        float m1 = mass1.getMass();
        float m01 = m0 + m1;
        float offset0 = m1 *  delta / m01;
        float offset1 = m0 *  delta / m01;

        contact.NormalA.mul(offset0 + 0.1f);
        contact.NormalB.mul(offset1 + 0.1f);

        sphere0.getPosition().add(contact.NormalA);
        sphere1.getPosition().add(contact.NormalB);

        mass0.applyBonce(contact.NormalA);
        mass1.applyBonce(contact.NormalB);
    }
}
