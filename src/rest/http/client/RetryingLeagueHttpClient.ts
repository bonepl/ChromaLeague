import https from 'node:https';
import {RunningState} from '../../../state/RunningState.js';

const agent = new https.Agent({ rejectUnauthorized: false });
const MAX_RETRIES = 5;
const RETRY_DELAY_MS = 1000;

export class RetryingLeagueHttpClient {
    async getResponse<T>(url: string): Promise<T> {
        let lastError: unknown;
        for (let attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                return await httpGet<T>(url);
            } catch (e) {
                if (!RunningState.getRiotApi().getValue()) {
                    throw new Error(`Retrying client stopped because Riot API is down: ${url}`, { cause: e });
                }
                lastError = e;
                if (attempt < MAX_RETRIES) {
                    await sleep(RETRY_DELAY_MS);
                }
            }
        }
        throw new Error(`Retrying client exhausted all ${MAX_RETRIES} retries for ${url}`, { cause: lastError });
    }
}

function httpGet<T>(url: string): Promise<T> {
    return new Promise((resolve, reject) => {
        const req = https.get(url, { agent, headers: { 'Content-Type': 'application/json; charset=UTF-8' } }, (res) => {
            if (res.statusCode !== 200) {
                res.resume();
                reject(new Error(`HTTP ${res.statusCode}`));
                return;
            }
            let data = '';
            res.on('data', (chunk) => { data += chunk; });
            res.on('end', () => {
                if (!data) {
                    reject(new Error('Empty response body'));
                    return;
                }
                try {
                    resolve(JSON.parse(data) as T);
                } catch (e) {
                    reject(new Error(`JSON parse failed (${data.length} chars): ${e}`));
                }
            });
        });
        req.on('error', reject);
        req.setTimeout(2000, () => {
            req.destroy();
            reject(Object.assign(new Error('Request timed out'), { code: 'ETIMEDOUT' }));
        });
    });
}

function sleep(ms: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, ms));
}
