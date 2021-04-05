package com.bonepl.chromaleague.tasks;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class VersionCheckTask implements Runnable {
    private static final String ONLINE_VERSION_URL = "https://raw.githubusercontent.com/bonepl/ChromaLeague/master/chroma-league/src/main/resources/VERSION";
    private static final String LATEST_RELEASE_URL = "https://github.com/bonepl/ChromaLeague/releases/latest";
    private static final String LOCAL_VERSION_PATH = "/VERSION";
    private static final Logger LOGGER = Logger.getLogger(VersionCheckTask.class.getName());

    @Override
    public void run() {
        try {
            final List<String> onlineVersion = getOnlineVersion();
            final List<String> localVersion = getLocalVersion();
            LOGGER.info(() -> "Running ChromaLeague version " + localVersion.get(0));
            if (newerVersionAvailable(localVersion.get(0), onlineVersion.get(0))) {
                LOGGER.info(() -> "There is a newer ChromaLeague version " + onlineVersion.get(0) + " available!");
                LOGGER.info("New changes:");
                IntStream.range(1, onlineVersion.size())
                        .mapToObj(onlineVersion::get)
                        .forEach(LOGGER::info);
                LOGGER.info(() -> "Please go to " + LATEST_RELEASE_URL + " to get the newest version!");
            } else {
                LOGGER.info(() -> "Your ChromaLeague version is up to date!");
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex, () -> "Couldn't check ChromaLeague version");
        }
    }

    public List<String> getOnlineVersion() throws IOException {
        try (final CloseableHttpClient defaultHttpClient = HttpClients.createDefault()) {
            return Arrays.asList(EntityUtils.toString(
                    defaultHttpClient.execute(new HttpGet(ONLINE_VERSION_URL)).getEntity())
                    .split(System.lineSeparator()));
        }
    }

    public List<String> getLocalVersion() throws IOException {
        try (final InputStream resourceAsStream = getClass().getResourceAsStream(LOCAL_VERSION_PATH)) {
            return Arrays.asList(new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8).split(System.lineSeparator()));
        }
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
