package com.example.social_network.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Array;

@Data
@Document(collection = "users")
public class Users {
    @Id
    private String _id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Friend[] friends;

    public Users(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Users(String name, String username, String email, String password,Friend[] friends) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends=friends;
    }
}
