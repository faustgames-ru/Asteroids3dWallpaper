package com.FaustGames.Core;

import java.util.LinkedList;
import java.util.Queue;

public class Statistics {
    public int Fps = 0;
    static Queue<Integer> mFps = new LinkedList<Integer>() {
    };

    public void clear()
    {
        Fps = 0;
        mFps.clear();
    }

    public void AddFps(int value){
        mFps.offer(value);
        if (mFps.size() > 10)
            mFps.poll();
        int result = 0;
        for (Integer fps: mFps){
            result += fps;
        }
        if (mFps.size() > 7)
            Fps = result / mFps.size();
    }
}
