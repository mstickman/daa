import algorithms.MergeSort;
import algorithms.QuickSelect;
import algorithms.QuickSort;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import metrics.Metrics;

public class BenchmarkRunner {

    private static final int[] SIZES = {1000, 10000, 100000, 1000_000};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    private static final String ROW_FORMAT = "%-12s %-11s %-10s %12s %14s %10s%n";

    public static void main(String[] args) {
        System.out.printf(Locale.US, ROW_FORMAT,
                "algorithm", "input", "n", "time_ms", "comparisons", "max_depth");
        System.out.println("-".repeat(75));

        for (String input : INPUTS) {
            for (int n : SIZES) {
                runCase("MergeSort", input, n);
                runCase("QuickSort", input, n);
                runCase("QuickSelect", input, n);
            }
        }
    }

    private static void runCase(String algorithm, String input, int n) {
        double[] times = new double[RUNS];
        long comparisons = 0;
        int maxDepth = 0;

        for (int run = 0; run < RUNS; run++) {
            int[] data = generate(input, n);
            Metrics m = new Metrics();

            m.startTimer();
            switch (algorithm) {
                case "MergeSort":
                    MergeSort.sort(data, m);
                    break;
                case "QuickSort":
                    QuickSort.sort(data, m);
                    break;
                case "QuickSelect":
                    QuickSelect.select(data, n / 2, m);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
            }
            m.stopTimer();

            times[run] = m.getTimeMs();
            comparisons = m.getComparisons();
            maxDepth = m.getMaxDepth();
        }

        Arrays.sort(times);
        double median = times[RUNS / 2];

        System.out.printf(Locale.US, ROW_FORMAT,
                algorithm, input, n,
                String.format(Locale.US, "%.3f", median),
                comparisons, maxDepth);
    }

    private static int[] generate(String type, int n) {
        Random random = new Random(42 + n);
        int[] a = new int[n];

        if (type.equals("random")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt();
            }
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(10);
            }
        } else {
            throw new IllegalArgumentException("Unknown type inpt: " + type);
        }
        return a;
    }
}
