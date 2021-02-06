package com.rcnstudios.jgame;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {


    private static Window window = null;

    private long glfwWindow;

    private int width, height;
    private String title;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "JGame Window";
    }

    public void run() {
        init();
        loop();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initilize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Window could not be initilized");
        }

        // Confingure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);

        //Create the window
        glfwWindow = GLFW.glfwCreateWindow(this.width,this.height,this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        // Set context to current and enable swap
        GLFW.glfwMakeContextCurrent(glfwWindow);
        GLFW.glfwSwapInterval(1);

        // Show the window & create GL capabilities after its all setup
        GLFW.glfwShowWindow(glfwWindow);
        GL.createCapabilities();
    }

    private void loop(){
        while (!GLFW.glfwWindowShouldClose(glfwWindow)) {
            GLFW.glfwPollEvents();

            GL33.glClearColor(1.0f,0.0f,0.0f,1.0f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            GLFW.glfwSwapBuffers(glfwWindow);
        }
    }

    public static Window get() {
        if (Window.window == null)
            Window.window = new Window();
        return Window.window;
    }

}
