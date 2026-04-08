import {Frame} from './Frame.js';

/**
 * Single animation frame interface
 */
export interface IFrame {
    /** Check if frame is available */
    hasFrame(): boolean;

    /** Return next animation Frame to draw */
    pollFrame(): Frame;
}
