import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './feed/feed.component'
import { FriendsComponent } from './friends/friends.component'
import { FriendAddComponent } from './friend-add/friend-add.component'
import { FriendRequestsComponent } from './friend-requests/friend-requests.component'
import { AuthPageComponent } from './auth-page/auth-page.component'
const routes: Routes = [
  { path: "feed", component: FeedComponent },
  { path: "friends", component: FriendsComponent },
  { path: "friend-requests", component: FriendRequestsComponent },
  { path: "friend-add", component: FriendAddComponent },
  { path: "", component: AuthPageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
