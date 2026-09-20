package algorithms;

import metrics.Metrics;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("list null");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("no in range: " + k);
        }
        return selectRange(a, 0, a.length - 1, k, m);
    }

    private static int selectRange(int[] a, int low, int high, int k, Metrics m) {
        m.enter();

        if (low == high) {
            m.exit();
            return a[low];
        }

        int pivot = medianOfMedians(a, low, high, m);
        int[] eq = partitionByValue(a, low, high, pivot, m);

        int result;
        if (k < eq[0]) {
            result = selectRange(a, low, eq[0] - 1, k, m);
        } else if (k > eq[1]) {
            result = selectRange(a, eq[1] + 1, high, k, m);
        } else {
            result = a[k];
        }

        m.exit();
        return result;
    }

    private static int medianOfMedians(int[] a, int low, int high, Metrics m) {
        int n = high - low + 1;

        if (n <= 5) {
            InsertionSort.sort(a, low, high, m);
            return a[low + n / 2];
        }

        int countGroups = 0;
        for (int start = low; start <= high; start += 5) {
            int end = Math.min(start + 4, high);
            InsertionSort.sort(a, start, end, m);
            int medianIndex = start + (end - start) / 2;

            swap(a, low + countGroups, medianIndex);
            countGroups++;
        }

        return selectRange(a, low, low + countGroups - 1, low + countGroups / 2, m);
    }

    private static int[] partitionByValue(int[] a, int low, int high, int pivot, Metrics m) {
        int lt = low, i = low, gt = high;
        while (i <= gt) {
            m.addComparison();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else {
                m.addComparison();
                if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
