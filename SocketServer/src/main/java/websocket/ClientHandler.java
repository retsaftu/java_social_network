package websocket;

import com.google.gson.Gson;
import database.MongoDB;
import models.Post;
import models.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private User client;
    private int clientId;
    private MongoDB database;
    private MyLogger<ClientHandler> myLogger;
    private Gson gson;
    private List<Post> oldPosts;
    private List<Post> currentPosts;

    public ClientHandler(Socket socket, int id) {
        myLogger = new MyLogger<>(ClientHandler.class);
        gson = new Gson();
        this.clientSocket = socket;
        database = new MongoDB();
        client = null;
        clientId = id;
        oldPosts = null;
        currentPosts = null;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String action;
            while (!isAuthorized()) {
//                sendMessage("Incorrect username or password! Please try again");
            }

            UpdateTimer updateTimer = new UpdateTimer();
            updateTimer.start();

            do {
                sendMessage("Options: /post, /update, /exit");
                action = getMessage();
                if (action != null) {
                    switch (action) {
                        case "/post" -> createPost();
                        case "/update" -> getPosts();
                        case "/exit" -> {
                            myLogger.myLog("""
                                    User logged out!
                                    Username: %s
                                    Password: %s
                                    """.formatted(client.getUsername(), client.getPassword()
                            ), "INFO");
                            client = null;
                            sendMessage("Exit the server...");
                        }
                        default -> sendMessage("Incorrect action, please try again!");
                    }
                }
            } while (!action.equals("/exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPost() throws IOException {
        Post post = new Post();
        post.setLike(0);
        post.setUsername(client.getUsername());
        post.setUserId(client.get_id());
        post.setComments(new ArrayList<>());

        sendMessage("Enter post title: ");
        post.setName(getMessage());

        sendMessage("Enter post description: ");
        post.setDescription(getMessage());

        sendMessage("[1 - friends], [2 - auth], [3 - all]");
        int postVisibilityNumber = Integer.parseInt(Objects.requireNonNull(getMessage()));

        switch (postVisibilityNumber) {
            case 1 -> post.setVisible("friends");
            case 2 -> post.setVisible("auth");
            default -> post.setVisible("all");
        }

        database.createPost(post);

        myLogger.myLog("""
                Successfully create new post!
                Title: %s
                Description: %s
                Author: %s
                Visibility: %s
                """.formatted(
                post.getName(),
                post.getDescription(),
                post.getUsername(),
                post.getVisible()
        ), "INFO");
    }

    private void getPosts() throws IOException {
        List<Post> posts = database.getAllPosts();
        String postsJson = gson.toJson(posts);
        sendMessage(postsJson);
        myLogger.myLog("""
                Successfully retrieved posts!
                %s
                """.formatted(
                postsJson
        ), "INFO");
    }

    private void getNewPosts() throws IOException {
        oldPosts = currentPosts;
        currentPosts = database.getAllPosts();

        if (currentPosts == null) {
            sendMessage("No posts found!");
            myLogger.myLog("No posts in the database found!", "INFO");
            return;
        }

        if (oldPosts == null) {
            String postsJson = gson.toJson(currentPosts);
            sendMessage(postsJson);
            myLogger.myLog("User %s got %s new posts!\n%s".formatted(
                    client.getUsername(),
                    String.valueOf(currentPosts.size()),
                    postsJson
            ), "INFO");
            return;
        }

        HashMap<String, Post> postById = new HashMap<>();
        List<Post> newPosts = new ArrayList<>();

        for (Post p : oldPosts) {
            postById.put(p.get_id(), p);
        }

        for (Post p : currentPosts) {
            if (postById.get(p.get_id()) == null) {
                newPosts.add(postById.get(p.get_id()));
            }
        }

        if (newPosts.isEmpty()) {
            sendMessage("Your posts are up to date!");
        } else {
            String postsJson = gson.toJson(newPosts);
            sendMessage(postsJson);
            myLogger.myLog("User %s got %s new posts!\n%s".formatted(
                    client.getUsername(),
                    String.valueOf(newPosts.size()),
                    postsJson
            ), "INFO");
        }
    }

    private boolean isAuthorized() throws IOException {
        sendMessage("Enter your username: ");
        String username = getMessage();

        sendMessage("Enter your password: ");
        String password = getMessage();

        client = database.loginUser(username, password);

        if (client != null) {
            myLogger.myLog("""
                    Successfully logged in!
                    Username: %s
                    Password: %s
                    """.formatted(
                    client.getUsername(),
                    client.getPassword()
            ), "INFO");
            return true;
        } else {
            myLogger.myLog("""
                    Failed to login!
                    Username: %s
                    Password: %s
                    """.formatted(username, password), "ERROR");
            return false;
        }
    }

    private String getMessage() {
        try {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Client#" + this.clientId + " -> Server: " + inputLine);
                return inputLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(String message) {
        writer.println(message);
        System.out.println("Server -> Client#" + this.clientId + ": " + message);
    }

    private class UpdateTimer extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    int secondsInterval = 5;
                    getNewPosts();
                    Thread.sleep(1000 * secondsInterval);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}