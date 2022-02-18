import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  constructor() { }
  items = Array.from({ length: 100000 }).map((_, i) => `Item #${i}`);

  ngOnInit(): void {
  }

}
