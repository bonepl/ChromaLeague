package com.bonepl.razersdk.sdk;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * Razer Chroma SDK C++ main class
 */
@SuppressWarnings({"NewMethodNamingConvention", "InterfaceNeverImplemented"})
public interface RzChromaSDK64 extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);
}
