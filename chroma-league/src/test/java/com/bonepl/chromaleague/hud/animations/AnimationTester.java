package com.bonepl.chromaleague.hud.animations;


import com.bonepl.razersdk.ChromaRestSDK;
import com.bonepl.razersdk.animation.IFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class AnimationTester {
    private static final Logger LOGGER = LogManager.getLogger();
    private Consumer<Integer> afterIterationAction;
    private int sleepTime = 50;

    public AnimationTester withAfterIterationAction(Consumer<Integer> action) {
        afterIterationAction = action;
        return this;
    }

    public AnimationTester withSleepTime(int milliseconds) {
        sleepTime = milliseconds;
        return this;
    }

    public void testAnimation(Supplier<? extends IFrame> supplier, int iterations) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; i < iterations; i++) {
                final IFrame frame = supplier.get();
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread();
            }
        }
    }

    public void testAnimation(IFrame frame) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; frame.hasFrame(); i++) {
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread();
            }
        }
    }

    public void testAnimation(IFrame frame, int iterations) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; i < iterations; i++) {
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread();
            }
        }
    }

    private void executeAfterIteration(int i) {
        if (afterIterationAction != null) {
            afterIterationAction.accept(i);
        }
    }

    private void sleepThread() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
}

