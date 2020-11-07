package com.bonepl.chromaleague;

import com.bonepl.razersdk.ChromaRestSDK;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class ChromaLeagueApp {

    static {
        final InputStream inputStream = ChromaRestSDK.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (final IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e, () -> "Could not load default logging.properties file for ChromaLeague");
        }
    }

    private ChromaLeagueApp() {
    }

    public static void main(String... args) {
        try (ChromaLeague chromaLeague = new ChromaLeague()) {
            chromaLeague.runChromaLeague();
        }
    }
}
