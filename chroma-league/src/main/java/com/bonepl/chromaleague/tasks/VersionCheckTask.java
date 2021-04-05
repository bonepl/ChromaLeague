package com.bonepl.chromaleague.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VersionCheckTask implements Runnable {
    private static final String ONLINE_VERSION_URL = "https://raw.githubusercontent.com/bonepl/ChromaLeague/master/chroma-league/src/main/resources/VERSION";
    private static final String LATEST_RELEASE_URL = "https://github.com/bonepl/ChromaLeague/releases/latest";
    private static final String LOCAL_VERSION_PATH = "VERSION";
    private static final Logger LOGGER = Logger.getLogger(VersionCheckTask.class.getName());

    @Override
    public void run() {
        try {
            final List<String> onlineVersion = getOnlineVersion();
            final List<String> localVersion = getLocalVersion();
            LOGGER.info(() -> "Running ChromaLeague version " + localVersion.get(0));
            if (newerVersionAvailable(localVersion.get(0), onlineVersion.get(0))) {
                LOGGER.info(() -> getNewerVersionMessage(onlineVersion));
            } else {
                LOGGER.info(() -> "Your ChromaLeague version is up to date!");
            }
        } catch (IOException | URISyntaxException ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Couldn't check ChromaLeague version");
        }
    }

    private static String getNewerVersionMessage(List<String> onlineVersion) {
        return "There is a newer ChromaLeague version " + onlineVersion.get(0) + " available!" + System.lineSeparator()
                + "New changes:" + System.lineSeparator()
                + IntStream.range(1, onlineVersion.size()).mapToObj(onlineVersion::get).collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator()
                + "Please go to " + LATEST_RELEASE_URL + " to get the newest version!";
    }

    public List<String> getOnlineVersion() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(ONLINE_VERSION_URL).openStream(), StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.toList());
        }
    }

    public List<String> getLocalVersion() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(VersionCheckTask.class.getClassLoader().getResource(LOCAL_VERSION_PATH).toURI()));
    }

    public boolean newerVersionAvailable(String currentVersion, String onlineVersion) {
        final int[] currentVersionSplit = getVersionNumbers(currentVersion);
        final int[] onlineVersionSplit = getVersionNumbers(onlineVersion);
        if (onlineVersionSplit[0] > currentVersionSplit[0]) {
            return true;
        } else if (onlineVersionSplit[1] > currentVersionSplit[1]) {
            return true;
        } else {
            return onlineVersionSplit[2] > currentVersionSplit[2];
        }
    }

    private static int[] getVersionNumbers(String version) {
        return Arrays.stream(version.split("\\.")).mapToInt(Integer::valueOf).toArray();
    }
}
