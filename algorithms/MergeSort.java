package algorithms;
import metrics.Metrics;

public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) {
            return;
        }
        int[] buffer = new int[a.length];
        sortPart(a, buffer, 0, a.length - 1, m);
    }

    private static void sortPart(int[] a, int[] buffer, int left, int right, Metrics m) {
        m.enter();

        if (right - left + 1 <= CUTOFF) {
            InsertionSort.sort(a, left, right, m);
            m.exit();
            return;
        }

        int mid = left + (right - left) / 2;

        sortPart(a, buffer, left, mid, m);
        sortPart(a, buffer, mid + 1, right, m);

        m.addComparison();
        if (a[mid] <= a[mid + 1]) {
            m.exit();
            return;
        }

        merge(a, buffer, left, mid, right, m);
        m.exit();
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics m) {
        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = buffer[j];
                j++;
            } else if (j > right) {
                a[k] = buffer[i];
                i++;
            } else {
                m.addComparison();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j];
                    j++;
                } else {
                    a[k] = buffer[i];
                    i++;
                }
            }
        }
    }
}
