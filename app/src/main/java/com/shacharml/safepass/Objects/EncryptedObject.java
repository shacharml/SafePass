package com.shacharml.safepass.Objects;

public class EncryptedObject {

    private String key;
    private String encryptedData;

    public EncryptedObject(String key, String encryptedData) {
        this.key = key;
        this.encryptedData = encryptedData;
    }

    public String getKey() {
        return key;
    }

    public EncryptedObject setKey(String key) {
        this.key = key;
        return this;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public EncryptedObject setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
        return this;
    }

    @Override
    public String toString() {
        return "EncryptedObject{" +
                "key='" + key + '\'' +
                ", encryptedData='" + encryptedData + '\'' +
                '}';
    }
}
