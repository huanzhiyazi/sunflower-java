package com.google.samples.apps.sunflower.utilities;

public final class GrowZoneUtil {
    private GrowZoneUtil() {}

    public static int getZoneForLatitude(double latitude) {
        double l = Math.abs(latitude);
        if (l <= 7.0) return 13;
        else if (l <= 14.0) return 12;
        else if (l <= 21.0) return 11;
        else if (l <= 28.0) return 10;
        else if (l <= 35.0) return 9;
        else if (l <= 42.0) return 8;
        else if (l <= 49.0) return 7;
        else if (l <= 56.0) return 6;
        else if (l <= 63.0) return 5;
        else if (l <= 70.0) return 4;
        else if (l <= 77.0) return 3;
        else if (l <= 84.0) return 2;
        else return 1;
    }
}
