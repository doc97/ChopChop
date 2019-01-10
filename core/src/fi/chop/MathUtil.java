package fi.chop;

public class MathUtil {

    public static double lerp(double a, double b, double t) {
        return (1 - t) * a + t * b;
    }

    public static float lerp(float a, float b, float t) {
        return (1 - t) * a + t * b;
    }

    public static double smoothStartN(double x, int n) {
        double res = 1;
        for (int i = 0; i < n; i++)
            res *= x;
        return res;
    }

    public static float smoothStartN(float x, int n) {
        float res = 1;
        for (int i = 0; i < n; i++)
            res *= x;
        return res;
    }

    public static double smoothStopN(double x, int n) {
        double res = 1;
        for (int i = 0; i < n; i++)
            res *= (1 - x);
        return res;
    }

    public static float smoothStopN(float x, int n) {
        float res = 1;
        for (int i = 0; i < n; i++)
            res *= (1 - x);
        return res;
    }
}
