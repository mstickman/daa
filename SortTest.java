package algorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import metrics.Metrics;
import org.junit.jupiter.api.Test;

public class SortTest {

    private final Random random = new Random(123);

    private int[] randomArray(int n, int bound) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt(bound);
        }
        return a;
    }

    @Test
    void mergeSortMatchesArraysSort() {
        for (int test = 0; test < 100; test++) {
            int[] a = randomArray(random.nextInt(500) + 1, 1000);
            int[] expected = a.clone();
            Arrays.sort(expected);

            MergeSort.sort(a, new Metrics());
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void quickSortMatchesArraysSort() {
        for (int test = 0; test < 100; test++) {
            int[] a = randomArray(random.nextInt(500) + 1, 1000);
            int[] expected = a.clone();
            Arrays.sort(expected);

            QuickSort.sort(a, new Metrics());
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void edgeCases() {
        int[][] cases = {
                {},
                {7},
                {5, 5, 5, 5, 5, 5},
                {1, 2, 3, 4, 5, 6, 7}
        };

        for (int[] original : cases) {
            int[] expected = original.clone();
            Arrays.sort(expected);

            int[] forMerge = original.clone();
            MergeSort.sort(forMerge, new Metrics());
            assertArrayEquals(expected, forMerge);

            int[] forQuick = original.clone();
            QuickSort.sort(forQuick, new Metrics());
            assertArrayEquals(expected, forQuick);
        }
    }

    @Test
    void quickSortDepthStaysSmallOnSortedInput() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        Metrics m = new Metrics();
        QuickSort.sort(a, m);

        double limit = 2 * (Math.log(n) / Math.log(2));
        assertTrue(m.getMaxDepth() <= limit,
                 m.getMaxDepth() " Limit: " + limit);
    }

    @Test
    void quickSelectFindsKthElement() {
        for (int test = 0; test < 100; test++) {
            int n = random.nextInt(300) + 1;
            int[] a = randomArray(n, 1000);
            int k = random.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int result = QuickSelect.select(a.clone(), k, new Metrics());
            assertEquals(sorted[k], result);
        }
    }

    @Test
    void quickSelectThrowsOnBadInput() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[0], 0, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, 5, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, -1, new Metrics()));
    }

    @Test
    void deterministicSelectMatchesSorted() {
        for (int test = 0; test < 50; test++) {
            int n = random.nextInt(300) + 1;
            int[] a = randomArray(n, 1000);
            int k = random.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            assertEquals(sorted[k], DeterministicSelect.select(a.clone(), k, new Metrics()));
        }
    }

    @Test
    void closestPairMatchesBruteForce() {
        for (int test = 0; test < 20; test++) {
            int n = random.nextInt(300) + 2;
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new ClosestPair.Point(random.nextInt(10000), random.nextInt(10000));
            }

            double fast = ClosestPair.findClosest(points, new Metrics());
            double slow = ClosestPair.bruteForceAll(points, new Metrics());
            assertEquals(slow, fast, 1e-9);
        }
    }
}
