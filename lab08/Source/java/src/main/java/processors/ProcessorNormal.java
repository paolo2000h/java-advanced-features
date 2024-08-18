package processors;

public class ProcessorNormal {

    public static double[][] convolve(double[][] input, double[][] kernel) {
        int inputHeight = input.length;
        int inputWidth = input[0].length;
        int kernelHeight = kernel.length;
        int kernelWidth = kernel[0].length;
        int paddingHeight = kernelHeight / 2;
        int paddingWidth = kernelWidth / 2;

        if (kernelHeight % 2 == 0 || kernelWidth % 2 == 0) {
            throw new IllegalArgumentException("Kernel dimensions must be odd.");
        }

        double[][] paddedInput = new double[inputHeight + 2 * paddingHeight][inputWidth + 2 * paddingWidth];

        for (int i = 0; i < inputHeight; i++) {
            for (int j = 0; j < inputWidth; j++) {
                paddedInput[i + paddingHeight][j + paddingWidth] = input[i][j];
            }
        }
        double[][] output = new double[inputHeight][inputWidth];

        for (int i = 0; i < inputHeight; i++) {
            for (int j = 0; j < inputWidth; j++) {
                double sum = 0.0;
                for (int m = 0; m < kernelHeight; m++) {
                    for (int n = 0; n < kernelWidth; n++) {
                        sum += kernel[m][n] * paddedInput[i + m][j + n];
                    }
                }
                output[i][j] = sum;
            }
        }
        return output;
    }
}