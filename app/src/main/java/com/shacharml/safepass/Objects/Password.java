package com.shacharml.safepass.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Password {
    private String id;
    private String name;
    private String password;
    private String urlToSite;
    private String img;

    public Password(String name, String password, String urlToSite, String img) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.urlToSite = urlToSite;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Password setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Password setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUrlToSite() {
        return urlToSite;
    }

    public Password setUrlToSite(String urlToSite) {
        this.urlToSite = urlToSite;
        return this;
    }

    public String getImg() {
        return img;
    }

    public Password setImg(String img) {
        this.img = img;
        return this;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", urlToSite='" + urlToSite + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("password", password);
        result.put("urlToSite", urlToSite);
        result.put("img" , img);
        return result;
    }
}

