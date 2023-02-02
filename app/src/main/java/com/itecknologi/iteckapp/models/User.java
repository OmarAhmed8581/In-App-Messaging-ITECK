package com.itecknologi.iteckapp.models;


public class User {

    String _id;
    String name;

    String role;

    String userId;

    public User(String _id, String name, String role) {
        this._id = _id;
        this.name = name;
        this.role = role;
    }

    public User(String _id, String name, String role, String userId) {
        this._id = _id;
        this.name = name;
        this.role = role;
        this.userId = userId;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }
}