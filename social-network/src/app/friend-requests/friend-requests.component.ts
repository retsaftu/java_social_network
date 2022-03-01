import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-friend-requests',
  templateUrl: './friend-requests.component.html',
  styleUrls: ['./friend-requests.component.css']
})
export class FriendRequestsComponent implements OnInit {

  friends: any;
  current_user: any;

  constructor() { }

  async ngOnInit() {
    // let userInfo = localStorage.getItem("userInfo");
    this.current_user = JSON.parse(localStorage.getItem("userInfo") || '{}').user;
    //http://localhost:3000/auth/users
    this.friends = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users`,
    })).data;
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

  }
  async delete(friend: any) {
    console.log(friend);

  }

}
