package net.booone.chromaleague.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class VersionCheckTaskTest {
    private VersionCheckTask versionCheckTask;

    @BeforeEach
    void setUp() {
        versionCheckTask = new VersionCheckTask();
    }

    @Test
    void shouldNotThrowExceptionWhenUpToDate() throws Exception {
        //given
        final VersionCheckTask spy = Mockito.spy(versionCheckTask);
        doReturn(List.of("1.0.0")).when(spy).getLocalVersion();
        doReturn(List.of("1.0.0")).when(spy).getOnlineVersion();

        //when
        assertDoesNotThrow(spy::run);
    }

    @Test
    void shouldNotThrowExceptionWhenUpdateAvailable() throws Exception {
        //given
        final VersionCheckTask spy = Mockito.spy(versionCheckTask);
        doReturn(List.of("1.0.0")).when(spy).getLocalVersion();
        doReturn(List.of("2.0.0")).when(spy).getOnlineVersion();

        //when
        assertDoesNotThrow(spy::run);
    }

    @Test
    void checkOnlineVersionFile() throws IOException {
        //when
        final List<String> onlineVersion = versionCheckTask.getOnlineVersion();

        //then
        assertFalse(onlineVersion.isEmpty());
        assertTrue(onlineVersion.getFirst().matches("\\d+\\.\\d+\\.\\d+"));
    }

    @Test
    void checkLocalVersionFile() throws Exception {
        //when
        final List<String> onlineVersion = versionCheckTask.getLocalVersion();

        //then
        assertFalse(onlineVersion.isEmpty());
        assertTrue(onlineVersion.getFirst().matches("\\d+\\.\\d+\\.\\d+"));
    }

    @ParameterizedTest
    @MethodSource("versionCheckParams")
    void versionCheck(String currentVersion, String onlineVersion, boolean expectedUpdateAvailable) {
        //when
        final boolean actualUpdateAvailable = versionCheckTask.newerVersionAvailable(currentVersion, onlineVersion);

        //then
        assertEquals(expectedUpdateAvailable, actualUpdateAvailable);
    }

    private static Stream<Arguments> versionCheckParams() {
        return Stream.of(
                Arguments.of("1.0.0", "2.0.0", true),
                Arguments.of("1.1.0", "1.2.0", true),
                Arguments.of("1.0.1", "1.0.2", true),
                Arguments.of("2.0.0", "1.0.0", false),
                Arguments.of("1.1.0", "1.0.0", false),
                Arguments.of("1.0.1", "1.0.0", false),
                Arguments.of("2.0.0", "2.0.0", false));
    }
}