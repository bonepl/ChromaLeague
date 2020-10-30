package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;

import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckRiotApiTask implements Runnable {
    private static final int RIOT_API_PORT = 2999;

    @Override
    public void run() {
        RunningState.setRiotApiUp(checkRiotApiUp());
    }

    public static boolean checkRiotApiUp() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", RIOT_API_PORT), 100);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
