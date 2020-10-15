package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;

class EventProcessorTest {
    private static RazerSDKClient razerSDKClient;

//    @BeforeAll
//    static void beforeAll() {
//        razerSDKClient = new RazerSDKClient();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        razerSDKClient.close();
//    }
//
//    @Test
//    void testAllyOceanDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Water"));
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyInfernalDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Fire"));
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyCloudDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Air"));
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyMountainDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Earth"));
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyElderDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Elder"));
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyHeraldAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockHeraldEvent());
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testAllyBaronAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockBaronEvent());
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyOceanDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Water"));
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyInfernalDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Fire"));
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyCloudDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Air"));
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyMountainDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Earth"));
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyElderDragonAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockDragonEvent("Elder"));
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyHeraldAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockHeraldEvent());
//        mockPlayerList(false);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    @Test
//    void testEnemyBaronAnimation() {
//        //given
//        final EventDataThread edt = mockDataThread(mockBaronEvent());
//        mockPlayerList(true);
//
//        //then
//        EventProcessor.processEvents(edt, razerSDKClient);
//    }
//
//    private Event mockDragonEvent(String dragonType) {
//        final Event mock = mock(Event.class);
//        when(mock.getEventName()).thenReturn("DragonKill");
//        when(mock.getDragonType()).thenReturn(dragonType);
//        return mock;
//    }
//
//    private Event mockHeraldEvent() {
//        final Event mock = mock(Event.class);
//        when(mock.getEventName()).thenReturn("HeraldKill");
//        return mock;
//    }
//
//    private Event mockBaronEvent() {
//        final Event mock = mock(Event.class);
//        when(mock.getEventName()).thenReturn("BaronKill");
//        return mock;
//    }
//
//    private EventDataThread mockDataThread(Event event) {
//        final EventDataThread mock = mock(EventDataThread.class);
//        when(mock.pollNextUnprocessedEvent()).thenReturn(event);
//        when(mock.hasUnprocessedEvents()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
//        return mock;
//    }
//
//    private void mockPlayerList(boolean isAlly) {
//        final PlayerList mock = mock(PlayerList.class);
//        when(mock.isAlly(any())).thenReturn(isAlly);
//        GameState.setPlayerList(mock);
//    }
}