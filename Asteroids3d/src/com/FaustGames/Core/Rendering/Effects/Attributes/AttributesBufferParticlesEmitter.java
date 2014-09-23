package com.FaustGames.Core.Rendering.Effects.Attributes;

import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.IParticlesVertex;
import com.FaustGames.Core.Rendering.Effects.Attributes.AttributeFormats.ParticlesEmitterVertex;

import java.util.ArrayList;

public class AttributesBufferParticlesEmitter extends AttributesBuffer {
    public static int ATTRIB_COUNT = 22;
    public static int ATTRIB_OFFSET_MIDPOINT = 0;
    public static int ATTRIB_OFFSET_POSITION = 3;
    public static int ATTRIB_OFFSET_TEX_POSITION = 6;
    public static int ATTRIB_OFFSET_START_VELOCITY = 8;
    public static int ATTRIB_OFFSET_ACCELERATION = 11;
    public static int ATTRIB_OFFSET_COLOR = 14;
    public static int ATTRIB_OFFSET_SCALE = 18;
    public static int ATTRIB_OFFSET_LIFETIME = 19;
    public static int ATTRIB_OFFSET_REBORNTIME = 20;
    public static int ATTRIB_OFFSET_INITPHASE = 21;

    public AttributesBufferParticlesEmitter() {
    }

    public void applyAttribute(Attribute attribute, int offset, int size) {
        applyAttribute(attribute, offset, size, ATTRIB_COUNT * 4);
    }

    @Override
    public void onApply() { }

    @Override
    public void onApply(ArrayList<Attribute> attributes) {
        applyAttribute(attributes.get(0), ATTRIB_OFFSET_MIDPOINT, 3);
        applyAttribute(attributes.get(1), ATTRIB_OFFSET_POSITION*4, 3);
        applyAttribute(attributes.get(2), ATTRIB_OFFSET_TEX_POSITION*4, 2);
        applyAttribute(attributes.get(3), ATTRIB_OFFSET_START_VELOCITY*4, 3);
        applyAttribute(attributes.get(4), ATTRIB_OFFSET_ACCELERATION*4, 3);
        applyAttribute(attributes.get(5), ATTRIB_OFFSET_COLOR*4, 4);
        applyAttribute(attributes.get(6), ATTRIB_OFFSET_SCALE*4, 1);
        applyAttribute(attributes.get(7), ATTRIB_OFFSET_LIFETIME*4, 1);
        applyAttribute(attributes.get(8), ATTRIB_OFFSET_REBORNTIME*4, 1);
        applyAttribute(attributes.get(9), ATTRIB_OFFSET_INITPHASE*4, 1);
    }

    public void setValues(ArrayList<ParticlesEmitterVertex> values) {
        float[] bufferData = new float[values.size() * ATTRIB_COUNT];
        for (int i = 0; i < values.size(); i++) {
            int o = i * ATTRIB_COUNT;
            ParticlesEmitterVertex v = values.get(i);
            bufferData[o + 0] = v.MidPoint.getX();
            bufferData[o + 1] = v.MidPoint.getY();
            bufferData[o + 2] = v.MidPoint.getZ();

            bufferData[o + 3] = v.Position.getX();
            bufferData[o + 4] = v.Position.getY();
            bufferData[o + 5] = v.Position.getZ();

            bufferData[o + 6] = v.TexturePosition.getX();
            bufferData[o + 7] = v.TexturePosition.getY();

            bufferData[o + 8] = v.StartVelocity.getX();
            bufferData[o + 9] = v.StartVelocity.getY();
            bufferData[o + 10] = v.StartVelocity.getZ();

            bufferData[o + 11] = v.Acceleration.getX();
            bufferData[o + 12] = v.Acceleration.getY();
            bufferData[o + 13] = v.Acceleration.getZ();

            bufferData[o + 14] = v.Color.getR();
            bufferData[o + 15] = v.Color.getG();
            bufferData[o + 16] = v.Color.getB();
            bufferData[o + 17] = v.Color.getA();

            bufferData[o + 18] = v.Scale;
            bufferData[o + 19] = v.LifeTime;
            bufferData[o + 20] = v.RebornTime;

            bufferData[o + 21] = v.InitPhase;
        }
        fillData(bufferData);
    }
}
