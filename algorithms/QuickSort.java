package algorithms;
import metrics.Metrics;

public class QuickSort {

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) {
            return;
        }
        sortRange(a, 0, a.length - 1, m);
    }

    private static void sortRange(int[] a, int low, int high, Metrics m) {
        m.enter();

        while (low < high) {
            int[] eq = Partition.partition3(a, low, high, m);
            int ltIndex = eq[0];
            int gtIndex = eq[1];

            int leftSize  = ltIndex - low;
            int rightSize = high - gtIndex;

            if (leftSize < rightSize) {
                sortRange(a, low, ltIndex - 1, m);
                low = gtIndex + 1;
            } else {
                sortRange(a, gtIndex + 1, high, m);
                high = ltIndex - 1;
            }
        }

        m.exit();
    }
}
