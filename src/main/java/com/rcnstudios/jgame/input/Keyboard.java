package com.rcnstudios.jgame.input;

import org.lwjgl.glfw.GLFW;

public class Keyboard {

    private static Keyboard instance;

    private boolean keyPressed[] = new boolean[350];

    private Keyboard() { }

    public static Keyboard get() {
        if (Keyboard.instance == null)
            Keyboard.instance = new Keyboard();
        return Keyboard.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if (action == GLFW.GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW.GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < get().keyPressed.length)
            return get().keyPressed[keyCode];
        return false;
    }

}
