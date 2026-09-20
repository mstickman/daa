package algorithms;

import java.util.Arrays;
import java.util.Comparator;
import metrics.Metrics;

public class ClosestPair {

    public static class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double findClosest(Point[] points, Metrics m) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Minimum 2 dots");
        }
        Point[] byX = points.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        return solve(byX, 0, byX.length - 1, m);
    }

    private static double solve(Point[] p, int low, int high, Metrics m) {
        m.enter();

        int n = high - low + 1;
        if (n <= 3) {
            double best = bruteForce(p, low, high, m);
            m.exit();
            return best;
        }

        int mid = (low + high) / 2;
        double midX = p[mid].x;

        double dLeft = solve(p, low, mid, m);
        double dRight = solve(p, mid + 1, high, m);
        double d = Math.min(dLeft, dRight);

        Point[] strip = new Point[n];
        int size = 0;
        for (int i = low; i <= high; i++) {
            m.addComparison();
            if (Math.abs(p[i].x - midX) < d) {
                strip[size++] = p[i];
            }
        }
        Arrays.sort(strip, 0, size, Comparator.comparingDouble(q -> q.y));

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && j <= i + 7; j++) {
                m.addComparison();
                if (strip[j].y - strip[i].y >= d) {
                    break;
                }
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }

        m.exit();
        return d;
    }

    private static double bruteForce(Point[] p, int low, int high, Metrics m) {
        double best = Double.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                m.addComparison();
                best = Math.min(best, distance(p[i], p[j]));
            }
        }
        return best;
    }

    public static double bruteForceAll(Point[] points, Metrics m) {
        return bruteForce(points, 0, points.length - 1, m);
    }

    private static double distance(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
