#include "pch.h"
#include <jni.h>
#include "processors_ProcessorNative.h"

JNIEXPORT jobjectArray JNICALL Java_processors_ProcessorNative_convolve
(JNIEnv* env, jobject obj, jobjectArray input, jobjectArray kernel) {
    // Get the size of the input array and the kernel array
    jsize inputHeight = env->GetArrayLength(input);
    jsize inputWidth = env->GetArrayLength(static_cast<jdoubleArray>(env->GetObjectArrayElement(input, 0)));
    jsize kernelHeight = env->GetArrayLength(kernel);
    jsize kernelWidth = env->GetArrayLength(static_cast<jdoubleArray>(env->GetObjectArrayElement(kernel, 0)));

    // Create a new output array
    jobjectArray output = env->NewObjectArray(inputHeight, env->FindClass("[D"), nullptr);

    // Allocate memory for the input and kernel arrays
    jdouble** inputArray = new jdouble * [inputHeight];
    jdouble** kernelArray = new jdouble * [kernelHeight];
    for (int i = 0; i < inputHeight; i++) {
        inputArray[i] = new jdouble[inputWidth];
        jdoubleArray row = static_cast<jdoubleArray>(env->GetObjectArrayElement(input, i));
        env->GetDoubleArrayRegion(row, 0, inputWidth, inputArray[i]);
    }
    for (int i = 0; i < kernelHeight; i++) {
        kernelArray[i] = new jdouble[kernelWidth];
        jdoubleArray row = static_cast<jdoubleArray>(env->GetObjectArrayElement(kernel, i));
        env->GetDoubleArrayRegion(row, 0, kernelWidth, kernelArray[i]);
    }

    // Compute the convolution
    int paddingHeight = kernelHeight / 2;
    int paddingWidth = kernelWidth / 2;
    for (int i = 0; i < inputHeight; i++) {
        jdoubleArray row = env->NewDoubleArray(inputWidth);
        jdouble* outputRow = new jdouble[inputWidth];
        for (int j = 0; j < inputWidth; j++) {
            jdouble sum = 0.0;
            for (int m = 0; m < kernelHeight; m++) {
                for (int n = 0; n < kernelWidth; n++) {
                    int ii = i + m - paddingHeight;
                    int jj = j + n - paddingWidth;
                    if (ii >= 0 && ii < inputHeight && jj >= 0 && jj < inputWidth) {
                        sum += kernelArray[m][n] * inputArray[ii][jj];
                    }
                }
            }
            outputRow[j] = sum;
        }
        env->SetDoubleArrayRegion(row, 0, inputWidth, outputRow);
        env->SetObjectArrayElement(output, i, row);
        delete[] outputRow;
    }

    // Free the memory allocated for the input and kernel arrays
    for (int i = 0; i < inputHeight; i++) {
        delete[] inputArray[i];
    }
    delete[] inputArray;
    for (int i = 0; i < kernelHeight; i++) {
        delete[] kernelArray[i];
    }
    delete[] kernelArray;

    return output;
}
