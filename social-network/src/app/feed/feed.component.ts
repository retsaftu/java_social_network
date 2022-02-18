import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css', './feed.component.scss']
})
export class FeedComponent implements OnInit {

  posts: any;
  constructor() { }

  async ngOnInit() {
    const result = (await axios({
      method: 'get',
      url: `http://localhost:3000/api/posts`,
    })).data;
    console.log(result);
    this.posts = result

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

}
