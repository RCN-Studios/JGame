package com.rcnstudios.jgame;

import org.lwjgl.Version;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello JGame! Using LWJGL version " + Version.getVersion());
        Window.get().run();
    }

}
