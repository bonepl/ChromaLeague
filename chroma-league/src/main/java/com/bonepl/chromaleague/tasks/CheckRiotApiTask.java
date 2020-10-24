package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.GameState;

import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckRiotApiTask implements Runnable {

    @Override
    public void run() {
        GameState.setRiotApiUp(checkRiotApiUp());
    }

    public static boolean checkRiotApiUp() {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress("localhost", 2999), 100);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
