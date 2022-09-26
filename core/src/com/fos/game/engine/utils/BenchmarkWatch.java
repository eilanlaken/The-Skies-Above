package com.fos.game.engine.utils;

public class BenchmarkWatch {

    public static final int DEFAULT_TARGET_FPS = 60;
    public static final double STANDARD_FRAME_TIME = 1.0 / DEFAULT_TARGET_FPS;

    public static long t1;
    public static long t2;

    public static void start() {
        t1 = System.nanoTime();
    }

    public static void stop() {
        t2 = System.nanoTime();
    }

    public static void printFrameBudget(int targetFps) {
        double elapsedTimeInSecond = (double) (t2-t1) / 1000000000;
        double frameTime = 1.0 / targetFps;
        String result = String.format("%.2f", 100 * elapsedTimeInSecond / frameTime);
        System.out.println(result + "%");
    }

    public static void printFrameDurationInSeconds() {
        double elapsedTimeInSecond = (double) (t2-t1) / 1000000000;
        System.out.println(elapsedTimeInSecond);
    }

}
