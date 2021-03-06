package com.example.social_network.controller;

import com.example.social_network.helpers.Token;
import com.example.social_network.models.Friend;
import com.example.social_network.models.Users;
import com.example.social_network.repository.UserRepository;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class UserController {
    MyLogger<UserController> myLogger = new MyLogger<>(UserController.class);

    @Autowired
    UserRepository userRepository;
    MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(), "social_network");

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(@RequestParam(required = false) String title) throws IOException {
        try {
            List<Users> users = new ArrayList<Users>();

            userRepository.findAll().forEach(users::add);
            System.out.println(users);
            myLogger.myLog(users.toString(), "INFO");

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Users> getUsersById(@PathVariable String userId,
            @RequestParam(required = false) String title) throws IOException {
        try {
            List<Users> users = new ArrayList<Users>();
            System.out.println(userId);
            myLogger.myLog(userId, "INFO");

            Users user = userRepository.findById(userId).orElse(null);
            System.out.println(users);
            System.out.println(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/{friendId}")
    public ResponseEntity<Users> cancelFriend(@PathVariable String userId, @PathVariable String friendId,
            @RequestParam(required = false) String title) throws IOException {
        try {
            Optional<Users> userData = userRepository.findById(userId);
            Optional<Users> friendData = userRepository.findById(friendId);
            System.out.printf(friendId);
            System.out.printf(userId);
            myLogger.myLog(userId, "INFO");
            myLogger.myLog(friendId, "INFO");

            // return new ResponseEntity<>(HttpStatus.OK);
            if (userData.isPresent()) {
                Users _user = userData.get();

                ArrayList<Friend> arr_friend = _user.getFriends();
                for (Friend friendsss : _user.getFriends()) {
                    // System.out.println(friendsss);
                    // System.out.println(friendsss.setStatus("notFriend"));

                    if (friendsss.getUserId().equals(friendId)) {
                        System.out.println("RABOTAET");
                        System.out.println(friendsss.getUserId());
                        System.out.println(friendId);
                        myLogger.myLog(friendsss.getUserId(), "INFO");

                        System.out.println(friendsss.getUserId() == friendId);
                        for (int i = 0; i < arr_friend.size(); i++) {
                            if (arr_friend.get(i).getUserId().equals(friendId)) {
                                arr_friend.get(i).setStatus("notFriend");
                            }
                        }
                        System.out.println(arr_friend);
                        _user.setFriends(arr_friend);
                        if (friendData.isPresent()) {
                            System.out.println("===========================");
                            System.out.println("===========START===========");
                            Users _friend = friendData.get();

                            ArrayList<Friend> arr_friend_friend = _friend.getFriends();
                            for (Friend friendsss_friends : _friend.getFriends()) {
                                System.out.println("======111111111========");
                                if (friendsss_friends.getUserId().equals(userId)) {
                                    System.out.println("======22222========");
                                    for (int i = 0; i < arr_friend_friend.size(); i++) {
                                        if (arr_friend_friend.get(i).getUserId().equals(userId)) {
                                            arr_friend_friend.get(i).setStatus("notFriend");
                                        }
                                    }
                                    System.out.println(arr_friend_friend);
                                    myLogger.myLog(arr_friend_friend.toString(), "INFO");

                                    _friend.setFriends(arr_friend_friend);
                                    userRepository.save(_friend);
                                    // _user.setFriends;
                                    // for (Friend templ_arr:arr_friend) {
                                    // if (templ_arr.getUserId().equals(friendId)){
                                    //
                                    // }
                                    // }
                                }
                            }
                            // _user.setFriends;
                            // for (Friend templ_arr:arr_friend) {
                            // if (templ_arr.getUserId().equals(friendId)){
                            //
                            // }
                            // }
                        }
                    }
                }
                // for (int i=0;i<_user.getFriends().size();i++){
                // System.out.println(_user.getFriends());
                // }
                // System.out.println(_user.getFriends());
                // _user.getFriends();
                // _user.setFriends(friendId);
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // List<Users> users = new ArrayList<Users>();
            //
            // Users user=userRepository.findById(userId).orElse(null);
            // System.out.println(users);
            // System.out.println(userId);
            // System.out.println(friendId);
            // if (user==null) {
            // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // }
            //
            // return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user) throws IOException {

        try {
            System.out.println("register");
            // Users check = mongoOperations.findOne(
            // Query.query(Criteria.where("email").is(user.getEmail())),
            // Users.class,
            // "users"
            // );
            // System.out.println(check.getEmail());
            // System.out.println(user.getEmail());
            //
            // if (check.getEmail().equals(user.getEmail())) {
            // check=null;
            // return new ResponseEntity<>(check,HttpStatus.BAD_REQUEST);
            // }
            user.setPassword(Token.getToken(user.getPassword()));
            Users _user = userRepository.save(new Users(user.getName(), user.getUsername(), user.getEmail(),
                    user.getPassword(), user.getFriends()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user) throws IOException {

        try {
            // MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(),
            // "social_network");
            user.setPassword(Token.getToken(user.getPassword()));
            Users _users = mongoOperations.findOne(
                    Query.query(Criteria.where("email").is(user.getEmail()).where("password").is(user.getPassword())),
                    Users.class,
                    "users");
            System.out.println("=====================");
            System.out.println(_users);
            myLogger.myLog(_users.toString(), "INFO");

            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        } catch (Exception e) {
            myLogger.myLog(e.toString(), "ERROR");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}