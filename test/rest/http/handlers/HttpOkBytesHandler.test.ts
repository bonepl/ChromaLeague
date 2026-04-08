import {describe, expect, test} from 'vitest';
import {NonBlockingLeagueHttpClient} from '../../../../src/rest/http/client/NonBlockingLeagueHttpClient.js';

/**
 * In Java, HttpOkBytesHandler returns Optional<byte[]> for OK status, empty for others.
 * In TS, NonBlockingLeagueHttpClient returns T|null -- null on any error (non-200, network, etc).
 */
describe('HttpOkBytesHandler', () => {
    test('shouldReturnNullOnNetworkError', async () => {
        //given
        const client = new NonBlockingLeagueHttpClient();

        //when - requesting invalid URL should fail silently
        const result = await client.getResponse<string>('https://127.0.0.1:1/nonexistent');

        //then
        expect(result).toBeNull();
    });

    test('nonBlockingClientShouldBeConstructable', () => {
        //given
        const client = new NonBlockingLeagueHttpClient();

        //then
        expect(client).toBeDefined();
    });
});
