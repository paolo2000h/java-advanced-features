package processors;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorNormalTest {
    double[][] input = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    double[][] kernel = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 2}
    };
    double[][] output = {
            {18, 26, 17},
            {26, 33, 18},
            {-13, -20, -17}
    };

    @Test
    public void testNormalProcessor() {
        for (int n = 10; n <= 100000; n *= 10) {
            long totalElapsedTime = 0;

            for (int i = 0; i < n; i++) {
                long startTime = System.nanoTime();
                assertTrue(Arrays.deepEquals(output, ProcessorNormal.convolve(input, kernel)));
                long endTime = System.nanoTime();

                long elapsedTime = endTime - startTime;
                totalElapsedTime += elapsedTime;
            }

            long averageElapsedTime = totalElapsedTime / n;
            System.out.println("For n=" + n + " Average elapsed time in nanoseconds: " + averageElapsedTime);
        }

    }


    @Test
    public void testNativeProcessor() {
        ProcessorNative processorNative = new ProcessorNative();

        for (int n = 10; n <= 100000; n *= 10) {
            long totalElapsedTime = 0;

            for (int i = 0; i < n; i++) {
                long startTime = System.nanoTime();
                assertTrue(Arrays.deepEquals(output, processorNative.convolve(input, kernel)));
                long endTime = System.nanoTime();

                long elapsedTime = endTime - startTime;
                totalElapsedTime += elapsedTime;
            }

            long averageElapsedTime = totalElapsedTime / n;
            System.out.println("For n=" + n + " Average elapsed time in nanoseconds: " + averageElapsedTime);
        }
    }


}