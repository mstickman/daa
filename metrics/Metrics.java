package metrics;
public class Metrics {

    private long comparisons;
    private int currentDepth;
    private int maxDepth;
    private long startTime;
    private long elapsedNanos;

    public void addComparison() {
        comparisons++;
    }

    public void enter() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exit() {
        currentDepth--;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        elapsedNanos = System.nanoTime() - startTime;
    }

    public double getTimeMs() {
        return elapsedNanos / 1000000.0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = 0;
        elapsedNanos = 0;
    }

    @Override
    public String toString() {
        return "time=" + getTimeMs() + "ms, comparisons=" + comparisons + ", maxDepth=" + maxDepth;
    }
}
