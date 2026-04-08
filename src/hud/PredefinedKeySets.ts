import {RzKey} from '../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../razer-sdk/sdk/RzKeySelector.js';

export const BLACKWIDOW_FIRST_ROW: RzKey[] = new RzKeySelector().withRow(0).sortedByColumn().asList();
export const BLACKWIDOW_SECOND_ROW: RzKey[] = new RzKeySelector().withRow(1).sortedByColumn().asList();
export const BLACKWIDOW_THIRD_ROW: RzKey[] = new RzKeySelector().withRow(2).sortedByColumn().asList();
export const BLACKWIDOW_FOURTH_ROW: RzKey[] = new RzKeySelector().withRow(3).sortedByColumn().asList();
export const BLACKWIDOW_FIFTH_ROW: RzKey[] = new RzKeySelector().withRow(4).sortedByColumn().asList();
export const BLACKWIDOW_SIXTH_ROW: RzKey[] = new RzKeySelector().withRow(5).sortedByColumn().asList();
export const BLACKWIDOW_FUNCTIONAL: RzKey[] = new RzKeySelector().withRectangleBetween(RzKey.RZKEY_PRINTSCREEN, RzKey.RZKEY_PAGEDOWN).asList();
export const FIRST_NUMPAD_COLUMN: RzKey[] = new RzKeySelector().withColumn(18).sortedByRow().asList();
export const SECOND_NUMPAD_COLUMN: RzKey[] = new RzKeySelector().withColumn(19).sortedByRow().asList();
export const THIRD_NUMPAD_COLUMN: RzKey[] = new RzKeySelector().withColumn(20).sortedByRow().asList();
export const FOURTH_NUMPAD_COLUMN: RzKey[] = new RzKeySelector().withColumn(21).sortedByRow().asList();
