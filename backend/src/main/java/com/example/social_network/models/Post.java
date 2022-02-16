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
    private String description;
    private Number like;
    private String visible;
    private Comment[] comments;

}
