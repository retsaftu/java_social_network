import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import axios from 'axios';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  user: User = new User;
  constructor() { }

  ngOnInit(): void {
  }
  async submit(user: any) {
    user.friends = [];
    console.log(user);
    // post.comment = [{}]
    // post.like = 0
    // post.userId = ''
    // post.username = ''

    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/auth/register`,
      data: user
    })).data;


  }

}
