package com.rcnstudios.jgame;

import com.rcnstudios.jgame.input.MouseListener;
import com.rcnstudios.jgame.input.KeyListener;
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

    private float r,g,b;
    private boolean fadeToBlack = false;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "JGame Window";
        this.r = 1;
        this.g = 1;
        this.b = 1;
    }

    public void run() {
        // Run the program
        init();
        loop();

        // Free the memory
        GLFW.glfwDestroyWindow(glfwWindow);

        // Terminate GLFW & free error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Window could not be initialized");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);

        //Create the window
        glfwWindow = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        // Create callbacks
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        GLFW.glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

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

            GL33.glClearColor(r,g,b, 1.0f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            if (fadeToBlack) {
                r = Math.max(r-0.001f,0);
                g = Math.max(g-0.01f,0);
                b = Math.max(b-0.1f,0);
            }

            if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                fadeToBlack = true;
            }

            GLFW.glfwSwapBuffers(glfwWindow);
        }
    }

    public static Window get() {
        if (Window.window == null)
            Window.window = new Window();
        return Window.window;
    }

}
