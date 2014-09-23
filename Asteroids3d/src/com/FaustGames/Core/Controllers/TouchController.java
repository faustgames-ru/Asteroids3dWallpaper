package com.FaustGames.Core.Controllers;

import com.FaustGames.Core.DeviceConfiguration;
import com.FaustGames.Core.Entities.Scene;
import com.FaustGames.Core.Mathematics.MathF;
import com.FaustGames.Core.Mathematics.Vertex;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class TouchController implements IPointerController {

    Scene mScene;

    final float rotationVelocityK = 0.75f;
    final int MaxCount = 500;
    final int MaxTime= 100;

    Queue<PointerData> mMoveHistory = new LinkedList<PointerData>();
    int mMoveHistoryCount = 0;

    Vertex mPrevPosition;

    void updateQueue(Vertex pointerPosition){
        long currentTime = System.currentTimeMillis();
        mMoveHistory.offer(new PointerData(pointerPosition, currentTime));
        mMoveHistoryCount++;

        while ((currentTime - mMoveHistory.peek().Time) > MaxTime){
            mMoveHistory.poll();
            mMoveHistoryCount--;
            if (mMoveHistory.peek() == null) break;
        }

        while (mMoveHistoryCount > MaxCount){
            mMoveHistory.poll();
            mMoveHistoryCount--;
        }
    }

    void endMove(Vertex pointerPosition){
        updateQueue(pointerPosition);


        PointerData prevPointerData = null;
        Vertex velocity = Vertex.Empty.clone();
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        for (PointerData pointerData : mMoveHistory){
            if (prevPointerData != null){
                velocity.add(Vertex.sub(pointerData.Position, prevPointerData.Position));//.div(timeDelta));
            }
            if (minTime > pointerData.Time)
                minTime = pointerData.Time;
            if (maxTime < pointerData.Time)
                maxTime = pointerData.Time;
            prevPointerData = pointerData;
        }

        if (maxTime > 0) {
            float timeDelta = (float)(maxTime - minTime)  / 1000.0f;
            if (!MathF.equals(timeDelta, 0.0f) )
                velocity.div(timeDelta);
        }

        if (velocity.isEmpty()) {
            mScene.getCamera().RotationVelocity.setValue(0);
        }
        else {
            mScene.getCamera().RotationVelocity
                    .setAxis(mScene.getCamera().ExtraRotation.getMatrix().transform(new Vertex(-velocity.getY(), -velocity.getX())))
                    .setValue(pixelsToAngle(velocity.length()) * rotationVelocityK);
        }

        mMoveHistory.clear();
        mMoveHistoryCount = 0;
        mPrevPosition = null;
    }

    float pixelsToAngle(float pixels){
        if (DeviceConfiguration.isTablet)
            return pixels * mScene.getCamera().getFov()*0.25f / mScene.getCamera().getHeight();
        else
            return pixels * mScene.getCamera().getFov() / mScene.getCamera().getHeight();
    }

    @Override
    public void setScene(Scene scene) {
        mScene = scene;
    }

    @Override
    public void move(Vertex pointerPosition) {
        updateQueue(pointerPosition);
        if (mPrevPosition != null){
            Vertex axis = Vertex.sub(pointerPosition, mPrevPosition);
            float l = axis.length();
            if (!MathF.equals(l, 0))
            {
                mScene.getCamera().RotationVelocity.setValue(0);
                mScene.getCamera().Rotation.rotate(mScene.getCamera().ExtraRotation.getMatrix().transform(new Vertex(-axis.getY(), -axis.getX())), pixelsToAngle(l));
            }
        }
        mPrevPosition = pointerPosition;
    }

    @Override
    public void down(Vertex pointerPosition) {
        mScene.getCamera().RotationVelocity.setValue(0);
        mPrevPosition = pointerPosition;
        mMoveHistory.clear();
        mMoveHistoryCount = 0;
    }

    @Override
    public void up(Vertex pointerPosition) {
        endMove(pointerPosition);
    }

    @Override
    public void cancel(Vertex pointerPosition) {
        mMoveHistory.clear();
        mMoveHistoryCount = 0;
        mScene.getCamera().RotationVelocity.setValue(0);
        mPrevPosition = null;
    }

    @Override
    public void leave(Vertex pointerPosition) {
        endMove(pointerPosition);
    }
}

class PointerData {
    public Vertex Position;
    public long Time;
    public PointerData(Vertex position, long time){
        Position = position;
        Time = time;
    }
}
