package com.FaustGames.Core;

public class IndicesFactory {
    public static short[] CreateQuadIndices(int primitivesCount){
        short[] result = new short[primitivesCount * 6];
        int j = 0;
        int k = 0;
        for (int i = 0; i < primitivesCount; i++) {
            result[j++] = (short)(k + 0);
            result[j++] = (short)(k + 1);
            result[j++] = (short)(k + 2);
            result[j++] = (short)(k + 0);
            result[j++] = (short)(k + 2);
            result[j++] = (short)(k + 3);
            k += 4;
        }
        return result;
    }
}
