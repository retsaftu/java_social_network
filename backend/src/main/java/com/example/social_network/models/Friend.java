package com.example.social_network.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "posts")
public class Friend {
    @Id
    private String _id;
    private String name;
    private String userId;

    public Friend(String name,  String userId) {
        this.name = name;
        this.userId = userId;
    }

}
