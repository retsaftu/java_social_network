package com.example.social_network.controller;

import com.example.social_network.models.Friend;
import com.example.social_network.models.Users;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friend")
public class FriendController {
    MyLogger<FriendController> myLogger = new MyLogger<>(FriendController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/request/{userId}")
    public ResponseEntity<List<Users>> friendRequest(@PathVariable String userId) {
        try {
            Users user1 = userRepository.findById(userId).orElse(null);

            List<Users> users = new ArrayList<Users>();

            List<Users> usersRequest = new ArrayList<Users>();
            myLogger.myLog(usersRequest.toString(), "INFO");

            userRepository.findAll().forEach(users::add);
            for (Users eachUser : users) {
                for (Friend eachFriend : eachUser.getFriends()) {
                    if (eachFriend.getUserId().equals(user1.get_id())) {
                        if (eachFriend.getStatus().equals("pending")) {
                            usersRequest.add(eachUser);
                        }
                    }
                }
            }
            return new ResponseEntity<>(usersRequest, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/requestAdd/{userId}")
    public ResponseEntity<Users> addFriendRequest(@PathVariable String userId, @RequestBody Friend friend) {
        try {
            System.out.println("+++++++++++++++++++");
            System.out.println(userId);
            System.out.println("+++++++++++++++++++");
            System.out.println(friend);
            System.out.println("+++++++++++++++++++");
             Users user1 = userRepository.findById(userId).orElse(null);
            // Users user2 = userRepository.findById(friend.getUserId()).orElse(null);
            Optional<Users> userData = userRepository.findById(userId);
            Optional<Users> friendData = userRepository.findById(friend.getUserId());
            myLogger.myLog(userData.toString(), "INFO");
            myLogger.myLog(friendData.toString(), "INFO");

            if (userData.isPresent() && friendData.isPresent()) {
                Users _user = userData.get();
                Users _friend = friendData.get();

                ArrayList<Friend> arr_friend = _user.getFriends();
                ArrayList<Friend> arr_friend_friend = _friend.getFriends();
                System.out.println("arr_friend");
                System.out.println(arr_friend);
                myLogger.myLog(arr_friend.toString(), "INFO");
                myLogger.myLog(arr_friend_friend.toString(), "INFO");

                System.out.println("arr_friend_friend");

                System.out.println(arr_friend_friend);
                for (Friend friendsss : _user.getFriends()) {
                    System.out.println("friendsss");
                    System.out.println(friendsss);
                    if (friendsss.getUserId().equals(friend.getUserId())) {
                        System.out.println("RABOTAET");
                        System.out.println(friendsss.getUserId());
                        myLogger.myLog(friendsss.getUserId(), "INFO");

                        // Syste.out.println(friendId);
                        // System.out.println(friendsss.getUserId() == friendId);
                        for (int i = 0; i <= arr_friend.size(); i++) {
                            if (arr_friend.get(i).getUserId().equals(friend.getUserId())) {
                                arr_friend.get(i).setStatus("friend");
                                System.out.println("111111111111111111111");
                            }
                        }
                        System.out.println(arr_friend);
                        _user.setFriends(arr_friend);
                        userRepository.save(_user);
                    }
                }
                System.out.println(userId);
                for (Friend friendsss_friends : _friend.getFriends()) {
                    System.out.println("||||||||||||||||||||");
                    System.out.println(_friend);
                    System.out.println(_friend.getFriends());
                    myLogger.myLog(_friend.getFriends().toString(), "INFO");
                    System.out.println("||||||||||||||||||||");
                    System.out.println("------------------");
                    System.out.println(friendsss_friends);
                    System.out.println("------------------");
                    System.out.println("friendsss_friends.getUserId()");
                    System.out.println(friendsss_friends.getUserId());
                    myLogger.myLog(friendsss_friends.getUserId(), "INFO");
                    if (friendsss_friends.getUserId().equals(userId)) {
                        System.out.println("+++========+++++");
                        for (int i = 0; i <= arr_friend_friend.size(); i++) {
                            if (arr_friend_friend.get(i).getUserId().equals(userId)) {
                                arr_friend_friend.get(i).setStatus("friend");
                                myLogger.myLog(arr_friend_friend.get(i).setStatus("friend"), "INFO");
                                System.out.println("22222222222222222222");
                            }
                        }
                        System.out.println(arr_friend_friend);
                        _friend.setFriends(arr_friend_friend);
                        userRepository.save(_friend);
                    }
                }
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            //
            // return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Users> addFriend(@PathVariable String userId, @RequestBody Friend friend) {
        try {
            Users user1 = userRepository.findById(userId).orElse(null);
            Users user2 = userRepository.findById(friend.getUserId()).orElse(null);
            Optional<Users> userData = userRepository.findById(userId);
            boolean check = false;
            System.out.println("===========================");
            System.out.println("===========START===========");
            System.out.println(user1);
            System.out.println(user2);
            System.out.println(check);
            System.out.println("friend");
            if (userData.isPresent()) {
                Users _user = userData.get();
                System.out.println("======111111111========");

                ArrayList<Friend> arr_friend = _user.getFriends();
                for (Friend friendsss : _user.getFriends()) {
                    System.out.println("======22222========");
                    // System.out.println(friendsss);
                    // System.out.println(friendsss.setStatus("notFriend"));
                    System.out.println(user2);
                    if (friendsss.getUserId().equals(user2.get_id())) {

                        System.out.println(friendsss.getUserId());
                        System.out.println(user2);
                        ArrayList<Friend> arr_friend_arr;
                        for (int i = 0; i < arr_friend.size(); i++) {
                            if (arr_friend.get(i).getUserId().equals(user2.get_id())) {
                                System.out.println("RABOTAET");
                                arr_friend.get(i).setStatus("pending");
                                check = true;
                                System.out.println(check);
                            }
                        }
                        System.out.println(arr_friend);
                        _user.setFriends(arr_friend);
                        // _user.setFriends;
                        // for (Friend templ_arr:arr_friend) {
                        // if (templ_arr.getUserId().equals(friendId)){
                        //
                        // }
                        // }
                    }
                }
                System.out.println(check);
                if (check)
                    return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);

            }
            System.out.println("===========================");
            System.out.println(check);

            if (user1 != null && user2 != null && !check) {
                user1.addFriend(friend);
                userRepository.save(user1);
                // Friend friend1 = new Friend(user1.getName(), user1.get_id(),"pending");
                // user2.addFriend(friend1);
                // userRepository.save(user2);
            }
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
