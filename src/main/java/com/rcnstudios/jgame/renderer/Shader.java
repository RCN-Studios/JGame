package com.rcnstudios.jgame.renderer;

import com.rcnstudios.jgame.utils.FileUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Shader {
    private static String basicFragmentShader;
    private static String basicVertexShader;
    private int vertexID, fragmentID, shaderProgram, vaoID;

    public Shader() {
        basicVertexShader = FileUtils.loadAsString("assets/shaders/vertex.glsl");
        basicFragmentShader = FileUtils.loadAsString("assets/shaders/fragment.glsl");
    }

    public void compile() {
        //Compile and link shaders needed

        //Load and compile vertex shader
        vertexID = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        GL33.glShaderSource(vertexID, basicVertexShader);
        GL33.glCompileShader(vertexID);


        //check for errors
        int success = GL33.glGetShaderi(vertexID, GL33.GL_COMPILE_STATUS);
        if (success == GL33.GL_FALSE) {
            int length = GL33.glGetShaderi(vertexID, GL33.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'vertex.gls'\n\tVertex shader compilation failed");
            System.out.println(GL33.glGetShaderInfoLog(vertexID, length));
            assert false : "";

        }
        //Load and compile Fragment shader
        fragmentID = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);
        GL33.glShaderSource(fragmentID, basicFragmentShader);
        GL33.glCompileShader(fragmentID);

        //check for errors
        success = GL33.glGetShaderi(fragmentID, GL33.GL_COMPILE_STATUS);
        if (success == GL33.GL_FALSE) {
            int length = GL33.glGetShaderi(fragmentID, GL33.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'fragment.gls'\n\tFragment shader compilation failed");
            System.out.println(GL33.glGetShaderInfoLog(fragmentID, length));
            assert false : "";

        }

        //Link shaders and check for errors
        shaderProgram = GL33.glCreateProgram();
        GL33.glAttachShader(shaderProgram, vertexID);
        GL33.glAttachShader(shaderProgram, fragmentID);
        GL33.glLinkProgram(shaderProgram);

        //Check for linking errors
        success = GL33.glGetProgrami(shaderProgram, GL33.GL_LINK_STATUS);
        if (success == GL33.GL_FALSE) {
            int length = GL33.glGetProgrami(shaderProgram, GL33.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Linking of shaders failed");
            System.out.println(GL33.glGetProgramInfoLog(shaderProgram, length));
            assert false : "";

        }


    }

    public void test() {

        float[] vertexArray = {
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
        };


        //counter clockwise order
        int[] elementaArray = {
                2, 1, 0,
                0, 1, 3

        };
        int vboID, eboID;

        //Generate VAO, VBO and EBO buffer objects to be sent to GPU
        vaoID = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(vaoID);

        // Create float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        //Create VBO and upload vertexBuffer
        vboID = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboID);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        //Create int buffer of elements
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementaArray.length);
        elementBuffer.put(elementaArray).flip();

        //Create EBO and upload elementBuffer
        eboID = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboID);
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL33.GL_STATIC_DRAW);

        //vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int vertexSizeInBytes = (positionSize + colorSize) * Float.BYTES;
        GL33.glVertexAttribPointer(0, positionSize, GL33.GL_FLOAT, false, vertexSizeInBytes, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glVertexAttribPointer(1, colorSize, GL33.GL_FLOAT, false, vertexSizeInBytes, positionSize * Float.BYTES);
        GL33.glEnableVertexAttribArray(1);


    }

    public void execute() {
        //Bind Shader program
        GL33.glUseProgram(shaderProgram);

        //Bind VAO that we are using
        GL33.glBindVertexArray(vaoID);

        //Enable vertex pointers
        GL33.glEnableVertexAttribArray(0);
        GL33.glEnableVertexAttribArray(1);

        GL33.glDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0);

        //unbind
        GL33.glDisableVertexAttribArray(0);
        GL33.glDisableVertexAttribArray(1);
        GL33.glBindVertexArray(0);
        GL33.glUseProgram(0);


    }
}


