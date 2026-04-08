import {ChromaLeague} from './ChromaLeague.js';

const chromaLeague = new ChromaLeague();

export function main(): void {
    chromaLeague.run();
}

export async function shutdown(): Promise<void> {
    await chromaLeague.shutdown();
}
