package net.thumbtack.asurovenko.trainee;

import net.thumbtack.asurovenko.trainee.exceptions.ColorException;

public enum Color {
    RED, GREEN, BLUE, YELLOW, WHITE, BLACK;

    public static Color chooseColor(String color) throws ColorException {
        try {
            return Color.valueOf(color);
        } catch (IllegalArgumentException ex) {
            throw new ColorException("Wrong color name: " + color);
        }
    }
}
