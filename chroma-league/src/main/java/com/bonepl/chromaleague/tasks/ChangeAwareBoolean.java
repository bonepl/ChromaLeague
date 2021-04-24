package com.bonepl.chromaleague.tasks;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class ChangeAwareBoolean {
    private static final Logger LOGGER = Logger.getLogger(ChangeAwareBoolean.class.getName());

    private boolean value;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public boolean waitForChange() {
        try {
            countDownLatch.await();
            countDownLatch = new CountDownLatch(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning(() -> "Thread interrupted while waiting for change");
        }
        return value;
    }

    public void reset() {
        if (countDownLatch.getCount() == 1) {
            countDownLatch.countDown();
        }
        value = false;
        countDownLatch = new CountDownLatch(1);
    }

    public boolean different(boolean other) {
        return value != other;
    }

    public void setValue(boolean newValue) {
        if (different(newValue)) {
            value = newValue;
            countDownLatch.countDown();
        }
    }

    public boolean getValue() {
        return value;
    }
}
