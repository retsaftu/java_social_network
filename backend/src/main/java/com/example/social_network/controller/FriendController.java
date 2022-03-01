package com.example.social_network.controller;


import com.example.social_network.models.Friend;
import com.example.social_network.models.Users;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Users> addFriend(@PathVariable String userId, @RequestBody Friend friend) {
        try {
            Users user1 = userRepository.findById(userId).orElse(null);
            Users user2 = userRepository.findById(friend.getUserId()).orElse(null);
            Optional< Users > userData = userRepository.findById(userId);
            boolean check=false;
            if (userData.isPresent()) {
                Users _user = userData.get();

                ArrayList<Friend> arr_friend =_user.getFriends();
                for (Friend friendsss:_user.getFriends()) {
//                    System.out.println(friendsss);
//                    System.out.println(friendsss.setStatus("notFriend"));

                    if (friendsss.getUserId().equals(user2)){
                        System.out.println("RABOTAET");
                        System.out.println(friendsss.getUserId());
                        System.out.println(user2);
                        ArrayList<Friend> arr_friend_arr;
                        for (int i=0;i<arr_friend.size();i++){
                            if (arr_friend.get(i).getUserId().equals(user2)){
                                arr_friend.get(i).setStatus("pending");
                                check=true;
                            }
                        }
                        System.out.println(arr_friend);
                        _user.setFriends(arr_friend);
//                        _user.setFriends;
//                        for (Friend templ_arr:arr_friend) {
//                            if (templ_arr.getUserId().equals(friendId)){
//
//                            }
//                        }
                    }
                }
                if (check)
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);

            }
            System.out.println(friend.getName());

            if (user1 != null && user2 != null) {
                user1.addFriend(friend);
                userRepository.save(user1);
                Friend friend1 = new Friend(user1.getName(), user1.get_id(),"pending");
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
