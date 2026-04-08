import https from 'node:https';
import {clearErrorSuppression, logErrorSuppressed} from '../../../tasks/TaskErrorUtils.js';
import {RunningState} from '../../../state/RunningState.js';

const agent = new https.Agent({ rejectUnauthorized: false });

export class NonBlockingLeagueHttpClient {
    async getResponse<T>(url: string): Promise<T | null> {
        try {
            const response = await httpGet<T>(url);
            clearErrorSuppression(url);
            return response;
        } catch (e) {
            if (RunningState.getRiotApi().getValue()) {
                const error = e as NodeJS.ErrnoException;
                const isConnectionError = error.code === 'ECONNRESET' || error.code === 'ETIMEDOUT' || error.code === 'ECONNREFUSED';

                if (!isConnectionError) {
                    const isEventData404 = url.endsWith('/eventdata') && (error.message === 'HTTP 404' || error.message?.includes('404'));

                    if (!isEventData404) {
                        logErrorSuppressed(url, e, `NonBlockingLeagueHttpClient error for ${url}`);
                    }
                }
            }
            // This client can fail silently - it shouldn't block the application
            return null;
        }
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
                try {
                    resolve(JSON.parse(data) as T);
                } catch (e) {
                    reject(e);
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
