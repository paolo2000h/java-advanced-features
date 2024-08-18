package processors;

import java.util.Arrays;

public class ProcessorNative {

    ProcessorNative() {
        System.loadLibrary("nativeJava");
    }

    public native double[][] convolve(double[][] input, double[][] kernel);

}
