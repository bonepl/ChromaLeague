package com.bonepl.razersdk.sdk.json.request;

import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.sdk.RzKey;
import com.jsoniter.annotation.JsonUnwrapper;
import com.jsoniter.output.JsonStream;

import java.io.IOException;
import java.util.Map;

public class KeyboardEffect {
    public static final int KEYBOARD_COLUMNS = 22;
    public static final int KEYBOARD_ROWS = 6;
    private static final String EFFECT = "CHROMA_CUSTOM";
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final int[][] param = new int[KEYBOARD_ROWS][KEYBOARD_COLUMNS];

    public KeyboardEffect(Map<RzKey, Color> keysToColors) {
        keysToColors.forEach((key, value) -> param[key.getRow()][key.getColumn()] = value.getSDKColorRef());
    }

    @JsonUnwrapper
    public void addEffectToJson(JsonStream stream) throws IOException {
        stream.writeObjectField("effect");
        stream.writeVal(EFFECT);
    }
}
