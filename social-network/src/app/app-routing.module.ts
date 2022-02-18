import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './feed/feed.component'
import { FriendsComponent } from './friends/friends.component'
const routes: Routes = [
  { path: "", component: FeedComponent },
  { path: "friends", component: FriendsComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
