package com.example.micha.puzzle;

/**
 * With objective programming philosophy,
 * class should be parametrized but, android in most
 * cases uses integer dimensions, so that's why :)
 */
public class DimensionsInt {
    int width, height;

    DimensionsInt() {
        super();
    }

    DimensionsInt(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
