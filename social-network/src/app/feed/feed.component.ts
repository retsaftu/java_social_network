import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { UserComment } from '../user';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css', './feed.component.scss']
})
export class FeedComponent implements OnInit {

  posts: any;
  comment_user: UserComment = new UserComment;
  current_user: any;
  constructor() { }

  async ngOnInit() {
    this.current_user = JSON.parse(localStorage.getItem("userInfo") || '{}').user;

    // this.current_user = localStorage.getItem('userInfo');

    const result = (await axios({
      method: 'get',
      url: `http://localhost:3000/api/posts`,
    })).data;
    console.log(result);
    this.posts = result
    console.log(this.current_user);

  }
  async onLike(newPost: any) {
    console.log('like');
    console.log(newPost);

    // if () {

    // }

  }
  onCheckboxChange(event: any, newPost: any) {
    console.log(event.target.checked);
    console.log(newPost);

    if (event.target.checked) {
      for (let i = 0; i < this.posts.length; i++) {
        console.log('++++++++++');

        console.log(this.posts[i]._id);
        console.log('+++++++++');

        if (this.posts[i]._id == newPost) {
          console.log('==========');
          this.posts[i].like = this.posts[i].like + 1;

        }
      }
    } else {
      for (let i = 0; i < this.posts.length; i++) {
        console.log('====================');

        console.log(this.posts[i]._id);
        console.log('====================');

        if (this.posts[i]._id == newPost) {
          console.log('==========');
          this.posts[i].like = this.posts[i].like - 1;

        }
      }
    }
    console.log(this.posts);


  }
  async submit(com: any, post: any) {
    // this.current_user
    console.log(com);
    this.posts
    let comments_json = JSON.parse(JSON.stringify(com))

    // this.posts.push(com);
    comments_json.name = this.current_user.name;
    comments_json.userId = this.current_user._id;
    for (let i = 0; i < this.posts.length; i++) {
      if (this.posts[i]._id == post._id) {
        console.log('==========');
        this.posts[i].comments.push(comments_json)
      }
    }
    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/api/posts/${post._id}`,
      data: comments_json
    })).data;
    // post.comment = [{}]


  }

}
