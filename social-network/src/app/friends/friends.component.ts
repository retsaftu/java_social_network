import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  friends: any;
  constructor() { }

  async ngOnInit() {
    //http://localhost:3000/auth/users
    this.friends = (await axios({
      method: 'get',
      url: `http://localhost:3000/auth/users`,
    })).data;
  }
  async add(friend: any) {
    console.log(friend);

  }
  async delete(friend: any) {
    console.log(friend);

  }

}
