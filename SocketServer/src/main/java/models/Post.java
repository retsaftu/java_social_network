package models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String _id;
    private String name;
    private String description;
    private Number like;
    private String visible;
    private String username;
    private String userId;
    private List<Comment> comments;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getLike() {
        return like;
    }

    public void setLike(Number like) {
        this.like = like;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean addComment(Comment comment) {
        if (comments == null)
            comments = new ArrayList<>();

        return comments.add(comment);
    }
}