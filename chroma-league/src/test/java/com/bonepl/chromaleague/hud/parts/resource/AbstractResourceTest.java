package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.GameStateMocks;
import com.bonepl.chromaleague.rest.activeplayer.ChampionStats;
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
        when(mockedChampionStats.getResourceValue()).thenReturn(resource);
        when(mockedChampionStats.getResourceMax()).thenReturn(maxResource);
    }

    protected void mockRange(double range) {
        final ChampionStats mockedChampionStats = gameStateMocks.championStats();
        when(mockedChampionStats.getAttackRange()).thenReturn(range);
    }
}
