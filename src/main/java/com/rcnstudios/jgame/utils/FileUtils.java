package com.rcnstudios.jgame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {}

    /**
     * Load a file as a string
     *
     * @param path The path of the file, can be relational path or absolute path
     * @return A string with the contents of the file
     */
    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (true) {
                String buffer = reader.readLine();
                if (buffer == null) {
                    break;
                }

                result.append(buffer).append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Load a file line by line and return a list of lines
     *
     * @param path The path of the file, can be relational path or absolute path
     * @return A list of Strings that are the lines in a file
     */
    public static String[] loadAsList(String path) {
        return loadAsString(path).split("\n");
    }

}
