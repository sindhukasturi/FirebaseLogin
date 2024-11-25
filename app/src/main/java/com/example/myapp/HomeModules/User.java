package com.example.myapp.HomeModules;

public class User {
    private String name;
    private int age;
    private boolean premium;

    // Default constructor required for Firebase
    public User() {}

    public User(String name, int age, boolean premium) {
        this.name = name;
        this.age = age;
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isPremium() {
        return premium;
    }
}
