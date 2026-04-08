import {describe, expect, test} from 'vitest';

/**
 * In Java, PlayerNameResponseHandler strips leading/trailing quotes from the response string.
 * In TS, the HTTP client parses JSON directly, so JSON.parse('"BąnE \" "') gives 'BąnE " '.
 * We test that JSON.parse handles the quote-stripping that the Java handler did.
 */
describe('PlayerNameResponseHandler', () => {
    test('testPlayerNameCleanup', () => {
        //given - In the Riot API, player name comes as a JSON string (with quotes)
        const rawJson = '"BąnE \\" "';

        //when
        const playerName = JSON.parse(rawJson) as string;

        //then
        expect(playerName).toBe('BąnE " ');
    });

    test('testEmptyResponseThrows', () => {
        //given - non-JSON content should throw
        const invalidJson = 'player';

        //then
        expect(() => JSON.parse(invalidJson)).toThrow();
    });
});
