package websocket;

import org.json.JSONObject;
import com.google.gson.Gson;
import database.MongoDB;
import models.Post;
import models.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
//    private List<Post> oldPosts;
//    private List<Post> newPosts;

    public ClientHandler(Socket socket, int id) {
        myLogger = new MyLogger<>(ClientHandler.class);
        gson = new Gson();
        this.clientSocket = socket;
        database = new MongoDB();
        client = null;
        clientId = id;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String action;
            while (!isAuthorized()) {
                sendMessage("Incorrect username or password! Please try again");
            }

//            UpdateTimer updateTimer = new UpdateTimer();
//            updateTimer.start();

            do {
                sendMessage("Options: /post, /update, /exit");
                action = getMessage();
                if (action != null) {
                    switch (action) {
                        case "/post" -> createPost();
                        case "/update" -> getNewPosts();
                        case "/exit" -> sendMessage("Exit the server...");
                        default -> sendMessage("Incorrect action, please try again!");
                    }
                }
            } while (!action.equals("/exit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPost() throws IOException {
        myLogger.myLog("Post created", "INFO");
        Post post = new Post();
        post.setLike(0);
        post.setUsername(client.getUsername());
        post.setUserId(client.get_id());
        post.setComments(new ArrayList<>());

        sendMessage("Enter post heading: ");
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
    }

    private void getNewPosts() {
        List<Post> posts = database.getAllPosts();
        String postsJson = gson.toJson(posts);
        System.out.println(new JSONObject(posts).toString(4));
        sendMessage(postsJson);
    }

    private boolean isAuthorized() {
        sendMessage("Enter your username: ");
        String username = getMessage();

        sendMessage("Enter your password: ");
        String password = getMessage();

        client = database.loginUser(username, password);

        return client != null;
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

//    private class UpdateTimer extends Thread {
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    int secondsInterval = 5;
//                    getNewPosts();
//                    Thread.sleep(1000 * secondsInterval);
//                } catch (InterruptedException | IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}