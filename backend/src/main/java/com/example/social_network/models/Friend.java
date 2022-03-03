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
    private String status;

    public Friend(String name, String userId, String status) {
        this.name = name;
        this.userId = userId;
        this.status = status;

    }

    public String setStatus(String status) {
        this.status = status;
        return status;
    }

    public String getUserId(String userId) {
        this.userId = userId;
        return userId;
    }

}
