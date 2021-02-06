package com.rcnstudios.jgame.utils.math.vectors;

public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }

    public boolean equals(Vector2f target) {
        if (this == target) return true;
        return this.x == target.x && this.y == target.y;
    }

}
