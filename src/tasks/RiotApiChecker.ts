import net from 'node:net';

const RIOT_API_PORT = 2999;

export function checkRiotApiUp(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
        const socket = new net.Socket();
        socket.setTimeout(100);
        socket.connect(RIOT_API_PORT, 'localhost', () => {
            socket.destroy();
            resolve(true);
        });
        socket.on('error', () => {
            socket.destroy();
            resolve(false);
        });
        socket.on('timeout', () => {
            socket.destroy();
            resolve(false);
        });
    });
}
