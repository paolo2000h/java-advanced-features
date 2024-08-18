package security;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

public class KeyUtils {
    public static PublicKey loadPublicKeyFromFile(String publicKeyFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyFilePath));
        String publicKeyPEM = new String(keyBytes);

        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

        publicKeyPEM = publicKeyPEM.replaceAll("\\s", "");

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey loadPrivateKeyFromFile(String privateKeyFilePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
        String privateKeyPEM = new String(keyBytes);

        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");

        privateKeyPEM = privateKeyPEM.replaceAll("\\s", "");

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
