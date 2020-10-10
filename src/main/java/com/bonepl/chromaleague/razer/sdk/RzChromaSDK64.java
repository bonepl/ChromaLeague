package com.bonepl.chromaleague.razer.sdk;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Guid.GUID;

public interface RzChromaSDK64 extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);

    int QueryDevice(GUID guid, RzDeviceInfo rzDeviceInfo);
}
