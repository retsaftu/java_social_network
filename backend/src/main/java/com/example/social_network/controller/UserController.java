package com.example.social_network.controller;

import com.example.social_network.helpers.Token;
import com.example.social_network.models.Friend;
import com.example.social_network.models.Users;
import com.example.social_network.repository.UserRepository;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")

public class UserController {


    @Autowired
    UserRepository userRepository;
    MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(), "social_network");

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(@RequestParam(required = false) String title) {
        try {
            List<Users> users = new ArrayList<Users>();

            userRepository.findAll().forEach(users::add);
            System.out.println(users);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {

        try {
            System.out.println("register");
//             Users check = mongoOperations.findOne(
//                    Query.query(Criteria.where("email").is(user.getEmail())),
//                    Users.class,
//                    "users"
//            );
//            System.out.println(check.getEmail());
//            System.out.println(user.getEmail());
//
//            if (check.getEmail().equals(user.getEmail())) {
//                check=null;
//                return new ResponseEntity<>(check,HttpStatus.BAD_REQUEST);
//            }
            user.setPassword(Token.getToken(user.getPassword()));
            Users _user = userRepository.save(new Users(user.getName(), user.getUsername(),user.getEmail(),user.getPassword(), user.getFriends()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user) {

        try {
//            MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(), "social_network");
            user.setPassword(Token.getToken(user.getPassword()));
            Users _users = mongoOperations.findOne(
                    Query.query(Criteria.where("email").is(user.getEmail()).where("password").is(user.getPassword())),
                    Users.class,
                    "users"
            );
            System.out.println("=====================");
            System.out.println(_users);
            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
