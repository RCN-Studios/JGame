package com.rcnstudios.jgame.input;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    private static Keyboard instance;

    private boolean keyPressed[] = new boolean[350];

    private final List<KeyboardEventListener> keyboardEventListeners = new ArrayList<>();

    private Keyboard() { }

    public static Keyboard get() {
        if (Keyboard.instance == null)
            Keyboard.instance = new Keyboard();
        return Keyboard.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        KeyboardEvent keyboardEvent = new KeyboardEvent(KeyAction.get(action), key);
        for (KeyboardEventListener keyboardEventListener : get().keyboardEventListeners) {
            keyboardEventListener.onKeyboardEvent(keyboardEvent);
        }

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

    public static void registerKeyboardEventListener(KeyboardEventListener keyboardEventListener) {
        get().keyboardEventListeners.add(keyboardEventListener);
    }

    public enum KeyAction {
        PRESS, REPEAT, RELEASE;

        public static KeyAction get(int actionID) {
            switch (actionID) {
                case GLFW.GLFW_RELEASE:
                    return RELEASE;
                case GLFW.GLFW_PRESS:
                    return PRESS;
                case GLFW.GLFW_REPEAT:
                    return REPEAT;
                default: return null;
            }
        }
    }

    public static class KeyboardEvent {
        private final KeyAction keyAction;
        private final int key;

        private KeyboardEvent(KeyAction keyAction, int key) {
            this.keyAction = keyAction;
            this.key = key;
        }

        public KeyAction getKeyAction() {
            return this.keyAction;
        }

        public int getKey() {
            return this.key;
        }
    }

    public interface KeyboardEventListener {
        void onKeyboardEvent(KeyboardEvent event);
    }

}
