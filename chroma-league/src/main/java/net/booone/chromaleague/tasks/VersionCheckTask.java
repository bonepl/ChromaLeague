package net.booone.chromaleague.tasks;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
            LOGGER.info(() -> "Running ChromaLeague version " + localVersion.getFirst());
            if (newerVersionAvailable(localVersion.getFirst(), onlineVersion.getFirst())) {
                LOGGER.info(() -> "There is a newer ChromaLeague version " + onlineVersion.getFirst() + " available!");
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
            return defaultHttpClient.execute(new HttpGet(ONLINE_VERSION_URL),
                    response -> Arrays.stream(EntityUtils.toString(response.getEntity())
                                    .split(System.lineSeparator())).map(String::strip)
                            .collect(Collectors.toList()));
        }
    }

    public List<String> getLocalVersion() throws IOException {
        try (final InputStream resourceAsStream = getClass().getResourceAsStream(LOCAL_VERSION_PATH)) {
            return Arrays.stream(new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8)
                    .split(System.lineSeparator())).map(String::strip).collect(Collectors.toList());
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
