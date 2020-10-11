package com.bonepl.chromaleague.razer.sdk;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface RzChromaSDK64 extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);
}
