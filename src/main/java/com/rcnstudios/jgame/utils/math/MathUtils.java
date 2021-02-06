package com.rcnstudios.jgame.utils.math;

import com.rcnstudios.jgame.utils.math.vectors.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtils {

    private MathUtils() { }

    /**
     * <p>Clamp a float between 2 values</p>
     *
     * @param val Value to clamp
     * @param min Minimum value
     * @param max Maximum Value
     * @return Clamped value
     */
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * <p>Clamp a double between 2 values</p>
     *
     * @param val Value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return Clamped value
     */
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * <p>Get a random number between 2 values. Values that can be chosen include min & max</p>
     * @param min Minimum value
     * @param max Maximum value
     * @return Return the random value
     */
    public static int randRange(int min, int max) {
        return (int) (Math.floor(Math.random() * ((max + 1) - min)) + min);
    }

    /**
     * <p>Get a random boolean, either true or false</p>
     */
    public static boolean randBool() {
        return new Random().nextBoolean();
    }

    /**
     * <p>Get a list of points in a circle</p>
     * @param points Amount of points to return
     * @param radius Radius of circle
     * @param center Center of circle
     * @return List of points
     */
    public static Vector2f[] getCirclePoints(int points, double radius, Vector2f center) {
        List<Vector2f> pointList = new ArrayList<>();
        double slice = 2 * Math.PI / points;
        pLoop: for (int i = 0; i < points; i++) {
            double angle = slice * i;
            int newX = (int) (center.getX() + radius * Math.cos(angle));
            int newY = (int) (center.getY() + radius * Math.sin(angle));
            Vector2f p = new Vector2f(newX, newY);

            for (Vector2f point : pointList)
                if (p.equals(point)) continue pLoop;
            pointList.add(p);
        }

        return pointList.toArray(new Vector2f[0]);
    }

}
