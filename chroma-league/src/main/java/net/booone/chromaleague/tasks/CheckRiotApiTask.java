package net.booone.chromaleague.tasks;

import net.booone.chromaleague.state.RunningState;

import java.net.InetSocketAddress;
import java.net.Socket;

public class CheckRiotApiTask implements Runnable {
    private static final int RIOT_API_PORT = 2999;

    @Override
    public void run() {
        RunningState.setRiotApi(checkRiotApiUp());
    }

    public static Boolean checkRiotApiUp() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", RIOT_API_PORT), 100);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
