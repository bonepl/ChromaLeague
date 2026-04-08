import {NonBlockingLeagueHttpClient} from './client/NonBlockingLeagueHttpClient.js';
import {RetryingLeagueHttpClient} from './client/RetryingLeagueHttpClient.js';

let nonBlockingClient = new NonBlockingLeagueHttpClient();
let retryingClient = new RetryingLeagueHttpClient();

export async function getNonBlockingResponse<T>(url: string): Promise<T | null> {
    return nonBlockingClient.getResponse<T>(url);
}

export async function getRetryingResponse<T>(url: string): Promise<T> {
    return retryingClient.getResponse<T>(url);
}

/** Reset client state (error suppression timers) between game sessions. */
export function resetHttpClients(): void {
    nonBlockingClient = new NonBlockingLeagueHttpClient();
    retryingClient = new RetryingLeagueHttpClient();
}
