package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.league.json.eventdata.model.Event;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventProcessorTest {
    private static RazerSDKClient razerSDKClient;

    @BeforeAll
    static void beforeAll() {
        razerSDKClient = new RazerSDKClient();
    }

    @AfterAll
    static void afterAll() {
        razerSDKClient.close();
    }

    @Test
    void testOceanDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Water"));

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testInfernalDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Fire"));

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testCloudDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Air"));

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testMountainDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Earth"));

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testElderDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Elder"));

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testHeraldAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockHeraldEvent());

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    @Test
    void testBaronAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockBaronEvent());

        //then
        EventProcessor.processEvents(edt, razerSDKClient);
    }

    private Event mockDragonEvent(String dragonType) {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("DragonKill");
        when(mock.getDragonType()).thenReturn(dragonType);
        return mock;
    }

    private Event mockHeraldEvent() {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("HeraldKill");
        return mock;
    }

    private Event mockBaronEvent() {
        final Event mock = mock(Event.class);
        when(mock.getEventName()).thenReturn("BaronKill");
        return mock;
    }

    private EventDataThread mockDataThread(Event event) {
        final EventDataThread mock = mock(EventDataThread.class);
        when(mock.pollNextUnprocessedEvent()).thenReturn(event);
        when(mock.hasUnprocessedEvents()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        return mock;
    }

}