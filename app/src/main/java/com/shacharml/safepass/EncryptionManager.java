package com.shacharml.safepass;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class EncryptionManager {
    // Algorithm used for encryption
    private static final String ALGORITHM = "AES";
    // Size of the encryption key in bits
    private static final int KEY_SIZE = 256;
    // Length of the GCM tag in bits
    private static final int GCM_TAG_LENGTH = 128;

    // Secret key for encryption and decryption
    private static SecretKey encryptionKey;

    static {
        // Generate the encryption key when the class is loaded
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            encryptionKey = keyGenerator.generateKey();
        } catch (Exception e) {
            // Throw an exception if the encryption key cannot be generated
            throw new IllegalStateException("Failed to generate encryption key", e);
        }
    }

    /**
     * Encrypt a plain text value using AES-GCM encryption.
     * @param value plain text value to be encrypted
     * @return encrypted value as a Base64 encoded string
     * @throws Exception if encryption fails
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String value) throws Exception {
        // Initialize the Cipher object for encryption
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Generate a new random IV
        byte[] iv = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        // Initialize the GCM parameter specification with the IV and GCM tag length
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, parameterSpec);

        // Encrypt the value
        byte[] cipherText = cipher.doFinal(value.getBytes());

        // Return the encrypted value as a Base64 encoded string
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Decrypt a Base64 encoded encrypted value using AES-GCM encryption.
     * @param encryptedValue Base64 encoded encrypted value
     * @return decrypted plain text value
     * @throws Exception if decryption fails
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String encryptedValue) throws Exception {
        // Initialize the Cipher object for decryption
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        // Decode the Base64 encoded encrypted value
        byte[] cipherText = Base64.getDecoder().decode(encryptedValue);
        // Initialize the GCM parameter specification with the IV and GCM tag length
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, cipherText, 0, 12);
        // Initialize the cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey, parameterSpec);

        // Decrypt the value
        byte[] decryptedValue = cipher.doFinal(cipherText, 12, cipherText.length - 12);
        // Return the decrypted value as a string
        return new String(decryptedValue);
    }
}
