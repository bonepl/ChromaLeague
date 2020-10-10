package com.bonepl.chromaleague.razersdk;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Guid;

public interface RzChromaSDK64 extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);

    int QueryDevice(Guid.GUID guid, RzDeviceInfo rzDeviceInfo);
}
