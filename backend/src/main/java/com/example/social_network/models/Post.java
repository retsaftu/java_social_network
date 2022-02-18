package com.example.social_network.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String _id;
    private String name;
    private String username;
    private String userId;
    private String description;
    private Number like;
    private String visible;
    private Comment[] comments;

    public Post(String name, String username, String userId, String description, Number like, String visible, Comment[] comments) {
        this.name = name;
        this.username = username;
        this.userId = userId;
        this.description = description;
        this.like = like;
        this.visible = visible;
        this.comments = comments;
    }



//    public Post(String name, String username, String email, String password) {
//        this.name = name;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }

}
