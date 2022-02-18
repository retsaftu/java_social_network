import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  friends: any;
  current_user: any;

  constructor() { }

  async ngOnInit() {
    this.current_user = localStorage.getItem('userInfo');
    //http://localhost:3000/auth/users
    this.friends = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users`,
    })).data;
  }
  async add(friend: any) {
    console.log(friend);
    let com: any;
    com.name = this.current_user.name;
    com.userId = this.current_user.userId;

    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/friend/${friend._id}`,
      data: com
    })).data;

  }
  async delete(friend: any) {
    console.log(friend);

  }

}
