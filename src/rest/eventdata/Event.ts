export interface Event {
    EventID: number;
    EventName: string;
    EventTime: number;
    DragonType?: string;
    KillerName?: string;
    Result?: string;
    VictimName?: string;
    Assisters?: string[];
}
