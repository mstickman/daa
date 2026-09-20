package algorithms;
import java.util.Random;
import metrics.Metrics;

public class Partition {

    private static final Random RANDOM = new Random();

    public static int[] partition3(int[] a, int low, int high, Metrics m) {
        int pivotIndex = low + RANDOM.nextInt(high - low + 1);
        int pivot = a[pivotIndex];

        int lt = low;
        int gt = high;
        int i = low;

        while (i <= gt) {
            m.addComparison();
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else {
                m.addComparison();
                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
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
