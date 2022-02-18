import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }
  isAuthenticated(): Boolean {
    let userInfo = localStorage.getItem('userInfo');
    return (userInfo && JSON.parse(userInfo));
  }

  setUser(userInfo: any) {
    localStorage.setItem('userInfo', JSON.stringify(userInfo));
  }
}
