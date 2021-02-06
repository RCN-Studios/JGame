package com.rcnstudios.jgame.utils;

public class Time {

    public static float timeStarted = System.nanoTime();

    public static float getDeltaTime() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }

}
