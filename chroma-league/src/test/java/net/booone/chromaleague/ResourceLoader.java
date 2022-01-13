package net.booone.chromaleague;

import com.jsoniter.JsonIterator;
import net.booone.chromaleague.rest.eventdata.Event;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public final class ResourceLoader {
    private ResourceLoader() {
    }

    public static Event eventFromJson(String fileName) {
        return JsonIterator.deserialize(readJsonEvent(fileName), Event.class);
    }

    private static String readJsonEvent(String fileName) {
        return readResource("json/events/" + fileName);
    }

    private static String readResource(String resourceName) {
        try {
            return Files.readString(Paths.get(Objects.requireNonNull(ResourceLoader.class.getClassLoader().getResource(resourceName)).toURI()));
        } catch (IOException | URISyntaxException ex) {
            throw new AssertionError("Couldn't read test resource ", ex);
        }
    }
}
