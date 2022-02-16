package com.example.social_network.models;

import lombok.Data;

@Data
public class Comment extends Object{
    private String _id;
    private String name;
    private String content;

    public Comment() {

    }
    public Comment(String _id, String name, String content) {
        this._id = _id;
        this.name = name;
        this.content = content;
    }

}
