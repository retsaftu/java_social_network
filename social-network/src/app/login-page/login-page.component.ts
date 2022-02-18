import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import axios from 'axios';
import { AuthService } from "../services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  user: User = new User;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }
  async submit(user: any) {
    console.log(user);
    // post.comment = [{}]
    // post.like = 0
    // post.userId = ''
    // post.username = ''

    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/auth/login`,
      data: user
    })).data;
    console.log(result);
    this.authService.setUser({ user: result });
    this.router.navigate(['feed']);



  }

}
