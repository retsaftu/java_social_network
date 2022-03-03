package com.example.social_network.models;

import lombok.Data;

@Data
public class Comment extends Object {
    // private String name;
    private String content;
    private String userId;
    private String username;
    private String name;

    public Comment() {

    }

    public Comment(String content, String userId, String username,String name) {
        // this.name = name;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.name=name;
    }

}
