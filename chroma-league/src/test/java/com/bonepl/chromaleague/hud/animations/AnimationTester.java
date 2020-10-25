package com.bonepl.chromaleague.hud.animations;

import com.bonepl.razersdk.RazerSDKClient;
import com.bonepl.razersdk.animation.IFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class AnimationTester {
    private static final Logger logger = LogManager.getLogger();
    Consumer<Integer> afterIterationAction;
    int sleepTime = 50;

    public AnimationTester withAfterIterationAction(Consumer<Integer> afterIterationAction) {
        this.afterIterationAction = afterIterationAction;
        return this;
    }

    public AnimationTester withSleepTime(int milliseconds) {
        this.sleepTime = milliseconds;
        return this;
    }

    public void testAnimation(Supplier<? extends IFrame> supplier, int iterations) {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < iterations; i++) {
                final IFrame frame = supplier.get();
                razerSDKClient.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread();
            }
        }
    }

    public void testAnimation(IFrame frame) {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; frame.hasFrame(); i++) {
                razerSDKClient.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread();
            }
        }
    }

    public void testAnimation(IFrame frame, int iterations) {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            for (int i = 0; i < iterations; i++) {
                razerSDKClient.createKeyboardEffect(frame);
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
            Thread.sleep(this.sleepTime);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}

