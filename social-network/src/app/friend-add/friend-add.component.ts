import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-friend-add',
  templateUrl: './friend-add.component.html',
  styleUrls: ['./friend-add.component.css']
})
export class FriendAddComponent implements OnInit {

  friends: any;
  friends_list: [];
  friends_all: [
    name: any
  ];
  current_user: any;

  constructor() { }

  async ngOnInit() {
    let arr: [
      name: any
    ];
    let friends_list_arr: [
      name: any
    ];
    // let userInfo = localStorage.getItem("userInfo");
    this.current_user = JSON.parse(localStorage.getItem("userInfo") || '{}').user;
    //http://localhost:3000/auth/users
    this.friends_list = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users/${(this.current_user._id)}`,
    })).data.friends;
    friends_list_arr = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users/${(this.current_user._id)}`,
    })).data.friends;
    this.friends_all = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users`,
    })).data;
    arr = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users`,
    })).data;
    arr.forEach((part, index) => {
      this.friends_all[index].status = 'notFriend'

      friends_list_arr.forEach((friends_list_arr_part, friends_list_arr_index) => {
        // console.log('index', index);
        // console.log('part', part);
        // console.log('type', typeof (arr[index]));
        // console.log(friends_list_arr_part.status);
        // console.log(friends_list_arr_part.);
        // this.friends_all[index].status = 'notFriend'
        if (friends_list_arr_part.status == 'pending') {
          if (part._id == friends_list_arr_part.userId) {
            console.log('pending');
            this.friends_all[index].status = 'pending'

          }
        } else if (friends_list_arr_part.status == 'friend') {
          if (part._id == friends_list_arr_part.userId) {
            console.log('friend');
            this.friends_all[index].status = 'friend'

          }
        }
        // this.friends_all[index].status = 'asdf'
        // this[index] = "hello world";
      }, friends_list_arr);
      // console.log('index', index);
      // console.log('part', part);
      // console.log('type', typeof (arr[index]));
      // console.log(arr[index].status);
      // this.friends_all[index].status = 'asdf'
      // this[index] = "hello world";
    }, arr);
    this.friends = this.friends_all;
  }
  async add(friend: any) {
    console.log("current_user", this.current_user);

    console.log(friend);
    console.log(this.current_user.name);
    console.log(this.current_user._id);
    // let com: any;
    // com.name = this.current_user.name;
    // com.userId = this.current_user._id;
    // console.log('com', com);




    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/friend/${friend._id}`,
      data: { name: this.current_user.name, userId: this.current_user._id }
    })).data;
    this.friends.forEach((part: any, index: any) => {
      console.log(this.friends[index]);
      if (this.friends[index]._id == friend._id) {
        this.friends[index].status = 'pending';
        console.log('equeal');

      }

    }, this.friends)

  }
  async cancelPending(friend: any) {
    console.log("current_user", this.current_user);

    console.log(friend);
    console.log(this.current_user.name);
    console.log(this.current_user._id);
    // let com: any;
    // com.name = this.current_user.name;
    // com.userId = this.current_user._id;
    // console.log('com', com);




    const result = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users/${(this.current_user._id)}/${(friend._id)}`,
    })).data.friends;
    this.friends.forEach((part: any, index: any) => {
      console.log(this.friends[index]);
      if (this.friends[index]._id == friend._id) {
        this.friends[index].status = 'notFriend';
        console.log('notFriend');

      }

    }, this.friends)

  }
  async delete(friend: any) {
    console.log(friend);

  }

}











