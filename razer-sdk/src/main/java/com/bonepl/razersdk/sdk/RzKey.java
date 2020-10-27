package com.bonepl.razersdk.sdk;

/**
 * Razer Chroma SDK keys mapping
 */
public enum RzKey {

    RZKEY_ESC(0, 1),
    RZKEY_F1(0, 3),
    RZKEY_F2(0, 4),
    RZKEY_F3(0, 5),
    RZKEY_F4(0, 6),
    RZKEY_F5(0, 7),
    RZKEY_F6(0, 8),
    RZKEY_F7(0, 9),
    RZKEY_F8(0, 10),
    RZKEY_F9(0, 11),
    RZKEY_F10(0, 12),
    RZKEY_F11(0, 13),
    RZKEY_F12(0, 14),
    RZKEY_1(1, 2),
    RZKEY_2(1, 3),
    RZKEY_3(1, 4),
    RZKEY_4(1, 5),
    RZKEY_5(1, 6),
    RZKEY_6(1, 7),
    RZKEY_7(1, 8),
    RZKEY_8(1, 9),
    RZKEY_9(1, 10),
    RZKEY_0(1, 11),
    RZKEY_A(3, 2),
    RZKEY_B(4, 7),
    RZKEY_C(4, 5),
    RZKEY_D(3, 4),
    RZKEY_E(2, 4),
    RZKEY_F(3, 5),
    RZKEY_G(3, 6),
    RZKEY_H(3, 7),
    RZKEY_I(2, 9),
    RZKEY_J(3, 8),
    RZKEY_K(3, 9),
    RZKEY_L(3, 10),
    RZKEY_M(4, 9),
    RZKEY_N(4, 8),
    RZKEY_O(2, 10),
    RZKEY_P(2, 11),
    RZKEY_Q(2, 2),
    RZKEY_R(2, 5),
    RZKEY_S(3, 3),
    RZKEY_T(2, 6),
    RZKEY_U(2, 8),
    RZKEY_V(4, 6),
    RZKEY_W(2, 3),
    RZKEY_X(4, 4),
    RZKEY_Y(2, 7),
    RZKEY_Z(4, 3),
    RZKEY_NUMLOCK(1, 18),
    RZKEY_NUMPAD0(5, 19),
    RZKEY_NUMPAD1(4, 18),
    RZKEY_NUMPAD2(4, 19),
    RZKEY_NUMPAD3(4, 20),
    RZKEY_NUMPAD4(3, 18),
    RZKEY_NUMPAD5(3, 19),
    RZKEY_NUMPAD6(3, 20),
    RZKEY_NUMPAD7(2, 18),
    RZKEY_NUMPAD8(2, 19),
    RZKEY_NUMPAD9(2, 20),
    RZKEY_NUMPAD_DIVIDE(1, 19),
    RZKEY_NUMPAD_MULTIPLY(1, 20),
    RZKEY_NUMPAD_SUBTRACT(1, 21),
    RZKEY_NUMPAD_ADD(2, 21),
    RZKEY_NUMPAD_ENTER(4, 21),
    RZKEY_NUMPAD_DECIMAL(5, 20),
    RZKEY_PRINTSCREEN(0, 15),
    RZKEY_SCROLL(0, 16),
    RZKEY_PAUSE(0, 17),
    RZKEY_INSERT(1, 15),
    RZKEY_HOME(1, 16),
    RZKEY_PAGEUP(1, 17),
    RZKEY_DELETE(2, 15),
    RZKEY_END(2, 16),
    RZKEY_PAGEDOWN(2, 17),
    RZKEY_UP(4, 16),
    RZKEY_LEFT(5, 15),
    RZKEY_DOWN(5, 16),
    RZKEY_RIGHT(5, 17),
    RZKEY_TAB(2, 1),
    RZKEY_CAPSLOCK(3, 1),
    RZKEY_BACKSPACE(1, 14),
    RZKEY_ENTER(3, 14),
    RZKEY_LCTRL(5, 1),
    RZKEY_LWIN(5, 2),
    RZKEY_LALT(5, 3),
    RZKEY_SPACE(5, 7),
    RZKEY_RALT(5, 11),
    RZKEY_FN(5, 12),
    RZKEY_RMENU(5, 13),
    RZKEY_RCTRL(5, 14),
    RZKEY_LSHIFT(4, 1),
    RZKEY_RSHIFT(4, 14),
    RZKEY_MACRO1(1, 0),
    RZKEY_MACRO2(2, 0),
    RZKEY_MACRO3(3, 0),
    RZKEY_MACRO4(4, 0),
    RZKEY_MACRO5(5, 0),
    RZKEY_TILDE(1, 1), // ~ `
    RZKEY_HYPHEN(1, 12), // - _
    RZKEY_EQUALS(1, 13), // = +
    RZKEY_SQUARE_BRACKET_LEFT(2, 12), // [ {
    RZKEY_SQUARE_BRACKET_RIGHT(2, 13), // ] }
    RZKEY_BACKSLASH(2, 14), // \ |
    RZKEY_SEMICOLON(3, 11), // ; :
    RZKEY_APOSTROPHE(3, 12), // ' "
    RZKEY_COMA(4, 10), // , <
    RZKEY_DOT(4, 11), // . >
    RZKEY_SLASH(4, 12), // / ?
    RZKEY_EUR_1(3, 13),
    RZKEY_EUR_2(4, 2),
    RZKEY_JPN_1(0, 21),
    RZKEY_JPN_2(4, 13),
    RZKEY_JPN_3(5, 4),
    RZKEY_JPN_4(5, 9),
    RZKEY_JPN_5(5, 10),
    RZKEY_KOR_1(0, 21),
    RZKEY_KOR_2(3, 13),
    RZKEY_KOR_3(4, 2),
    RZKEY_KOR_4(4, 13),
    RZKEY_KOR_5(5, 4),
    RZKEY_KOR_6(5, 9),
    RZKEY_KOR_7(5, 10);

    private final int row;
    private final int column;

    RzKey(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
