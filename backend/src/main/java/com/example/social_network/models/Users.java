package com.example.social_network.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class Users {
    @Id
    private String _id;
    private String name;
    private String username;
    private String email;
    private String password;

    public Users(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // public Users(String name, String username, String email) {
    // }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String get_id() {
//        return _id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
}
