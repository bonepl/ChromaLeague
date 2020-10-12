package com.bonepl.chromaleague.razer.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.razer.league.json.eventdata.model.Event;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventProcessorTest {
    @Test
    void testOceanDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Water"));

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testInfernalDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Fire"));

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testCloudDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Air"));

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testMountainDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Earth"));

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testElderDragonAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockDragonEvent("Elder"));

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testHeraldAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockHeraldEvent());

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
    }

    @Test
    void testBaronAnimation() {
        //given
        final EventDataThread edt = mockDataThread(mockBaronEvent());

        //then
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            EventProcessor.processEvents(edt, razerSDKClient);
        }
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