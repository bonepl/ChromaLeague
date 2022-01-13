package net.booone.chromaleague.hud;


import net.booone.razersdk.ChromaRestSDK;
import net.booone.razersdk.animation.IFrame;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AnimationTester {
    private static final Logger LOGGER = Logger.getLogger(AnimationTester.class.getName());
    private Consumer<Integer> afterIterationAction;
    private Consumer<Integer> beforeIterationAction;
    private int sleepTime = 50;
    private int sleepAfter;

    public AnimationTester withBeforeIterationAction(Consumer<Integer> action) {
        beforeIterationAction = action;
        return this;
    }

    public AnimationTester withAfterIterationAction(Consumer<Integer> action) {
        afterIterationAction = action;
        return this;
    }

    public AnimationTester withSleepTime(int milliseconds) {
        sleepTime = milliseconds;
        return this;
    }

    public AnimationTester withSleepAfter(int milliseconds) {
        sleepAfter = milliseconds;
        return this;
    }

    public void testAnimation(Supplier<? extends IFrame> supplier, int iterations) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; i < iterations; i++) {
                executeBeforeIteration(i);
                final IFrame frame = supplier.get();
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread(sleepTime);
            }
            sleepThread(sleepAfter);
        }
    }

    public void testAnimation(IFrame frame) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; frame.hasFrame(); i++) {
                executeBeforeIteration(i);
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread(sleepTime);
            }
            sleepThread(sleepAfter);
        }
    }

    public void testAnimation(IFrame frame, int iterations) {
        try (ChromaRestSDK chromaRestSDK = new ChromaRestSDK()) {
            for (int i = 0; i < iterations; i++) {
                executeBeforeIteration(i);
                chromaRestSDK.createKeyboardEffect(frame);
                executeAfterIteration(i);
                sleepThread(sleepTime);
            }
            sleepThread(sleepAfter);
        }
    }

    private void executeAfterIteration(int i) {
        if (afterIterationAction != null) {
            afterIterationAction.accept(i);
        }
    }

    private void executeBeforeIteration(int i) {
        if (beforeIterationAction != null) {
            beforeIterationAction.accept(i);
        }
    }

    private static void sleepThread(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e, () -> "AnimationTester thread interrupted");
            Thread.currentThread().interrupt();
        }
    }
}

