package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.GameState;

import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckRiotApiTask implements Runnable {

    private static final int faultTolerance = 3;
    private static int currentFault = faultTolerance;

    @Override
    public void run() {
        GameState.setRiotApiUp(checkRiotApiUp());
    }

    public static boolean checkRiotApiUp() {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress("localhost", 2999), 100);
//            currentFault = 0;
            return true;
        } catch (Exception e) {
            return false;
//            currentFault++;
//            return currentFault >= faultTolerance;
        }
    }
}
