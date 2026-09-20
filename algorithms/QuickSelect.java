package algorithms;
import metrics.Metrics;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("List null");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k > 0 and k < a");
        }
        return selectRange(a, 0, a.length - 1, k, m);
    }

    private static int selectRange(int[] a, int low, int high, int k, Metrics m) {
        m.enter();

        if (low == high) {
            m.exit();
            return a[low];
        }

        int[] eq = Partition.partition3(a, low, high, m);
        int ltIndex = eq[0];
        int gtIndex = eq[1];

        int result;
        if (k < ltIndex) {
            result = selectRange(a, low, ltIndex - 1, k, m);
        } else if (k > gtIndex) {
            result = selectRange(a, gtIndex + 1, high, k, m);
        } else {
            result = a[k];
        }

        m.exit();
        return result;
    }
}
