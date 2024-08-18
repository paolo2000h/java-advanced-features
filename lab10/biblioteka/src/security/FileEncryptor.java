package security;

import java.io.*;
import java.security.*;
import javax.crypto.*;

public class FileEncryptor {
    private static final String ALGORITHM = "RSA";

    public static void encryptFile(String inputFile, String outputFile, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            byte[] inputBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(inputBuffer)) >= 0) {
                byte[] outputBuffer = cipher.update(inputBuffer, 0, bytesRead);
                if (outputBuffer != null) {
                    out.write(outputBuffer);
                }
            }

            byte[] outputBuffer = cipher.doFinal();
            if (outputBuffer != null) {
                out.write(outputBuffer);
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (InputStream in = new BufferedInputStream(new FileInputStream(inputFile));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            byte[] inputBuffer = new byte[128];
            int bytesRead;

            while ((bytesRead = in.read(inputBuffer)) >= 0) {
                byte[] outputBuffer = cipher.update(inputBuffer, 0, bytesRead);
                if (outputBuffer != null) {
                    out.write(outputBuffer);
                }
            }

            byte[] outputBuffer = cipher.doFinal();
            if (outputBuffer != null) {
                out.write(outputBuffer);
            }
        }
    }
}

