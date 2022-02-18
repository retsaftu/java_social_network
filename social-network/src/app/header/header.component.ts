import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuTrigger } from '@angular/material/menu';
import { Post } from '../post';
import axios from 'axios';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @ViewChild('menuTrigger') menuTrigger: MatMenuTrigger;

  constructor(public dialog: MatDialog) { }

  openDialog() {
    const dialogRef = this.dialog.open(DialogFromMenuExampleDialog, { restoreFocus: false });

    // Manually restore focus to the menu trigger since the element that
    // opens the dialog won't be in the DOM any more when the dialog closes.
    dialogRef.afterClosed().subscribe(() => this.menuTrigger.focus());
  }

  ngOnInit(): void {
  }

}
@Component({
  selector: 'dialog-from-menu-dialog',
  templateUrl: 'dialog-from-menu-example-dialog.html',
  styleUrls: ['./header.component.css']
})
export class DialogFromMenuExampleDialog {
  post: Post = new Post;
  // model: any = 'asdf';
  ngOnInit(): void {
  }

  async submit(post: any) {
    console.log(post);
    // post.comment = [{}]
    post.like = 0
    post.userId = ''
    post.username = ''

    const result = (await axios({
      method: 'post',
      url: `http://localhost:3000/api/posts`,
      data: post
    })).data;


  }
}