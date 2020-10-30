package com.bonepl.chromaleague;

public final class ChromaLeagueApp {
    private ChromaLeagueApp() {
    }

    public static void main(String... args) {
        try (ChromaLeague chromaLeague = new ChromaLeague()) {
            chromaLeague.runChromaLeague();
        }
    }
}
