package com.example.social_network.controller;


import com.example.social_network.models.Friend;
import com.example.social_network.models.Users;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Users> addFriend(@PathVariable String userId, @RequestBody Friend friend) {
        try {
            System.out.println(friend.getName());
            Users user1 = userRepository.findById(userId).orElse(null);
            Users user2 = userRepository.findById(friend.getUserId()).orElse(null);
            if (user1 != null && user2 != null) {
                user1.addFriend(friend);
                userRepository.save(user1);
                Friend friend1 = new Friend(user1.getName(), user1.get_id());
                user2.addFriend(friend1);
                userRepository.save(user2);
            }
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
