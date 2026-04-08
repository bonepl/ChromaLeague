const firstFailureTime = new Map<string, number>();

/**
 * Log error only if it persists for more than 10 seconds.
 * Resets on success.
 *
 * @param key Unique key for the task/operation
 * @param error The error to log
 * @param message Custom message prefix
 */
export function logErrorSuppressed(key: string, error: any, message: string): void {
    const now = Date.now();
    const firstFail = firstFailureTime.get(key);

    if (firstFail === undefined) {
        firstFailureTime.set(key, now);
    } else if (now - firstFail > 10000) {
        console.warn(`${message} (persisting for >10s):`, error);
    }
}

/**
 * Clear failure state for a key. Call this on success.
 */
export function clearErrorSuppression(key: string): void {
    firstFailureTime.delete(key);
}
