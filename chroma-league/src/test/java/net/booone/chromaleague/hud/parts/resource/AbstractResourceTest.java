package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.GameStateMocks;
import net.booone.chromaleague.rest.activeplayer.ChampionStats;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.when;

public abstract class AbstractResourceTest {
    protected GameStateMocks gameStateMocks;

    @BeforeEach
    void setUp() {
        gameStateMocks = new GameStateMocks();
    }

    protected void mockResource(double resource, double maxResource) {
        final ChampionStats mockedChampionStats = gameStateMocks.championStats();
        when(mockedChampionStats.resourceValue()).thenReturn(resource);
        when(mockedChampionStats.resourceMax()).thenReturn(maxResource);
    }

    protected void mockRange(double range) {
        final ChampionStats mockedChampionStats = gameStateMocks.championStats();
        when(mockedChampionStats.attackRange()).thenReturn(range);
    }
}
