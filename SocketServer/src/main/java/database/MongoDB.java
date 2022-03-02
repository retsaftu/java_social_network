package database;

import com.mongodb.*;
import models.Comment;
import models.Friend;
import models.Post;
import models.User;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDB {
    private MongoClient mongoClient;
    private DB socialNetworkDatabase;
    private DBCollection usersCollection;
    private DBCollection postsCollection;

    public MongoDB() {
        try {
            this.mongoClient = new MongoClient();
            this.socialNetworkDatabase = mongoClient.getDB("social_network");
            this.usersCollection = socialNetworkDatabase.getCollection("users");
            this.postsCollection = socialNetworkDatabase.getCollection("posts");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DBObject createComment(String content, String username, String userId) {
        return new BasicDBObject()
                .append("content", content)
                .append("username", username)
                .append("userId", userId);
    }

    private Comment convertObjToComment(DBObject commentObj) {
        Comment comment = new Comment();
        comment.setContent((String) commentObj.get("content"));
        comment.setUsername((String) commentObj.get("username"));
        comment.setUserId(commentObj.get("userId").toString());
        return comment;
    }

    public User loginUser(String username, String password) {

        // Get all users with given username
        DBCursor potentialUsers = usersCollection.find(new BasicDBObject().append("username", username));

        // No users found
        if (!potentialUsers.hasNext())
            return null;

        DBObject foundUser = potentialUsers.next();

        // Incorrect password
        if (!foundUser.get("password").equals(password))
            return null;

        User user = new User();
        user.set_id(foundUser.get("_id").toString());
        user.setName((String) foundUser.get("name"));
        user.setUsername((String) foundUser.get("username"));
        user.setEmail((String) foundUser.get("email"));
        user.setPassword((String) foundUser.get("password"));
        user.setFriends(new ArrayList<>());

        if (foundUser.get("friends") != null) {
            List<Friend> friends = new ArrayList<>();
            List<DBObject> friendsObj = (List<DBObject>) foundUser.get("friends");
            for (DBObject friendObj : friendsObj) {
                Friend friend = new Friend();
                friend.set_id(friendObj.get("_id").toString());
                friend.setName((String) friendObj.get("name"));
                friend.setUserId((String) friendObj.get("userId"));
                friends.add(friend);
            }
            user.setFriends(friends);
        }

        return user;
    }

    public List<Post> getAllPosts() {

        List<Post> posts = new ArrayList<>();

        DBCursor postsObj = postsCollection.find();

        while (postsObj.hasNext()) {
            DBObject postObj = postsObj.next();

            Post post = new Post();
            post.set_id(postObj.get("_id").toString());
            post.setName((String) postObj.get("name"));
            post.setDescription((String) postObj.get("description"));
            post.setLike((Number) postObj.get("likes"));
            post.setVisible((String) postObj.get("visibility"));
            post.setUsername((String) postObj.get("username"));
            post.setUserId(postObj.get("userId").toString());
            post.setComments(new ArrayList<>());

            if (postObj.get("comments") != null) {
                List<Comment> comments = new ArrayList<>();
                List<DBObject> commentsObj = (List<DBObject>) postObj.get("comments");
                for (DBObject commentObj : commentsObj) {
                    comments.add(convertObjToComment(commentObj));
                }

                post.setComments(comments);
            }

            posts.add(post);
        }

        return posts;
    }

    public void createPost(Post post) {
        postsCollection.save(new BasicDBObject()
                .append("name", post.getName())
                .append("description", post.getDescription())
                .append("likes", post.getLike())
                .append("visibility", post.getVisible())
                .append("username", post.getUsername())
                .append("userId", post.getUserId())
        );
    }
}
