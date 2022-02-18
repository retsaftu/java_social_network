import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './feed/feed.component'
import { FriendsComponent } from './friends/friends.component'
import { AuthPageComponent } from './auth-page/auth-page.component'
const routes: Routes = [
  { path: "", component: FeedComponent },
  { path: "friends", component: FriendsComponent },
  { path: "auth", component: AuthPageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
