package com.shacharml.safepass.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.shacharml.safepass.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity(tableName = "password_table")
//,foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId")})
public class Password {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    private String name;
    private String password;
    private String urlToSite;
    private String img;
//    @ColumnInfo(name = "userId", index = true)
//    private int userId;

    public Password(String name, String password) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        img = String.valueOf(R.drawable.ic_favorite);
//        this.userId = userId;
    }

    public Password(String name, String password, String urlToSite, String img) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.urlToSite = urlToSite;
        this.img = img;
    }

    public Password() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlToSite() {
        return urlToSite;
    }

    public void setUrlToSite(String urlToSite) {
        this.urlToSite = urlToSite;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

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
        result.put("img", img);
        return result;
    }
}

