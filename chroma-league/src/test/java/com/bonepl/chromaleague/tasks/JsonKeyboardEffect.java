package com.bonepl.chromaleague.tasks;

import java.util.Arrays;

public class JsonKeyboardEffect {
    String effect = "CHROMA_CUSTOM";
    int[][] param = new int[6][22];

    public JsonKeyboardEffect(int fill) {
        for (int[] ints : param) {
            Arrays.fill(ints, fill);
        }
    }
}
