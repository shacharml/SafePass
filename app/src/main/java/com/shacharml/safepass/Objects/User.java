package com.shacharml.safepass.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private String id;
    private String phoneNumber;
    private HashMap<String, Password> passwords;

    public User(String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.phoneNumber = phoneNumber;
        this.passwords = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HashMap<String, Password> getPasswords() {
        return passwords;
    }

    public void addPassword(Password password) {
        passwords.put(password.getId(), password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passwords=" + passwords +
                '}';
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("phoneNumber", phoneNumber);
        result.put("passwords", passwords);

        return result;
    }
}

