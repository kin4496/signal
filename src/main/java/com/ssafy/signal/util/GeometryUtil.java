package com.ssafy.signal.util;

public class GeometryUtil {
    private final static int EARTH_RADIUS = 6371;
    private final static int NEAR_DISTANCE = 10;

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
    }

    public static Boolean isNear(double lat1, double lon1, double lat2, double lon2) {
        return getDistance(lat1, lon1, lat2, lon2) < NEAR_DISTANCE;
    }
}
