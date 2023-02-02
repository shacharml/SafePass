package com.shacharml.safepass.Objects;

public class Company {

    private int id;
    private int icon;
    private String name;

    public Company(int id, int icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public Company setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", icon=" + icon +
                ", name='" + name + '\'' +
                '}';
    }
}
